package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.Organization;
import kz.kazgisa.data.enums.OrgType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    List<Organization> findByRegionIdAndCommunalNotNullOrderById(Long regionId);
    @Query(value = "select * from organization where org_type='COM' or org_type='ARCH' or org_type='OZO' or org_type='ZKITON' order by id", nativeQuery = true)
    List<Organization> findByCommunalNotNullOrderById();
    @Query(value = "select * from organization where communal_id is not null or id=1 or id=10 or id=8 order by id", nativeQuery = true)
    List<Organization> findArchZkitonCommunal();
    List<Organization> findByCommunalId(Long communalId);
    @Query(value = "select * from organization where id in (:ids)", nativeQuery = true)
    List<Organization> findByIdIn(@Param("ids") List<Long> ids);
    List<Organization> findByOzoConfirmer(Boolean ozoConfirmer);


    @Query(value = "select * from organization where id in (select org_id from ozo_send_org where app_id=:appId)", nativeQuery = true)
    List<Organization> findConfirmOrgsByAppId(@Param("appId") Long appId);

    @Query(value = "select * from organization where communal_id is not null order by id", nativeQuery = true)
    List<Organization> findCommunalOrgs();

    Organization findFirstByOrgType(OrgType valueOf);
}
