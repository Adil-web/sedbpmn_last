package kz.kazgisa.data.repositories.appinfo;

import kz.kazgisa.data.entity.appinfo.ElectricInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectricInfoRepository extends JpaRepository<ElectricInfo, Long> {
}
