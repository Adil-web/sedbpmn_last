package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.engineering.EngAttr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EngAttrRepository extends JpaRepository<EngAttr, Long> {
    List<EngAttr> findByLayerId(Long layerId);
}