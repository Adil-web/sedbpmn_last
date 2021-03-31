package kz.kazgisa.data.entity.appinfo;

import kz.kazgisa.data.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Entity
public class ObjectInfo extends BaseEntity {
    private String name;
    private String address;
    private Integer period;
    private Integer floorsCount;
    private Double area;
    private Integer flatsCount;
    private Integer roomsCount;
    private Integer cabinetsCount;
    private String cadastreNumber;
    private Boolean isDifficult;
    private String location;
    private String extendLocation;
    private String archLocation;
    private String useRight;
    private String purpose;
    private String purposeRequested;
    private String changeReason;
    private Boolean inOzo;

    private String designer;
    private String licenseCategoryGsl;
    private String licenseNumberGsl;
    private Date licenseDateGsl;

    private Boolean additionalPurposeUse;
    private Double additionalArea;
    private String identDocNumber;
    private Date identDocDate;
    private String legalDocNumber;
    private Date legalDocDate;
    /*private PurposeUse purposeUse;
    private FuncUse funcUse;
    private EstateObjectType estateObjectType;
    private OwnershipForm ownershipForm;
    private GenOwnershipForm genOwnershipForm;
    private RightType rightType;
    private LandCategory landCategory;*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getFloorsCount() {
        return floorsCount;
    }

    public void setFloorsCount(Integer floorsCount) {
        this.floorsCount = floorsCount;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Integer getFlatsCount() {
        return flatsCount;
    }

    public void setFlatsCount(Integer flatsCount) {
        this.flatsCount = flatsCount;
    }

    public Integer getRoomsCount() {
        return roomsCount;
    }

    public void setRoomsCount(Integer roomsCount) {
        this.roomsCount = roomsCount;
    }

    public Integer getCabinetsCount() {
        return cabinetsCount;
    }

    public void setCabinetsCount(Integer cabinetsCount) {
        this.cabinetsCount = cabinetsCount;
    }

    public String getCadastreNumber() {
        return cadastreNumber;
    }

    public void setCadastreNumber(String cadastreNumber) {
        this.cadastreNumber = cadastreNumber;
    }

    public Boolean getDifficult() {
        return isDifficult;
    }

    public void setDifficult(Boolean difficult) {
        isDifficult = difficult;
    }

    @Column(columnDefinition = "text")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column(columnDefinition = "text")
    public String getExtendLocation() {
        return extendLocation;
    }

    public void setExtendLocation(String extendLocation) {
        this.extendLocation = extendLocation;
    }

    @Column(columnDefinition = "text")
    public String getArchLocation() {
        return archLocation;
    }

    public void setArchLocation(String archLocation) {
        this.archLocation = archLocation;
    }

    public String getUseRight() {
        return useRight;
    }

    public void setUseRight(String useRight) {
        this.useRight = useRight;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getPurposeRequested() {
        return purposeRequested;
    }

    public void setPurposeRequested(String purposeRequested) {
        this.purposeRequested = purposeRequested;
    }

    public String getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }

    public Boolean getInOzo() {
        return inOzo;
    }

    public void setInOzo(Boolean inOzo) {
        this.inOzo = inOzo;
    }

    public String getDesigner() {
        return designer;
    }

    public void setDesigner(String designer) {
        this.designer = designer;
    }

    public String getLicenseCategoryGsl() {
        return licenseCategoryGsl;
    }

    public void setLicenseCategoryGsl(String licenseCategoryGsl) {
        this.licenseCategoryGsl = licenseCategoryGsl;
    }

    public String getLicenseNumberGsl() {
        return licenseNumberGsl;
    }

    public void setLicenseNumberGsl(String licenseNumberGsl) {
        this.licenseNumberGsl = licenseNumberGsl;
    }

    public Date getLicenseDateGsl() {
        return licenseDateGsl;
    }

    public void setLicenseDateGsl(Date licenseDateGsl) {
        this.licenseDateGsl = licenseDateGsl;
    }

    public Boolean getAdditionalPurposeUse() {
        return additionalPurposeUse;
    }

    public void setAdditionalPurposeUse(Boolean additionalPurposeUse) {
        this.additionalPurposeUse = additionalPurposeUse;
    }

    public Double getAdditionalArea() {
        return additionalArea;
    }

    public void setAdditionalArea(Double additionalArea) {
        this.additionalArea = additionalArea;
    }

    public String getIdentDocNumber() {
        return identDocNumber;
    }

    public void setIdentDocNumber(String identDocNumber) {
        this.identDocNumber = identDocNumber;
    }

    public Date getIdentDocDate() {
        return identDocDate;
    }

    public void setIdentDocDate(Date identDocDate) {
        this.identDocDate = identDocDate;
    }

    public String getLegalDocNumber() {
        return legalDocNumber;
    }

    public void setLegalDocNumber(String legalDocNumber) {
        this.legalDocNumber = legalDocNumber;
    }

    public Date getLegalDocDate() {
        return legalDocDate;
    }

    public void setLegalDocDate(Date legalDocDate) {
        this.legalDocDate = legalDocDate;
    }

    /*@ManyToOne
    public PurposeUse getPurposeUse() {
        return purposeUse;
    }

    public void setPurposeUse(PurposeUse purposeUse) {
        this.purposeUse = purposeUse;
    }

    @ManyToOne
    public FuncUse getFuncUse() {
        return funcUse;
    }

    public void setFuncUse(FuncUse funcUse) {
        this.funcUse = funcUse;
    }

    @ManyToOne
    public EstateObjectType getEstateObjectType() {
        return estateObjectType;
    }

    public void setEstateObjectType(EstateObjectType estateObjectType) {
        this.estateObjectType = estateObjectType;
    }

    @ManyToOne
    public OwnershipForm getOwnershipForm() {
        return ownershipForm;
    }

    public void setOwnershipForm(OwnershipForm ownershipForm) {
        this.ownershipForm = ownershipForm;
    }

    @ManyToOne
    public GenOwnershipForm getGenOwnershipForm() {
        return genOwnershipForm;
    }

    public void setGenOwnershipForm(GenOwnershipForm genOwnershipForm) {
        this.genOwnershipForm = genOwnershipForm;
    }

    @ManyToOne
    public RightType getRightType() {
        return rightType;
    }

    public void setRightType(RightType rightType) {
        this.rightType = rightType;
    }

    @ManyToOne
    public LandCategory getLandCategory() {
        return landCategory;
    }

    public void setLandCategory(LandCategory landCategory) {
        this.landCategory = landCategory;
    }*/
}
