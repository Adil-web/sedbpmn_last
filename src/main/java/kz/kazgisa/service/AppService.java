package kz.kazgisa.service;

import kz.kazgisa.data.dto.*;
import kz.kazgisa.data.entity.*;
import kz.kazgisa.data.entity.dict.Subservice;
import kz.kazgisa.data.entity.user.User;
import kz.kazgisa.data.enums.FileCategory;
import kz.kazgisa.data.enums.OrgType;
import kz.kazgisa.data.enums.SearchOperation;
import kz.kazgisa.data.enums.Status;
import kz.kazgisa.data.repositories.*;
import kz.kazgisa.data.repositories.appinfo.*;
import kz.kazgisa.data.repositories.dict.ServiceRepository;
import kz.kazgisa.data.repositories.dict.SubserviceRepository;
import kz.kazgisa.specification.AppSpecification;
import kz.kazgisa.utils.AppUtils;
import kz.kazgisa.utils.CustomDateUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Math.toIntExact;

@Service
public class AppService {
    private final AppRepository appRepository;
    private final AppOrganizationRepository appOrganizationRepository;
    private final OrganizationRepository organizationRepository;
    private final AppFileRepository appFileRepository;
    private final RuntimeService runtimeService;
    private final TaskService taskService;
    private final TaskCustomService taskCustomService;
    private final HistoryCustomService historyCustomService;
    private final AppStatusRepository appStatusRepository;
    private final AppOrgStatusRepository appOrgStatusRepository;
    private final ElectricInfoRepository electricInfoRepository;
    private final GasInfoRepository gasInfoRepository;
    private final HeatInfoRepository heatInfoRepository;
    private final SewerageInfoRepository sewerageInfoRepository;
    private final WaterInfoRepository waterInfoRepository;
    private final ObjectInfoRepository objectInfoRepository;
    private final ServiceRepository serviceRepository;
    private final SubserviceRepository subserviceRepository;
    private final StampService stampService;
    private final FileService fileService;
    private final UserService userService;
    private final RestTemplate restTemplate;
    private final DesignerInfoRepository designerInfoRepository;
    private final AppOrgConnectionRepository appOrgConnectionRepository;
    private final OzoInfoRepository ozoInfoRepository;

    @Value("${project.eqyzmet.url}")
    private String eqyzmetUrl;
    @Value("${project.eqyzmet.secret}")
    private String eqyzmetSecret;

    public AppService(AppRepository appRepository,
                      AppOrganizationRepository appOrganizationRepository,
                      OrganizationRepository organizationRepository,
                      AppFileRepository appFileRepository,
                      RuntimeService runtimeService,
                      TaskService taskService,
                      TaskCustomService taskCustomService,
                      HistoryCustomService historyCustomService,
                      AppStatusRepository appStatusRepository,
                      AppOrgStatusRepository appOrgStatusRepository,
                      ElectricInfoRepository electricInfoRepository,
                      GasInfoRepository gasInfoRepository,
                      HeatInfoRepository heatInfoRepository,
                      SewerageInfoRepository sewerageInfoRepository,
                      WaterInfoRepository waterInfoRepository,
                      ObjectInfoRepository objectInfoRepository,
                      ServiceRepository serviceRepository,
                      SubserviceRepository subserviceRepository,
                      StampService stampService,
                      FileService fileService,
                      UserService userService,
                      RestTemplate restTemplate, DesignerInfoRepository designerInfoRepository, AppOrgConnectionRepository appOrgConnectionRepository, OzoInfoRepository ozoInfoRepository) {
        this.appRepository = appRepository;
        this.appOrganizationRepository = appOrganizationRepository;
        this.organizationRepository = organizationRepository;
        this.appFileRepository = appFileRepository;
        this.runtimeService = runtimeService;
        this.taskService = taskService;
        this.taskCustomService = taskCustomService;
        this.historyCustomService = historyCustomService;
        this.appStatusRepository = appStatusRepository;
        this.appOrgStatusRepository = appOrgStatusRepository;
        this.electricInfoRepository = electricInfoRepository;
        this.gasInfoRepository = gasInfoRepository;
        this.heatInfoRepository = heatInfoRepository;
        this.sewerageInfoRepository = sewerageInfoRepository;
        this.waterInfoRepository = waterInfoRepository;
        this.objectInfoRepository = objectInfoRepository;
        this.serviceRepository = serviceRepository;
        this.subserviceRepository = subserviceRepository;
        this.stampService = stampService;
        this.fileService = fileService;
        this.userService = userService;
        this.restTemplate = restTemplate;
        this.designerInfoRepository = designerInfoRepository;
        this.appOrgConnectionRepository = appOrgConnectionRepository;
        this.ozoInfoRepository = ozoInfoRepository;
    }

