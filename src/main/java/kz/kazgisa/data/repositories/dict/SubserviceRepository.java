package kz.kazgisa.data.repositories.dict;

import kz.kazgisa.data.entity.dict.Subservice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubserviceRepository extends JpaRepository<Subservice, Long>, JpaSpecificationExecutor<Subservice> {
    List<Subservice> findByServiceIdOrderByPriority(Long serviceId);
    List<Subservice> findByTagsContaining(String tags);

    @Query(value = "select * from subservice where id in " +
            "(select subservice_id from user_subservice where user_id=:userId)", nativeQuery = true)
    List<Subservice> getByUserId(@Param("userId") Long userId);

    Page<Subservice> findByNameRuContainingIgnoreCase(String name, Pageable pageable);

    Subservice findFirstByCode(String tuJaryk);
}
