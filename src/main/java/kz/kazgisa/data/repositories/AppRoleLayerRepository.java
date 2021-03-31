package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.AppRoleLayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppRoleLayerRepository extends JpaRepository<AppRoleLayer, Long> {
    List<AppRoleLayer> findByAppRoleId(Long appRoleId);
}

