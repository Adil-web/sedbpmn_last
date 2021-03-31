package kz.kazgisa.data.entity;

import kz.kazgisa.data.entity.base.LocalNameEntity;
import kz.kazgisa.data.entity.dict.Communal;
import kz.kazgisa.data.enums.OrgType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Organization extends LocalNameEntity {
    private String name;
    private Communal communal;
    private String shortName;
    private String shortNameRu;
    private String shortNameKk;
    private String shortNameEn;
//    private byte[] headerImage;
    private Boolean isOzoConfirmer;
    private Long regionId;
    private OrgType orgType;
    private String headUserName;
    private String headUserPositionKk;
    private String headUserPositionRu;
    private String headUserShortName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    @JoinColumn(name = "communal_id")
    public Communal getCommunal() {
        return communal;
    }

    public void setCommunal(Communal communal) {
        this.communal = communal;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getShortNameRu() {
        return shortNameRu;
    }

    public void setShortNameRu(String shortNameRu) {
        this.shortNameRu = shortNameRu;
    }

    public String getShortNameKk() {
        return shortNameKk;
    }

    public void setShortNameKk(String shortNameKk) {
        this.shortNameKk = shortNameKk;
    }

    public String getShortNameEn() {
        return shortNameEn;
    }

    public void setShortNameEn(String shortNameEn) {
        this.shortNameEn = shortNameEn;
    }

    public Boolean getOzoConfirmer() {
        return isOzoConfirmer;
    }

    public void setOzoConfirmer(Boolean ozoConfirmer) {
        isOzoConfirmer = ozoConfirmer;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    @Enumerated(EnumType.STRING)
    public OrgType getOrgType() {
        return orgType;
    }

    public void setOrgType(OrgType orgType) {
        this.orgType = orgType;
    }

    public String getHeadUserName() {
        return headUserName;
    }

    public void setHeadUserName(String headUserName) {
        this.headUserName = headUserName;
    }

    public String getHeadUserPositionKk() {
        return headUserPositionKk;
    }

    public void setHeadUserPositionKk(String headUserPositionKk) {
        this.headUserPositionKk = headUserPositionKk;
    }

    public String getHeadUserPositionRu() {
        return headUserPositionRu;
    }

    public void setHeadUserPositionRu(String headUserPositionRu) {
        this.headUserPositionRu = headUserPositionRu;
    }

    public String getHeadUserShortName() {
        return headUserShortName;
    }

    public void setHeadUserShortName(String headUserShortName) {
        this.headUserShortName = headUserShortName;
    }
}
