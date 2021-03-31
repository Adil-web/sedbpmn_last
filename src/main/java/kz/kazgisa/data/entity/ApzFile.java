package kz.kazgisa.data.entity;

import kz.kazgisa.data.entity.base.FileEntity;
import kz.kazgisa.data.enums.FileCategory;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ApzFile extends FileEntity {
    private Apz apz;
    private FileCategory fileCategory;

    @ManyToOne
    @JoinColumn(name = "apz_id")
    @com.fasterxml.jackson.annotation.JsonIgnore
    public Apz getApz() {
        return apz;
    }

    public void setApz(Apz apz) {
        this.apz = apz;
    }

    @Enumerated(EnumType.STRING)
    public FileCategory getFileCategory() {
        return fileCategory;
    }

    public void setFileCategory(FileCategory fileCategory) {
        this.fileCategory = fileCategory;
    }
}
