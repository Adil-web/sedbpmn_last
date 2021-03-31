package kz.kazgisa.data.entity.appinfo;

import kz.kazgisa.data.entity.base.BaseEntity;
import kz.kazgisa.data.entity.dict.Phase;
import kz.kazgisa.data.entity.dict.PhasePeriod;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ElectricInfo extends BaseEntity {
    private Double requiredPower; // Требуемая мощность
    private Double onePhaseElec;
    private Double threePhaseElec;
    private Phase phase; // Характер нагрузки (фаза)
    private PhasePeriod phasePeriod; // Характер нагрузки периодичность
    private String telephone;

    // Категория надежности
    private Double relCat1;
    private Double relCat2;
    private Double relCat3;
    private String relCat;

    // Максимальная нагрузка после ввода в эксплуатацию по годам
    private String maxLoadByYears;

    // Из указанной максимальной нагрузки относится к приемникам
    private Double loadCat1;
    private Double loadCat2;
    private Double loadCat3;

    // Электрокотлы
    private Integer boilerCount;
    private Double boilerPower;

    // Электрокалориферы
    private Integer heaterCount;
    private Double heaterPower;

    // Электроплитки
    private Integer stoveCount;
    private Double stovePower;

    // Электропечи
    private Integer furnaceCount;
    private Double furnacePower;

    // Электроводонагреватели
    private Integer waterHeaterCount;
    private Double waterHeaterPower;

    // Мощьность трансформатора
    private String transformerNumber1;
    private String transformerNumber2;
    private Double transformerPower1;
    private Double transformerPower2;

    // Существующая максимальная нагрузка
    private Double maxLoad;

    public Double getRequiredPower() {
        return requiredPower;
    }

    public void setRequiredPower(Double requiredPower) {
        this.requiredPower = requiredPower;
    }

    @ManyToOne
    @JoinColumn(name = "phase_id")
    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    @ManyToOne
    @JoinColumn(name = "phase_period_id")
    public PhasePeriod getPhasePeriod() {
        return phasePeriod;
    }

    public void setPhasePeriod(PhasePeriod phasePeriod) {
        this.phasePeriod = phasePeriod;
    }

    public Double getRelCat1() {
        return relCat1;
    }

    public void setRelCat1(Double relCat1) {
        this.relCat1 = relCat1;
    }

    public Double getRelCat2() {
        return relCat2;
    }

    public void setRelCat2(Double relCat2) {
        this.relCat2 = relCat2;
    }

    public Double getRelCat3() {
        return relCat3;
    }

    public void setRelCat3(Double relCat3) {
        this.relCat3 = relCat3;
    }

    public String getRelCat() {
        return relCat;
    }

    public void setRelCat(String relCat) {
        this.relCat = relCat;
    }

    public String getMaxLoadByYears() {
        return maxLoadByYears;
    }

    public void setMaxLoadByYears(String maxLoadByYears) {
        this.maxLoadByYears = maxLoadByYears;
    }

    public Double getLoadCat1() {
        return loadCat1;
    }

    public void setLoadCat1(Double loadCat1) {
        this.loadCat1 = loadCat1;
    }

    public Double getLoadCat2() {
        return loadCat2;
    }

    public void setLoadCat2(Double loadCat2) {
        this.loadCat2 = loadCat2;
    }

    public Double getLoadCat3() {
        return loadCat3;
    }

    public void setLoadCat3(Double loadCat3) {
        this.loadCat3 = loadCat3;
    }

    public Integer getBoilerCount() {
        return boilerCount;
    }

    public void setBoilerCount(Integer boilerCount) {
        this.boilerCount = boilerCount;
    }

    public Double getBoilerPower() {
        return boilerPower;
    }

    public void setBoilerPower(Double boilerPower) {
        this.boilerPower = boilerPower;
    }

    public Integer getHeaterCount() {
        return heaterCount;
    }

    public void setHeaterCount(Integer heaterCount) {
        this.heaterCount = heaterCount;
    }

    public Double getHeaterPower() {
        return heaterPower;
    }

    public void setHeaterPower(Double heaterPower) {
        this.heaterPower = heaterPower;
    }

    public Integer getStoveCount() {
        return stoveCount;
    }

    public void setStoveCount(Integer stoveCount) {
        this.stoveCount = stoveCount;
    }

    public Double getStovePower() {
        return stovePower;
    }

    public void setStovePower(Double stovePower) {
        this.stovePower = stovePower;
    }

    public Integer getFurnaceCount() {
        return furnaceCount;
    }

    public void setFurnaceCount(Integer furnaceCount) {
        this.furnaceCount = furnaceCount;
    }

    public Double getFurnacePower() {
        return furnacePower;
    }

    public void setFurnacePower(Double furnacePower) {
        this.furnacePower = furnacePower;
    }

    public Integer getWaterHeaterCount() {
        return waterHeaterCount;
    }

    public void setWaterHeaterCount(Integer waterHeaterCount) {
        this.waterHeaterCount = waterHeaterCount;
    }

    public Double getWaterHeaterPower() {
        return waterHeaterPower;
    }

    public void setWaterHeaterPower(Double waterHeaterPower) {
        this.waterHeaterPower = waterHeaterPower;
    }

    public String getTransformerNumber1() {
        return transformerNumber1;
    }

    public void setTransformerNumber1(String transformerNumber1) {
        this.transformerNumber1 = transformerNumber1;
    }

    public String getTransformerNumber2() {
        return transformerNumber2;
    }

    public void setTransformerNumber2(String transformerNumber2) {
        this.transformerNumber2 = transformerNumber2;
    }

    public Double getTransformerPower1() {
        return transformerPower1;
    }

    public void setTransformerPower1(Double transformerPower1) {
        this.transformerPower1 = transformerPower1;
    }

    public Double getTransformerPower2() {
        return transformerPower2;
    }

    public void setTransformerPower2(Double transformerPower2) {
        this.transformerPower2 = transformerPower2;
    }

    public Double getMaxLoad() {
        return maxLoad;
    }

    public void setMaxLoad(Double maxLoad) {
        this.maxLoad = maxLoad;
    }

    public Double getOnePhaseElec() {
        return onePhaseElec;
    }

    public void setOnePhaseElec(Double onePhaseElec) {
        this.onePhaseElec = onePhaseElec;
    }

    public Double getThreePhaseElec() {
        return threePhaseElec;
    }

    public void setThreePhaseElec(Double threePhaseElec) {
        this.threePhaseElec = threePhaseElec;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
