package kz.kazgisa.data.entity;

import kz.kazgisa.data.entity.base.FullNameEntity;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class Person extends FullNameEntity {
    private String iin;
    private Date birthDate;
    private Date deathDate;
    private String gender;
    private String personStatus;
    private String nationality;
    private String citizenship;
    private String birthCountry;
    private String birthDistricts;
    private String birthRegion;
    private String birthCity;
    private String regAddressCountry;
    private String regAddressDistricts;
    private String regAddressRegion;
    private String regAddressCity;
    private String regAddressStreet;
    private String regAddressBuilding;
    private String regAddressCorpus;
    private String regAddressFlat;
    private String arCode;
    private String capableStatus;
    private Date capableDate;
    private String capableNumber;
    private String court;
    private Date capableEndDate;
    private String missing;
    private Date missingDate;
    private Date missingEndDate;
    private String missingNumber;
    private String gpTerritorial;
    private String excludeReason;
    private Date excludeReasonDate;
    private Date excludeDate;
    private String excludeParticipant;
    private String disappear;
    private Date disappearDate;
    private String disappearNumber;
    private Date disappearEndDate;
    private String disappearGpTerritorial;

    public String getIin() {
        return iin;
    }

    public void setIin(String iin) {
        this.iin = iin;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(Date deathDate) {
        this.deathDate = deathDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPersonStatus() {
        return personStatus;
    }

    public void setPersonStatus(String personStatus) {
        this.personStatus = personStatus;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getBirthCountry() {
        return birthCountry;
    }

    public void setBirthCountry(String birthCountry) {
        this.birthCountry = birthCountry;
    }

    public String getBirthDistricts() {
        return birthDistricts;
    }

    public void setBirthDistricts(String birthDistricts) {
        this.birthDistricts = birthDistricts;
    }

    public String getBirthRegion() {
        return birthRegion;
    }

    public void setBirthRegion(String birthRegion) {
        this.birthRegion = birthRegion;
    }

    public String getBirthCity() {
        return birthCity;
    }

    public void setBirthCity(String birthCity) {
        this.birthCity = birthCity;
    }

    public String getRegAddressCountry() {
        return regAddressCountry;
    }

    public void setRegAddressCountry(String regAddressCountry) {
        this.regAddressCountry = regAddressCountry;
    }

    public String getRegAddressDistricts() {
        return regAddressDistricts;
    }

    public void setRegAddressDistricts(String regAddressDistricts) {
        this.regAddressDistricts = regAddressDistricts;
    }

    public String getRegAddressRegion() {
        return regAddressRegion;
    }

    public void setRegAddressRegion(String regAddressRegion) {
        this.regAddressRegion = regAddressRegion;
    }

    public String getRegAddressCity() {
        return regAddressCity;
    }

    public void setRegAddressCity(String regAddressCity) {
        this.regAddressCity = regAddressCity;
    }

    public String getRegAddressStreet() {
        return regAddressStreet;
    }

    public void setRegAddressStreet(String regAddressStreet) {
        this.regAddressStreet = regAddressStreet;
    }

    public String getRegAddressBuilding() {
        return regAddressBuilding;
    }

    public void setRegAddressBuilding(String regAddressBuilding) {
        this.regAddressBuilding = regAddressBuilding;
    }

    public String getRegAddressCorpus() {
        return regAddressCorpus;
    }

    public void setRegAddressCorpus(String regAddressCorpus) {
        this.regAddressCorpus = regAddressCorpus;
    }

    public String getRegAddressFlat() {
        return regAddressFlat;
    }

    public void setRegAddressFlat(String regAddressFlat) {
        this.regAddressFlat = regAddressFlat;
    }

    public String getArCode() {
        return arCode;
    }

    public void setArCode(String arCode) {
        this.arCode = arCode;
    }

    public String getCapableStatus() {
        return capableStatus;
    }

    public void setCapableStatus(String capableStatus) {
        this.capableStatus = capableStatus;
    }

    public Date getCapableDate() {
        return capableDate;
    }

    public void setCapableDate(Date capableDate) {
        this.capableDate = capableDate;
    }

    public String getCapableNumber() {
        return capableNumber;
    }

    public void setCapableNumber(String capableNumber) {
        this.capableNumber = capableNumber;
    }

    public String getCourt() {
        return court;
    }

    public void setCourt(String court) {
        this.court = court;
    }

    public Date getCapableEndDate() {
        return capableEndDate;
    }

    public void setCapableEndDate(Date capableEndDate) {
        this.capableEndDate = capableEndDate;
    }

    public String getMissing() {
        return missing;
    }

    public void setMissing(String missing) {
        this.missing = missing;
    }

    public Date getMissingDate() {
        return missingDate;
    }

    public void setMissingDate(Date missingDate) {
        this.missingDate = missingDate;
    }

    public Date getMissingEndDate() {
        return missingEndDate;
    }

    public void setMissingEndDate(Date missingEndDate) {
        this.missingEndDate = missingEndDate;
    }

    public String getMissingNumber() {
        return missingNumber;
    }

    public void setMissingNumber(String missingNumber) {
        this.missingNumber = missingNumber;
    }

    public String getGpTerritorial() {
        return gpTerritorial;
    }

    public void setGpTerritorial(String gpTerritorial) {
        this.gpTerritorial = gpTerritorial;
    }

    public String getExcludeReason() {
        return excludeReason;
    }

    public void setExcludeReason(String excludeReason) {
        this.excludeReason = excludeReason;
    }

    public Date getExcludeReasonDate() {
        return excludeReasonDate;
    }

    public void setExcludeReasonDate(Date excludeReasonDate) {
        this.excludeReasonDate = excludeReasonDate;
    }

    public Date getExcludeDate() {
        return excludeDate;
    }

    public void setExcludeDate(Date excludeDate) {
        this.excludeDate = excludeDate;
    }

    public String getExcludeParticipant() {
        return excludeParticipant;
    }

    public void setExcludeParticipant(String excludeParticipant) {
        this.excludeParticipant = excludeParticipant;
    }

    public String getDisappear() {
        return disappear;
    }

    public void setDisappear(String disappear) {
        this.disappear = disappear;
    }

    public Date getDisappearDate() {
        return disappearDate;
    }

    public void setDisappearDate(Date disappearDate) {
        this.disappearDate = disappearDate;
    }

    public String getDisappearNumber() {
        return disappearNumber;
    }

    public void setDisappearNumber(String disappearNumber) {
        this.disappearNumber = disappearNumber;
    }

    public Date getDisappearEndDate() {
        return disappearEndDate;
    }

    public void setDisappearEndDate(Date disappearEndDate) {
        this.disappearEndDate = disappearEndDate;
    }

    public String getDisappearGpTerritorial() {
        return disappearGpTerritorial;
    }

    public void setDisappearGpTerritorial(String disappearGpTerritorial) {
        this.disappearGpTerritorial = disappearGpTerritorial;
    }
}
