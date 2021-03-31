package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.UserSubservice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSubserviceRepository extends JpaRepository<UserSubservice, Long> {
    List<UserSubservice> findByUserId(Long userId);
}
