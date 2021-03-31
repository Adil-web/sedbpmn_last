package kz.kazgisa.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kz.kazgisa.data.entity.base.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@Entity
public class AppOrganization extends BaseEntity {
    private App app;
    private Organization organization;
//    private Status status;
//    private Date statusDate;
    private List<AppOrgObject> appOrgObjects;
    private String connections;
    private String connectionsData;
    private Boolean signed = false;
    private String signedXml;
    private Date signedDate;
    private AppOrgStatus currentStatus;
    private byte[] techCondition;
    private Integer techConditionFontSize;
    private Boolean toSign = false;
    private byte[] approvedFile;
    private byte[] approvedFile2;
    private byte[] rejectedFile;

    private String recommendation;
    private Long recommendationUserId;
    private Date recommendationDate;

    private String confirmerStatus;

    @ManyToOne
    @JoinColumn(name = "app_id")
    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    @ManyToOne
    @JoinColumn(name = "organization_id")
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

//    public Status getStatus() {
//        return status;
//    }
//
//    public void setStatus(Status status) {
//        this.status = status;
//    }
//
//    @Temporal(TemporalType.TIMESTAMP)
//    public Date getStatusDate() {
//        return statusDate;
//    }
//
//    public void setStatusDate(Date statusDate) {
//        this.statusDate = statusDate;
//    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "appOrgId")
    public List<AppOrgObject> getAppOrgObjects() {
        return appOrgObjects;
    }

    public void setAppOrgObjects(List<AppOrgObject> appOrgObjects) {
        this.appOrgObjects = appOrgObjects;
    }

    @Column(columnDefinition = "text")
    public String getConnections() {
        return connections;
    }

    public void setConnections(String connections) {
        this.connections = connections;
    }

    @Column(length = 5000)
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

    @Column(columnDefinition = "text")
    public String getSignedXml() {
        return signedXml;
    }

    public void setSignedXml(String signedXml) {
        this.signedXml = signedXml;
    }

    public Date getSignedDate() {
        return signedDate;
    }

    public void setSignedDate(Date signedDate) {
        this.signedDate = signedDate;
    }

    @ManyToOne
    @JoinColumn(name = "app_org_status_id")
    public AppOrgStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(AppOrgStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    @JsonIgnore
    public byte[] getTechCondition() {
        return techCondition;
    }

    public void setTechCondition(byte[] techCondition) {
        this.techCondition = techCondition;
    }

    public Integer getTechConditionFontSize() {
        return techConditionFontSize;
    }

    public void setTechConditionFontSize(Integer techConditionFontSize) {
        this.techConditionFontSize = techConditionFontSize;
    }

    public Boolean getToSign() {
        return toSign;
    }

    public void setToSign(Boolean toSign) {
        this.toSign = toSign;
    }

    public byte[] getApprovedFile() {
        return approvedFile;
    }

    public void setApprovedFile(byte[] approvedFile) {
        this.approvedFile = approvedFile;
    }

    public byte[] getApprovedFile2() {
        return approvedFile2;
    }

    public void setApprovedFile2(byte[] approvedFile2) {
        this.approvedFile2 = approvedFile2;
    }

    public byte[] getRejectedFile() {
        return rejectedFile;
    }

    public void setRejectedFile(byte[] rejectedFile) {
        this.rejectedFile = rejectedFile;
    }

    @Column(length = 5000)
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
