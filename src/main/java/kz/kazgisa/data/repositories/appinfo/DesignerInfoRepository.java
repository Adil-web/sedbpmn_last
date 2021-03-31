package kz.kazgisa.data.repositories.appinfo;

import kz.kazgisa.data.entity.appinfo.DesignerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignerInfoRepository extends JpaRepository<DesignerInfo, Long> {
}
