package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.OrgTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrgTemplateRepository extends JpaRepository<OrgTemplate, Long> {
    List<OrgTemplate> findByOrgId(Long orgId);
    List<OrgTemplate> findByOrgIdAndApproved(Long orgId, Boolean approved);

    List<OrgTemplate> findByOrgIdAndSubserviceId(Long orgId, Long subserviceId);
    List<OrgTemplate> findByOrgIdAndSubserviceIdAndApproved(Long orgId, Long subserviceId, Boolean approved);
}
