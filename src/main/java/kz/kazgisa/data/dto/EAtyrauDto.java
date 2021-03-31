package kz.kazgisa.data.dto;

import java.util.Date;

public class EAtyrauDto {
    private Long appId;
    private Boolean approved;
    private String archSignedXml;
    private Date archSignedDate;

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public String getArchSignedXml() {
        return archSignedXml;
    }

    public void setArchSignedXml(String archSignedXml) {
        this.archSignedXml = archSignedXml;
    }

    public Date getArchSignedDate() {
        return archSignedDate;
    }

    public void setArchSignedDate(Date archSignedDate) {
        this.archSignedDate = archSignedDate;
    }
}
