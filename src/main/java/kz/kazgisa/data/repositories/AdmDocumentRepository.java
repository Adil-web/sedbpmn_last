package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.AdmDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdmDocumentRepository extends JpaRepository<AdmDocument, Long> {
}
