package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.Apz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ApzRepository extends JpaRepository<Apz, Long> {
    Apz findFirstByAppId(Long appId);

    @Query(value = "select id from apz where app_id = :appId limit 1", nativeQuery = true)
    Long getIdByAppId(@Param("appId") Long appId);
}
