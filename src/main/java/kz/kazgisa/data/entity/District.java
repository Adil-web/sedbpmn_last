package kz.kazgisa.data.entity;

import kz.kazgisa.data.entity.base.BaseEntity;

import javax.persistence.Entity;

@Entity
public class District extends BaseEntity {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
