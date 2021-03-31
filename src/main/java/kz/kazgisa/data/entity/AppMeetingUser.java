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
public class AppMeetingUser extends BaseEntity {
    private Long appId;
    private Long meetingUserId;
    private Status status;
    private Date statusDate;
    private String message;

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Long getMeetingUserId() {
        return meetingUserId;
    }

    public void setMeetingUserId(Long meetingUserId) {
        this.meetingUserId = meetingUserId;
    }

    @Enumerated(EnumType.STRING)
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    @Column(columnDefinition = "text")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
