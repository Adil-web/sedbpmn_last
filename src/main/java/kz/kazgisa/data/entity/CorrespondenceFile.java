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
public class CorrespondenceFile extends FileEntity {
    private Correspondence correspondence;
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
    public Correspondence getCorrespondence() {
        return correspondence;
    }

    public void setCorrespondence(Correspondence correspondence) {
        this.correspondence = correspondence;
    }
}
