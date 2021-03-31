package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.OrgConfirmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrgConfirmerRepository extends JpaRepository<OrgConfirmer, Long> {
    List<OrgConfirmer> findByOrOrgId(Long orgId);
}
