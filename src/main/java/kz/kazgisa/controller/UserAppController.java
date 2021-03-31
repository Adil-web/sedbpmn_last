package kz.kazgisa.controller;

import kz.kazgisa.data.dto.*;
import kz.kazgisa.data.entity.*;
import kz.kazgisa.data.entity.dict.Subservice;
import kz.kazgisa.data.entity.user.User;
import kz.kazgisa.data.enums.FileCategory;
import kz.kazgisa.data.enums.Status;
import kz.kazgisa.data.repositories.AppOrgStatusRepository;
import kz.kazgisa.data.repositories.AppOrganizationRepository;
import kz.kazgisa.data.repositories.AppRepository;
import kz.kazgisa.data.repositories.SignUserRepository;
import kz.kazgisa.data.repositories.dict.SubserviceRepository;
import kz.kazgisa.mapper.TaskMapper;
import kz.kazgisa.service.*;
import kz.kazgisa.utils.AppUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("userapp")
public class UserAppController {
    Logger log = LoggerFactory.getLogger(UserAppController.class);

    private final AppService appService;
    private final PdfGenerateService pdfGenerateService;
    private final TaskService taskService;
    private final SignService signService;
    private final FileService fileService;
    private final StampService stampService;
    private final RuntimeService runtimeService;
    private final AgreementDocumentService agreementDocumentService;
    private final AppOrganizationRepository appOrganizationRepository;
    private final SubserviceRepository subserviceRepository;
    private final SignUserRepository signUserRepository;
    private final UserService userService;
    private final AppOrgStatusRepository appOrgStatusRepository;
    private final ResultSendEqyzmetService resultSendEqyzmetService;
    private final AppRepository appRepository;
    private final TaskCustomService taskCustomService;

    public UserAppController(AppService appService,
                             PdfGenerateService pdfGenerateService,
                             TaskService taskService,
                             SignService signService,
                             FileService fileService,
                             StampService stampService,
                             RuntimeService runtimeService,
                             AgreementDocumentService agreementDocumentService,
                             AppOrganizationRepository appOrganizationRepository,
                             SubserviceRepository subserviceRepository,
                             SignUserRepository signUserRepository,
                             UserService userService,
                             AppOrgStatusRepository appOrgStatusRepository,
                             ResultSendEqyzmetService resultSendEqyzmetService,
                             AppRepository appRepository,
                             TaskCustomService taskCustomService) {
        this.appService = appService;
        this.pdfGenerateService = pdfGenerateService;
        this.taskService = taskService;
        this.signService = signService;
        this.fileService = fileService;
        this.stampService = stampService;
        this.runtimeService = runtimeService;
        this.agreementDocumentService = agreementDocumentService;
        this.appOrganizationRepository = appOrganizationRepository;
        this.subserviceRepository = subserviceRepository;
        this.signUserRepository = signUserRepository;
        this.userService = userService;
        this.appOrgStatusRepository = appOrgStatusRepository;
        this.resultSendEqyzmetService = resultSendEqyzmetService;
        this.appRepository = appRepository;
        this.taskCustomService = taskCustomService;
    }

    @PostMapping
    public ResponseEntity saveApp(@RequestBody App userApp) {
        return ResponseEntity.ok(appService.save(userApp));
    }

    @PostMapping("ext")
    public ResponseEntity saveAppExt(@RequestBody App userApp) {
        App app = appService.saveExt(userApp);
        appService.sendAndStartProcess(app.getId());
        return ResponseEntity.ok(app);
    }

    @GetMapping(value = "ext", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity get(@RequestParam(required = false) String cadastreNumber,
                              @RequestParam(required = false) Long serviceId,
                              @RequestParam(required = false) Long appId) {
        if (appId != null)
            return ResponseEntity.ok(appService.getById(appId));
        return ResponseEntity.ok(appService.getByCadastreNumber(cadastreNumber, serviceId));
    }

    @PostMapping("ext/{id}/files")
    public ResponseEntity<?> handleFileUpload(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "fileName", required = false) String fileName,
            @RequestParam(value = "fileCategory", required = false) FileCategory fileCategory,
            @RequestParam(value = "contentType", required = false) String contentType) throws IOException {
        AppFile appFile = new AppFile();
        appFile.setFileCategory(fileCategory);
        appFile.setName(fileName);
        appFile.setContentType(contentType);
        return ResponseEntity.ok(appService.addAppFileExt(appFile, id, file.getBytes()));
    }

