package kz.kazgisa.data.repositories.dict;

import kz.kazgisa.data.entity.dict.OwnershipForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnershipFormRepository extends JpaRepository<OwnershipForm, Long> {
}
