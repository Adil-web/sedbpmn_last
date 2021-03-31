package kz.kazgisa.data.entity;

import kz.kazgisa.data.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class FileType extends BaseEntity {
    private String title;
    private String type;
    private Long subserviceId;
    private Boolean required;
    private String extensions;

    @Column(length = 1000)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public void setSubserviceId(Long subserviceId) {
        this.subserviceId = subserviceId;
    }

    public Long getSubserviceId() {
        return subserviceId;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getExtensions() {
        return extensions;
    }

    public void setExtensions(String extensions) {
        this.extensions = extensions;
    }
}

