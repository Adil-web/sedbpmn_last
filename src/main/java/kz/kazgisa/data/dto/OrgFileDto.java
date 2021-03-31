package kz.kazgisa.data.dto;

import kz.kazgisa.data.entity.AppOrgFile;
import kz.kazgisa.data.entity.Organization;

import java.util.List;

public class OrgFileDto {
    private Organization organization;
    private List<AppOrgFile> appOrgFiles;
    private Long appOrgId;
    private Boolean hasTechCondition;

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public List<AppOrgFile> getAppOrgFiles() {
        return appOrgFiles;
    }

    public void setAppOrgFiles(List<AppOrgFile> appOrgFiles) {
        this.appOrgFiles = appOrgFiles;
    }

    public Long getAppOrgId() {
        return appOrgId;
    }

    public void setAppOrgId(Long appOrgId) {
        this.appOrgId = appOrgId;
    }

    public Boolean getHasTechCondition() {
        return hasTechCondition;
    }

    public void setHasTechCondition(Boolean hasTechCondition) {
        this.hasTechCondition = hasTechCondition;
    }
}
