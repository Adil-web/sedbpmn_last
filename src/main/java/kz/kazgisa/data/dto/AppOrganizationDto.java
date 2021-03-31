package kz.kazgisa.data.dto;

import kz.kazgisa.data.entity.*;

import java.util.Date;
import java.util.List;

public class AppOrganizationDto {
    private Long id;
    private AppDto app;
    private Organization organization;
    private List<AppOrgObject> appOrgObjects;
    private List<AppOrgFile> appOrgFiles;
    private String connections;
    private String connectionsData;
    private Boolean signed;
    private Date signedDate;
    private Boolean toSign;

    private AppOrgStatus currentStatus;
    private Boolean hasTechCondition;

    private String recommendation;
    private Long recommendationUserId;
    private Date recommendationDate;

    private String confirmerStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppDto getApp() {
        return app;
    }

    public void setApp(AppDto app) {
        this.app = app;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public List<AppOrgObject> getAppOrgObjects() {
        return appOrgObjects;
    }

    public void setAppOrgObjects(List<AppOrgObject> appOrgObjects) {
        this.appOrgObjects = appOrgObjects;
    }

    public List<AppOrgFile> getAppOrgFiles() {
        return appOrgFiles;
    }

    public void setAppOrgFiles(List<AppOrgFile> appOrgFiles) {
        this.appOrgFiles = appOrgFiles;
    }

    public String getConnections() {
        return connections;
    }

    public void setConnections(String connections) {
        this.connections = connections;
    }

    public String getConnectionsData() {
        return connectionsData;
    }

    public void setConnectionsData(String connectionsData) {
        this.connectionsData = connectionsData;
    }

    public Boolean getSigned() {
        return signed;
    }

    public void setSigned(Boolean signed) {
        this.signed = signed;
    }

    public Date getSignedDate() {
        return signedDate;
    }

    public void setSignedDate(Date signedDate) {
        this.signedDate = signedDate;
    }

    public Boolean getToSign() {
        return toSign;
    }

    public void setToSign(Boolean toSign) {
        this.toSign = toSign;
    }

    public AppOrgStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(AppOrgStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Boolean getHasTechCondition() {
        return hasTechCondition;
    }

    public void setHasTechCondition(Boolean hasTechCondition) {
        this.hasTechCondition = hasTechCondition;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public Long getRecommendationUserId() {
        return recommendationUserId;
    }

    public void setRecommendationUserId(Long recommendationUserId) {
        this.recommendationUserId = recommendationUserId;
    }

    public Date getRecommendationDate() {
        return recommendationDate;
    }

    public void setRecommendationDate(Date recommendationDate) {
        this.recommendationDate = recommendationDate;
    }

    public String getConfirmerStatus() {
        return confirmerStatus;
    }

    public void setConfirmerStatus(String confirmerStatus) {
        this.confirmerStatus = confirmerStatus;
    }
}