    @GetMapping("ext/{id}/files")
    public ResponseEntity<?> getExtFiles(@PathVariable Long id, @RequestParam String apiToken) {
        /*if(!apiToken.equals(dejurkaSecret))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();*/
        return ResponseEntity.ok(appService.getAppFiles(id));
    }


    @PostMapping("/{id}/files")
    public ResponseEntity<?> addFiles(@RequestBody List<AppFile> files,
                                      @PathVariable Long id) {

        return ResponseEntity.ok(appService.addAppFile(files, id));
    }

    @GetMapping("/{id}/files")
    public ResponseEntity<?> getFiles(@PathVariable Long id) {

        return ResponseEntity.ok(appService.getAppFiles(id));
    }

    @DeleteMapping("{id}/files/{fileId}")
    public ResponseEntity deleteFile(@PathVariable Long id, @PathVariable Long fileId) {
        appService.deleteFile(fileId);
        return ResponseEntity.ok(new AppFile());
    }


    @PostMapping("/{id}/send")
    public ResponseEntity<?> send(@PathVariable Long id) {
        return ResponseEntity.ok(appService.sendAndStartProcess(id));
    }


    @GetMapping
    public ResponseEntity getAppList(@RequestParam(defaultValue = "false") Boolean signed,
                                     @RequestParam(defaultValue = "true") Boolean open,
                                     @RequestParam(required = false) String control,
                                     Pageable pageable) {
        return ResponseEntity.ok(appService.getAll(signed, open, control, pageable));
    }

