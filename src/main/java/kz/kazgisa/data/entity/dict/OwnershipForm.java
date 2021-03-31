package kz.kazgisa.data.entity.dict;

import kz.kazgisa.data.entity.base.LocalNameEntity;

import javax.persistence.Entity;

// forma sobstvennosti
@Entity
public class OwnershipForm extends LocalNameEntity {
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
