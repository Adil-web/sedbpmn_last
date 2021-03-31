package kz.kazgisa.data.repositories.contract;

import kz.kazgisa.data.entity.dict.ContractSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractSubjectRepository extends JpaRepository<ContractSubject, Long> {
}
