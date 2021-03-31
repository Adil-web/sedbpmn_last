package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.CommissionControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommissionControlRepository extends JpaRepository<CommissionControl, Long> {
}
