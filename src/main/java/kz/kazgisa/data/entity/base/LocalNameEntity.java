package kz.kazgisa.data.entity.base;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class LocalNameEntity extends BaseEntity {
    private String nameRu;
    private String nameKk;
    private String nameEn;

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

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }
}
