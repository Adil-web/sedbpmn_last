package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.AgreementAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgreementActionRepository extends JpaRepository<AgreementAction, Long> {
    List<AgreementAction> findByAgreementId(Long agreementId);
}
