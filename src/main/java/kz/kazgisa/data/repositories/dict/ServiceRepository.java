package kz.kazgisa.data.repositories.dict;

import kz.kazgisa.data.entity.dict.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    List<Service> findByServiceTypeId(Long serviceTypeId);
}
