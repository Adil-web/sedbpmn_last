package kz.kazgisa.data.entity;

import kz.kazgisa.data.entity.base.FileEntity;
import kz.kazgisa.data.enums.FileCategory;

import javax.persistence.Entity;

@Entity
public class AddressFile extends FileEntity {
    private Long appId;
    private FileCategory fileCategory;

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public FileCategory getFileCategory() {
        return fileCategory;
    }

    public void setFileCategory(FileCategory fileCategory) {
        this.fileCategory = fileCategory;
    }
}
