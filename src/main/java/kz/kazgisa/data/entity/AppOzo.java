package kz.kazgisa.data.entity;

import kz.kazgisa.data.entity.base.FullNameEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class AppOzo extends FullNameEntity {
    private String iin;
    private String address;
    private String phone;
    private String inputNumber;
    private String actNumber;
    private Date actDate;
    private AppOzoStatus currentStatus;
    private Date endDate;
    private String areaWkt;
    private String areaParams;
    private Double areaSize;
    private String scale;

    public String getIin() {
        return iin;
    }

    public void setIin(String iin) {
        this.iin = iin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getInputNumber() {
        return inputNumber;
    }

    public void setInputNumber(String inputNumber) {
        this.inputNumber = inputNumber;
    }

    public String getActNumber() {
        return actNumber;
    }

    public void setActNumber(String actNumber) {
        this.actNumber = actNumber;
    }

    public Date getActDate() {
        return actDate;
    }

    public void setActDate(Date actDate) {
        this.actDate = actDate;
    }

    @ManyToOne
    @JoinColumn(name = "app_ozo_status_id")
    public AppOzoStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(AppOzoStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Column(columnDefinition = "text")
    public String getAreaWkt() {
        return areaWkt;
    }

    public void setAreaWkt(String areaWkt) {
        this.areaWkt = areaWkt;
    }

    public String getAreaParams() {
        return areaParams;
    }

    public void setAreaParams(String areaParams) {
        this.areaParams = areaParams;
    }

    public Double getAreaSize() {
        return areaSize;
    }

    public void setAreaSize(Double areaSize) {
        this.areaSize = areaSize;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }
}
