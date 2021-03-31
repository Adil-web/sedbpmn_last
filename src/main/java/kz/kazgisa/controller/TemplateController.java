package kz.kazgisa.controller;

import kz.kazgisa.data.entity.OrgTemplate;
import kz.kazgisa.service.PdfGenerateService;
import kz.kazgisa.service.TemplateService;
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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("templates")
public class TemplateController {

    private final TemplateService templateService;
    private final static Long id = 1L;
    private final PdfGenerateService pdfGenerateService;

    public TemplateController(TemplateService templateService, PdfGenerateService pdfGenerateService) {
        this.templateService = templateService;
        this.pdfGenerateService = pdfGenerateService;
    }


    @GetMapping
    public ResponseEntity<?> getOrgTemplates(@RequestParam(required = false, defaultValue = "0") Long subserviceId,
                                             @RequestParam(required = false) Boolean approved) {
        return ResponseEntity.ok(templateService.getTemplates(id, subserviceId, approved));
    }

    @GetMapping("approved")
    public ResponseEntity<?> getOrgTemplatesApproved(@RequestParam(required = false, defaultValue = "0") Long subserviceId) {
        return ResponseEntity.ok(templateService.getTemplates(id, subserviceId, true));
    }

    @GetMapping("rejected")
    public ResponseEntity<?> getOrgTemplatesRejected(@RequestParam(required = false, defaultValue = "0") Long subserviceId) {
        return ResponseEntity.ok(templateService.getTemplates(id, subserviceId, false));
    }

    @PostMapping
    public ResponseEntity<?> addTemplate(@RequestBody OrgTemplate orgTemplate) {
        return ResponseEntity.ok(templateService.saveTemplate(orgTemplate));
    }

    @PutMapping
    public ResponseEntity<?> editTemplate(@RequestBody OrgTemplate orgTemplate) {
        return ResponseEntity.ok(templateService.saveTemplate(orgTemplate));
    }

    @DeleteMapping("{templateId}")
    public ResponseEntity<?> deleteTemplates(@PathVariable Long templateId) {
        templateService.deleteTemplate(templateId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("preview")
    public ResponseEntity<?> previewApp(@RequestBody Map<String, String> map) throws UnsupportedEncodingException {
        String message = map.get("message");
        byte[] pdf = pdfGenerateService.generatePdf(message);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf;charset=UTF-8"));
        headers.setContentLength(pdf.length);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=preview-"+ UUID.randomUUID().toString().replaceAll("-","") +".pdf");
        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);

    }

    @GetMapping("header-images")
    public ResponseEntity<?> getResouceHeaders() throws IOException {
        return ResponseEntity.ok(templateService.getTemplatesHeaders());
    }

}
