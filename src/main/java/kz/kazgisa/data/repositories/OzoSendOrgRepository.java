package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.OzoSendOrg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface OzoSendOrgRepository extends JpaRepository<OzoSendOrg, Long> {
    @Query(value = "select org_id from ozo_send_org where app_id=:appId", nativeQuery = true)
    List<BigInteger> findOrgIdsByAppId(@Param("appId") Long appId);

    @Modifying
    @Transactional
    @Query(value = "delete from ozo_send_org where org_id=:orgId", nativeQuery = true)
    void deleteByOrgId(@Param("orgId") Long orgId);
}
