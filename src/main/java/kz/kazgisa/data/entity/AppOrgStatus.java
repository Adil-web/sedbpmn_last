package kz.kazgisa.data.entity;

import kz.kazgisa.data.entity.base.BaseEntity;
import kz.kazgisa.data.enums.Status;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
public class AppOrgStatus extends BaseEntity {
    private Status status;
    private Date date;
    private String message;
    private Long appOrganizationId;
    private Long userId;
    private String actionNumber;
    private Date actionDate;
    private String lastStatus;

    @Enumerated(EnumType.STRING)
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(columnDefinition = "text")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getAppOrganizationId() {
        return appOrganizationId;
    }

    public void setAppOrganizationId(Long appOrganizationId) {
        this.appOrganizationId = appOrganizationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getActionNumber() {
        return actionNumber;
    }

    public void setActionNumber(String actionNumber) {
        this.actionNumber = actionNumber;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public String getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(String lastStatus) {
        this.lastStatus = lastStatus;
    }
}