    public App save(App userApp) {
        userApp.setCreateDate(new Date());
        userApp.setOpen(false);
        if (userApp.getOzoInfo() != null)
            userApp.setOzoInfo(ozoInfoRepository.save(userApp.getOzoInfo()));

        return appRepository.save(userApp);
    }

    public App update(App userApp) {
        if (userApp.getCurrentStatus() != null)
            userApp.setCurrentStatus(appStatusRepository.save(userApp.getCurrentStatus()));
        if (userApp.getObjectInfo() != null)
            userApp.setObjectInfo(objectInfoRepository.save(userApp.getObjectInfo()));
        if (userApp.getOzoInfo() != null)
            userApp.setOzoInfo(ozoInfoRepository.save(userApp.getOzoInfo()));
        return appRepository.save(userApp);
    }

    public Page<App> getAll(Boolean finished, Boolean open, String control, Pageable pageable) {
            User user = userService.getCurrentUser();
            if(user.getOrganization() != null && user.getOrganization().getOrgType() != null &&
                    user.getOrganization().getOrgType().equals(OrgType.AKIMAT_OKRUG)){
                return appRepository.findByRegionIdAndOpenFalse(user.getOrganization().getRegionId(), pageable);
            }
            List<Subservice> subservices = userService.getCurrentUserSubservices();
        if (control != null) {
            return appRepository.findByControlAndSubserviceInAndArchSignedFalse(control, subservices, pageable)
                    .map(app -> {
                        Task task = taskService.createTaskQuery().processVariableValueEquals("appId", app.getId()).singleResult();
                        if (task != null) {
                            app.setCurrentTaskName(task.getName());
                            app.setCurrentExecutor(task.getAssignee());
                            app.setCurrentOwner(task.getOwner());
                            app.setApproved((Boolean) taskService.getVariable(task.getId(), "approved"));
                        } else {
                            System.out.println("task is null");
                        }
                        return app;
                    });
        }
        if (finished) {
            return appRepository.findBySubserviceInAndArchSignedTrue(subservices, pageable);
        } else if (open) {
            return appRepository.findBySubserviceInAndArchSignedFalseAndOpenTrue(subservices, pageable);
        } else
            return appRepository.findBySubserviceInAndArchSignedFalseAndOpenFalse(subservices, pageable);
    }

    public List addAppFile(List<AppFile> files, Long appId) {
        for (AppFile file : files) {
            file.setApp(appRepository.findById(appId).get());
        }

        return appFileRepository.saveAll(files);
    }


    public List<AppFile> getAppFiles(Long id) {
        return appFileRepository.findByAppId(id);
    }

    public List getAppFiles(Long appId, FileCategory fileCategory) {
        return appFileRepository.findByAppIdAndFileCategory(appId, fileCategory);
    }

    public App getById(long id) {
        return appRepository.findById(id).get();
    }

    public void deleteFile(Long id) {
        appFileRepository.deleteById(id);
    }

    public void deleteAppFiles(List<AppFile> appFiles) {
        appFileRepository.deleteAll(appFiles);
    }

