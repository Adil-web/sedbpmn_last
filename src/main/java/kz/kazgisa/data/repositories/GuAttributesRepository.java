package kz.kazgisa.data.repositories;


import kz.kazgisa.data.entity.engineering.GuAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuAttributesRepository extends JpaRepository<GuAttribute,Long>, JpaSpecificationExecutor<GuAttribute> {
    List<GuAttribute> findByLayerId(Long layerId);


}
