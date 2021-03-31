package kz.kazgisa.data.dto;

import kz.kazgisa.data.enums.Status;

import java.util.Date;
import java.util.List;

public class AppDutyDto {
    private Long id;
    private String subserviceShortNameRu;
    private String orgName;
    private String firstName;
    private String lastName;
    private String secondName;
    private String iin;
    private String bin;
    private String cadastreNumber;
    private String geometry;
    private String address;
    private String phone;
    private Date regDate;
    private Date finishDate;
    private Status status;
    private List<FileDutyDto> files;
    private FileDutyDto resultFile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubserviceShortNameRu() {
        return subserviceShortNameRu;
    }

    public void setSubserviceShortNameRu(String subserviceShortNameRu) {
        this.subserviceShortNameRu = subserviceShortNameRu;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
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

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public String getCadastreNumber() {
        return cadastreNumber;
    }

    public void setCadastreNumber(String cadastreNumber) {
        this.cadastreNumber = cadastreNumber;
    }

    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
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

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<FileDutyDto> getFiles() {
        return files;
    }

    public void setFiles(List<FileDutyDto> files) {
        this.files = files;
    }

    public FileDutyDto getResultFile() {
        return resultFile;
    }

    public void setResultFile(FileDutyDto resultFile) {
        this.resultFile = resultFile;
    }
}
