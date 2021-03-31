package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.AppOzoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppOzoStatusRepository extends JpaRepository<AppOzoStatus, Long> {
}
