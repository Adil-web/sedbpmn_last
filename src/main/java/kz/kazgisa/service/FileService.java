package kz.kazgisa.service;

import kz.kazgisa.data.dto.FileDto;
import kz.kazgisa.data.enums.FileCategory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class FileService {

    private RestTemplate restTemplate;

    public FileService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public FileDto uploadFile(byte[] uploadFile, String fileName, FileCategory fileCategory) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        ByteArrayResource resource = new ByteArrayResource(uploadFile) {
            @Override
            public String getFilename() {
                return fileName;
            }
        };
        body.add("file", resource);
        body.add("fileName",fileName);
        body.add("documentCategory", fileCategory.name());
        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);
        String serverUrl = "http://localhost:8181/api/files/upload";
        ResponseEntity<FileDto> response = restTemplate.postForEntity(serverUrl, requestEntity, FileDto.class);
        return response.getBody();
    }

    public byte[] downloadFile(String objectId) {
        return restTemplate.getForObject("http://localhost:8181/api/files/download/"+objectId, byte[].class);
    }
}
