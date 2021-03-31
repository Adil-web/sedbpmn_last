package kz.kazgisa.data.entity.dict;

import kz.kazgisa.data.entity.base.LocalNameEntity;

import javax.persistence.Entity;

@Entity
public class Service extends LocalNameEntity {
    private String shortNameKk;
    private String shortNameRu;
    private String shortNameEn;
    private Long serviceTypeId;
    private String code;

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

    public Long getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(Long serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
