package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.AppUserConfirm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface AppUserConfirmRepository extends JpaRepository<AppUserConfirm, Long> {
    List<AppUserConfirm> findByAppId(Long appId);
    List<AppUserConfirm> findByAppIdAndOrganizationIdOrderByIdAsc(Long appId, Long organizationId);
    AppUserConfirm findFirstByAppIdAndOrganizationIdAndUserId(Long appId, Long organizationId, Long userId);
    AppUserConfirm findFirstByAppIdAndUserId(Long appId, Long userId);

    @Query(value = "SELECT DISTINCT(organization_id) FROM app_user_confirm WHERE app_id=:appId and organization_id is not null", nativeQuery = true)
    List<BigInteger> distinctByOrgId(@Param("appId") Long appId);

}
