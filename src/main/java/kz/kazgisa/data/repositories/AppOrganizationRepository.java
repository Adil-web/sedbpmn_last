package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.AppOrganization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppOrganizationRepository extends JpaRepository<AppOrganization, Long>, JpaSpecificationExecutor<AppOrganization> {
    AppOrganization findFirstByAppIdAndOrganizationId(Long appId, Long organizationId);
    List<AppOrganization> findByAppId(Long appId);
    List<AppOrganization> findByOrganizationId(Long organizationId);
    Page<AppOrganization> findByOrganizationId(Long organizationId, Pageable pageable);
    Page<AppOrganization> findByOrganizationIdAndApp_SubserviceId(Long organizationId, Long organizationid, Pageable pageable);

    @Query(value = "select count(ap.id) from (select app.id from app_organization apo inner join app on app.id=apo.app_id where organization_id = :organizationId) ap left join (select * from open_app_user where user_id = :userId and app_id is not null) oa on oa.app_id = ap.id where oa.id is null", nativeQuery = true)
    int unreadAppsByOrgId(@Param("organizationId") Long organizationId, @Param("userId") Long userId);

    AppOrganization findFirstByAppIdAndOrganizationCommunalCode(Long id, String code);
}
