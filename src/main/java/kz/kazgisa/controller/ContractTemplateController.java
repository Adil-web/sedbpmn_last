package kz.kazgisa.controller;

import kz.kazgisa.data.entity.OrgTemplate;
import kz.kazgisa.data.entity.contract.ContractTemplate;
import kz.kazgisa.data.entity.dict.ContractSubject;
import kz.kazgisa.service.ContractTemplateService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

@Controller
@RequestMapping("contract-templates")
public class ContractTemplateController {
    private final ContractTemplateService contractTemplateService;

    public ContractTemplateController(ContractTemplateService contractTemplateService) {
        this.contractTemplateService = contractTemplateService;
    }

    @GetMapping
    public ResponseEntity<?> getOrgTemplates(@RequestParam(required = false) Long contractSubjectId,
                                             Pageable pageable) {
        return ResponseEntity.ok(contractTemplateService.getTemplates(contractSubjectId, pageable));
    }

    @PostMapping
    public ResponseEntity<?> addTemplate(@RequestBody ContractTemplate orgTemplate) {
        return ResponseEntity.ok(contractTemplateService.saveTemplate(orgTemplate));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> editTemplate(@PathVariable Long id,@RequestBody ContractTemplate orgTemplate) {
        orgTemplate.setId(id);
        return ResponseEntity.ok(contractTemplateService.saveTemplate(orgTemplate));
    }

    @DeleteMapping("{templateId}")
    public ResponseEntity<?> deleteTemplates(@PathVariable Long templateId) {
        contractTemplateService.deleteTemplate(templateId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
