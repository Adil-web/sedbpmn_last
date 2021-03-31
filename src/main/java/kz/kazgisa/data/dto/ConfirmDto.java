package kz.kazgisa.data.dto;

import kz.kazgisa.data.entity.AppUserConfirm;
import kz.kazgisa.data.entity.Organization;

import java.util.List;

public class ConfirmDto {
    private Organization organization;
    private List<AppUserConfirm> appUserConfirms;

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public List<AppUserConfirm> getAppUserConfirms() {
        return appUserConfirms;
    }

    public void setAppUserConfirms(List<AppUserConfirm> appUserConfirms) {
        this.appUserConfirms = appUserConfirms;
    }
}
