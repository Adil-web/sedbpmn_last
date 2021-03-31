package kz.kazgisa.data.entity;

import kz.kazgisa.data.entity.base.LocalNameEntity;
import kz.kazgisa.data.entity.dict.AdmDocumentCategory;
import kz.kazgisa.data.entity.user.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class AdmDocument extends LocalNameEntity {
    private User author;
    private Date date;
    private AdmDocumentCategory category;
    private User employee;
    private String body;

    @ManyToOne
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @ManyToOne
    public AdmDocumentCategory getCategory() {
        return category;
    }

    public void setCategory(AdmDocumentCategory category) {
        this.category = category;
    }

    @ManyToOne
    public User getEmployee() {
        return employee;
    }

    public void setEmployee(User employee) {
        this.employee = employee;
    }

    @Column(columnDefinition = "text")
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
