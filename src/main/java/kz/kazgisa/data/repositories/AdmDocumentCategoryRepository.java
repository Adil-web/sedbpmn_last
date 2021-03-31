package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.dict.AdmDocumentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdmDocumentCategoryRepository extends JpaRepository<AdmDocumentCategory, Long> {
}
