package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.AgreementFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgreementFileRepository extends JpaRepository<AgreementFile, Long> {
}
