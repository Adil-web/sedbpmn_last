package kz.kazgisa.data.repositories.dict;

import kz.kazgisa.data.entity.dict.Phase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhaseRepository extends JpaRepository<Phase, Long> {
}
