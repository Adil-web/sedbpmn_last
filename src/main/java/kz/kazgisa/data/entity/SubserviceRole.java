package kz.kazgisa.data.entity;

import kz.kazgisa.data.entity.base.BaseEntity;
import kz.kazgisa.data.entity.dict.Subservice;
import kz.kazgisa.data.entity.user.Role;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class SubserviceRole extends BaseEntity {
    private Subservice subservice;
    private Role role;

    @ManyToOne
    public Subservice getSubservice() {
        return subservice;
    }

    public void setSubservice(Subservice subservice) {
        this.subservice = subservice;
    }

    @ManyToOne
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
