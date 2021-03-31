package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.UnusualDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UnusualDayRepository extends JpaRepository<UnusualDay, Long> {
    List<UnusualDay> findByDateGreaterThanEqualAndDateLessThanEqual(Date start, Date end);
}
