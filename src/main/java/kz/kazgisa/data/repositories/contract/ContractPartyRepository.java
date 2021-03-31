package kz.kazgisa.data.repositories.contract;

import kz.kazgisa.data.entity.contract.ContractParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractPartyRepository extends JpaRepository<ContractParty, Long> {
}
