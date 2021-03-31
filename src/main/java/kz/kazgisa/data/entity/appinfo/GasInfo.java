package kz.kazgisa.data.entity.appinfo;

import kz.kazgisa.data.entity.base.BaseEntity;

import javax.persistence.Entity;

@Entity
public class GasInfo extends BaseEntity {
    private Double cooking; // Приготовление пищи (м3/час)
    private Double heating; // Отопление (м3/час)
    private Double ventilation; // Вентиляция (м3/час)
    private Double conditioning; // Кондиционирование (м3/час)
    private Double hotWater; // Горячее водоснабжение (м3/час)
    private Double total;// Общая потребляемость (м3/час)
    private String gazification;

    public Double getCooking() {
        return cooking;
    }

    public void setCooking(Double cooking) {
        this.cooking = cooking;
    }

    public Double getHeating() {
        return heating;
    }

    public void setHeating(Double heating) {
        this.heating = heating;
    }

    public Double getVentilation() {
        return ventilation;
    }

    public void setVentilation(Double ventilation) {
        this.ventilation = ventilation;
    }

    public Double getConditioning() {
        return conditioning;
    }

    public void setConditioning(Double conditioning) {
        this.conditioning = conditioning;
    }

    public Double getHotWater() {
        return hotWater;
    }

    public void setHotWater(Double hotWater) {
        this.hotWater = hotWater;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getGazification() {
        return gazification;
    }

    public void setGazification(String gazification) {
        this.gazification = gazification;
    }

}
