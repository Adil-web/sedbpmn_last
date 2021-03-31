package kz.kazgisa.controller;

import kz.kazgisa.data.entity.contract.ContractExecution;
import kz.kazgisa.service.ContractExecutionService;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("contract-executions")
public class ContractExecutionController {
    private final ContractExecutionService contractExecutionService;

    public ContractExecutionController(ContractExecutionService contractExecutionService) {
        this.contractExecutionService = contractExecutionService;
    }

    @PostMapping
    public ResponseEntity createContract(@RequestBody ContractExecution contract) {
        return ResponseEntity.ok(contractExecutionService.saveContractExecution(contract));
    }

    @PutMapping("{id}")
    public ResponseEntity updateContract(@PathVariable Long id, @RequestBody ContractExecution contract) {
        contract.setId(id);
        return ResponseEntity.ok(contractExecutionService.saveContractExecution(contract));
    }

    @GetMapping
    public ResponseEntity getContracts(@RequestParam(required = false) Long contractId, Pageable pageable) {
        if(contractId != null) {
            return ResponseEntity.ok(contractExecutionService.getContractExecutionsByContractId(contractId, pageable));
        }
        return ResponseEntity.ok(contractExecutionService.getContractExecutions(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity getContractsById(@PathVariable Long id) {
        return ResponseEntity.ok(contractExecutionService.getContractExecutionById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteContractsById(@PathVariable Long id) {
        return ResponseEntity.ok(contractExecutionService.deleteContractExecutionById(id));
    }

}
