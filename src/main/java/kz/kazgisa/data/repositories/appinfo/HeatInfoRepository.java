package kz.kazgisa.data.repositories.appinfo;

import kz.kazgisa.data.entity.appinfo.HeatInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeatInfoRepository extends JpaRepository<HeatInfo, Long> {
}
