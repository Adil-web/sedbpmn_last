package kz.kazgisa.data.entity;

import kz.kazgisa.data.entity.base.LocalNameEntity;

import javax.persistence.Entity;

@Entity
public class ReportType extends LocalNameEntity {
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
