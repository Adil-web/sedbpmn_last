package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.SignUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SignUserRepository extends JpaRepository<SignUser, Long> {
    List<SignUser> findBySubserviceIdAndRegionId(Long subserviceId, Long regionId);
}
