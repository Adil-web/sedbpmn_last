package kz.kazgisa.data.entity;

import kz.kazgisa.data.entity.base.BaseEntity;
import kz.kazgisa.data.enums.Status;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class AppOrgConfirm extends BaseEntity {
    private AppOrganization appOrganization;
    private Long userId;
    private Status status;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "app_organization_id")
    public AppOrganization getAppOrganization() {
        return appOrganization;
    }

    public void setAppOrganization(AppOrganization appOrganization) {
        this.appOrganization = appOrganization;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
