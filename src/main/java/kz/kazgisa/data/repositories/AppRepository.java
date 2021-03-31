package kz.kazgisa.data.repositories;

import kz.kazgisa.data.dto.AppDutyDto;
import kz.kazgisa.data.dto.AppDutyView;
import kz.kazgisa.data.entity.App;
import kz.kazgisa.data.entity.dict.Service;
import kz.kazgisa.data.entity.dict.Subservice;
import kz.kazgisa.data.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.DoubleStream;

@Repository
public interface AppRepository extends JpaRepository<App, Long>, JpaSpecificationExecutor<App> {

    App findByAppId(Long id);

    Page<App> findByControlAndSubserviceInAndArchSignedFalse(String control, List<Subservice> list, Pageable pageable);

    Page<App> findBySubserviceInAndArchSignedTrue(List<Subservice> list,Pageable pageable);

    Page<App> findBySubserviceInAndArchSignedFalseAndOpenTrue(List<Subservice> list,Pageable pageable);

    Long countBySubserviceInAndArchSignedFalseAndOpenTrue(List<Subservice> list);

    Page<App> findBySubserviceInAndArchSignedFalseAndOpenFalse(List<Subservice> list,Pageable pageable);

    Long countBySubserviceInAndFactEndDateIsNotNull(List<Subservice> list);

    Long countBySubserviceInAndControlAndArchSignedFalse(List<Subservice> list,String username);

    List<App> findByObjectInfoCadastreNumber(String cadastreNumber);

    int countBySubserviceIdInAndSignedDateBetweenAndOpenTrue(List<Long> subservices, Date startDate, Date endDate);

    int countBySubserviceIdInAndSignedDateBetweenAndApprovedTrueAndFactEndDateIsNotNull(List<Long> subservices, Date startDate, Date endDate);

    int countBySubserviceIdInAndSignedDateBetweenAndApprovedFalseAndFactEndDateIsNotNull(List<Long> subservices, Date startDate, Date endDate);

    int countBySubserviceIdInAndSignedDateBetweenAndApprovedIsNullAndFactEndDateIsNotNull(List<Long> subservices, Date startDate, Date endDate);

    int countBySubserviceIdInAndSignedDateBetweenAndOpenTrueAndFactEndDateIsNull(List<Long> subservices, Date startDate, Date endDate);

    int countBySubserviceIdInAndSignedDateBetweenAndOpenTrueAndSubserviceService(List<Long> subservices, Date startDate, Date endDate, Service service);

    int countBySubserviceIdInAndSignedDateBetweenAndApprovedTrueAndFactEndDateIsNotNullAndSubserviceService(List<Long> subservices, Date startDate, Date endDate, Service service);

    int countBySubserviceIdInAndSignedDateBetweenAndApprovedFalseAndFactEndDateIsNotNullAndSubserviceService(List<Long> subservices, Date startDate, Date endDate, Service service);

    int countBySubserviceIdInAndSignedDateBetweenAndApprovedIsNullAndFactEndDateIsNotNullAndSubserviceService(List<Long> subservices, Date startDate, Date endDate, Service service);

    int countBySubserviceIdInAndSignedDateBetweenAndOpenTrueAndFactEndDateIsNullAndSubserviceService(List<Long> subservices, Date startDate, Date endDate, Service service);

    int countBySubserviceIdInAndSignedDateBetweenAndOpenTrueAndSubservice(List<Long> subservices, Date startDate, Date endDate, Subservice subservice);

    int countBySubserviceIdInAndSignedDateBetweenAndApprovedTrueAndFactEndDateIsNotNullAndSubservice(List<Long> subservices, Date startDate, Date endDate, Subservice subservice);

    int countBySubserviceIdInAndSignedDateBetweenAndApprovedFalseAndFactEndDateIsNotNullAndSubservice(List<Long> subservices, Date startDate, Date endDate, Subservice subservice);

    int countBySubserviceIdInAndSignedDateBetweenAndApprovedIsNullAndFactEndDateIsNotNullAndSubservice(List<Long> subservices, Date startDate, Date endDate, Subservice subservice);

    int countBySubserviceIdInAndSignedDateBetweenAndOpenTrueAndFactEndDateIsNullAndSubservice(List<Long> subservices, Date startDate, Date endDate, Subservice subservice);

//    @Query(value = "select id,first_name firstName,last_name lastName,second_name secondName,iin from app", nativeQuery = true)
    @Query(value = "select app.id,app.first_name firstName,app.last_name lastName,app.second_name secondName," +
            "app.iin,app.bin,app.address,app.phone,app.org_name orgName,app.approved," +
            "app.create_date createDate,app.fact_end_date factEndDate," +
            "oi.cadastre_number cadastreNumber,ST_AsText(ST_Transform(ST_GeomFromGeoJSON(cast(oi.location as json)->'geometry'),4326)) geometry," +
            "ss.short_name_ru subserviceShortNameRu from app " +
            "inner join object_info oi on oi.id=app.object_info_id " +
            "inner join subservice ss on ss.id=app.subservice_id " +
            "where (cast(:cadastreNumber as varchar) is null or oi.cadastre_number like cast(:cadastreNumber as varchar)) and " +
            "(:serviceId is null or ss.service_id=cast(cast(:serviceId as varchar) as bigint)) " +
            "order by app.create_date", nativeQuery = true)
    List<AppDutyView> findDutyApps(@Param("cadastreNumber") String cadastreNumber, @Param("serviceId") Long serviceId);

    Page<App> findByRegionIdAndOpenFalse(Long regionId, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "update app set app_status_id = null where id in(:appId);" +
            "update app_organization set app_org_status_id = null where app_id in(:appId);" +
            "delete from app_status where app_id in(:appId);" +
            "delete from app_org_status where app_organization_id in (select id from app_organization where app_id in(:appId));" +
            "delete from app_org_file where app_organization_id in (select id from app_organization where app_id in(:appId));" +
            "delete from app_organization where app_id in(:appId);" +
            "delete from app_file where app_id in(:appId);" +
            "delete from apz where app_id in(:appId);" +
            "delete from open_app_user where app_id in(:appId);" +
            "delete from app where id in(:appId);", nativeQuery = true)
    void deleteApp(@Param("appId") Long appId);
}
