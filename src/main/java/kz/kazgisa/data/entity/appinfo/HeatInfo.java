package kz.kazgisa.data.entity.appinfo;

import kz.kazgisa.data.entity.base.BaseEntity;

import javax.persistence.Entity;

@Entity
public class HeatInfo extends BaseEntity {
    private Double heating; // Отопление (Гкал/час)
    private Double ventilation; // Вентиляция (всего) (Гкал/час)
    private Double hotWater; // Горячее водоснабжение (Гкал/час)
    private Double technical; // Технологические нужды (пар) (Гкал/час)
    private Double total; // Общая тепловая нагрузка (Гкал/час)
    private String centralHeating;

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

    public Double getHotWater() {
        return hotWater;
    }

    public void setHotWater(Double hotWater) {
        this.hotWater = hotWater;
    }

    public Double getTechnical() {
        return technical;
    }

    public void setTechnical(Double technical) {
        this.technical = technical;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getCentralHeating() {
        return centralHeating;
    }

    public void setCentralHeating(String centralHeating) {
        this.centralHeating = centralHeating;
    }
}
