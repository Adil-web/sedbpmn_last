package kz.kazgisa.data.entity;

import kz.kazgisa.data.entity.base.FileEntity;
import kz.kazgisa.data.enums.FileCategory;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

@Entity
public class AdmDocumentFile extends FileEntity {
    private AdmDocument admDocument;
    private FileCategory fileCategory;

    @Enumerated(EnumType.STRING)
    public FileCategory getFileCategory() {
        return fileCategory;
    }

    public void setFileCategory(FileCategory fileCategory) {
        this.fileCategory = fileCategory;
    }

    @ManyToOne
    @com.fasterxml.jackson.annotation.JsonIgnore
    public AdmDocument getAdmDocument() {
        return admDocument;
    }

    public void setAdmDocument(AdmDocument admDocument) {
        this.admDocument = admDocument;
    }
}
