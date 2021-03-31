package kz.kazgisa.data.entity;

import kz.kazgisa.data.entity.base.BaseEntity;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class UnusualDay extends BaseEntity {
    private Date date;
    private Boolean workDay;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getWorkDay() {
        return workDay;
    }

    public void setWorkDay(Boolean workDay) {
        this.workDay = workDay;
    }
}
