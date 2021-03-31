package kz.kazgisa.data.entity;

import kz.kazgisa.data.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Entity
public class LandSelectionData extends BaseEntity {
    private Long appId;
    private String city;
    private String fullName;
    private String objectName;
    private String address;
    private String cadastreNumber;
    private String coordinates;
    private String purpose;
    private String area;
    private String parkingArea;
    private String greeningArea;
    private String extendingArea;
    private String assigned;
    private String greeningYears;
    private String parkingYears;
    private String extendingYears;
    private String objectAdditionalInfo;
    private String restrictions;
    private String matchesNorm;
    private String landNeeds;
    private String executor;
    private String inquiryPhone;
    private Date agreementDate;
    private String landAreaJson;
    private String landYearsJson;
    private String mapImage;

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCadastreNumber() {
        return cadastreNumber;
    }

    public void setCadastreNumber(String cadastreNumber) {
        this.cadastreNumber = cadastreNumber;
    }

    @Column(length = 4000)
    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    @Column(length = 1000)
    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getParkingArea() {
        return parkingArea;
    }

    public void setParkingArea(String parkingArea) {
        this.parkingArea = parkingArea;
    }

    public String getGreeningArea() {
        return greeningArea;
    }

    public void setGreeningArea(String greeningArea) {
        this.greeningArea = greeningArea;
    }

    public String getAssigned() {
        return assigned;
    }

    public void setAssigned(String assigned) {
        this.assigned = assigned;
    }

    public String getGreeningYears() {
        return greeningYears;
    }

    public void setGreeningYears(String greeningYears) {
        this.greeningYears = greeningYears;
    }

    public String getParkingYears() {
        return parkingYears;
    }

    public void setParkingYears(String parkingYears) {
        this.parkingYears = parkingYears;
    }

    public String getObjectAdditionalInfo() {
        return objectAdditionalInfo;
    }

    public void setObjectAdditionalInfo(String objectAdditionalInfo) {
        this.objectAdditionalInfo = objectAdditionalInfo;
    }

    @Column(length = 1000)
    public String getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

    public String getMatchesNorm() {
        return matchesNorm;
    }

    public void setMatchesNorm(String matchesNorm) {
        this.matchesNorm = matchesNorm;
    }

    public String getLandNeeds() {
        return landNeeds;
    }

    public void setLandNeeds(String landNeeds) {
        this.landNeeds = landNeeds;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public String getInquiryPhone() {
        return inquiryPhone;
    }

    public void setInquiryPhone(String inquiryPhone) {
        this.inquiryPhone = inquiryPhone;
    }

    public Date getAgreementDate() {
        return agreementDate;
    }

    public void setAgreementDate(Date agreementDate) {
        this.agreementDate = agreementDate;
    }

    public String getExtendingArea() {
        return extendingArea;
    }

    public void setExtendingArea(String extendingArea) {
        this.extendingArea = extendingArea;
    }

    public String getExtendingYears() {
        return extendingYears;
    }

    public void setExtendingYears(String extendingYears) {
        this.extendingYears = extendingYears;
    }

    @Column(length = 4000)
    public String getLandAreaJson() {
        return landAreaJson;
    }

    public void setLandAreaJson(String landAreaJson) {
        this.landAreaJson = landAreaJson;
    }

    @Column(length = 4000)
    public String getLandYearsJson() {
        return landYearsJson;
    }

    public void setLandYearsJson(String landYearsJson) {
        this.landYearsJson = landYearsJson;
    }

    @Column(columnDefinition = "text")
    public String getMapImage() {
        return mapImage;
    }

    public void setMapImage(String mapImage) {
        this.mapImage = mapImage;
    }
}
