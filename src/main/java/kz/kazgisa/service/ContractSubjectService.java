package kz.kazgisa.service;

import kz.kazgisa.data.entity.dict.ContractSubject;
import kz.kazgisa.data.repositories.contract.ContractSubjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractSubjectService {
    private final ContractSubjectRepository contractSubjectRepository;

    public ContractSubjectService(ContractSubjectRepository contractSubjectRepository) {
        this.contractSubjectRepository = contractSubjectRepository;
    }

    public Page<ContractSubject> getContractSubjects(Pageable pageable) {
        return contractSubjectRepository.findAll(pageable);
    }

    public ContractSubject createContractSubject(ContractSubject contract) {
        return contractSubjectRepository.save(contract);
    }
}
