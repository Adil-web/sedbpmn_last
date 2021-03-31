package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.App;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface AppCountRepository extends JpaRepository<App, Long> {
    @Query(value = "select count(*) as all from" +
            " (" +
            " select app.id, app.reg_date, ss.name_ru as subservice, app.last_name, app.first_name, app.second_name, app.org_name, app.arch_signed_date as finish_date, app.plan_end_date, st.status from app " +
            " inner join subservice ss on app.subservice_id=ss.id" +
            " inner join service s on ss.service_id=s.id" +
            " inner join app_status st on app.app_status_id=st.id" +
            " where ss.id!=14 and" +
            " (app.arch_tech is null or app.arch_tech=false) and st.status!='DRAFT'" +
            " UNION" +
            " select app.id, coalesce(app.reg_date,app.create_date) as reg_date, ss.name_ru as subservice, app.last_name, app.first_name, app.second_name, app.org_name, app.ozo_signed_date as finish_date, app.plan_end_date, st.status from app " +
            " inner join subservice ss on app.subservice_id=ss.id" +
            " inner join service s on ss.service_id=s.id" +
            " inner join app_status st on app.app_status_id=st.id" +
            " where ss.id=14 and st.status!='DRAFT'" +
            " ) apps", nativeQuery = true)
    int getCountAll();

    @Query(value = "select count(*) as finished from" +
            " (" +
            " select app.id, app.reg_date, ss.name_ru as subservice, app.last_name, app.first_name, app.second_name, app.org_name, app.arch_signed_date as finish_date, app.plan_end_date, st.status from app " +
            " inner join subservice ss on app.subservice_id=ss.id" +
            " inner join service s on ss.service_id=s.id" +
            " inner join app_status st on app.app_status_id=st.id" +
            " where ss.id!=14 and" +
            " (app.arch_tech is null or app.arch_tech=false) and arch_signed=true" +
            " UNION" +
            " select app.id, coalesce(app.reg_date,app.create_date) as reg_date, ss.name_ru as subservice, app.last_name, app.first_name, app.second_name, app.org_name, app.ozo_signed_date as finish_date, app.plan_end_date, st.status from app " +
            " inner join subservice ss on app.subservice_id=ss.id" +
            " inner join service s on ss.service_id=s.id" +
            " inner join app_status st on app.app_status_id=st.id" +
            " where ss.id=14 and ozo_signed=true" +
            " ) apps", nativeQuery = true)
    int getCountFinished();

    @Query(value = "select count(*) as approved from" +
            " (" +
            " select app.id, app.reg_date, ss.name_ru as subservice, app.last_name, app.first_name, app.second_name, app.org_name, app.arch_signed_date as finish_date, app.plan_end_date, st.status from app " +
            " inner join subservice ss on app.subservice_id=ss.id" +
            " inner join service s on ss.service_id=s.id" +
            " inner join app_status st on app.app_status_id=st.id" +
            " where ss.id!=14 and st.status='APPROVED' and " +
            " (app.arch_tech is null or app.arch_tech=false) and arch_signed=true" +
            " UNION" +
            " select app.id, coalesce(app.reg_date,app.create_date) as reg_date, ss.name_ru as subservice, app.last_name, app.first_name, app.second_name, app.org_name, app.ozo_signed_date as finish_date, app.plan_end_date, st.status from app " +
            " inner join subservice ss on app.subservice_id=ss.id" +
            " inner join service s on ss.service_id=s.id" +
            " inner join app_status st on app.app_status_id=st.id" +
            " where ss.id=14 and ozo_signed=true" +
            " ) apps", nativeQuery = true)
    int getCountApproved();

    @Query(value = "select count(*) as rejected from" +
            " (" +
            " select app.id, app.reg_date, ss.name_ru as subservice, app.last_name, app.first_name, app.second_name, app.org_name, app.arch_signed_date as finish_date, app.plan_end_date, st.status from app " +
            " inner join subservice ss on app.subservice_id=ss.id" +
            " inner join service s on ss.service_id=s.id" +
            " inner join app_status st on app.app_status_id=st.id" +
            " where ss.id!=14 and st.status='REJECTED' and " +
            " (app.arch_tech is null or app.arch_tech=false) and arch_signed=true" +
            " UNION" +
            " select app.id, coalesce(app.reg_date,app.create_date) as reg_date, ss.name_ru as subservice, app.last_name, app.first_name, app.second_name, app.org_name, app.ozo_signed_date as finish_date, app.plan_end_date, st.status from app " +
            " inner join subservice ss on app.subservice_id=ss.id" +
            " inner join service s on ss.service_id=s.id" +
            " inner join app_status st on app.app_status_id=st.id" +
            " where ss.id=14 and st.status='REJECTED' and ozo_signed=true" +
            " ) apps", nativeQuery = true)
    int getCountRejected();

    @Query(value = "select count(*) as registered from" +
            " (" +
            " select app.id, app.reg_date, ss.name_ru as subservice, app.last_name, app.first_name, app.second_name, app.org_name, app.arch_signed_date as finish_date, app.plan_end_date, st.status from app " +
            " inner join subservice ss on app.subservice_id=ss.id" +
            " inner join service s on ss.service_id=s.id" +
            " inner join app_status st on app.app_status_id=st.id" +
            " where ss.id!=14 and" +
            " (app.arch_tech is null or app.arch_tech=false) and st.status!='DRAFT' and st.status!='APPLIED' and (arch_signed=false or arch_signed is null)" +
            " UNION" +
            " " +
            " select app.id, coalesce(app.reg_date,app.create_date) as reg_date, ss.name_ru as subservice, app.last_name, app.first_name, app.second_name, app.org_name, app.ozo_signed_date as finish_date, app.plan_end_date, st.status from app " +
            " inner join subservice ss on app.subservice_id=ss.id" +
            " inner join service s on ss.service_id=s.id" +
            " inner join app_status st on app.app_status_id=st.id" +
            " where ss.id=14 and st.status!='DRAFT' and st.status!='APPLIED' and (ozo_signed=false or ozo_signed is null)" +
            " ) apps", nativeQuery = true)
    int getCountInProgress();

    @Query(value = "select count(*) as all from" +
            " (" +
            " select app.id, app.reg_date, ss.name_ru as subservice, app.last_name, app.first_name, app.second_name, app.org_name, app.arch_signed_date as finish_date, app.plan_end_date, st.status from app " +
            " inner join subservice ss on app.subservice_id=ss.id" +
            " inner join service s on ss.service_id=s.id" +
            " inner join app_status st on app.app_status_id=st.id" +
            " where ss.id!=14 and app.plan_end_date < app.arch_signed_date and " +
            " (app.arch_tech is null or app.arch_tech=false) and st.status!='DRAFT'" +
            " UNION" +
            " select app.id, coalesce(app.reg_date,app.create_date) as reg_date, ss.name_ru as subservice, app.last_name, app.first_name, app.second_name, app.org_name, app.ozo_signed_date as finish_date, app.plan_end_date, st.status from app " +
            " inner join subservice ss on app.subservice_id=ss.id" +
            " inner join service s on ss.service_id=s.id" +
            " inner join app_status st on app.app_status_id=st.id" +
            " where ss.id=14 and st.status!='DRAFT' and app.plan_end_date < app.ozo_signed_date " +
            " ) apps", nativeQuery = true)
    int getCountExpired();

    @Query(value = "select count(*) from" +
            " (" +
            " select app.id, app.reg_date, ss.name_ru as subservice, app.last_name, app.first_name, app.second_name, app.org_name, app.arch_signed_date as finish_date, app.plan_end_date, st.status from app " +
            " inner join subservice ss on app.subservice_id=ss.id" +
            " inner join service s on ss.service_id=s.id" +
            " inner join app_status st on app.app_status_id=st.id" +
            " where ss.id!=14 and" +
            " (app.arch_tech is null or app.arch_tech=false) and st.status='APPLIED'" +
            " UNION" +
            " select app.id, coalesce(app.reg_date,app.create_date) as reg_date, ss.name_ru as subservice, app.last_name, app.first_name, app.second_name, app.org_name, app.ozo_signed_date as finish_date, app.plan_end_date, st.status from app " +
            " inner join subservice ss on app.subservice_id=ss.id" +
            " inner join service s on ss.service_id=s.id" +
            " inner join app_status st on app.app_status_id=st.id" +
            " where ss.id=14 and st.status='APPLIED'" +
            " ) apps", nativeQuery = true)
    int getCountApplied();

    // ------------------------------------------------------------------------------------

    @Query(value = "select count(*) from app " +
            " inner join subservice ss on app.subservice_id=ss.id" +
            " inner join service s on ss.service_id=s.id" +
            " inner join app_status st on app.app_status_id=st.id" +
            " where ss.id=:subserviceId and" +
            " (app.arch_tech is null or app.arch_tech=false) and st.status!='DRAFT'", nativeQuery = true)
    int getCountAll(@Param("subserviceId") Long subserviceId);

    @Query(value = "select count(*) from app " +
            " inner join subservice ss on app.subservice_id=ss.id" +
            " inner join service s on ss.service_id=s.id" +
            " inner join app_status st on app.app_status_id=st.id" +
            " where ss.id=:subserviceId", nativeQuery = true)
    int getCountFinished(@Param("subserviceId") Long subserviceId);

    @Query(value = "select count(*) from app " +
            " inner join subservice ss on app.subservice_id=ss.id" +
            " inner join service s on ss.service_id=s.id" +
            " inner join app_status st on app.app_status_id=st.id" +
            " where ss.id=:subserviceId and st.status='APPROVED' and " +
            " (app.arch_tech is null or app.arch_tech=false) and arch_signed=true", nativeQuery = true)
    int getCountApproved(@Param("subserviceId") Long subserviceId);

    @Query(value = "select count(*) from app " +
            " inner join subservice ss on app.subservice_id=ss.id" +
            " inner join service s on ss.service_id=s.id" +
            " inner join app_status st on app.app_status_id=st.id" +
            " where ss.id=subserviceId and st.status='REJECTED' and " +
            " (app.arch_tech is null or app.arch_tech=false) and arch_signed=true", nativeQuery = true)
    int getCountRejected(@Param("subserviceId") Long subserviceId);

    @Query(value = "select count(*) from app " +
            " inner join subservice ss on app.subservice_id=ss.id" +
            " inner join service s on ss.service_id=s.id" +
            " inner join app_status st on app.app_status_id=st.id" +
            " where ss.id=:subserviceId and" +
            " (app.arch_tech is null or app.arch_tech=false) and st.status!='DRAFT' and st.status!='APPLIED' and (arch_signed=false or arch_signed is null)", nativeQuery = true)
    int getCountInProgress(@Param("subserviceId") Long subserviceId);

    @Query(value = "select count(*) from app " +
            " inner join subservice ss on app.subservice_id=ss.id" +
            " inner join service s on ss.service_id=s.id" +
            " inner join app_status st on app.app_status_id=st.id" +
            " where ss.id=:subserviceId and app.plan_end_date < app.arch_signed_date and " +
            " (app.arch_tech is null or app.arch_tech=false) and st.status!='DRAFT'", nativeQuery = true)
    int getCountExpired(@Param("subserviceId") Long subserviceId);

    @Query(value = "select app.id, app.reg_date, ss.name_ru as subservice, app.last_name, app.first_name, app.second_name, app.org_name, app.arch_signed_date as finish_date, app.plan_end_date, st.status from app " +
            " inner join subservice ss on app.subservice_id=ss.id" +
            " inner join service s on ss.service_id=s.id" +
            " inner join app_status st on app.app_status_id=st.id" +
            " where ss.id=:subserviceId and" +
            " (app.arch_tech is null or app.arch_tech=false) and st.status='APPLIED'", nativeQuery = true)
    int getCountApplied(@Param("subserviceId") Long subserviceId);


    @Query(value = "select count(*) as finished from" +
            " (" +
            " select app.id, app.reg_date, ss.name_ru as subservice, app.last_name, app.first_name, app.second_name, app.org_name, app.arch_signed_date as finish_date, app.plan_end_date, st.status from app " +
            " inner join subservice ss on app.subservice_id=ss.id" +
            " inner join service s on ss.service_id=s.id" +
            " inner join app_status st on app.app_status_id=st.id" +
            " where ss.id=:subserviceId and" +
            " (app.arch_tech is null or app.arch_tech=false) and arch_signed=true" +
            " and st.status!='REJECTED' " +
            " and app.signed_date between :startDate and :endDate" +
            " ) apps", nativeQuery = true)
    int getCountApprovedBySubservice(@Param("subserviceId") Long subserviceId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "select count(*) as finished from" +
            " (" +
            " select app.id, coalesce(app.reg_date,app.create_date) as reg_date, ss.name_ru as subservice, app.last_name, app.first_name, app.second_name, app.org_name, app.ozo_signed_date as finish_date, app.plan_end_date, st.status from app " +
            " inner join subservice ss on app.subservice_id=ss.id" +
            " inner join service s on ss.service_id=s.id" +
            " inner join app_status st on app.app_status_id=st.id" +
            " where ss.id=14 and ozo_signed=true" +
            " and st.status!='REJECTED' " +
            " and app.signed_date between :startDate and :endDate" +
            " ) apps", nativeQuery = true)
    int getCountApprovedBySubserviceIjs(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "select count(*) as finished from" +
            " (" +
            " select app.id, app.reg_date, ss.name_ru as subservice, app.last_name, app.first_name, app.second_name, app.org_name, app.arch_signed_date as finish_date, app.plan_end_date, st.status from app " +
            " inner join subservice ss on app.subservice_id=ss.id" +
            " inner join service s on ss.service_id=s.id" +
            " inner join app_status st on app.app_status_id=st.id" +
            " where ss.id=:subserviceId and" +
            " (app.arch_tech is null or app.arch_tech=false) and arch_signed=true" +
            " and st.status='REJECTED' " +
            " and app.signed_date between :startDate and :endDate" +
            " ) apps", nativeQuery = true)
    int getCountRejectedBySubservice(@Param("subserviceId") Long subserviceId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "select count(*) as finished from" +
            " (" +
            " select app.id, coalesce(app.reg_date,app.create_date) as reg_date, ss.name_ru as subservice, app.last_name, app.first_name, app.second_name, app.org_name, app.ozo_signed_date as finish_date, app.plan_end_date, st.status from app " +
            " inner join subservice ss on app.subservice_id=ss.id" +
            " inner join service s on ss.service_id=s.id" +
            " inner join app_status st on app.app_status_id=st.id" +
            " where ss.id=14 and ozo_signed=true" +
            " and st.status='REJECTED' " +
            " and app.signed_date between :startDate and :endDate" +
            " ) apps", nativeQuery = true)
    int getCountRejectedBySubserviceIjs(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "select count(*) as finished from" +
            " (" +
            " select app.id, app.reg_date, ss.name_ru as subservice, app.last_name, app.first_name, app.second_name, app.org_name, app.arch_signed_date as finish_date, app.plan_end_date, st.status from app " +
            " inner join subservice ss on app.subservice_id=ss.id" +
            " inner join service s on ss.service_id=s.id" +
            " inner join app_status st on app.app_status_id=st.id" +
            " where ss.id=:subserviceId and" +
            " (app.arch_tech is null or app.arch_tech=false) and arch_signed=true" +
            " and app.signed_date between :startDate and :endDate" +
            " ) apps", nativeQuery = true)
    int getCountFinishedBySubservice(@Param("subserviceId") Long subserviceId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "select count(*) as finished from" +
            " (" +
            " select app.id, coalesce(app.reg_date,app.create_date) as reg_date, ss.name_ru as subservice, app.last_name, app.first_name, app.second_name, app.org_name, app.ozo_signed_date as finish_date, app.plan_end_date, st.status from app " +
            " inner join subservice ss on app.subservice_id=ss.id" +
            " inner join service s on ss.service_id=s.id" +
            " inner join app_status st on app.app_status_id=st.id" +
            " where ss.id=14 and ozo_signed=true" +
            " and app.signed_date between :startDate and :endDate" +
            " ) apps", nativeQuery = true)
    int getCountFinishedBySubserviceIjs(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "select count(*) as finished from" +
            " (" +
            " select app.id, app.reg_date, ss.name_ru as subservice, app.last_name, app.first_name, app.second_name, app.org_name, app.arch_signed_date as finish_date, app.plan_end_date, st.status from app " +
            " inner join subservice ss on app.subservice_id=ss.id" +
            " inner join service s on ss.service_id=s.id" +
            " inner join app_status st on app.app_status_id=st.id" +
            " where ss.id=:subserviceId and st.status='APPLIED' and " +
            " (app.arch_tech is null or app.arch_tech=false) " +
            " and app.signed_date between :startDate and :endDate" +
            " ) apps", nativeQuery = true)
    int getCountAppliedBySubservice(@Param("subserviceId") Long subserviceId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "select count(*) as finished from" +
            " (" +
            " select app.id, coalesce(app.reg_date,app.create_date) as reg_date, ss.name_ru as subservice, app.last_name, app.first_name, app.second_name, app.org_name, app.ozo_signed_date as finish_date, app.plan_end_date, st.status from app " +
            " inner join subservice ss on app.subservice_id=ss.id" +
            " inner join service s on ss.service_id=s.id" +
            " inner join app_status st on app.app_status_id=st.id" +
            " where ss.id=14 and st.status='APPLIED' " +
            " and app.signed_date between :startDate and :endDate" +
            " ) apps", nativeQuery = true)
    int getCountAppliedBySubserviceIjs(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "select count(*) as finished from" +
            " (" +
            " select app.id, app.reg_date, ss.name_ru as subservice, app.last_name, app.first_name, app.second_name, app.org_name, app.arch_signed_date as finish_date, app.plan_end_date, st.status from app " +
            " inner join subservice ss on app.subservice_id=ss.id" +
            " inner join service s on ss.service_id=s.id" +
            " inner join app_status st on app.app_status_id=st.id" +
            " where ss.id=:subserviceId and " +
            " (st.status='REGISTERED' or ((st.status='APPROVED' or st.status='REJECTED') and (app.arch_signed is null or app.arch_signed=false))) and " +
            " (app.arch_tech is null or app.arch_tech=false) " +
            " and app.signed_date between :startDate and :endDate" +
            " ) apps", nativeQuery = true)
    int getCountRegisteredBySubservice(@Param("subserviceId") Long subserviceId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "select count(*) as finished from" +
            " (" +
            " select app.id, coalesce(app.reg_date,app.create_date) as reg_date, ss.name_ru as subservice, app.last_name, app.first_name, app.second_name, app.org_name, app.ozo_signed_date as finish_date, app.plan_end_date, st.status from app " +
            " inner join subservice ss on app.subservice_id=ss.id" +
            " inner join service s on ss.service_id=s.id" +
            " inner join app_status st on app.app_status_id=st.id" +
            " where ss.id=14 and " +
            " (st.status='REGISTERED' or ((st.status='APPROVED' or st.status='REJECTED') and (app.arch_signed is null or app.arch_signed=false))) " +
            " and app.signed_date between :startDate and :endDate" +
            " ) apps", nativeQuery = true)
    int getCountRegisteredBySubserviceIjs(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "select count(*) as finished from" +
            " (" +
            " select app.id, app.reg_date, ss.name_ru as subservice, app.last_name, app.first_name, app.second_name, app.org_name, app.arch_signed_date as finish_date, app.plan_end_date, st.status from app " +
            " inner join subservice ss on app.subservice_id=ss.id" +
            " inner join service s on ss.service_id=s.id" +
            " inner join app_status st on app.app_status_id=st.id" +
            " where ss.id=:subserviceId and" +
            " (app.arch_tech is null or app.arch_tech=false) and arch_signed=true" +
            " and app.arch_signed_date between :startDate and :endDate" +
            " ) apps", nativeQuery = true)
    int getCountPeriodFinishedBySubservice(@Param("subserviceId") Long subserviceId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "select count(*) as finished from" +
            " (" +
            " select app.id, coalesce(app.reg_date,app.create_date) as reg_date, ss.name_ru as subservice, app.last_name, app.first_name, app.second_name, app.org_name, app.ozo_signed_date as finish_date, app.plan_end_date, st.status from app " +
            " inner join subservice ss on app.subservice_id=ss.id" +
            " inner join service s on ss.service_id=s.id" +
            " inner join app_status st on app.app_status_id=st.id" +
            " where ss.id=14 and ozo_signed=true" +
            " and app.ozo_signed_date between :startDate and :endDate" +
            " ) apps", nativeQuery = true)
    int getCountPeriodFinishedBySubserviceIjs(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "select count(*) as finished from" +
            " (" +
            " select app.id, app.reg_date, ss.name_ru as subservice, app.last_name, app.first_name, app.second_name, app.org_name, app.arch_signed_date as finish_date, app.plan_end_date, st.status from app " +
            " inner join subservice ss on app.subservice_id=ss.id" +
            " inner join service s on ss.service_id=s.id" +
            " inner join app_status st on app.app_status_id=st.id" +
            " where ss.id=:subserviceId and" +
            " (app.arch_tech is null or app.arch_tech=false) and arch_signed=true" +
            " and st.status!='REJECTED' " +
            " and app.arch_signed_date between :startDate and :endDate" +
            " ) apps", nativeQuery = true)
    int getCountPeriodApprovedBySubservice(@Param("subserviceId") Long subserviceId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "select count(*) as finished from" +
            " (" +
            " select app.id, coalesce(app.reg_date,app.create_date) as reg_date, ss.name_ru as subservice, app.last_name, app.first_name, app.second_name, app.org_name, app.ozo_signed_date as finish_date, app.plan_end_date, st.status from app " +
            " inner join subservice ss on app.subservice_id=ss.id" +
            " inner join service s on ss.service_id=s.id" +
            " inner join app_status st on app.app_status_id=st.id" +
            " where ss.id=14 and ozo_signed=true" +
            " and st.status!='REJECTED' " +
            " and app.ozo_signed_date between :startDate and :endDate" +
            " ) apps", nativeQuery = true)
    int getCountPeriodApprovedBySubserviceIjs(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "select count(*) as finished from" +
            " (" +
            " select app.id, app.reg_date, ss.name_ru as subservice, app.last_name, app.first_name, app.second_name, app.org_name, app.arch_signed_date as finish_date, app.plan_end_date, st.status from app " +
            " inner join subservice ss on app.subservice_id=ss.id" +
            " inner join service s on ss.service_id=s.id" +
            " inner join app_status st on app.app_status_id=st.id" +
            " where ss.id=:subserviceId and" +
            " (app.arch_tech is null or app.arch_tech=false) and arch_signed=true" +
            " and st.status='REJECTED' " +
            " and app.arch_signed_date between :startDate and :endDate" +
            " ) apps", nativeQuery = true)
    int getCountPeriodRejectedBySubservice(@Param("subserviceId") Long subserviceId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "select count(*) as finished from" +
            " (" +
            " select app.id, coalesce(app.reg_date,app.create_date) as reg_date, ss.name_ru as subservice, app.last_name, app.first_name, app.second_name, app.org_name, app.ozo_signed_date as finish_date, app.plan_end_date, st.status from app " +
            " inner join subservice ss on app.subservice_id=ss.id" +
            " inner join service s on ss.service_id=s.id" +
            " inner join app_status st on app.app_status_id=st.id" +
            " where ss.id=14 and ozo_signed=true" +
            " and st.status='REJECTED' " +
            " and app.ozo_signed_date between :startDate and :endDate" +
            " ) apps", nativeQuery = true)
    int getCountPeriodRejectedBySubserviceIjs(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
