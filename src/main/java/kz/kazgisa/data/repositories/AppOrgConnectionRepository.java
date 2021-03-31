package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.AppOrgConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppOrgConnectionRepository extends JpaRepository<AppOrgConnection, Long> {
    List<AppOrgConnection> findByAppOrgId(Long appOrgId);
}
