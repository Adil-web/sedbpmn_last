package kz.kazgisa.service;

import kz.kazgisa.data.entity.App;
import kz.kazgisa.data.entity.AppFile;
import kz.kazgisa.data.entity.AppStatus;
import kz.kazgisa.data.entity.EqyzmetSendError;
import kz.kazgisa.data.enums.FileCategory;
import kz.kazgisa.data.enums.Status;
import kz.kazgisa.data.repositories.EqyzmetSendErrorRepository;
import org.apache.commons.lang3.BooleanUtils;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ResultSendEqyzmetService implements JavaDelegate {
    @Autowired
    AppService appService;
    @Autowired
    FileService fileService;
    @Autowired
    EqyzmetSendErrorRepository eqyzmetSendErrorRepository;

    @Value("${project.eqyzmet.url}")
    private String eqyzmetUrl;
    @Value("${project.eqyzmet.secret}")
    private String eqyzmetSecret;
    @Value("${project.dejurka.url}")
    private String dejurkaUrl;
    @Value("${project.dejurka.secret}")
    private String dejurkaSecret;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        long id = ((Number) delegateExecution.getVariable("appId")).longValue();
        Boolean approved = ((Boolean) delegateExecution.getVariable("approved"));
        List<Map<String, Object>> zkFiles = ((List<Map<String, Object>>) delegateExecution.getVariable("zkFiles"));
        List<Map<String, Object>> scanFiles = ((List<Map<String, Object>>) delegateExecution.getVariable("scanFiles"));
        List<Map<String, Object>> taskFiles = new ArrayList<>();
        taskFiles.addAll(zkFiles);
        taskFiles.addAll(scanFiles);

        sendApp(id, approved, taskFiles);
    }

    public void sendApp(Long id, Boolean approved, List<Map<String, Object>> taskFiles) {
        try {
            System.out.println("sendApp LOG");
            Status status;
            if(approved != null && approved) {
                status = Status.APPROVED;
            } else {
                status = Status.REJECTED;
            }
            App app = appService.getById(id);
            AppStatus appStatus = new AppStatus();
            appStatus.setAppId(app.getAppId());
            appStatus.setDate(new Date());
            appStatus.setStatus(status);
            appStatus.setUserId(app.getArchSignedUserId() != null ? app.getArchSignedUserId() : app.getOzoSignedUserId());

            String url = eqyzmetUrl + "/eqyzmet/api/app/ext/" + app.getId() + "/result?secret=" + eqyzmetSecret;
            RestTemplate restTemplate = new RestTemplate();


            try {
                ResponseEntity<App> appResponse = restTemplate.postForEntity(url, appStatus, App.class);

                if (appResponse != null && appResponse.getStatusCode() != HttpStatus.OK) {
                    createEqyzmetSendError(id, "sending app error", appResponse.getStatusCode().toString());
                    throw new RuntimeException("Не удалось отправить результат заявления заявителю");
                }
            } catch (Exception e) {
                e.printStackTrace();
                createEqyzmetSendError(id, "sending app error (catch)", e.getLocalizedMessage());
                throw new RuntimeException("Не удалось отправить результат заявления заявителю");
            }

            List<AppFile> appFiles = appService.getAppFiles(id);
            for(AppFile appFile: appFiles) {
                sendAppFile(appFile, app.getId(), restTemplate);
            }

            System.out.println("Print taskFiles");
            if(taskFiles != null) {
                System.out.println("taskFiles not null");
                int index = 1;
                for(Map<String, Object> taskFile : taskFiles) {
                    System.out.println("index: " + index);
                    index++;
                    try {
                        String uid = taskFile.get("uid").toString();
                        System.out.println("uid: " + uid);
                        Long size = Long.valueOf(taskFile.get("size").toString());
                        String fileName = taskFile.get("fileName").toString();
                        String fileCategory = taskFile.get("fileCategory").toString();
                        AppFile taskAppFile = new AppFile();
                        taskAppFile.setObjectId(uid);
                        taskAppFile.setSize(size);
                        taskAppFile.setName(fileName);
                        if (fileCategory.equals("conclusionForZK")) {
                            taskAppFile.setFileCategory(FileCategory.ZK_FILES);
                        } else if (fileCategory.equals("CN_ACT")) {
                            taskAppFile.setFileCategory(FileCategory.SCAN_FILES);
                        }
                        System.out.println("fileCategory: " + taskAppFile.getFileCategory());
                        taskAppFile.setContentType("application/pdf");

                        sendAppFile(taskAppFile, app.getId(), restTemplate);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            System.out.println("before saveToAppArchLayer");

            saveToAppArchLayer(app, status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendAppFile(AppFile appFile, Long appId, RestTemplate restTemplate) {
        try {
            if(appFile.getFileCategory() == FileCategory.RESULT_ATTACHMENT
                    || appFile.getFileCategory() == FileCategory.RESULT_FILE
                    || appFile.getFileCategory() == FileCategory.TECH_CONDITION_JARYK
                    || appFile.getFileCategory() == FileCategory.TECH_CONDITION_SUARNASY
                    || appFile.getFileCategory() == FileCategory.TECH_CONDITION_TEPLOSETI
                    || appFile.getFileCategory() == FileCategory.ESKIZ_STAMP
                    || appFile.getFileCategory() == FileCategory.ZU_PROJECT_STAMP
                    || appFile.getFileCategory() == FileCategory.ZK_FILES
                    || appFile.getFileCategory() == FileCategory.SCAN_FILES) {
                if (appFile.getObjectId() != null) {
                    byte[] file = fileService.downloadFile(appFile.getObjectId());
                    if (file != null) {
                        System.out.println("file not null");
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
//                    body.add("uploadDate", appFile.getUploadDate());
                        body.add("file", resource);
                        System.out.println("body: " + body);
                        HttpEntity<MultiValueMap<String, Object>> requestEntity
                                = new HttpEntity<>(body, headers);
                        String serverUrl = eqyzmetUrl + "/eqyzmet/api/app/ext/" + appId + "/files?secret=" + eqyzmetSecret;

                        ResponseEntity<AppFile> fileResponse = null;

                        try {
                            fileResponse = restTemplate.postForEntity(serverUrl, requestEntity, AppFile.class);

                            if (fileResponse != null && fileResponse.getStatusCode() != HttpStatus.OK) {
                                createEqyzmetSendError(appId, "sending file error", fileResponse.getStatusCode().toString());
                                throw new RuntimeException("Не удалось отправить документ заявителю");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            createEqyzmetSendError(appId, "sending file error (catch)", e.getLocalizedMessage());
                            throw new RuntimeException("Не удалось отправить документ заявителю");
                        }

                        if(fileResponse != null)
                            System.out.println("----- file status: " + fileResponse.getStatusCode() + "; " + fileResponse.getStatusCodeValue());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveToAppArchLayer(App app, Status status) {
        System.out.println("saveToAppArchLayer");
        try {
            String url = dejurkaUrl + "/dutymap/api/layers/app_arch/ext/objects?geoserverId=1&secret=" + dejurkaSecret;
            System.out.println("saveToAppArchLayer url: " + url);
            RestTemplate restTemplate = new RestTemplate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Map<String, Object> attrs = new HashMap<>();
            attrs.put("id", app.getId());
            attrs.put("subserviceshortnameru", app.getSubservice().getShortNameRu());
            attrs.put("orgname", app.getOrgName());
            attrs.put("firstname", app.getFirstName());
            attrs.put("lastname", app.getLastName());
            attrs.put("secondname", app.getSecondName());
            attrs.put("iin", app.getIin());
            attrs.put("bin", app.getBin());
            attrs.put("address", app.getAddress());
            attrs.put("phone", app.getPhone());
            attrs.put("status", status.name());
            if(app.getSignedDate() != null) {
                attrs.put("regdate", sdf.format(app.getSignedDate()));
            }
            if(app.getFactEndDate() != null) {
                attrs.put("finishdate", sdf.format(app.getFactEndDate()));
            }
            attrs.put("cadastrenumber", app.getObjectInfo().getCadastreNumber());
            String geom = "ST_SetSRID(ST_GeomFromGeoJSON(cast('" + app.getObjectInfo().getLocation() + "' as json)->>'geometry'),4326)";
            attrs.put("geom", geom);
            attrs.put("subservice_id", app.getSubservice().getId());
            System.out.println("before response");
            ResponseEntity<Long> response = restTemplate.postForEntity(url, attrs, Long.class);
            System.out.println("after response");
            System.out.println("---------- layer object status: " + response.getStatusCode() + "; " + response.getStatusCodeValue());
            System.out.println("---------- layer object body: " + response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendUnsentEskizFiles() {
        try {
            Long[] ids = appService.getAppIdsWithoutEskiz();
            if(ids != null && ids.length > 0) {
                for(Long id: ids) {
                    List<AppFile> appFiles = appService.getAppFiles(id);
                    RestTemplate restTemplate = new RestTemplate();
                    for (AppFile appFile : appFiles) {
                        if(appFile.getFileCategory() == FileCategory.ESKIZ_STAMP) {
                            sendAppFile(appFile, id, restTemplate);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createEqyzmetSendError(Long appId, String type, String error) {
        EqyzmetSendError eqyzmetSendError = new EqyzmetSendError();
        eqyzmetSendError.setAppId(appId);
        eqyzmetSendError.setDate(new Date());
        eqyzmetSendError.setType(type);
        eqyzmetSendError.setError(error);
        eqyzmetSendErrorRepository.save(eqyzmetSendError);
    }
}
