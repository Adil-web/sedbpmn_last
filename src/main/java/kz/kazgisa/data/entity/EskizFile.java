package kz.kazgisa.data.entity;

import kz.kazgisa.data.entity.base.FileEntity;

import javax.persistence.Entity;

@Entity
public class EskizFile extends FileEntity {
    private byte[] file;
    private Long appId;
    private String guid;
    private String fileCategory;

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getFileCategory() {
        return fileCategory;
    }

    public void setFileCategory(String fileCategory) {
        this.fileCategory = fileCategory;
    }
}
