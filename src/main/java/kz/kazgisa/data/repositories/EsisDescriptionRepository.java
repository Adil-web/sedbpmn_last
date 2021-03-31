package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.engineering.EsisDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EsisDescriptionRepository extends JpaRepository<EsisDescription, Long>, JpaSpecificationExecutor<EsisDescription> {
    EsisDescription findByLayerName(String LayerName);
    List<EsisDescription> findByIdIn(List<Long> ids);
}
