package kz.kazgisa.data.entity.engineering;


import kz.kazgisa.data.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "gu_attributes")
public class GuAttribute extends BaseEntity {
    private Boolean isMain;
    private Boolean isEnum;
    private Long layerId;
    private String type;
    private String nameRu;
    private String nameKz;
    private String nameEn;

    @Column(name = "is_main")
    public Boolean getMain() {
        return isMain;
    }

    public void setMain(Boolean main) {
        isMain = main;
    }

    @Column(name = "is_enum")
    public Boolean getEnum() {
        return isEnum;
    }

    public void setEnum(Boolean anEnum) {
        isEnum = anEnum;
    }

    public Long getLayerId() {
        return layerId;
    }

    public void setLayerId(Long layerId) {
        this.layerId = layerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public String getNameKz() {
        return nameKz;
    }

    public void setNameKz(String nameKz) {
        this.nameKz = nameKz;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }
}
