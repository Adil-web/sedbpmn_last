package kz.kazgisa.data.entity.appinfo;


import kz.kazgisa.data.entity.base.FullNameEntity;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class LandInfo extends FullNameEntity {
    private String orgName;
    private Long copyCount;
    private String protocolNumber;
    private Date protocolDate;
    private String mioNumber;
    private Date mioDate;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Long getCopyCount() {
        return copyCount;
    }

    public void setCopyCount(Long copyCount) {
        this.copyCount = copyCount;
    }

    public String getProtocolNumber() {
        return protocolNumber;
    }

    public void setProtocolNumber(String protocolNumber) {
        this.protocolNumber = protocolNumber;
    }

    public Date getProtocolDate() {
        return protocolDate;
    }

    public void setProtocolDate(Date protocolDate) {
        this.protocolDate = protocolDate;
    }

    public String getMioNumber() {
        return mioNumber;
    }

    public void setMioNumber(String mioNumber) {
        this.mioNumber = mioNumber;
    }

    public Date getMioDate() {
        return mioDate;
    }

    public void setMioDate(Date mioDate) {
        this.mioDate = mioDate;
    }
}
