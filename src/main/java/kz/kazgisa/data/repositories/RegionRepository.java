package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    Region findFirstById(Long id);
}
