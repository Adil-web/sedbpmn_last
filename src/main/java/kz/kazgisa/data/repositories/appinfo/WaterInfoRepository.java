package kz.kazgisa.data.repositories.appinfo;

import kz.kazgisa.data.entity.appinfo.WaterInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaterInfoRepository extends JpaRepository<WaterInfo, Long> {
}
