package kz.kazgisa.data.dto;

import kz.kazgisa.data.dto.report.AppPepFileDto;

import java.util.List;

public class AppPepDto {
    private String id;
    private String firstName;
    private String lastName;
    private String secondName;
    private String iin;
    private String address;
    private String phone;
    private String objectAddress;
    private String purpose;
    private Double area;
    private String location;
    private String extendLocation;
    private String cadastreNumber;
    private List<AppPepFileDto> files;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

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

    public String getObjectAddress() {
        return objectAddress;
    }

    public void setObjectAddress(String objectAddress) {
        this.objectAddress = objectAddress;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getExtendLocation() {
        return extendLocation;
    }

    public void setExtendLocation(String extendLocation) {
        this.extendLocation = extendLocation;
    }

    public String getCadastreNumber() {
        return cadastreNumber;
    }

    public void setCadastreNumber(String cadastreNumber) {
        this.cadastreNumber = cadastreNumber;
    }

    public List<AppPepFileDto> getFiles() {
        return files;
    }

    public void setFiles(List<AppPepFileDto> files) {
        this.files = files;
    }
}
