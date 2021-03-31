package kz.kazgisa.data.entity.engineering;

import kz.kazgisa.data.entity.base.BaseEntity;

import javax.persistence.Entity;

@Entity
public class EsisDescription extends BaseEntity {
    private String layerName;
    private String layerType;
    private Integer esisComSvcTypeid;
    private String comSvcType;
    private String esisOwners;
    private String layerDescription;
    private String descriptionRu;
    private String descriptionKz;
    private String comments;
    private Boolean hasId;
    private Long srid;

    public String getLayerName() {
        return layerName;
    }

    public void setLayerName(String layerName) {
        this.layerName = layerName;
    }

    public String getLayerType() {
        return layerType;
    }

    public void setLayerType(String layerType) {
        this.layerType = layerType;
    }

    public Integer getEsisComSvcTypeid() {
        return esisComSvcTypeid;
    }

    public void setEsisComSvcTypeid(Integer esisComSvcTypeid) {
        this.esisComSvcTypeid = esisComSvcTypeid;
    }

    public String getComSvcType() {
        return comSvcType;
    }

    public void setComSvcType(String comSvcType) {
        this.comSvcType = comSvcType;
    }

    public String getEsisOwners() {
        return esisOwners;
    }

    public void setEsisOwners(String esisOwners) {
        this.esisOwners = esisOwners;
    }

    public String getLayerDescription() {
        return layerDescription;
    }

    public void setLayerDescription(String layerDescription) {
        this.layerDescription = layerDescription;
    }

    public String getDescriptionRu() {
        return descriptionRu;
    }

    public void setDescriptionRu(String descriptionRu) {
        this.descriptionRu = descriptionRu;
    }

    public String getDescriptionKz() {
        return descriptionKz;
    }

    public void setDescriptionKz(String descriptionKz) {
        this.descriptionKz = descriptionKz;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Boolean getHasId() {
        return hasId;
    }

    public void setHasId(Boolean hasId) {
        this.hasId = hasId;
    }

    public Long getSrid() {
        return srid;
    }

    public void setSrid(Long srid) {
        this.srid = srid;
    }
}
