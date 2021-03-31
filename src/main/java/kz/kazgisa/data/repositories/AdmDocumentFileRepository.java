package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.AdmDocumentFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdmDocumentFileRepository extends JpaRepository<AdmDocumentFile, Long> {
    List<AdmDocumentFile> findByAdmDocumentId(Long id);

    AdmDocumentFile findFirstByName(String id);
}
