package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.ValidApplicant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidApplicantRepository extends JpaRepository<ValidApplicant, Long> {
    @Query(value = "select * from valid_applicant where deleted=false or deleted is null", nativeQuery = true)
    Page<ValidApplicant> getValidApplicants(Pageable pageable);

    @Query(value = "select * from valid_applicant where (deleted=false or deleted is null) and iin like %:iin%", nativeQuery = true)
    Page<ValidApplicant> getValidApplicantsByIin(@Param("iin") String iin, Pageable pageable);

    @Query(value = "select * from valid_applicant where deleted=true", nativeQuery = true)
    Page<ValidApplicant> findDeletedValidApplicants(Pageable pageable);

    @Query(value = "select * from valid_applicant where deleted=true and iin like %:iin%", nativeQuery = true)
    Page<ValidApplicant> findDeletedValidApplicantsByIin(@Param("iin") String iin, Pageable pageable);

    ValidApplicant findFirstByIin(String iin);
}
