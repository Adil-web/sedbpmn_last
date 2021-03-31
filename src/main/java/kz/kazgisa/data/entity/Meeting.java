package kz.kazgisa.data.entity;

import kz.kazgisa.data.entity.base.BaseEntity;
import org.springframework.data.annotation.Transient;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class Meeting extends BaseEntity {
    private String number;
    private Date createDate;
    private Date date;
    private Boolean finished = false;
    private Date finishedDate;
    private Long appsCount;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Date getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(Date finishedDate) {
        this.finishedDate = finishedDate;
    }

    @Transient
    public Long getAppsCount() {
        return appsCount;
    }

    public void setAppsCount(Long appsCount) {
        this.appsCount = appsCount;
    }
}