    @PostMapping("filter")
    public ResponseEntity getAppList(@RequestBody List<SearchCriteriaDto> list,
                                     Pageable pageable) {

        return ResponseEntity.ok(appService.searchWithFilter(list, pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity getAppById(@PathVariable Long id) {
        return ResponseEntity.ok(appService.getById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity updateApp(@PathVariable Long id, @RequestBody App app) {
        app.setId(id);
        return ResponseEntity.ok(appService.update(app));
    }

    @PostMapping("/{id}/stamp")
    public ResponseEntity<?> addStamp(@PathVariable Long id, @RequestBody App app) {
        return ResponseEntity.ok(appService.addStamp(id, app));
    }

    @PostMapping("/{id}/xml")
    public ResponseEntity<?> getArchXml(@PathVariable Long id, @RequestBody XmlData data) {
        App app = appService.getById(id);
        String xml;
        xml = appService.getStringXml(data.getData());
        Map<String, String> map = new HashMap<>();
        map.put("xml", xml);
        return ResponseEntity.ok(map);
    }

    @PostMapping("/{id}/user/xml")
    public ResponseEntity<?> getUserXml(@PathVariable Long id) {
        App app = appService.getById(id);
        String xml;
        xml = appService.getStringXml(app);
        Map<String, String> map = new HashMap<>();
        map.put("xml", xml);
        return ResponseEntity.ok(map);
    }

    @PostMapping("/{id}/user/sign")
    public ResponseEntity<?> signAppUserId(@PathVariable Long id, @RequestBody Map<String, String> map, Principal principal) throws RuntimeException {
        String xml = map.get("xml");
        SignResponse validateSignature = signService.validateSignature(xml);
        if (validateSignature.getSigned() == true) {
            App app = appService.getById(id);
            app.setSigned(true);
            app.setSignedXml(xml);
            app.setSignedDate(new Date());
//            appService.save(app);
            App createdApp = appService.sendAndStartProcess(id);
            if(createdApp == null) {
                throw new RuntimeException("app-already-sent");
            }

            if (app.getSubservice().getId() == 25L || app.getSubservice().getId() == 75L) {
                pdfGenerateService.saveApplicationPdfFile(id);
            }

            appService.sendAppToEatyrau(app);
        }
        return ResponseEntity.ok(validateSignature);
    }

    @PostMapping("/{id}/sign")
    public ResponseEntity<?> signAppId(@PathVariable Long id, @RequestBody Map<String, String> map, Principal principal) {
        String xml = map.get("xml");
        User user = userService.getCurrentUser();
        App app = appService.getById(id);
        SignResponse validateSignature = signService.validateSignature(xml);
        if (validateSignature.getSigned() == true) {
//            String serialNumber = signService.getDsInfoProperty(validateSignature.getSubjectDn(), "SERIALNUMBER");
//            String iin = serialNumber.substring(3);
//            if(!iin.equals(user.getIin())) {
//                return ResponseEntity
//                        .status(HttpStatus.FORBIDDEN)
//                        .body("ЭЦП не принадлежит текущему пользователю");
//            }
//
//            List<SignUser> signUsers = signUserRepository.findBySubserviceIdAndRegionId(app.getSubservice().getId(), app.getRegionId());
//            if(signUsers != null && signUsers.size() > 0) {
//                SignUser signUser = signUsers.stream()
//                        .filter(s -> iin.equals(s.getUser().getIin())).findFirst().orElse(null);
//                if(signUser == null) {
//                    return ResponseEntity
//                            .status(HttpStatus.FORBIDDEN)
//                            .body("К сожалению у вас нет прав для подписания этой заявки");
//                }
//            }

            app.setArchSigned(true);
            app.setArchSignedXml(xml);
            app.setArchSignedDate(new Date());
            if (user != null) {
                app.setArchSignedUserId(user.getId());
            }
            String ip = appService.getIp();
            if(ip != null) {
                app.setArchSignedUserIp(ip);
            }
            appService.update(app);
        }
        try {
            AppFile file = appService.getAppFileByCategory(id, FileCategory.RESULT_FILE);
            if (file == null) {
                String code = app.getSubservice().getCode() != null ? app.getSubservice().getCode() :
                        app.getSubservice().getService().getCode();
                ProcessInstance instance;
                if (code != null) {
                    System.out.println("code: " + code);
                    instance = runtimeService.createProcessInstanceQuery()
                            .processDefinitionKey(code)
                            .processInstanceBusinessKey(String.valueOf(app.getId())).active().singleResult();
                } else {
                    System.out.println("code is null");
                    instance = runtimeService.createProcessInstanceQuery()
                            .processInstanceBusinessKey(String.valueOf(app.getId())).singleResult();
                }
                String message = (String) runtimeService.getVariable(instance.getId(), "message");
                Boolean approved = (Boolean) runtimeService.getVariable(instance.getId(), "approved");
                log.info("appId: " + app.getId());
                log.info("message signed: ");
                log.info("decision: " + approved);
                String strTempId = map.get("templateId");
                Long templateId = null;
                if (strTempId != null && !strTempId.isEmpty())
                    templateId = Long.valueOf(strTempId);
                byte[] pdf = pdfGenerateService.generateEskizPdf(id, message, xml, templateId);
                app.setApproved(approved);
                appService.update(app);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String fileName = app.getId() + "_" + dateFormat.format(app.getSignedDate() != null ? app.getSignedDate() : new Date()) + ".pdf";
                FileDto fileDto = fileService.uploadFile(pdf, fileName, FileCategory.RESULT_FILE);
                file = new AppFile();
                file.setName(fileDto.fileName);
                file.setObjectId(fileDto.uid);
                file.setContentType(fileDto.contentType);
                file.setSize(fileDto.fileSize);
                file.setApp(app);
                file.setFileCategory(FileCategory.RESULT_FILE);
                appService.saveAppFile(file);
                //taskService.complete(task.getId());
            }
            return ResponseEntity.ok(validateSignature);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/preview/uzp")
    public ResponseEntity<?> previewUzpFile(@PathVariable Long id) throws UnsupportedEncodingException {
        App app = appService.getById(id);
        if (app == null) {
            return null;
        }
        String html = pdfGenerateService.templateUzp(id);
        byte[] pdf = pdfGenerateService.generateUzpPdf(id, html, app.getSignedXml(), null);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf;charset=UTF-8"));
        headers.setContentLength(pdf.length);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=preview-"+ UUID.randomUUID().toString().replaceAll("-","") +".pdf");
        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);

    }

    @PostMapping("/{id}/save/uzp")
    public ResponseEntity<?> saveUzpFile(@PathVariable Long id) throws UnsupportedEncodingException {
        App app = appService.getById(id);
        if (app == null) {
            return null;
        }
        String html = pdfGenerateService.templateUzp(id);
        byte[] pdf = pdfGenerateService.generateUzpPdf(id, html, app.getSignedXml(), null);
        FileDto fileDto = fileService.uploadFile(pdf, "Заявление.pdf", FileCategory.APPLICATION_PDF);
        AppFile file = new AppFile();
        file.setName(fileDto.fileName);
        file.setObjectId(fileDto.uid);
        file.setContentType(fileDto.contentType);
        file.setSize(fileDto.fileSize);
        file.setApp(app);
        file.setFileCategory(FileCategory.APPLICATION_PDF);

        List<AppFile> files = appService.getAppFiles(app.getId(), FileCategory.APPLICATION_PDF);
        appService.deleteAppFiles(files);

        appService.saveAppFile(file);

        /*Map <String, Object> taskFile = new HashMap<>();
        taskFile.put("uid", fileDto.getUid());
        taskFile.put("fileName", fileDto.getFileName());
        taskFile.put("size", fileDto.getFileSize());
        taskFile.put("fileCategory", "internal");


        TaskQuery taskQuery = taskService.createTaskQuery();
        Task task = taskQuery.taskId(taskId).singleResult();
        TaskDto dto = TaskMapper.mapToDto(task);

        Map<String, Object> taskVars = taskService.getVariables(dto.getId());
        List<Map> internalFiles = (List<Map>) taskVars.get("internalFiles");
        internalFiles.add(taskFile);
        taskVars.put("internalFiles", internalFiles);

        dto.setContent(taskVars);
        taskCustomService.updateTask(dto.getId(), dto);
        return new ResponseEntity<>(taskCustomService.getUserTaskById(taskId), HttpStatus.OK);*/
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/download/uzp")
    public ResponseEntity<?> downloadUzpPdf(@PathVariable Long id) {
        App app = appService.getById(id);
        if (app == null) {
            return null;
        }
        AppFile appFile = appService.getAppFileByCategory(id, FileCategory.APPLICATION_PDF);
        byte [] file = new byte[0];
        if(appFile != null) {
            String objectId = appFile.getObjectId();
            if (!objectId.isEmpty()) {
            file = fileService.downloadFile(objectId);
            }
        } else {
            return null;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf;charset=UTF-8"));
        headers.setContentLength(file.length);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=preview-"+ UUID.randomUUID().toString().replaceAll("-","") +".pdf");
        return new ResponseEntity<>(file, headers, HttpStatus.OK);
    }

    @PostMapping("/{id}/communal/sign/{subserviceId}")
    public ResponseEntity<?> signAppOrgCommunal(@PathVariable Long id,
                                                @PathVariable Long subserviceId,
                                                @RequestBody Map<String, String> map,
                                                Principal principal) {
        String xml = map.get("xml");
        SignResponse validateSignature = signService.validateSignature(xml);
        if (validateSignature.getSigned() == true) {
            Subservice subservice = subserviceRepository.findById(subserviceId).get();
            FileCategory fileCategory = null;
            switch (subservice.getCode()) {
                case "tuJaryk":
                    fileCategory = FileCategory.TECH_CONDITION_JARYK;
                    break;
                case "tuSuArnasy":
                    fileCategory = FileCategory.TECH_CONDITION_SUARNASY;
                    break;
                case "tuTeploSeti":
                    fileCategory = FileCategory.TECH_CONDITION_TEPLOSETI;
                    break;
                default:
                    break;
            }
            AppFile file = appService.getAppFileByCategory(id, fileCategory);
            if (file == null) {
                AppOrganization appOrg = appOrganizationRepository.findFirstByAppIdAndOrganizationCommunalCode(id, subservice.getCode());
                appOrg.setSigned(true);
                appOrg.setSignedXml(xml);
                appOrg.setSignedDate(new Date());

                App app = appOrg.getApp();
                ProcessInstance instance = runtimeService.createProcessInstanceQuery()
                        .processInstanceBusinessKey(String.valueOf(app.getId()))
                        .processDefinitionKey(subservice.getCode())
                        .singleResult();
                String message = (String) runtimeService.getVariable(instance.getId(), "message");

                int fontSize = 14;
                if (appOrg.getTechConditionFontSize() != null) {
                    fontSize = appOrg.getTechConditionFontSize();
                }
                byte[] pdf = pdfGenerateService.generateByteArrayFile(app, appOrg.getOrganization().getId(), message, true, "communal", app.getMapImage(), fontSize, principal);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String fileName = app.getId() + "_" + dateFormat.format(app.getSignedDate() != null ? app.getSignedDate() : new Date()) + ".pdf";
                FileDto fileDto = fileService.uploadFile(pdf, fileName, fileCategory);
                file = new AppFile();
                file.setName(fileDto.fileName);
                file.setObjectId(fileDto.uid);
                file.setContentType(fileDto.contentType);
                file.setSize(fileDto.fileSize);
                file.setApp(app);
                file.setFileCategory(fileCategory);
                User user = userService.getCurrentUser();
                AppOrgStatus appOrgStatus = AppUtils.createAppOrgStatus(appOrg, Status.APPROVED, user.getId(), null);
                appOrgStatusRepository.save(appOrgStatus);
                appOrg.setCurrentStatus(appOrgStatus);
                appOrg = appOrganizationRepository.save(appOrg);
                appService.saveAppFile(file);
                appOrganizationRepository.save(appOrg);
                appService.appOrgSign(id, appOrg.getOrganization().getId(), xml, true, principal);
            }
        }
        return ResponseEntity.ok(validateSignature);
    }

    @GetMapping("/{id}/preview")
    public ResponseEntity<?> previewApp(@PathVariable Long id,
                                        @RequestParam(required = false) Long templateId,
                                        @RequestParam(required = false) Long subserviceId,
                                        Principal principal) throws UnsupportedEncodingException {
        ProcessInstance instance = null;
        if (subserviceId == null)
            instance = runtimeService.createProcessInstanceQuery()
                    .processInstanceBusinessKey(String.valueOf(id)).singleResult();
        else {
            Subservice subservice = subserviceRepository.findById(subserviceId).get();
            instance = runtimeService.createProcessInstanceQuery()
                    .processDefinitionKey(subservice.getCode())
                    .processInstanceBusinessKey(String.valueOf(id)).singleResult();
        }
        String message = (String) runtimeService.getVariable(instance.getId(), "message");
        byte[] pdf = pdfGenerateService.generateEskizPdf(id, message, null, templateId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf;charset=UTF-8"));
        headers.setContentLength(pdf.length);
        String encoded = URLEncoder.encode(String.valueOf(id), "utf-8").replace("+", "%20");
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"" + encoded + ".pdf\"");
        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);

    }

    @GetMapping("/{id}/integration")
    public ResponseEntity<?> sendIntegration(@PathVariable Long id) throws Exception {

        return ResponseEntity.ok(stampService.executeMe(id));
    }

    @GetMapping("/{id}/orgs")
    public ResponseEntity<?> getAppOrgs(@PathVariable Long id) {
        return ResponseEntity.ok(appService.getAppOrgs(id));
    }

    @PostMapping("/{id}/send/orgs")
    public ResponseEntity<?> sendAppToOrgs(@PathVariable Long id,
                                           @RequestBody List<Organization> orgs,
                                           @RequestParam(required = false, defaultValue = "false") boolean confirming,
                                           Principal principal) {
        try {
            App app = appService.getById(id);
            appService.sendAppToOrgs(app, orgs);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/numerate")
    public ResponseEntity<?> prelimNumerate(@PathVariable Long id,
                                            @RequestBody NumerationDto numerationDto) {
        numerationDto.setNumerationDate(DateUtils.addHours(numerationDto.getNumerationDate(), 1));
        App app = appService.getById(id);
        app.setNumeration(numerationDto.getNumeration());
        app.setNumerationDate(numerationDto.getNumerationDate());
        appService.update(app);
        stampService.setStamp(app);

        if (app.getSubservice().getId() == 23L) {
            stampService.addAppNumberStamp(app, numerationDto.getNumeration(), numerationDto.getNumerationDate());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/stamp/{fileId}/download")
    public ResponseEntity<?> prelimStampDownload(@PathVariable Long id,
                                                 @PathVariable Long fileId) {
        FileContentDto fileDto = stampService.getStampFile(id, fileId);
        if (fileDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/pdf;charset=UTF-8"));
            headers.setContentLength(fileDto.getFile().length);
            String encoded = URLEncoder.encode(fileDto.getFileName(), "utf-8").replace("+", "%20");
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + encoded + ".pdf\"");
            return new ResponseEntity<>(fileDto.getFile(), headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/cn/agreement/preview")
    public ResponseEntity<?> generateAgreement(@PathVariable Long id) {
        byte[] file = agreementDocumentService.generateAgreementDocument(id, false);
        if (file == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/pdf;charset=UTF-8"));
            headers.setContentLength(file.length);
            String encoded = URLEncoder.encode("agreement_document", "utf-8").replace("+", "%20");
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + encoded + ".pdf\"");
            return new ResponseEntity<>(file, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/cn/agreement/docx")
    public ResponseEntity<?> generateAgreementDocx(@PathVariable Long id) {
        byte[] file = agreementDocumentService.generateAgreementDocumentDocx(id, false);
        if (file == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document;charset=UTF-8"));
            headers.setContentLength(file.length);
            String encoded = URLEncoder.encode("agreement_document", "utf-8").replace("+", "%20");
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + encoded + ".docx\"");
            return new ResponseEntity<>(file, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/cn/agreement")
    public ResponseEntity<?> downloadAgreement(@PathVariable Long id) {
        byte[] file = agreementDocumentService.getGeneratedAgreementDocument(id);
        if (file == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/pdf;charset=UTF-8"));
            headers.setContentLength(file.length);
            String encoded = URLEncoder.encode("agreement_document", "utf-8").replace("+", "%20");
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + encoded + ".pdf\"");
            return new ResponseEntity<>(file, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/eskizStampToEatyrau")
    public ResponseEntity<?> test2(@PathVariable Long id) {
//        return ResponseEntity.ok(appService.calculateEndDateOnlyWorkDays(new Date(), 7));

//        App app = appService.getById(id);
        List<AppFile> appFiles = appService.getAppFiles(id);
        RestTemplate restTemplate = new RestTemplate();
        for (AppFile appFile : appFiles) {
            if(appFile.getFileCategory() == FileCategory.ESKIZ_STAMP) {
                resultSendEqyzmetService.sendAppFile(appFile, id, restTemplate);
            }
        }

        return null;

    }

    @PostMapping("/{id}/resultFileToEatyrau")
    public ResponseEntity<?> sendResultFile(@PathVariable Long id) {
        List<AppFile> appFiles = appService.getAppFiles(id);
        RestTemplate restTemplate = new RestTemplate();
        for (AppFile appFile : appFiles) {
            if(appFile.getFileCategory() == FileCategory.RESULT_FILE) {
                resultSendEqyzmetService.sendAppFile(appFile, id, restTemplate);
            }
        }
        return null;

    }

    @GetMapping("/{id}/application/pdf")
    public ResponseEntity<?> getApplicationPdf(@PathVariable Long id) {
        AppFile appFile = appService.getAppFileByCategory(id, FileCategory.APPLICATION_PDF);
        byte[] file = fileService.downloadFile(appFile.getObjectId());
        if (appFile == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/pdf;charset=UTF-8"));
            headers.setContentLength(appFile.getSize());
            String encoded = URLEncoder.encode(appFile.getName(), "utf-8").replace("+", "%20");
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + encoded + "\"");
            return new ResponseEntity<>(file, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/application/pdf/preview")
    public ResponseEntity<?> getApplicationPdfPreview(@PathVariable Long id) {
        App app = appService.getById(id);
        String html = pdfGenerateService.getHtmlOfApplicationPdf(app);
        byte[] file = pdfGenerateService.generateApplicationPdf(id, html, app.getSignedXml());
        if (file == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/pdf;charset=UTF-8"));
            headers.setContentLength(file.length);
            String encoded = URLEncoder.encode("application_" + id, "utf-8").replace("+", "%20");
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + encoded + ".pdf\"");
            return new ResponseEntity<>(file, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/testPlanEndDate/{days}")
    public ResponseEntity<?> testPlanEndDate(@PathVariable int days) {
        return ResponseEntity.ok(appService.calculateEndDateOnlyWorkDays(new Date(), days));
    }

    @PostMapping("/{appId}/planEndDate/recalculate")
    public ResponseEntity<?> recalculatePlanEndDate(@PathVariable Long appId,
                                                    @RequestParam(required = false) Integer days) {
        appService.recalculatePlanEndDate(appId, days);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{appId}/planEndDate/set")
    public ResponseEntity<?> setPlanEndDate(@PathVariable Long appId,
                                            @RequestBody Map<String, Date> body) {
        appService.setPlanEndDate(appId, body.get("date"));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{appId}/resultSendEqyzmet")
    public ResponseEntity<?> resultSendEqyzmet(@PathVariable Long appId) {
        resultSendEqyzmetService.sendApp(appId, true, new ArrayList<>());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
