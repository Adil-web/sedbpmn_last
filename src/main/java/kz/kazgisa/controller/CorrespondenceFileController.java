package kz.kazgisa.controller;

import kz.kazgisa.data.entity.AppFile;
import kz.kazgisa.data.entity.Correspondence;
import kz.kazgisa.data.entity.CorrespondenceFile;
import kz.kazgisa.service.CorrespondenceService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequestMapping("correspondence-files")
public class CorrespondenceFileController {
    private final CorrespondenceService correspondenceService;
    private final RestTemplate restTemplate;

    public CorrespondenceFileController(CorrespondenceService correspondenceService, RestTemplate restTemplate) {
        this.correspondenceService = correspondenceService;
        this.restTemplate = restTemplate;
    }


    @DeleteMapping("{id}")
    public ResponseEntity deleteFile(@PathVariable Long id) {
        correspondenceService.deleteFile(id);
        return ResponseEntity.ok(new AppFile());
    }

    @GetMapping("{id}")
    public ResponseEntity downloadFile(@PathVariable String id) throws UnsupportedEncodingException {
        CorrespondenceFile appFile = correspondenceService.getFileByName(id);
        byte[] file = restTemplate.getForObject("http://localhost:8181/api/files/download/"+appFile.getObjectId(), byte[].class);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf;charset=UTF-8"));
        headers.setContentLength(file.length);
        String encoded = URLEncoder.encode(appFile.getName(), "utf-8").replace("+", "%20");
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + encoded + ".pdf\"");
        return new ResponseEntity<>(file, headers, HttpStatus.OK);
    }
}
