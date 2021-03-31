package kz.kazgisa.data.entity.user;

import kz.kazgisa.data.entity.base.BaseEntity;

import javax.persistence.Entity;

@Entity
public class UserLayer extends BaseEntity {
    private Long userId;
    private Long layerId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLayerId() {
        return layerId;
    }

    public void setLayerId(Long layerId) {
        this.layerId = layerId;
    }
}
