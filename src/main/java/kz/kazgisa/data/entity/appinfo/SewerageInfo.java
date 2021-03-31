package kz.kazgisa.data.entity.appinfo;

import kz.kazgisa.data.entity.base.BaseEntity;

import javax.persistence.Entity;

@Entity
public class SewerageInfo extends BaseEntity {
    private Double fecal; // Фекальные (всего) (м3/сутки)
    private Double maxFecal; // Фекальные максимум (всего) (м3/сутки)
    private Double industrial; // Производственно загрязненные (всего) (м3/сутки)
    private Double maxIndustrial; // Производственно загрязненные максимум (всего) (м3/час)
    private Double clean; // Условно чистые (всего) (м3/сутки) (м3/час)
    private Double maxClean; // Условно чистые максимум (всего) (м3/сутки)
    private Double total; // Общее количество сточных вод (м3/сутки)
    private Double maxTotal; // Максимальное бщее количество сточных вод (м3/час)
    private String centralSewerage;

    public Double getFecal() {
        return fecal;
    }

    public void setFecal(Double fecal) {
        this.fecal = fecal;
    }

    public Double getMaxFecal() {
        return maxFecal;
    }

    public void setMaxFecal(Double maxFecal) {
        this.maxFecal = maxFecal;
    }

    public Double getIndustrial() {
        return industrial;
    }

    public void setIndustrial(Double industrial) {
        this.industrial = industrial;
    }

    public Double getMaxIndustrial() {
        return maxIndustrial;
    }

    public void setMaxIndustrial(Double maxIndustrial) {
        this.maxIndustrial = maxIndustrial;
    }

    public Double getClean() {
        return clean;
    }

    public void setClean(Double clean) {
        this.clean = clean;
    }

    public Double getMaxClean() {
        return maxClean;
    }

    public void setMaxClean(Double maxClean) {
        this.maxClean = maxClean;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(Double maxTotal) {
        this.maxTotal = maxTotal;
    }

    public String getCentralSewerage() {
        return centralSewerage;
    }

    public void setCentralSewerage(String centralSewerage) {
        this.centralSewerage = centralSewerage;
    }

}
