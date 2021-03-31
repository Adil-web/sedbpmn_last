package kz.kazgisa.data.entity.appinfo;

import kz.kazgisa.data.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
public class OzoInfo extends BaseEntity {
    private String inputNumber;
    private Date inputDate;
    private String actNumber;
    private Date actDate;
    private String areaAddress;
    private Double areaSize;
    private String areaParams;
    private String scale;
    private String location;
    private Long locationId;
    private Long oldLocationId;
    private String generalConditions;
    private String districts;
    private String agreementObjectId;
    private String note;

    public String getInputNumber() {
        return inputNumber;
    }

    public void setInputNumber(String inputNumber) {
        this.inputNumber = inputNumber;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    public String getActNumber() {
        return actNumber;
    }

    public void setActNumber(String actNumber) {
        this.actNumber = actNumber;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getActDate() {
        return actDate;
    }

    public void setActDate(Date actDate) {
        this.actDate = actDate;
    }

    public String getAreaAddress() {
        return areaAddress;
    }

    public void setAreaAddress(String areaAddress) {
        this.areaAddress = areaAddress;
    }

    public Double getAreaSize() {
        return areaSize;
    }

    public void setAreaSize(Double areaSize) {
        this.areaSize = areaSize;
    }

    public String getAreaParams() {
        return areaParams;
    }

    public void setAreaParams(String areaParams) {
        this.areaParams = areaParams;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    @Column(columnDefinition = "text")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getOldLocationId() {
        return oldLocationId;
    }

    public void setOldLocationId(Long oldLocationId) {
        this.oldLocationId = oldLocationId;
    }

    @Column(length = 5000)
    public String getGeneralConditions() {
        return generalConditions;
    }

    public void setGeneralConditions(String generalConditions) {
        this.generalConditions = generalConditions;
    }

    public String getDistricts() {
        return districts;
    }

    public void setDistricts(String districts) {
        this.districts = districts;
    }

    public String getAgreementObjectId() {
        return agreementObjectId;
    }

    public void setAgreementObjectId(String agreementObjectId) {
        this.agreementObjectId = agreementObjectId;
    }

    @Column(length = 5000)
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
