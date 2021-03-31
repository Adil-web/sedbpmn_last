package kz.kazgisa.data.entity;

import kz.kazgisa.data.entity.base.BaseEntity;
import kz.kazgisa.data.enums.Status;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class MeetingApp extends BaseEntity {
    private Long meetingId;
    private Long appId;
    private Integer orderNum;
    private Status status;
    private String message;
    private Long statusUserId;

    public MeetingApp() {}
    public MeetingApp(Long meetingId, Long appId, Integer orderNum) {
        this.meetingId = meetingId;
        this.appId = appId;
        this.orderNum = orderNum;
    }

    public Long getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Long meetingId) {
        this.meetingId = meetingId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    @Enumerated(EnumType.STRING)
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Column(length = 3000)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getStatusUserId() {
        return statusUserId;
    }

    public void setStatusUserId(Long statusUserId) {
        this.statusUserId = statusUserId;
    }
}
