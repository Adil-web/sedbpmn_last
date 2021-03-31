package kz.kazgisa.data.entity;

import kz.kazgisa.data.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class OrgTemplate extends BaseEntity {
    private String nameRu;
    private String nameKk;
    private String text;
    private Long orgId;
    private Long subserviceId;
    private Boolean approved;
    private HeaderFile headerFile;

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public String getNameKk() {
        return nameKk;
    }

    public void setNameKk(String nameKk) {
        this.nameKk = nameKk;
    }

    @Column(length = 20000)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getSubserviceId() {
        return subserviceId;
    }

    public void setSubserviceId(Long subserviceId) {
        this.subserviceId = subserviceId;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }


    @ManyToOne
    public HeaderFile getHeaderFile() {
        return headerFile;
    }

    public void setHeaderFile(HeaderFile headerFile) {
        this.headerFile = headerFile;
    }
}
