package kz.kazgisa.data.repositories.appinfo;

import kz.kazgisa.data.entity.appinfo.GasInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GasInfoRepository extends JpaRepository<GasInfo, Long> {
}
