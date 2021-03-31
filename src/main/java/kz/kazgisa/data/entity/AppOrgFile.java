package kz.kazgisa.data.entity;

import kz.kazgisa.data.entity.base.FileEntity;
import kz.kazgisa.data.enums.FileCategory;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class AppOrgFile extends FileEntity {
    private AppOrganization appOrganization;
    private FileCategory fileCategory;

    @ManyToOne
    @JoinColumn(name = "app_organization_id")
    @com.fasterxml.jackson.annotation.JsonIgnore
    public AppOrganization getAppOrganization() {
        return appOrganization;
    }

    public void setAppOrganization(AppOrganization appOrganization) {
        this.appOrganization = appOrganization;
    }

    @Enumerated(EnumType.STRING)
    public FileCategory getFileCategory() {
        return fileCategory;
    }

    public void setFileCategory(FileCategory fileCategory) {
        this.fileCategory = fileCategory;
    }
}
