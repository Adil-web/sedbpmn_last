package kz.kazgisa.data.entity.appinfo;

import kz.kazgisa.data.entity.base.BaseEntity;

import javax.persistence.Entity;

@Entity
public class WaterInfo extends BaseEntity {
    private Double dayDrink; // На хозяйственно-питьевые нужды (м3/сутки)
    private Double hourDrink; // На хозяйственно-питьевые нужды (м3/час)
    private Double maxDrink; // Максимально на хозяйственно-питьевые нужды (м3/секунд)
    private Double dayIndustrial; // На производственные нужды (м3/сутки)
    private Double hourIndustrial; // На производственные нужды (м3/час)
    private Double maxIndustrial; // Максимально на производственные нужды (м3/секунд)
    private Double firefighting; // Потребные расходы пожаротушения (м3/секунд)
    private Double total; // Общая потребность в воде (м3/сутки)
    private Double totalDrink; // Общая потребность в питьевой воде (м3/сутки)
    private Double maxTotal; // Максимальная общая потребность в воде (м3/сутки)
    private String waterDisposal;
    private String centralHotWater;

    public Double getDayDrink() {
        return dayDrink;
    }

    public void setDayDrink(Double dayDrink) {
        this.dayDrink = dayDrink;
    }

    public Double getHourDrink() {
        return hourDrink;
    }

    public void setHourDrink(Double hourDrink) {
        this.hourDrink = hourDrink;
    }

    public Double getMaxDrink() {
        return maxDrink;
    }

    public void setMaxDrink(Double maxDrink) {
        this.maxDrink = maxDrink;
    }

    public Double getDayIndustrial() {
        return dayIndustrial;
    }

    public void setDayIndustrial(Double dayIndustrial) {
        this.dayIndustrial = dayIndustrial;
    }

    public Double getHourIndustrial() {
        return hourIndustrial;
    }

    public void setHourIndustrial(Double hourIndustrial) {
        this.hourIndustrial = hourIndustrial;
    }

    public Double getMaxIndustrial() {
        return maxIndustrial;
    }

    public void setMaxIndustrial(Double maxIndustrial) {
        this.maxIndustrial = maxIndustrial;
    }

    public Double getFirefighting() {
        return firefighting;
    }

    public void setFirefighting(Double firefighting) {
        this.firefighting = firefighting;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getTotalDrink() {
        return totalDrink;
    }

    public void setTotalDrink(Double totalDrink) {
        this.totalDrink = totalDrink;
    }

    public Double getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(Double maxTotal) {
        this.maxTotal = maxTotal;
    }

    public String getWaterDisposal() {
        return waterDisposal;
    }

    public void setWaterDisposal(String waterDisposal) {
        this.waterDisposal = waterDisposal;
    }

    public String getCentralHotWater() {
        return centralHotWater;
    }

    public void setCentralHotWater(String centralHotWater) {
        this.centralHotWater = centralHotWater;
    }
}
