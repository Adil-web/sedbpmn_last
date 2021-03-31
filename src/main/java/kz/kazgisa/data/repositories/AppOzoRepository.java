package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.AppOzo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppOzoRepository extends JpaRepository<AppOzo, Long> {
}
