package kz.kazgisa.data.repositories.contract;

import kz.kazgisa.data.entity.contract.ContractExecution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractExecutionRepository extends JpaRepository<ContractExecution, Long> {
    Page<ContractExecution> findByContractId(Long id, Pageable pageable);
}
