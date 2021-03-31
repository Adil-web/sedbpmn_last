package kz.kazgisa.controller;

import kz.kazgisa.data.entity.AdmDocumentFile;
import kz.kazgisa.data.entity.CorrespondenceFile;
import kz.kazgisa.service.AdmDocumentService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequestMapping("adm-files")
public class AdmFileController {
    private final AdmDocumentService admDocumentService;
    private final RestTemplate restTemplate;

    public AdmFileController(AdmDocumentService admDocumentService, RestTemplate restTemplate) {
        this.admDocumentService = admDocumentService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("{id}")
    public ResponseEntity downloadFile(@PathVariable String id) throws UnsupportedEncodingException {
        AdmDocumentFile appFile = admDocumentService.getFileByName(id);
        byte[] file = restTemplate.getForObject("http://localhost:8181/api/files/download/"+appFile.getObjectId(), byte[].class);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf;charset=UTF-8"));
        headers.setContentLength(file.length);
        String encoded = URLEncoder.encode(appFile.getName(), "utf-8").replace("+", "%20");
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + encoded + ".pdf\"");
        return new ResponseEntity<>(file, headers, HttpStatus.OK);
    }
}
