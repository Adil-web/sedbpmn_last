package kz.kazgisa.data.entity;

import kz.kazgisa.data.entity.base.BaseEntity;
import kz.kazgisa.data.entity.dict.Subservice;
import kz.kazgisa.data.entity.user.User;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class SignUser extends BaseEntity {
    private Subservice subservice;
    private Long regionId;
    private User user;

    @ManyToOne
    public Subservice getSubservice() {
        return subservice;
    }

    public void setSubservice(Subservice subservice) {
        this.subservice = subservice;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
