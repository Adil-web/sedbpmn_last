package kz.kazgisa.data.entity;

import kz.kazgisa.data.entity.base.FullNameEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class AppUserConfirm extends FullNameEntity {
    private Long appId;
    private Long userId;
    private Long organizationId;
    private Boolean confirmed;
    private String message;

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    @Column(columnDefinition = "text")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
