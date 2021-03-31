package kz.kazgisa.data.entity;

import kz.kazgisa.data.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Entity
public class CommissionControl extends BaseEntity {
    private Long appId;
    private Date date;
    private String text;

    public CommissionControl() {}
    public CommissionControl(Long appId, Date date, String text) {
        this.appId = appId;
        this.date = date;
        this.text = text;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(columnDefinition = "text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
