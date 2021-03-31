package kz.kazgisa.data.entity;

import kz.kazgisa.data.entity.appinfo.*;
import kz.kazgisa.data.entity.base.FullNameEntity;
import kz.kazgisa.data.entity.dict.Subservice;
import kz.kazgisa.data.enums.UserType;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
@Entity
public class App extends FullNameEntity {
    private Date createDate;
    private Date regDate;
    private String iin;
    private String address;
    private String oldAddress;
    private String newAddress;
    private String addressRegCode;
    private String phone;
    private Subservice subservice;
    private Date planEndDate;
    private Date factEndDate;
//    private List<OpenAppUser> openAppUsers;
    private Long apzId;
    private AppStatus currentStatus;
    private Boolean communalsChecked = null;
    private Date communalsCheckedDate;
    private byte[] rejectedFile;
    private byte[] rejectedFileOriginal;
    private Boolean toSign = false;

    private String signedXml;
    private Boolean signed = false;
    private Date signedDate;

    private String archSignedXml;
    private Boolean archSigned = false;
    private Date archSignedDate;
    private Long archSignedUserId;
    private String archSignedUserIp;

    private String ozoSignedXml;
    private Boolean ozoSigned = false;
    private Date ozoSignedDate;
    private Long ozoSignedUserId;

    private Boolean onCommission = false;
    private Boolean finishedCommission = false;

    private Boolean onNumeration = false;
    private String numeration;
    private Date numerationDate;

    // for organizations
    private String bin;
    private String orgName;
    private String control;
    private Boolean approved;

    private Boolean isOrg;

    private Long regionId;

    private String regNumber;

    private String projectName;
    private String projectAddress;

    // appinfo
    private ObjectInfo objectInfo;
    private ElectricInfo electricInfo;
    private WaterInfo waterInfo;
    private SewerageInfo sewerageInfo;
    private HeatInfo heatInfo;
    private GasInfo gasInfo;
    private DesignerInfo designerInfo;
    private LandInfo landInfo;
    /*private AuctionInfo auctionInfo;
    // ozo*/
    private OzoInfo ozoInfo;

    public byte[] getApprovedFile() {
        return approvedFile;
    }

    private Boolean isOpen;
    private String mapImage;

    public void setApprovedFile(byte[] approvedFile) {
        this.approvedFile = approvedFile;
    }

    private String subscribeEmail;
    private Boolean isArchTech;

    private Boolean deleted;
    private byte[] approvedFile;
    private byte[] approvedFileOzo;
    private byte[] approvedFileOriginal;
    private byte[] eskizFile;

    private Boolean applicantRejected;
    private Boolean onClarification;
    private Boolean addressFinished;

    private byte[] landSelectionAgreement;
    private byte[] actOfReconciliation;
    private Date actOfReconciliationDate;
    private byte[] schemaZu;

    private String requestNumber;

    private String currentExecutor;

    private String currentTaskName;

    private String currentOwner;

    private Long appId;

    private Boolean otherApplicant;
    private Boolean applicantIsOrg;
    private String applicantIinBin;
    private String applicantName;

    public byte[] getEskizFile() {
        return eskizFile;
    }

    public void setEskizFile(byte[] eskizFile) {
        this.eskizFile = eskizFile;
    }

    @Column(columnDefinition = "text")
    public String getMapImage() {
        return mapImage;
    }

