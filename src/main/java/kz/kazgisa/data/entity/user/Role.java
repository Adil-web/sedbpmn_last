package kz.kazgisa.data.entity.user;

import kz.kazgisa.data.entity.base.LocalNameEntity;

import javax.persistence.Entity;

@Entity
public class Role extends LocalNameEntity {
    private String name;

    private String shortNameRu;
    private String shortNameKk;
    private String shortNameEn;
    private String description;
    private Integer num;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
