package kz.kazgisa.data.repositories.appinfo;

import kz.kazgisa.data.entity.appinfo.OzoInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OzoInfoRepository extends JpaRepository<OzoInfo, Long> {
}
