package kz.kazgisa.service;

import kz.kazgisa.data.entity.contract.ContractExecution;
import kz.kazgisa.data.repositories.contract.ContractExecutionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;

@Service
public class ContractExecutionService  {

    private final ContractExecutionRepository contractExecutionRepository;

    public ContractExecutionService(ContractExecutionRepository contractExecutionRepository) {
        this.contractExecutionRepository = contractExecutionRepository;
    }

    public ContractExecution saveContractExecution(ContractExecution contract) {
        return contractExecutionRepository.save(contract);
    }

    public Page<ContractExecution> getContractExecutions(Pageable pageable) {
        return contractExecutionRepository.findAll(pageable);
    }


    public ContractExecution getContractExecutionById(Long id) {
        return contractExecutionRepository.findById(id).orElseGet(() -> {
           throw new NotFoundException();
        });
    }

    public ContractExecution deleteContractExecutionById(Long id) {
        contractExecutionRepository.deleteById(id);
        return new ContractExecution();
    }

    public Page<ContractExecution> getContractExecutionsByContractId(Long contractId, Pageable pageable) {
        return contractExecutionRepository.findByContractId(contractId,pageable);
    }
}
