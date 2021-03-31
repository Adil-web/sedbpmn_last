package kz.kazgisa.data.repositories.contract;

import kz.kazgisa.data.entity.contract.ContractStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractStatusHistoryRepository extends JpaRepository<ContractStatusHistory, Long> {
}
