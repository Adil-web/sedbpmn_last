package kz.kazgisa.data.repositories.contract;

import kz.kazgisa.data.entity.contract.ContractTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractTemplateRepository extends JpaRepository<ContractTemplate, Long> {
    Page<ContractTemplate> findByContractSubjectId(Long contractSubjectId, Pageable pageable);
}
