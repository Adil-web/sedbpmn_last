package kz.kazgisa.data.entity;

import kz.kazgisa.data.entity.base.BaseEntity;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class Agreement extends BaseEntity {
    private String name;
    private Date date;
    private Date endDate;
    private Long termMonths;
    private Long userId;
    private Long clientOrgId;
    private Long orgId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getTermMonths() {
        return termMonths;
    }

    public void setTermMonths(Long termMonths) {
        this.termMonths = termMonths;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getClientOrgId() {
        return clientOrgId;
    }

    public void setClientOrgId(Long clientOrgId) {
        this.clientOrgId = clientOrgId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
}
