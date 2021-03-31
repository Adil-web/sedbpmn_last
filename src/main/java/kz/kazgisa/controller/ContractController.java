package kz.kazgisa.controller;

import kz.kazgisa.data.entity.contract.Contract;
import kz.kazgisa.service.ContractService;
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

@Controller
@RequestMapping("contracts")
public class ContractController {
    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @PostMapping
    public ResponseEntity createContract(@RequestBody Contract contract) {
        return ResponseEntity.ok(contractService.saveContract(contract));
    }

    @PutMapping("{id}")
    public ResponseEntity updateContract(@PathVariable Long id,@RequestBody Contract contract) {
        contract.setId(id);
        return ResponseEntity.ok(contractService.saveContract(contract));
    }

    @GetMapping
    public ResponseEntity getContracts(Pageable pageable) {
        return ResponseEntity.ok(contractService.getContracts(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity getContractsById(@PathVariable Long id) {
        return ResponseEntity.ok(contractService.getContractById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteContractsById(@PathVariable Long id) {
        return ResponseEntity.ok(contractService.deleteContractById(id));
    }


}
