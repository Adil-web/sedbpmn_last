package kz.kazgisa.data.entity;

import kz.kazgisa.data.entity.base.BaseEntity;

import javax.persistence.Entity;

@Entity
public class OrgConfirmer extends BaseEntity {
    private Long orgId;
    private Long userId;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
