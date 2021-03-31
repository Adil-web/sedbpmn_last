package kz.kazgisa.data.repositories.dict;

import kz.kazgisa.data.entity.dict.PurposeUse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurposeUseRepository extends JpaRepository<PurposeUse, Long> {
}
