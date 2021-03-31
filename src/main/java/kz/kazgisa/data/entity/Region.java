package kz.kazgisa.data.entity;

import kz.kazgisa.data.entity.base.LocalNameEntity;

import javax.persistence.Entity;

@Entity
public class Region extends LocalNameEntity {
    private Long parentId;
    private String declensionRu;
    private String declensionKk;
    private String declensionRuAkim;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getDeclensionRu() {
        return declensionRu;
    }

    public void setDeclensionRu(String declensionRu) {
        this.declensionRu = declensionRu;
    }

    public String getDeclensionKk() {
        return declensionKk;
    }

    public void setDeclensionKk(String declensionKk) {
        this.declensionKk = declensionKk;
    }

    public String getDeclensionRuAkim() {
        return declensionRuAkim;
    }

    public void setDeclensionRuAkim(String declensionRuAkim) {
        this.declensionRuAkim = declensionRuAkim;
    }
}
