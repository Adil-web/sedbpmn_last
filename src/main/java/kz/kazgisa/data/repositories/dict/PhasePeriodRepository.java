package kz.kazgisa.data.repositories.dict;

import kz.kazgisa.data.entity.dict.PhasePeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhasePeriodRepository extends JpaRepository<PhasePeriod, Long> {
}
