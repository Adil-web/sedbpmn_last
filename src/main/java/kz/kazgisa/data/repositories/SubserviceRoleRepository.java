package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.SubserviceRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubserviceRoleRepository extends JpaRepository<SubserviceRole, Long> {
    List<SubserviceRole> findBySubserviceId(Long id);

    List<SubserviceRole> findByRoleId(Long id);

    void deleteBySubserviceIdAndRoleId(Long id, Long roleId);
}
