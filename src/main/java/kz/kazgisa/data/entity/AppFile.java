package kz.kazgisa.data.entity;

import kz.kazgisa.data.entity.base.FileEntity;
import kz.kazgisa.data.enums.FileCategory;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class AppFile extends FileEntity {
    private App app;
    private FileCategory fileCategory;

    @ManyToOne
    @JoinColumn(name = "app_id")
    @com.fasterxml.jackson.annotation.JsonIgnore
    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    @Enumerated(EnumType.STRING)
    public FileCategory getFileCategory() {
        return fileCategory;
    }

    public void setFileCategory(FileCategory fileCategory) {
        this.fileCategory = fileCategory;
    }
}