    @Transactional
    public App sendAndStartProcess(Long id) {
        Map<String, Object> info = new HashMap<>();
        Integer intId = id.intValue();
        info.put("appId",  intId);
        App app = getById(id);
        System.out.println("--- appId: " + id);
        System.out.println("--- app.open: " + app.getOpen());
        if (app.getOpen() != null && app.getOpen()) {
            System.out.println("--- app is open");
            return null;
        }
        System.out.println("--- app not open");
        app.setOpen(true);
        appRepository.save(app);
        if(BooleanUtils.isTrue(app.getSubservice().getWorkdaysOnly())) {
            app.setPlanEndDate(calculateEndDateOnlyWorkDays(new Date(), app.getSubservice().getDays()));
        } else {
            app.setPlanEndDate(calculateEndDate(new Date(), app.getSubservice().getDays()));
        }
        info.put("firstName", app.getFirstName());
        info.put("lastName", app.getLastName());
        info.put("secondName", app.getSecondName());
        Integer intRegionId = app.getRegionId().intValue();
        info.put("regionId", intRegionId);
        Integer intRegAppId = app.getAppId() != null ? app.getAppId().intValue() : app.getId().intValue();
        info.put("regAppId", intRegAppId);
        info.put("planEndDate", app.getPlanEndDate());
        Integer intSubserviceId = app.getSubservice().getId().intValue();
        info.put("subserviceId", intSubserviceId);
        info.put("subserviceType", app.getSubservice().getShortNameRu() != null ? app.getSubservice().getShortNameRu() : app.getSubservice().getNameRu());
        info.put("iin", app.getIin());
        info.put("bin",app.getBin());
        info.put("isOrg", app.getOrg());
        info.put("orgName", app.getOrgName());
        info.put("applicantIinBin", app.getApplicantIinBin());
        info.put("applicantName", app.getApplicantName());
        info.put("applicantIsOrg", BooleanUtils.isTrue(app.getApplicantIsOrg()));
        if(app.getObjectInfo() != null) {
            info.put("objectAddress", app.getObjectInfo().getAddress());
            info.put("objectDesigner", app.getObjectInfo().getDesigner());
        }
        appRepository.save(app);
        System.out.println("--- subserviceId: " + app.getSubservice().getId());
        System.out.println("--- serviceId: " + app.getSubservice().getService().getId());
        System.out.println("--- serviceCode: " + app.getSubservice().getService().getCode());
        String procDef = subserviceRepository.findById(app.getSubservice().getId()).get().getCode();
        if (procDef == null) {
            procDef = serviceRepository.findById(app.getSubservice().getService().getId()).get().getCode();
        }
        System.out.println("--- procDef: " + procDef);
        if (app.getSubservice().getId().equals(21L)) {
            runtimeService.startProcessInstanceByKey(procDef, String.valueOf(id), info);
        } else if (app.getSubservice().getService().getId().equals(6L)) {
            runtimeService.startProcessInstanceByKey(procDef, String.valueOf(id), info);
        } else {
            runtimeService.startProcessInstanceByKey(procDef, String.valueOf(id), info);
        }
        return appRepository.findById(id).get();
    }

