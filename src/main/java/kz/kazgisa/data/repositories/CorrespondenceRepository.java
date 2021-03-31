package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.Correspondence;
import kz.kazgisa.data.enums.CorrespondenceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorrespondenceRepository extends JpaRepository<Correspondence, Long> {
    Page<Correspondence> findByType(CorrespondenceType type, Pageable pageable);

    Long countByType(CorrespondenceType in);
}
