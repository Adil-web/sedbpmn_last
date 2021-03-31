package kz.kazgisa.service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.*;
import kz.kazgisa.data.dto.EAtyrauDto;
import kz.kazgisa.data.dto.FileDto;
import kz.kazgisa.data.dto.FileContentDto;
import kz.kazgisa.data.entity.App;
import kz.kazgisa.data.entity.AppFile;
import kz.kazgisa.data.enums.FileCategory;
import kz.kazgisa.data.repositories.AppFileRepository;
import kz.kazgisa.data.repositories.AppRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.codehaus.jackson.map.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StampService implements JavaDelegate {
    private final PdfGenerateService pdfService;
    private final AppFileRepository appFileRepository;
    private final AppRepository appRepository;
    private final FileService fileService;
    private final RestTemplate restTemplate;
    private static String eatyrau = "http://localhost:3000";

    public StampService(PdfGenerateService pdfService, AppFileRepository appFileRepository, AppRepository appRepository, FileService fileService, RestTemplate restTemplate) {
        this.appRepository = appRepository;
        this.pdfService = pdfService;
        this.appFileRepository = appFileRepository;
        this.fileService = fileService;
        this.restTemplate = restTemplate;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        //add stamp
        long id = ((Number) delegateExecution.getVariable("appId")).longValue();
        Boolean approved = (Boolean) delegateExecution.getVariable("approved");
        App app = appRepository.findById(id).get();
        app.setFactEndDate(new Date());
        app.setArchSigned(true);
        appRepository.save(app);
        try {
            EAtyrauDto dto = new EAtyrauDto();
            dto.setApproved(approved);
            dto.setAppId(app.getAppId());
            dto.setArchSignedDate(app.getArchSignedDate());
            dto.setArchSignedXml(app.getSignedXml());
            restTemplate.postForObject(eatyrau+"/eqyzmet/api/sed/app",dto,EAtyrauDto.class);
            List<AppFile> appFiles = appFileRepository.findByAppId(app.getId()).stream().filter(appFile -> appFile.getFileCategory().equals(FileCategory.RESULT_FILE) ||
                    appFile.getFileCategory().equals(FileCategory.ESKIZ_STAMP)
                    || appFile.getFileCategory().equals(FileCategory.RESULT_ATTACHMENT)).collect(Collectors.toList());
            for (AppFile appFile : appFiles) {
                HttpHeaders headers = new HttpHeaders();
                byte[] uploadFile = fileService.downloadFile(appFile.getObjectId());
                headers.setContentType(MediaType.MULTIPART_FORM_DATA);
                MultiValueMap<String, Object> body
                        = new LinkedMultiValueMap<>();
                ByteArrayResource resource = new ByteArrayResource(uploadFile) {
                    @Override
                    public String getFilename() {
                        return appFile.getName();
                    }
                };
                body.add("file", resource);
                body.add("fileName",appFile.getName());
                body.add("fileCategory", appFile.getFileCategory().name());
                HttpEntity<MultiValueMap<String, Object>> requestEntity
                        = new HttpEntity<>(body, headers);
                String serverUrl = eatyrau+"/eqyzmet/api/sed/app/"+app.getAppId()+"/files";
                ResponseEntity<FileDto> response = restTemplate.postForEntity(serverUrl, requestEntity, FileDto.class);
            }
        } catch (Exception e) {

        }
    }

    public App executeMe(Long id) throws Exception {
        //add stamp

        App app = appRepository.findById(id).get();
        Boolean approved = app.getApproved();
        app.setFactEndDate(new Date());
        app.setArchSigned(true);
        appRepository.save(app);
        try {
            EAtyrauDto dto = new EAtyrauDto();
            dto.setApproved(approved);
            dto.setAppId(app.getAppId());
            dto.setArchSignedDate(app.getArchSignedDate());
            dto.setArchSignedXml(app.getSignedXml());
            restTemplate.postForObject(eatyrau+"/eqyzmet/api/sed/app",dto,EAtyrauDto.class);
            List<AppFile> appFiles = appFileRepository.findByAppId(app.getId()).stream().filter(appFile -> appFile.getFileCategory().equals(FileCategory.RESULT_FILE) ||
                    appFile.getFileCategory().equals(FileCategory.ESKIZ_STAMP) ||
                    appFile.getFileCategory().equals(FileCategory.RESULT_ATTACHMENT)).collect(Collectors.toList());
            for (AppFile appFile : appFiles) {
                HttpHeaders headers = new HttpHeaders();
                byte[] uploadFile = fileService.downloadFile(appFile.getObjectId());
                headers.setContentType(MediaType.MULTIPART_FORM_DATA);
                MultiValueMap<String, Object> body
                        = new LinkedMultiValueMap<>();
                ByteArrayResource resource = new ByteArrayResource(uploadFile) {
                    @Override
                    public String getFilename() {
                        return appFile.getName();
                    }
                };
                body.add("file", resource);
                body.add("fileName",appFile.getName());
                body.add("fileCategory", appFile.getFileCategory().name());
                HttpEntity<MultiValueMap<String, Object>> requestEntity
                        = new HttpEntity<>(body, headers);
                String serverUrl = eatyrau+"/eqyzmet/api/sed/app/"+app.getAppId()+"/files";
                ResponseEntity<FileDto> response = restTemplate.postForEntity(serverUrl, requestEntity, FileDto.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return app;
    }

    public void setStamp(App app) {
        try {
            List<AppFile> appFiles = appFileRepository.findByAppId(app.getId());
            List<AppFile> eskizProjectFiles = new ArrayList<>();
            List<AppFile> existingStampFiles = new ArrayList<>();
            for (AppFile appFile : appFiles) {
                if (appFile.getFileCategory() == FileCategory.ESKIZ_TITLE
                        || appFile.getFileCategory() == FileCategory.ESKIZ_GENPLAN
                        || appFile.getFileCategory() == FileCategory.ESKIZ_LANDSCAPING
                        || appFile.getFileCategory() == FileCategory.ESKIZ_EXTERIOR
                        || appFile.getFileCategory() == FileCategory.ESKIZ_COLORING
                        || appFile.getFileCategory() == FileCategory.ESKIZ_FACADES
                        || appFile.getFileCategory() == FileCategory.ESKIZ_NIGHT_DECOR
                        || appFile.getFileCategory() == FileCategory.ESKIZ_OTHERS
                        || appFile.getFileCategory() == FileCategory.ZU_PROJECT) {
                    eskizProjectFiles.add(appFile);
                }
                if (appFile.getFileCategory() == FileCategory.ESKIZ_STAMP
                        || appFile.getFileCategory() == FileCategory.ZU_PROJECT_STAMP) {
                    existingStampFiles.add(appFile);
                }
            }

            appFileRepository.deleteAll(existingStampFiles);

            FileCategory stampCategory = null;
            if (app.getSubservice().getId() == 21) {
                stampCategory = FileCategory.ESKIZ_STAMP;
            } else if (app.getSubservice().getId() == 23) {
                stampCategory = FileCategory.ZU_PROJECT_STAMP;
            }

            if (eskizProjectFiles.size() > 0) {
                for (AppFile eskizProjectFile : eskizProjectFiles) {
                    String fileName = eskizProjectFile.getName();
                    AppFile eskizFile = new AppFile();
                    eskizFile.setApp(app);
                    eskizFile.setFileCategory(stampCategory);
                    eskizFile.setName(fileName);
                    eskizFile = appFileRepository.save(eskizFile);
                    byte[] eskizProjectFileByte = fileService.downloadFile(eskizProjectFile.getObjectId());
                    byte[] eskizStamped = pdfService.addTextStamp(eskizProjectFileByte, eskizFile.getId(),
                            app.getNumeration(), app.getNumerationDate(), app.getSubservice().getId(), app.getId());
                    if (eskizStamped != null) {
                        FileDto response = fileService.uploadFile(eskizStamped, fileName, stampCategory);
                        if (response != null) {
                            eskizFile.setUploadDate(new Date());
                            eskizFile.setSize(response.fileSize);
                            eskizFile.setContentType(response.contentType);
                            eskizFile.setObjectId(response.uid);
                            appFileRepository.save(eskizFile);
                        }
                    } else {
                        appFileRepository.delete(eskizFile);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setEskizApprovingStamp(App app) {
        byte[] approvedFileOriginal = app.getApprovedFileOriginal();
        byte[] approvedFile;
        if (approvedFileOriginal != null) {
            approvedFile = pdfService.addEskizApprovingRejectingStamp(approvedFileOriginal, app.getNumeration(), app.getNumerationDate());
            app.setApprovedFile(approvedFile);
            appRepository.save(app);
        }
    }

    public void setEskizRejectingStamp(App app) {
        byte[] rejectedFileOriginal = app.getRejectedFileOriginal();
        byte[] rejectedFile;
        if (rejectedFileOriginal != null) {
            rejectedFile = pdfService.addEskizApprovingRejectingStamp(rejectedFileOriginal, app.getNumeration(), app.getNumerationDate());
            app.setRejectedFile(rejectedFile);
            appRepository.save(app);
        }
    }

    public FileContentDto getStampFile(Long appId, Long fileId) {
        List<AppFile> files = appFileRepository.findByAppId(appId);
        for(AppFile file : files) {
            if(fileId.equals(file.getId()) &&
                    (file.getFileCategory() == FileCategory.ESKIZ_STAMP || file.getFileCategory() == FileCategory.ZU_PROJECT_STAMP)) {
                String objectId = file.getObjectId();
                FileContentDto fileDto = new FileContentDto();
                fileDto.setFileName(file.getName());
                byte[] f = fileService.downloadFile(objectId);
                fileDto.setFile(f);
                return fileDto;
            }
        }
        return null;
    }

    public void addAppNumberStamp(App app, String numeration, Date numerationDate) {
        try {
            AppFile currentFile = appFileRepository.findFirstByAppIdAndFileCategory(app.getId(), FileCategory.RESULT_FILE);
            long currentResultFileId = currentFile.getId();

            AppFile resultFileOriginal = appFileRepository.findFirstByAppIdAndFileCategory(app.getId(), FileCategory.RESULT_FILE_ORIGINAL);
            if (resultFileOriginal == null) {
                AppFile newOriginalFile = new AppFile();
                BeanUtils.copyProperties(currentFile, newOriginalFile, "id", "fileCategory");
                newOriginalFile.setFileCategory(FileCategory.RESULT_FILE_ORIGINAL);
                AppFile createdOriginal = appFileRepository.save(newOriginalFile);
                resultFileOriginal = createdOriginal;
            }

            byte[] originalFile = fileService.downloadFile(resultFileOriginal.getObjectId());
            byte[] numeratedFile = addNumberStamp(originalFile, numeration, numerationDate);
            FileDto fileDto = fileService.uploadFile(numeratedFile, currentFile.getName(), FileCategory.RESULT_FILE);

            AppFile newResultFile = new AppFile();
            BeanUtils.copyProperties(currentFile, newResultFile, "id", "fileCategory", "objectId", "fileSize");
            newResultFile.setFileCategory(FileCategory.RESULT_FILE);
            newResultFile.setObjectId(fileDto.getUid());
            newResultFile.setSize(fileDto.getFileSize());
            appFileRepository.deleteById(currentResultFileId);
            appFileRepository.save(newResultFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] addNumberStamp(byte[] file, String numeration, Date numerationDate) {
        try {
            BaseFont baseFont = BaseFont.createFont("/usr/share/fonts/times-new-roman.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            PdfReader reader = new PdfReader(file);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            PdfStamper stamper = new PdfStamper(reader, byteArrayOutputStream);
            int pages = reader.getNumberOfPages();
            PdfContentByte canvas = stamper.getOverContent(1);
            Font stampFont = new Font(baseFont, 12);
            stampFont.setColor(BaseColor.BLACK);
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            String numerationString = "";
            if(numeration != null && !numeration.equals("")) {
                numerationString = "№ " + numeration;
            }
            Phrase phrase1 = new Phrase(sdf.format(numerationDate) + " ж/г.", stampFont);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase1, 54, 650, 0);
            Phrase phrase2 = new Phrase(numerationString, stampFont);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase2, 54, 670, 0);
            stamper.close();
            reader.close();
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
