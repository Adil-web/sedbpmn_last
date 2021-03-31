package kz.kazgisa.service;

import kz.kazgisa.data.entity.contract.ContractTemplate;
import kz.kazgisa.data.entity.dict.ContractSubject;
import kz.kazgisa.data.repositories.contract.ContractTemplateRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ContractTemplateService {
    private final ContractTemplateRepository contractTemplateRepository;

    public ContractTemplateService(ContractTemplateRepository contractTemplateRepository) {
        this.contractTemplateRepository = contractTemplateRepository;
    }

    public Page<ContractTemplate> getTemplates(Long contractSubjectId, Pageable pageable) {
        if(contractSubjectId != null) {
            return contractTemplateRepository.findByContractSubjectId(contractSubjectId, pageable);
        }
        return contractTemplateRepository.findAll(pageable);
    }

    public ContractTemplate saveTemplate(ContractTemplate orgTemplate) {
        return contractTemplateRepository.save(orgTemplate);
    }

    public void deleteTemplate(Long templateId) {
        contractTemplateRepository.deleteById(templateId);
    }
}
