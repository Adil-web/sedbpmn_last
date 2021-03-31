package kz.kazgisa.data.dto;

import kz.kazgisa.data.entity.Organization;
import kz.kazgisa.data.entity.user.Role;
import kz.kazgisa.data.enums.UserType;

import java.util.List;

public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String secondName;
    private String username;
    private Boolean blocked = false;
    private Boolean active = true;
    private String iin;
    private String bin;
    private String orgName;
    private UserType userType;
    private Organization organization;
    private List<Role> roles;
    private String link;
    private String positionRu;
    private String positionKk;
    private Boolean current;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getIin() {
        return iin;
    }

    public void setIin(String iin) {
        this.iin = iin;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPositionRu() {
        return positionRu;
    }

    public void setPositionRu(String positionRu) {
        this.positionRu = positionRu;
    }

    public String getPositionKk() {
        return positionKk;
    }

    public void setPositionKk(String positionKk) {
        this.positionKk = positionKk;
    }

    public Boolean getCurrent() {
        return current;
    }

    public void setCurrent(Boolean current) {
        this.current = current;
    }
}
