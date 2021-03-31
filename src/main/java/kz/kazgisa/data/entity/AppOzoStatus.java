package kz.kazgisa.data.entity;

import kz.kazgisa.data.entity.base.BaseEntity;
import kz.kazgisa.data.enums.Status;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class AppOzoStatus extends BaseEntity {
    private Status status;
    private Date date;
    private String message;
    private Long appOzoId;
    private Long userId;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getAppOzoId() {
        return appOzoId;
    }

    public void setAppOzoId(Long appOzoId) {
        this.appOzoId = appOzoId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
