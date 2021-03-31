package kz.kazgisa.data.entity.dict;

import kz.kazgisa.data.entity.base.LocalNameEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Subservice extends LocalNameEntity {
    private Service service;
    private Integer days;
    private Boolean workdaysOnly = true;
    private String communals;
    private String signingOrgs;
    private Integer priority;

    private String shortNameKk;
    private String shortNameRu;
    private String shortNameEn;
    private String tags;
    private String code;

    @ManyToOne
    @JoinColumn(name = "service_id")
    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Boolean getWorkdaysOnly() {
        return workdaysOnly;
    }

    public void setWorkdaysOnly(Boolean workdaysOnly) {
        this.workdaysOnly = workdaysOnly;
    }

    public String getCommunals() {
        return communals;
    }

    public void setCommunals(String communals) {
        this.communals = communals;
    }

    public String getSigningOrgs() {
        return signingOrgs;
    }

    public void setSigningOrgs(String signingOrgs) {
        this.signingOrgs = signingOrgs;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getShortNameKk() {
        return shortNameKk;
    }

    public void setShortNameKk(String shortNameKk) {
        this.shortNameKk = shortNameKk;
    }

    public String getShortNameRu() {
        return shortNameRu;
    }

    public void setShortNameRu(String shortNameRu) {
        this.shortNameRu = shortNameRu;
    }

    public String getShortNameEn() {
        return shortNameEn;
    }

    public void setShortNameEn(String shortNameEn) {
        this.shortNameEn = shortNameEn;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
