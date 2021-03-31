package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.OpenAppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpenAppUserRepository extends JpaRepository<OpenAppUser, Long> {
    OpenAppUser findFirstByAppIdAndUserId(Long appId, Long userId);
    List<OpenAppUser> findByAppId(Long appId);
}
