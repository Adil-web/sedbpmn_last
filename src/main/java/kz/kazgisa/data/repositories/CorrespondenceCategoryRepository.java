package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.dict.CorrespondenceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorrespondenceCategoryRepository extends JpaRepository<CorrespondenceCategory, Long> {
}
