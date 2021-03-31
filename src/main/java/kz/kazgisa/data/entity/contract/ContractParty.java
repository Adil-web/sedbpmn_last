package kz.kazgisa.data.entity.contract;

import kz.kazgisa.data.entity.base.FullNameEntity;
import kz.kazgisa.data.entity.base.LocalNameEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class ContractParty extends FullNameEntity {
    private String companyPositionRu;
    private String companyPositionKk;
    private String companyName;
    private String companyBin;
    private String iin;
    private boolean legal;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyBin() {
        return companyBin;
    }

    public void setCompanyBin(String companyBin) {
        this.companyBin = companyBin;
    }

    public String getIin() {
        return iin;
    }

    public void setIin(String iin) {
        this.iin = iin;
    }

    @Column(columnDefinition = "boolean default false")
    public boolean isLegal() {
        return legal;
    }

    public void setLegal(boolean legal) {
        this.legal = legal;
    }

    public String getCompanyPositionRu() {
        return companyPositionRu;
    }

    public void setCompanyPositionRu(String companyPositionRu) {
        this.companyPositionRu = companyPositionRu;
    }

    public String getCompanyPositionKk() {
        return companyPositionKk;
    }

    public void setCompanyPositionKk(String companyPositionKk) {
        this.companyPositionKk = companyPositionKk;
    }
}
