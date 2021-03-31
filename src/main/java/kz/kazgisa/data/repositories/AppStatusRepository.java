package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.AppStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppStatusRepository extends JpaRepository<AppStatus, Long> {
    @Query(value = "select * from app_status where app_id=:appId and (status = 'APPROVED' or status = 'REJECTED') and message is not null order by date desc limit 1", nativeQuery = true)
    AppStatus getLastApprovedRejectedStatus(@Param("appId") Long appId);
    @Query(value = "select * from app_status where app_id=:appId and (status = 'APPROVED' or status = 'REJECTED') order by date desc limit 1", nativeQuery = true)
    AppStatus getLastApprovedRejectedStatusEmptyMessage(@Param("appId") Long appId);
    @Query(value = "select * from app_status where app_id=:appId and status = 'REWORK' order by date desc", nativeQuery = true)
    List<AppStatus> getReworkStatuses(@Param("appId") Long appId);
}
