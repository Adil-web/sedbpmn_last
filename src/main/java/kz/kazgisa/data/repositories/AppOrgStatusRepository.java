package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.AppOrgStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface AppOrgStatusRepository extends JpaRepository<AppOrgStatus, Long> {
    @Transactional
    List<AppOrgStatus> findByAppOrganizationId(Long appOrganizationId);

    @Query(value = "select * from app_org_status where app_organization_id=:appOrgId and (status = 'APPROVED' or status = 'REJECTED') and message is not null order by date desc limit 1", nativeQuery = true)
    AppOrgStatus getLastApprovedRejectedStatus(@Param("appOrgId") Long appOrgId);
}
