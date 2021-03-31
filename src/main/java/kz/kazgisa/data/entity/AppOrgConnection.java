package kz.kazgisa.data.entity;

import kz.kazgisa.data.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class AppOrgConnection extends BaseEntity {
    private Long appOrgId;
    private String connection;
    private String data;

    public Long getAppOrgId() {
        return appOrgId;
    }

    public void setAppOrgId(Long appOrgId) {
        this.appOrgId = appOrgId;
    }

    @Column(columnDefinition = "text")
    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
