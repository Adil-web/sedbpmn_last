package kz.kazgisa.controller;

import kz.kazgisa.data.entity.AppFile;
import kz.kazgisa.data.entity.HeaderFile;
import kz.kazgisa.data.repositories.HeaderFileRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequestMapping("header-files")
public class HeaderFileController {
    private final RestTemplate restTemplate;
    private final HeaderFileRepository headerFileRepository;

    public HeaderFileController(RestTemplate restTemplate, HeaderFileRepository headerFileRepository) {
        this.restTemplate = restTemplate;
        this.headerFileRepository = headerFileRepository;
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteFile(@PathVariable Long id) {
        headerFileRepository.deleteById(id);
        return ResponseEntity.ok(new HeaderFile());
    }

    @GetMapping("{id}")
    public ResponseEntity downloadFile(@PathVariable Long id) throws UnsupportedEncodingException {
        HeaderFile appFile = headerFileRepository.findById(id).get();
        byte[] file = restTemplate.getForObject("http://localhost:8181/api/files/download/"+appFile.getObjectId(), byte[].class);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf;charset=UTF-8"));
        headers.setContentLength(file.length);
        String encoded = URLEncoder.encode(appFile.getName(), "utf-8").replace("+", "%20");
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + encoded + ".pdf\"");
        return new ResponseEntity<>(file, headers, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getFiles() {
        return ResponseEntity.ok(headerFileRepository.findAll());
    }

    @PostMapping
    public ResponseEntity saveFiles(@RequestBody HeaderFile file) {
        return ResponseEntity.ok(headerFileRepository.save(file));
    }

}
