package kz.kazgisa.data.entity.user;

import kz.kazgisa.data.entity.base.BaseEntity;
import kz.kazgisa.data.entity.dict.Subservice;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class UserRole extends BaseEntity {
    private Role role;
    private String username;
    private Subservice subservice;
    private User user;
    private Boolean current;

    @ManyToOne
    @JoinColumn(name = "role_id")
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @ManyToOne
    public Subservice getSubservice() {
        return subservice;
    }

    public void setSubservice(Subservice subservice) {
        this.subservice = subservice;
    }

    @Transient  
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(columnDefinition = "boolean default true")
    public Boolean getCurrent() {
        return current;
    }

    public void setCurrent(Boolean current) {
        this.current = current;
    }

}
