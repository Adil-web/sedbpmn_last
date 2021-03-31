package kz.kazgisa.data.entity;

import kz.kazgisa.data.entity.base.BaseEntity;

import javax.persistence.Entity;

@Entity
public class AppRoleLayer extends BaseEntity {
    private Long appRoleId;
    private Long layerId;

    public Long getAppRoleId() {
        return appRoleId;
    }

    public void setAppRoleId(Long appRoleId) {
        this.appRoleId = appRoleId;
    }

    public Long getLayerId() {
        return layerId;
    }

    public void setLayerId(Long layerId) {
        this.layerId = layerId;
    }
}

