package kz.kazgisa.data.entity.dict;

import kz.kazgisa.data.entity.base.LocalNameEntity;

import javax.persistence.Entity;

@Entity
public class EstateObjectType extends LocalNameEntity {
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
