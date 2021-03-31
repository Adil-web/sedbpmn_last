package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.LandSelectionData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LandSelectionDataRepository extends JpaRepository<LandSelectionData, Long> {
    LandSelectionData findFirstByAppId(Long appId);
}