    public void setMapImage(String mapImage) {
        this.mapImage = mapImage;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public String getIin() {
        return iin;
    }

    public void setIin(String iin) {
        this.iin = iin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @ManyToOne
    @JoinColumn(name = "subservice_id")
    public Subservice getSubservice() {
        return subservice;
    }

    public void setSubservice(Subservice subservice) {
        this.subservice = subservice;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(Date planEndDate) {
        this.planEndDate = planEndDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getFactEndDate() {
        return factEndDate;
    }

    public void setFactEndDate(Date factEndDate) {
        this.factEndDate = factEndDate;
    }

    @ManyToOne
    @JoinColumn(name = "app_status_id")
    public AppStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(AppStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Boolean getCommunalsChecked() {
        return communalsChecked;
    }

    public void setCommunalsChecked(Boolean communalsChecked) {
        this.communalsChecked = communalsChecked;
    }

    public Date getCommunalsCheckedDate() {
        return communalsCheckedDate;
    }

    public void setCommunalsCheckedDate(Date communalsCheckedDate) {
        this.communalsCheckedDate = communalsCheckedDate;
    }

//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "appId")
//    public List<OpenAppUser> getOpenAppUsers() {
//        return openAppUsers;
//    }
//
//    public void setOpenAppUsers(List<OpenAppUser> openAppUsers) {
//        this.openAppUsers = openAppUsers;
//    }

    public Long getApzId() {
        return apzId;
    }

    public void setApzId(Long apzId) {
        this.apzId = apzId;
    }

    public byte[] getRejectedFile() {
        return rejectedFile;
    }

    public void setRejectedFile(byte[] rejectedFile) {
        this.rejectedFile = rejectedFile;
    }

    public Boolean getToSign() {
        return toSign;
    }

    public void setToSign(Boolean toSign) {
        this.toSign = toSign;
    }

    @Column(columnDefinition = "text")
    public String getSignedXml() {
        return signedXml;
    }

    public void setSignedXml(String signedXml) {
        this.signedXml = signedXml;
    }

    public Boolean getSigned() {
        return signed;
    }

    public void setSigned(Boolean signed) {
        this.signed = signed;
    }

    public Date getSignedDate() {
        return signedDate;
    }

    public void setSignedDate(Date signedDate) {
        this.signedDate = signedDate;
    }

    @Column(columnDefinition = "text")
    public String getArchSignedXml() {
        return archSignedXml;
    }

    public void setArchSignedXml(String archSignedXml) {
        this.archSignedXml = archSignedXml;
    }

    public Boolean getArchSigned() {
        return archSigned;
    }

    public void setArchSigned(Boolean archSigned) {
        this.archSigned = archSigned;
    }

    public Date getArchSignedDate() {
        return archSignedDate;
    }

    public void setArchSignedDate(Date archSignedDate) {
        this.archSignedDate = archSignedDate;
    }

    public Long getArchSignedUserId() {
        return archSignedUserId;
    }

    public void setArchSignedUserId(Long archSignedUserId) {
        this.archSignedUserId = archSignedUserId;
    }

    public String getArchSignedUserIp() {
        return archSignedUserIp;
    }

    public void setArchSignedUserIp(String archSignedUserIp) {
        this.archSignedUserIp = archSignedUserIp;
    }

    @Column(columnDefinition = "text")
    public String getOzoSignedXml() {
        return ozoSignedXml;
    }

    public void setOzoSignedXml(String ozoSignedXml) {
        this.ozoSignedXml = ozoSignedXml;
    }

    public Boolean getOzoSigned() {
        return ozoSigned;
    }

    public void setOzoSigned(Boolean ozoSigned) {
        this.ozoSigned = ozoSigned;
    }

    public Date getOzoSignedDate() {
        return ozoSignedDate;
    }

    public void setOzoSignedDate(Date ozoSignedDate) {
        this.ozoSignedDate = ozoSignedDate;
    }

    public Long getOzoSignedUserId() {
        return ozoSignedUserId;
    }

    public void setOzoSignedUserId(Long ozoSignedUserId) {
        this.ozoSignedUserId = ozoSignedUserId;
    }

    public Boolean getOnCommission() {
        return onCommission;
    }

    public void setOnCommission(Boolean onCommission) {
        this.onCommission = onCommission;
    }

    public Boolean getFinishedCommission() {
        return finishedCommission;
    }

    public void setFinishedCommission(Boolean finishedCommission) {
        this.finishedCommission = finishedCommission;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Boolean getOrg() {
        return isOrg;
    }

    public void setOrg(Boolean org) {
        isOrg = org;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "designer_info_id")
    public DesignerInfo getDesignerInfo() {
        return designerInfo;
    }

    public void setDesignerInfo(DesignerInfo designerInfo) {
        this.designerInfo = designerInfo;
    }

    @OneToOne
    @JoinColumn(name = "object_info_id")
    public ObjectInfo getObjectInfo() {
        return objectInfo;
    }

    public void setObjectInfo(ObjectInfo objectInfo) {
        this.objectInfo = objectInfo;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "electric_info_id")
    public ElectricInfo getElectricInfo() {
        return electricInfo;
    }
;
    public void setElectricInfo(ElectricInfo electricInfo) {
        this.electricInfo = electricInfo;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "water_info_id")
    public WaterInfo getWaterInfo() {
        return waterInfo;
    }

    public void setWaterInfo(WaterInfo waterInfo) {
        this.waterInfo = waterInfo;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sewerage_info_id")
    public SewerageInfo getSewerageInfo() {
        return sewerageInfo;
    }

    public void setSewerageInfo(SewerageInfo sewerageInfo) {
        this.sewerageInfo = sewerageInfo;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "heat_info_id")
    public HeatInfo getHeatInfo() {
        return heatInfo;
    }

    public void setHeatInfo(HeatInfo heatInfo) {
        this.heatInfo = heatInfo;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gas_info_id")
    public GasInfo getGasInfo() {
        return gasInfo;
    }

    public void setGasInfo(GasInfo gasInfo) {
        this.gasInfo = gasInfo;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "land_info_id")
    public LandInfo getLandInfo() {
        return landInfo;
    }

    public void setLandInfo(LandInfo landInfo) {
        this.landInfo = landInfo;
    }

    /*@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ozo_info_id")
    public OzoInfo getOzoInfo() {
        return ozoInfo;
    }

    public void setOzoInfo(OzoInfo ozoInfo) {
        this.ozoInfo = ozoInfo;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "auction_info_id")
    public AuctionInfo getAuctionInfo() {
        return auctionInfo;
    }

    public void setAuctionInfo(AuctionInfo auctionInfo) {
        this.auctionInfo = auctionInfo;
    }
*/
    public String getSubscribeEmail() {
        return subscribeEmail;
    }

    public void setSubscribeEmail(String subscribeEmail) {
        this.subscribeEmail = subscribeEmail;
    }

    public Boolean getArchTech() {
        return isArchTech;
    }

    public void setArchTech(Boolean archTech) {
        isArchTech = archTech;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getOnNumeration() {
        return onNumeration;
    }

    public void setOnNumeration(Boolean onNumeration) {
        this.onNumeration = onNumeration;
    }

    public String getNumeration() {
        return numeration;
    }

    public void setNumeration(String numeration) {
        this.numeration = numeration;
    }

    public Date getNumerationDate() {
        return numerationDate;
    }

    public void setNumerationDate(Date numerationDate) {
        this.numerationDate = numerationDate;
    }

    public byte[] getApprovedFileOriginal() {
        return approvedFileOriginal;
    }

    public void setApprovedFileOriginal(byte[] approvedFileOriginal) {
        this.approvedFileOriginal = approvedFileOriginal;
    }

    public byte[] getRejectedFileOriginal() {
        return rejectedFileOriginal;
    }

    public void setRejectedFileOriginal(byte[] rejectedFileOriginal) {
        this.rejectedFileOriginal = rejectedFileOriginal;
    }

    public Boolean getApplicantRejected() {
        return applicantRejected;
    }

    public void setApplicantRejected(Boolean applicantRejected) {
        this.applicantRejected = applicantRejected;
    }

    public Boolean getOnClarification() {
        return onClarification;
    }

    public void setOnClarification(Boolean onClarification) {
        this.onClarification = onClarification;
    }

    public Boolean getAddressFinished() {
        return addressFinished;
    }

    public void setAddressFinished(Boolean addressFinished) {
        this.addressFinished = addressFinished;
    }


    public byte[] getLandSelectionAgreement() {
        return landSelectionAgreement;
    }

    public void setLandSelectionAgreement(byte[] landSelectionAgreement) {
        this.landSelectionAgreement = landSelectionAgreement;
    }

    public byte[] getActOfReconciliation() {
        return actOfReconciliation;
    }

    public void setActOfReconciliation(byte[] actOfReconciliation) {
        this.actOfReconciliation = actOfReconciliation;
    }

    public byte[] getSchemaZu() {
        return schemaZu;
    }

    public void setSchemaZu(byte[] schemaZu) {
        this.schemaZu = schemaZu;
    }

    public byte[] getApprovedFileOzo() {
            return approvedFileOzo;
    }

    public void setApprovedFileOzo(byte[] approvedFileOzo) {
        this.approvedFileOzo = approvedFileOzo;
    }

    public String getOldAddress() {
        return oldAddress;
    }

    public void setOldAddress(String oldAddress) {
        this.oldAddress = oldAddress;
    }

    public String getNewAddress() {
        return newAddress;
    }

    public void setNewAddress(String newAddress) {
        this.newAddress = newAddress;
    }

    public String getAddressRegCode() {
        return addressRegCode;
    }

    public void setAddressRegCode(String addressRegCode) {
        this.addressRegCode = addressRegCode;
    }

    public String getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(String requestNumber) {
        this.requestNumber = requestNumber;
    }

    public Date getActOfReconciliationDate() {
        return actOfReconciliationDate;
    }

    public void setActOfReconciliationDate(Date actOfReconciliationDate) {
        this.actOfReconciliationDate = actOfReconciliationDate;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    @Transient
    public String getCurrentExecutor() {
        return currentExecutor;
    }

    public void setCurrentExecutor(String currentExecutor) {
        this.currentExecutor = currentExecutor;
    }

    @Transient
    public String getCurrentTaskName() {
        return currentTaskName;
    }

    public void setCurrentTaskName(String currentTaskName) {
        this.currentTaskName = currentTaskName;
    }

    @Transient
    public String getCurrentOwner() {
        return currentOwner;
    }

    public void setCurrentOwner(String currentOwner) {
        this.currentOwner = currentOwner;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    @Column(length = 2000)
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Column(length = 2000)
    public String getProjectAddress() {
        return projectAddress;
    }

    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
    }

    @Column(unique = true)
    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    @OneToOne
    @JoinColumn(name = "ozo_info_id")
    public OzoInfo getOzoInfo() {
        return ozoInfo;
    }

    public void setOzoInfo(OzoInfo ozoInfo) {
        this.ozoInfo = ozoInfo;
    }

    public Boolean getOtherApplicant() {
        return otherApplicant;
    }

    public void setOtherApplicant(Boolean otherApplicant) {
        this.otherApplicant = otherApplicant;
    }

    public Boolean getApplicantIsOrg() {
        return applicantIsOrg;
    }

    public void setApplicantIsOrg(Boolean applicantIsOrg) {
        this.applicantIsOrg = applicantIsOrg;
    }

    public String getApplicantIinBin() {
        return applicantIinBin;
    }

    public void setApplicantIinBin(String applicantIinBin) {
        this.applicantIinBin = applicantIinBin;
    }

    @Column(length = 1000)
    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }
}
