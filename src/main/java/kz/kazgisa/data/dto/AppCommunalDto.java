package kz.kazgisa.data.dto;

import kz.kazgisa.data.entity.AppOrgFile;
import kz.kazgisa.data.enums.Status;

import java.util.Date;
import java.util.List;

public class AppCommunalDto {
    private Long id;
    private String name;
    private Boolean isSent;
    private Date sendDate;
    private Date responseDate;
    private Status status;
    private String connections;
    private List<AppOrgFile> appOrgFiles;
    private Long appOrgId;
    private Boolean hasTechCondition;
    private Boolean signed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSent() {
        return isSent;
    }

    public void setSent(Boolean sent) {
        isSent = sent;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Date getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(Date responseDate) {
        this.responseDate = responseDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getConnections() {
        return connections;
    }

    public void setConnections(String connections) {
        this.connections = connections;
    }

    public List<AppOrgFile> getAppOrgFiles() {
        return appOrgFiles;
    }

    public void setAppOrgFiles(List<AppOrgFile> appOrgFiles) {
        this.appOrgFiles = appOrgFiles;
    }

    public Long getAppOrgId() {
        return appOrgId;
    }

    public void setAppOrgId(Long appOrgId) {
        this.appOrgId = appOrgId;
    }

    public Boolean getHasTechCondition() {
        return hasTechCondition;
    }

    public void setHasTechCondition(Boolean hasTechCondition) {
        this.hasTechCondition = hasTechCondition;
    }

    public Boolean getSigned() {
        return signed;
    }

    public void setSigned(Boolean signed) {
        this.signed = signed;
    }
}

