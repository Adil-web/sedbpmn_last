package kz.kazgisa.data.entity;

import kz.kazgisa.data.entity.base.BaseEntity;
import kz.kazgisa.data.entity.dict.Subservice;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UserSubservice extends BaseEntity {
    private Subservice subservice;
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "subservice_id")
    public Subservice getSubservice() {
        return subservice;
    }

    public void setSubservice(Subservice subservice) {
        this.subservice = subservice;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
