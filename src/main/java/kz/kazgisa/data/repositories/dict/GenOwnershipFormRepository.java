package kz.kazgisa.data.repositories.dict;

import kz.kazgisa.data.entity.dict.GenOwnershipForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenOwnershipFormRepository extends JpaRepository<GenOwnershipForm, Long> {
}
