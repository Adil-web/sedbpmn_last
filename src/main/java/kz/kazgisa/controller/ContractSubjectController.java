package kz.kazgisa.controller;

import kz.kazgisa.data.entity.contract.Contract;
import kz.kazgisa.data.entity.dict.ContractSubject;
import kz.kazgisa.service.ContractSubjectService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("contract-subjects")
public class ContractSubjectController {
    private final ContractSubjectService contractSubjectService;

    public ContractSubjectController(ContractSubjectService contractSubjectService) {
        this.contractSubjectService = contractSubjectService;
    }

    @GetMapping
    public ResponseEntity getContractSubjects(Pageable pageable) {
        return ResponseEntity.ok(contractSubjectService.getContractSubjects(pageable));
    }

    @PostMapping
    public ResponseEntity createContract(@RequestBody ContractSubject contract) {
        return ResponseEntity.ok(contractSubjectService.createContractSubject(contract));
    }

    @PutMapping("{id}")
    public ResponseEntity updateContract(@PathVariable Long id, @RequestBody ContractSubject contract) {
        contract.setId(id);
        return ResponseEntity.ok(contractSubjectService.createContractSubject(contract));
    }
}
