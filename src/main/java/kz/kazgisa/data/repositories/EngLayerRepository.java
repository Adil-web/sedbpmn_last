package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.engineering.EngLayer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EngLayerRepository extends JpaRepository<EngLayer,Long> {
    List<EngLayer> findByIdIn(List<Long> ids);
}