    public String getStringXml(String data) {
        try {
            XmlData xmlData = new XmlData();
            xmlData.setData(data.replaceAll("<figure[^>]*?>.*?</figure[^>]*?>", ""));
            JAXBContext jaxbContext = JAXBContext.newInstance(XmlData.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(xmlData, sw);
            return sw.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getStringXml(App app) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(App.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(app, sw);
            return sw.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Date calculateEndDate(Date inDate, int days) {
        Date endDate = DateUtils.addDays(inDate, days);

        Calendar cal = Calendar.getInstance();
        cal.setTime(endDate);
        cal.set(Calendar.HOUR_OF_DAY, 22);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date planEndDate = cal.getTime();
        return planEndDate;
    }

    public Date calculateEndDateOnlyWorkDays(Date inDate, int days) {
        int addDays = 200;
        Date date = CustomDateUtils.getDayStart(inDate);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String url = eqyzmetUrl + "/eqyzmet/api/dict/days/period?startDate=" +
                sdf.format(date) + "&endDate=" +
                sdf.format(CustomDateUtils.getDayEnd(DateUtils.addDays(date, addDays)));
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UnusualDay[]> response = restTemplate.getForEntity(url, UnusualDay[].class);

        List<UnusualDay> unusualDays = Arrays.asList(response.getBody());

        int countDays = 1;
        int i = 0;
        while (i < days) {
            Date nextDay = DateUtils.addDays(date, countDays);
            Optional<UnusualDay> unusualNextDay = unusualDays.stream()
                    .filter(day -> nextDay.compareTo(day.getDate()) == 0)
                    .findFirst();
            if (!unusualNextDay.isPresent() && !CustomDateUtils.isWeekend(nextDay)) {
                i++;
            } else if (unusualNextDay.isPresent() && CustomDateUtils.isWeekend(nextDay)) {
                i++;
            }
            countDays++;
        }
        Date endDate = DateUtils.addDays(date, countDays - 1);
        Calendar cal = Calendar.getInstance();
        cal.setTime(endDate);
        cal.set(Calendar.HOUR_OF_DAY, 22);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date planEndDate = cal.getTime();

        Calendar dateCalendar = GregorianCalendar.getInstance();
        dateCalendar.setTime(inDate);
        int hour = dateCalendar.get(Calendar.HOUR_OF_DAY);
        if(hour > 17) {
            planEndDate = DateUtils.addDays(planEndDate, 1);
        }

        return planEndDate;
    }

    public AppFile getFileById(Long id) {
        return appFileRepository.findById(id).get();
    }

    public AppFile getFileByName(String id) {
        return appFileRepository.findFirstByName(id);
    }

    public App addStamp(Long id, App appDto) {
        App app = appRepository.findById(id).get();
        app.setNumeration(appDto.getNumeration());
        app.setNumerationDate(appDto.getNumerationDate());
        app = appRepository.save(app);
        String numeration = app.getNumeration();
        Date numerationDate = app.getNumerationDate();
        if (BooleanUtils.isTrue(app.getApproved())) {
            try {
                stampService.setStamp(app);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if ((numeration != null && !numeration.equals("")) || numerationDate != null) {
                stampService.setEskizApprovingStamp(app);
            }
        } else {
            if ((numeration != null && !numeration.equals("")) || numerationDate != null) {
                stampService.setEskizRejectingStamp(app);
            }
        }
        return app;
    }

    public AppFile getAppFileByCategory(Long id, FileCategory resultFile) {
        return appFileRepository.findFirstByAppIdAndFileCategory(id, resultFile);
    }

    public void saveAppFile(AppFile file) {
        appFileRepository.save(file);
    }

    public App saveExt(App userApp) {
        userApp.setAppId(userApp.getId());
        userApp.setId(null);
        userApp.setOpen(false);
        userApp.setElectricInfo(electricInfoRepository.save(userApp.getElectricInfo()));
        userApp.setGasInfo(gasInfoRepository.save(userApp.getGasInfo()));
        userApp.setHeatInfo(heatInfoRepository.save(userApp.getHeatInfo()));
        userApp.setSewerageInfo(sewerageInfoRepository.save(userApp.getSewerageInfo()));
        userApp.setWaterInfo(waterInfoRepository.save(userApp.getWaterInfo()));
        userApp.setObjectInfo(objectInfoRepository.save(userApp.getObjectInfo()));
        userApp.setDesignerInfo(designerInfoRepository.save(userApp.getDesignerInfo()));
        if (userApp.getCurrentStatus() != null)
            userApp.setCurrentStatus(appStatusRepository.save(userApp.getCurrentStatus()));
        return appRepository.save(userApp);
    }

    public AppFile addAppFileExt(AppFile file, Long id, byte[] fileBytes) {
        App app = appRepository.findByAppId(id);
        FileDto dto = fileService.uploadFile(fileBytes, file.getName() != null ? file.getName() : "", file.getFileCategory());
        file.setObjectId(dto.uid);
        file.setSize(dto.fileSize);
        file.setApp(app);
        return appFileRepository.save(file);
    }

    public List<AppDutyDto> getByCadastreNumber(String cadastreNumber, Long serviceId) {
        List<AppDutyDto> appDutyDtoList = new ArrayList<>();
        List<AppDutyView> appDutyViews = appRepository.findDutyApps(cadastreNumber, serviceId);
        for (AppDutyView appDutyView : appDutyViews) {
            AppDutyDto appDutyDto = new AppDutyDto();
            appDutyDto.setId(appDutyView.getId());
            appDutyDto.setFirstName(appDutyView.getFirstName());
            appDutyDto.setLastName(appDutyView.getLastName());
            appDutyDto.setSecondName(appDutyView.getSecondName());
            appDutyDto.setOrgName(appDutyView.getOrgName());
            appDutyDto.setCadastreNumber(appDutyView.getCadastreNumber());
            appDutyDto.setGeometry(appDutyView.getGeometry());
            appDutyDto.setAddress(appDutyView.getAddress());
            appDutyDto.setPhone(appDutyView.getPhone());
            appDutyDto.setIin(appDutyView.getIin());
            appDutyDto.setBin(appDutyView.getBin());
            appDutyDto.setRegDate(appDutyView.getCreateDate());
            appDutyDto.setFinishDate(appDutyView.getFactEndDate());
            if (appDutyView.getApproved() != null && appDutyView.getApproved() == true) {
                appDutyDto.setStatus(Status.APPROVED);
            } else {
                appDutyDto.setStatus(Status.REJECTED);
            }
            appDutyDto.setSubserviceShortNameRu(appDutyView.getSubserviceShortNameRu());

            List<AppFile> appFiles = appFileRepository.findByAppId(appDutyDto.getId());
            List<FileDutyDto> fileDutyDtoList = new ArrayList<>();
            for (AppFile appFile : appFiles) {

                FileDutyDto fileDutyDto = new FileDutyDto();
                BeanUtils.copyProperties(appFile, fileDutyDto);
                fileDutyDto.setAppId(appFile.getApp().getId());
                fileDutyDto.setUrl("http://bp.eatyrau.kz/fileserver/api/files/download/" + appFile.getObjectId());

                if (appFile.getFileCategory().equals(FileCategory.RESULT_ATTACHMENT)) {
                    appDutyDto.setResultFile(fileDutyDto);
                } else {
                    fileDutyDtoList.add(fileDutyDto);
                }
            }
            appDutyDto.setFiles(fileDutyDtoList);

            appDutyDtoList.add(appDutyDto);
        }
        return appDutyDtoList;
    }

    public List<AppCommunalDto> getAppOrgs(Long appId) {
        List<AppOrganization> appOrganizations = appOrganizationRepository.findByAppId(appId);
        List<Organization> organizations = organizationRepository.findByRegionIdAndCommunalNotNullOrderById(1L);
        List<AppCommunalDto> appCommunalDtos = new ArrayList<>();
        for (Organization org : organizations) {
            AppCommunalDto appCommunalDto = new AppCommunalDto();
            appCommunalDto.setId(org.getId());
            appCommunalDto.setName(org.getName());
            Optional<AppOrganization> appOrganization = appOrganizations.stream()
                    .filter(a -> a.getOrganization() != null && a.getOrganization().getId() == org.getId()).findFirst();
            if (appOrganization.isPresent()) {
                appCommunalDto.setSent(true);
                AppOrganization appOrg = appOrganization.get();
                appCommunalDto.setSigned(appOrg.getSigned());
                List<AppOrgStatus> appOrgStatuses = appOrgStatusRepository.findByAppOrganizationId(appOrg.getId());

                appOrgStatuses.sort(new Comparator<AppOrgStatus>() {
                    @Override
                    public int compare(AppOrgStatus o1, AppOrgStatus o2) {
                        return toIntExact(o1.getDate().getTime() - o2.getDate().getTime());
                    }
                });
//                appCommunalDto.setAppOrgFiles(appOrg.getAppOrgFiles());
                appCommunalDto.setConnections(appOrg.getConnections());
                appCommunalDto.setAppOrgId(appOrg.getId());
                appCommunalDto.setHasTechCondition(appOrg.getTechCondition() != null);

                Optional<AppOrgStatus> regStatus = appOrgStatuses.stream().filter(s -> s.getStatus() == Status.APPLIED).findFirst();
                if (regStatus.isPresent()) {
                    appCommunalDto.setSendDate(regStatus.get().getDate());
                }
                Optional<AppOrgStatus> responseStatus = appOrgStatuses.stream()
                        .filter(s -> (Status.APPROVED == s.getStatus() || Status.REJECTED == s.getStatus())).findFirst();
                if (responseStatus.isPresent() && appOrg.getSigned() != null && appOrg.getSigned()) {
                    appCommunalDto.setResponseDate(responseStatus.get().getDate());
                    AppOrgStatus lastStatus = appOrgStatuses.get(appOrgStatuses.size() - 1);
                    appCommunalDto.setStatus(lastStatus.getStatus());
                } else {
                    appCommunalDto.setStatus(Status.REGISTERED);
                }
            } else {
                appCommunalDto.setSent(false);
            }
            appCommunalDtos.add(appCommunalDto);
        }
        return appCommunalDtos;
    }

    @Transactional
    public void sendAppToOrgs(App app, List<Organization> orgs) {
        for (Organization org : orgs) {
            createAppOrgWithStatuses(app, org.getId());
            Organization model = organizationRepository.findById(org.getId()).get();
            if(model.getCommunal() != null) {
                Subservice subservice = subserviceRepository.findFirstByCode(model.getCommunal().getCode());
                startOrgProcess(app, subservice);
            }
        }
    }

    public App startOrgProcess(App app, Subservice subservice) {
        Map<String, Object> info = new HashMap<>();
        String procDef = subservice.getCode();
        Integer intId = app.getId().intValue();
        info.put("appId", intId);
        info.put("firstName", app.getFirstName());
        info.put("lastName", app.getLastName());
        info.put("secondName", app.getSecondName());
        Integer intRegAppId = app.getAppId() != null ? app.getAppId().intValue() : app.getId().intValue();
        info.put("regAppId", intRegAppId);
        info.put("planEndDate", app.getPlanEndDate());
        Integer intSubserviceId = app.getSubservice().getId().intValue();
        info.put("subserviceId", intSubserviceId);
        Integer intRegionId = app.getRegionId().intValue();
        info.put("regionId",intRegionId);
        info.put("iin", app.getIin());
        info.put("bin",app.getBin());
        info.put("isOrg", app.getOrg());
        info.put("orgName", app.getOrgName());
        info.put("subserviceType", subservice.getShortNameRu() != null ? subservice.getShortNameRu() : subservice.getNameRu());
        runtimeService.startProcessInstanceByKey(procDef, String.valueOf(app.getId()), info);
        return app;
    }

    public void createAppOrgWithStatuses(App app, Long orgId) {
        AppOrganization dbAppOrg = appOrganizationRepository.findFirstByAppIdAndOrganizationId(app.getId(), orgId);
        if (dbAppOrg == null) {
            AppOrganization appOrganization = new AppOrganization();
            appOrganization.setApp(app);
            Organization organization = organizationRepository.findById(orgId).get();
            appOrganization.setOrganization(organization);
            AppOrganization createdAppOrg = appOrganizationRepository.save(appOrganization);

            AppOrgStatus appOrgStatus = AppUtils.createAppOrgStatus(createdAppOrg, Status.APPLIED, null, null);
            appOrgStatusRepository.save(appOrgStatus);
            createdAppOrg.setCurrentStatus(appOrgStatus);
            appOrganizationRepository.save(createdAppOrg);
        }
    }


    public AppOrganization getAppOrgByAppIdAndOrgId(Long id, Long orgId) {
        return appOrganizationRepository.findFirstByAppIdAndOrganizationId(id, orgId);
    }

    public void sendAppToEatyrau(App app) {
        try {
            String url = eqyzmetUrl + "/eqyzmet/api/app/ext?secret=" + eqyzmetSecret;
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<App> response = restTemplate.postForEntity(url, app, App.class);

            List<AppFile> appFiles = getAppFiles(app.getId());
            for (AppFile appFile : appFiles) {
                appFile.setId(null);
                try {
                    byte[] file = fileService.downloadFile(appFile.getObjectId());
                    if (file != null) {
                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
                        MultiValueMap<String, Object> body
                                = new LinkedMultiValueMap<>();
                        ByteArrayResource resource = new ByteArrayResource(file) {
                            @Override
                            public String getFilename() {
                                return appFile.getName();
                            }
                        };
                        body.add("fileCategory", appFile.getFileCategory().name());
                        body.add("objectId", appFile.getObjectId());
                        body.add("contentType", appFile.getContentType());
                        body.add("fileName", appFile.getName());
                        body.add("size", appFile.getSize());
                        body.add("uploadDate", appFile.getUploadDate());
                        body.add("file", resource);
                        HttpEntity<MultiValueMap<String, Object>> requestEntity
                                = new HttpEntity<>(body, headers);
                        String serverUrl = eqyzmetUrl + "/eqyzmet/api/app/ext/" + app.getId() + "/files?secret=" + eqyzmetSecret;
                        ResponseEntity<AppFile> fileResponse = restTemplate.postForEntity(serverUrl, requestEntity, AppFile.class);
                        System.out.println("----- file status: " + fileResponse.getStatusCode() + "; " + fileResponse.getStatusCodeValue());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void appOrgSign(Long id, Long orgId, String xml, boolean isCommunal, Principal principal) {
        AppOrganization appOrganization = appOrganizationRepository.findFirstByAppIdAndOrganizationId(id, orgId);
        appOrganization.setSigned(true);
        appOrganization.setSignedXml(xml);
        appOrganization.setSignedDate(new Date());
        appOrganizationRepository.save(appOrganization);

        App app = getById(id);
        setOrgsChecked(app, isCommunal);
    }

    public void setOrgsChecked(App app, boolean onlyCommunals) {
        List<AppOrganization> appOrgs = appOrganizationRepository.findByAppId(app.getId());
        if (appOrgs.size() > 0) {
            boolean complete = checkOrgsComplete(appOrgs, onlyCommunals);
            if (complete) {
                app.setCommunalsChecked(true);
                app.setCommunalsCheckedDate(new Date());
            }
        }
    }

    public boolean checkOrgsComplete(List<AppOrganization> appOrgs, boolean onlyCommunals) {
        boolean complete = true;
        for (AppOrganization appOrg : appOrgs) {
            if (onlyCommunals) {
                if (appOrg.getOrganization() != null && appOrg.getOrganization().getCommunal() == null) {
                    continue;
                }
            }
            if (appOrg.getSigned()==null || !appOrg.getSigned()) {
                complete = false;
                break;
            }
        }
        return complete;
    }

    public Page<App> searchWithFilter(List<SearchCriteriaDto> list, Pageable pageable) {
        User user = userService.getCurrentUser();
        if(user.getOrganization() != null && user.getOrganization().getOrgType() != null &&
                user.getOrganization().getOrgType().equals(OrgType.AKIMAT_OKRUG)){
            appRepository.findByRegionIdAndOpenFalse(user.getOrganization().getRegionId(), pageable);
            SearchCriteriaDto dto = new SearchCriteriaDto("regionId",user.getOrganization().getRegionId(), SearchOperation.EQUAL);
            list.add(dto);
            boolean openFound=false;
            for (SearchCriteriaDto searchCriteriaDto : list) {
                if(searchCriteriaDto.getKey().equals("open")) {
                    searchCriteriaDto.setValue("true");
                    openFound = true;
                }
            }
            if(!openFound) {
                SearchCriteriaDto dto2 = new SearchCriteriaDto("open",false, SearchOperation.EQUAL);
                list.add(dto2);
            }
            AppSpecification appSpecification = new AppSpecification(list);
            return appRepository.findAll(appSpecification,pageable);
        }
        boolean subserviceFilterFound = list.stream().anyMatch(s->s.getKey().equals("subservice"));
        if(!subserviceFilterFound) {
            SearchCriteriaDto dto3 = new SearchCriteriaDto("subservice",userService.getCurrentUserSubservices(),SearchOperation.IN);
            list.add(dto3);
        }

        AppSpecification appSpecification = new AppSpecification(list);
        Page<App> appsPage = appRepository.findAll(appSpecification,pageable);

//        for(App app : appsPage.getContent()) {
//            app.setFactEndDate(historyCustomService.getFinishDate(app.getId()));
//        }

        return appsPage;
    }

    public List<AppOrgConnection> getConnections(Long appOrgId) {
        return appOrgConnectionRepository.findByAppOrgId(appOrgId);
    }

    public void addConnection(Long appOrgId, List<AppOrgConnection> connections) {
        appOrgConnectionRepository.saveAll(connections);
    }

    public void deleteConnection(Long id) {
        appOrgConnectionRepository.deleteById(id);
    }

    public Long[] getAppIdsWithoutEskiz() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Long[]> ids = restTemplate.getForEntity(eqyzmetUrl + "/eqyzmet/api/app/ext/withoutEskiz?secret=" + eqyzmetSecret, Long[].class);
        return ids.getBody();
    }

    public String getIp() {
        String ip = null;
        try
        {
            URL url = new URL("http://bot.whatismyipaddress.com");
            BufferedReader sc =
                    new BufferedReader(new InputStreamReader(url.openStream()));
            ip = sc.readLine().trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ip;
    }

    public void recalculatePlanEndDate(Long appId, Integer addDays) {
        App app = getById(appId);
        Integer days = app.getSubservice().getDays();
        if(addDays != null) {
            days += addDays;
        }
        Boolean workdaysOnly = app.getSubservice().getWorkdaysOnly();
        Date planEndDate;
        if(workdaysOnly != null && workdaysOnly) {
            planEndDate = calculateEndDateOnlyWorkDays(app.getSignedDate(), days);
        } else {
            planEndDate = calculateEndDate(app.getSignedDate(), days);
        }
        app.setPlanEndDate(planEndDate);
        appRepository.save(app);

        TaskDto taskDto = taskCustomService.getTaskByBusinessKey(appId);
        taskDto.getContent().put("planEndDate", planEndDate);
        taskCustomService.updateTask(taskDto.getId(), taskDto);


        String url = eqyzmetUrl + "/eqyzmet/api/app/ext/" + appId + "/setPlanEndDate?secret=" + eqyzmetSecret;
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Date> body = new HashMap<>();
        body.put("date", planEndDate);
        try {
            ResponseEntity<Long> appResponse = restTemplate.postForEntity(url, body, Long.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPlanEndDate(Long appId, Date date) {
        TaskDto taskDto = taskCustomService.getTaskByBusinessKey(appId);
        taskDto.getContent().put("planEndDate", date);
        taskCustomService.updateTask(taskDto.getId(), taskDto);

        String url = eqyzmetUrl + "/eqyzmet/api/app/ext/" + appId + "/setPlanEndDate?secret=" + eqyzmetSecret;
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Date> body = new HashMap<>();
        body.put("date", date);
        try {
            ResponseEntity<Long> appResponse = restTemplate.postForEntity(url, body, Long.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
