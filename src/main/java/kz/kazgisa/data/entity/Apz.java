package kz.kazgisa.data.entity;

import kz.kazgisa.data.entity.base.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
@Entity
public class Apz extends BaseEntity {
    private App app;
    private Organization organization;
    private String number;
    private Date date;
    private String baseNumber;
    private Date baseDate;
    private String areaCharInfo;
    private String objectCharInfo;
    private String gradReqInfo;
    private String archReqInfo;
    private String exteriorReqInfo;
    private String engNetworkReqInfo;
    private String devCommitmentInfo;
    private String objectNameKz;
    private String objectNameRu;
    private String declarerNameKz;
    private String declarerNameRu;

    public String getDeclarerNameKz() {
        return declarerNameKz;
    }

    public void setDeclarerNameKz(String declarerNameKz) {
        this.declarerNameKz = declarerNameKz;
    }

    public String getDeclarerNameRu() {
        return declarerNameRu;
    }

    public void setDeclarerNameRu(String declarerNameRu) {
        this.declarerNameRu = declarerNameRu;
    }

    public String getObjectNameKz() {
        return objectNameKz;
    }

    public void setObjectNameKz(String objectNameKz) {
        this.objectNameKz = objectNameKz;
    }

    public String getObjectNameRu() {
        return objectNameRu;
    }

    public void setObjectNameRu(String objectNameRu) {
        this.objectNameRu = objectNameRu;
    }

    private byte[] pdf;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "app_id")
    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "organization_id")
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBaseNumber() {
        return baseNumber;
    }

    public void setBaseNumber(String baseNumber) {
        this.baseNumber = baseNumber;
    }

    public Date getBaseDate() {
        return baseDate;
    }

    public void setBaseDate(Date baseDate) {
        this.baseDate = baseDate;
    }

    @Column(columnDefinition = "text")
    public String getAreaCharInfo() {
        return areaCharInfo;
    }

    public void setAreaCharInfo(String areaCharInfo) {
        this.areaCharInfo = areaCharInfo;
    }

    @Column(columnDefinition = "text")
    public String getObjectCharInfo() {
        return objectCharInfo;
    }

    public void setObjectCharInfo(String objectCharInfo) {
        this.objectCharInfo = objectCharInfo;
    }

    @Column(columnDefinition = "text")
    public String getGradReqInfo() {
        return gradReqInfo;
    }

    public void setGradReqInfo(String gradReqInfo) {
        this.gradReqInfo = gradReqInfo;
    }

    @Column(columnDefinition = "text")
    public String getArchReqInfo() {
        return archReqInfo;
    }

    public void setArchReqInfo(String archReqInfo) {
        this.archReqInfo = archReqInfo;
    }

    @Column(columnDefinition = "text")
    public String getExteriorReqInfo() {
        return exteriorReqInfo;
    }

    public void setExteriorReqInfo(String exteriorReqInfo) {
        this.exteriorReqInfo = exteriorReqInfo;
    }

    @Column(columnDefinition = "text")
    public String getEngNetworkReqInfo() {
        return engNetworkReqInfo;
    }

    public void setEngNetworkReqInfo(String engNetworkReqInfo) {
        this.engNetworkReqInfo = engNetworkReqInfo;
    }

    @Column(columnDefinition = "text")
    public String getDevCommitmentInfo() {
        return devCommitmentInfo;
    }

    public void setDevCommitmentInfo(String devCommitmentInfo) {
        this.devCommitmentInfo = devCommitmentInfo;
    }

    public byte[] getPdf() {
        return pdf;
    }

    public void setPdf(byte[] pdf) {
        this.pdf = pdf;
    }
}
