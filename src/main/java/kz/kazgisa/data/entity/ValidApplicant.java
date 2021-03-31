package kz.kazgisa.data.entity;

import kz.kazgisa.data.entity.base.FullNameEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Entity
public class ValidApplicant extends FullNameEntity {
    private String iin;
    private Date regDate;
    private Long regUserId;
    private Boolean deleted;
    private Date deletedDate;
    private Long deletedUserId;
    private String note;

    public String getIin() {
        return iin;
    }

    public void setIin(String iin) {
        this.iin = iin;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Date getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }

    @Column(length = 5000)
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getRegUserId() {
        return regUserId;
    }

    public void setRegUserId(Long regUserId) {
        this.regUserId = regUserId;
    }

    public Long getDeletedUserId() {
        return deletedUserId;
    }

    public void setDeletedUserId(Long deletedUserId) {
        this.deletedUserId = deletedUserId;
    }
}
