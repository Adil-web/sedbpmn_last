package kz.kazgisa.data.repositories.appinfo;

import kz.kazgisa.data.entity.appinfo.SewerageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SewerageInfoRepository extends JpaRepository<SewerageInfo, Long> {
}
