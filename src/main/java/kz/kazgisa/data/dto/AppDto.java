package kz.kazgisa.data.dto;

import kz.kazgisa.data.entity.*;
import kz.kazgisa.data.entity.appinfo.*;
import kz.kazgisa.data.entity.dict.Subservice;

import java.util.Date;
import java.util.List;

public class AppDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String secondName;

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

    private Boolean communalsChecked;
    private Date communalsCheckedDate;
    private AppStatus currentStatus;
    private Long apzId;
    private Boolean toSign;

    private AppMeetingUser meetingStatus;

    private Boolean isOpen;
    private Boolean hasFiles;
    private List<AppFile> appFiles;
    private AddressFile addressFile;
    private List<IjsFile> ijsFiles;

    private Boolean signed;
    private Date signedDate;
    private Boolean archSigned;
    private Date archSignedDate;
    private Long archSignedUserId;
    private Boolean ozoSigned;
    private Date ozoSignedDate;
    private Long ozoSignedUserId;

    private Boolean onCommission;
    private Boolean finishedCommission;

    private Boolean onNumeration = false;
    private String numeration;
    private Date numerationDate;

    // for organizations
    private String bin;
    private String orgName;
    private String mapImage;
    private Boolean isOrg;

    private String subscribeEmail;
    private Boolean isArchTech;

    private Boolean applicantRejected;
    private Boolean onClarification;
    private Boolean addressFinished;

    private Boolean hasLandSelectionAgreement;
    private Boolean hasActOfReconciliation;
    private Boolean hasSchemaZu;
    private Boolean hasApprovedFileOzo;
    private Boolean hasApprovedRejectedFile;

    private String projectName;
    private String projectAddress;

    private Boolean otherApplicant;
    private Boolean applicantIsOrg;
    private String applicantIinBin;
    private String applicantName;

    public String getMapImage() {
        return mapImage;
    }

    public void setMapImage(String mapImage) {
        this.mapImage = mapImage;
    }

    // appinfo
    /*private DesignerInfo designerInfo;
    private ObjectInfo objectInfo;
    private ElectricInfo electricInfo;
    private WaterInfo waterInfo;
    private SewerageInfo sewerageInfo;
    private HeatInfo heatInfo;
    private GasInfo gasInfo;
    private LandInfo landInfo;
    private OzoInfo ozoInfo;
    private AuctionInfo auctionInfo;*/

    //commission
    private Long commissionApprovedCount;
    private Long commissionRejectedCount;
    private Long commissionNotRespondedCount;
    private MeetingApp meetingApp;

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

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

    public Subservice getSubservice() {
        return subservice;
    }

    public void setSubservice(Subservice subservice) {
        this.subservice = subservice;
    }

    public Date getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(Date planEndDate) {
        this.planEndDate = planEndDate;
    }

    public Date getFactEndDate() {
        return factEndDate;
    }

    public void setFactEndDate(Date factEndDate) {
        this.factEndDate = factEndDate;
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

    public AppStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(AppStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Long getApzId() {
        return apzId;
    }

    public void setApzId(Long apzId) {
        this.apzId = apzId;
    }

    public Boolean getToSign() {
        return toSign;
    }

    public void setToSign(Boolean toSign) {
        this.toSign = toSign;
    }

    public AppMeetingUser getMeetingStatus() {
        return meetingStatus;
    }

    public void setMeetingStatus(AppMeetingUser meetingStatus) {
        this.meetingStatus = meetingStatus;
    }

    public Boolean getHasFiles() {
        return hasFiles;
    }

    public void setHasFiles(Boolean hasFiles) {
        this.hasFiles = hasFiles;
    }

    public List<AppFile> getAppFiles() {
        return appFiles;
    }

    public void setAppFiles(List<AppFile> appFiles) {
        this.appFiles = appFiles;
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

   /* public DesignerInfo getDesignerInfo() {
        return designerInfo;
    }

    public void setDesignerInfo(DesignerInfo designerInfo) {
        this.designerInfo = designerInfo;
    }

    public ObjectInfo getObjectInfo() {
        return objectInfo;
    }

    public void setObjectInfo(ObjectInfo objectInfo) {
        this.objectInfo = objectInfo;
    }

    public ElectricInfo getElectricInfo() {
        return electricInfo;
    }

    public void setElectricInfo(ElectricInfo electricInfo) {
        this.electricInfo = electricInfo;
    }

    public WaterInfo getWaterInfo() {
        return waterInfo;
    }

    public void setWaterInfo(WaterInfo waterInfo) {
        this.waterInfo = waterInfo;
    }

    public SewerageInfo getSewerageInfo() {
        return sewerageInfo;
    }

    public void setSewerageInfo(SewerageInfo sewerageInfo) {
        this.sewerageInfo = sewerageInfo;
    }

    public HeatInfo getHeatInfo() {
        return heatInfo;
    }

    public void setHeatInfo(HeatInfo heatInfo) {
        this.heatInfo = heatInfo;
    }

    public GasInfo getGasInfo() {
        return gasInfo;
    }

    public void setGasInfo(GasInfo gasInfo) {
        this.gasInfo = gasInfo;
    }

    public LandInfo getLandInfo() {
        return landInfo;
    }

    public void setLandInfo(LandInfo landInfo) {
        this.landInfo = landInfo;
    }

    public OzoInfo getOzoInfo() {
        return ozoInfo;
    }

    public void setOzoInfo(OzoInfo ozoInfo) {
        this.ozoInfo = ozoInfo;
    }

    public AuctionInfo getAuctionInfo() {
        return auctionInfo;
    }

    public void setAuctionInfo(AuctionInfo auctionInfo) {
        this.auctionInfo = auctionInfo;
    }
*/
    public Long getCommissionApprovedCount() {
        return commissionApprovedCount;
    }

    public void setCommissionApprovedCount(Long commissionApprovedCount) {
        this.commissionApprovedCount = commissionApprovedCount;
    }

    public Long getCommissionRejectedCount() {
        return commissionRejectedCount;
    }

    public void setCommissionRejectedCount(Long commissionRejectedCount) {
        this.commissionRejectedCount = commissionRejectedCount;
    }

    public Long getCommissionNotRespondedCount() {
        return commissionNotRespondedCount;
    }

    public void setCommissionNotRespondedCount(Long commissionNotRespondedCount) {
        this.commissionNotRespondedCount = commissionNotRespondedCount;
    }

    public MeetingApp getMeetingApp() {
        return meetingApp;
    }

    public void setMeetingApp(MeetingApp meetingApp) {
        this.meetingApp = meetingApp;
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

    public AddressFile getAddressFile() {
        return addressFile;
    }

    public void setAddressFile(AddressFile addressFile) {
        this.addressFile = addressFile;
    }

    public List<IjsFile> getIjsFiles() {
        return ijsFiles;
    }

    public void setIjsFiles(List<IjsFile> ijsFiles) {
        this.ijsFiles = ijsFiles;
    }

    public Boolean getAddressFinished() {
        return addressFinished;
    }

    public void setAddressFinished(Boolean addressFinished) {
        this.addressFinished = addressFinished;
    }

    public Boolean getHasLandSelectionAgreement() {
        return hasLandSelectionAgreement;
    }

    public void setHasLandSelectionAgreement(Boolean hasLandSelectionAgreement) {
        this.hasLandSelectionAgreement = hasLandSelectionAgreement;
    }

    public Boolean getHasActOfReconciliation() {
        return hasActOfReconciliation;
    }

    public void setHasActOfReconciliation(Boolean hasActOfReconciliation) {
        this.hasActOfReconciliation = hasActOfReconciliation;
    }

    public Boolean getHasSchemaZu() {
        return hasSchemaZu;
    }

    public void setHasSchemaZu(Boolean hasSchemaZu) {
        this.hasSchemaZu = hasSchemaZu;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectAddress() {
        return projectAddress;
    }

    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
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

    public Boolean getHasApprovedFileOzo() {
        return hasApprovedFileOzo;
    }

    public void setHasApprovedFileOzo(Boolean hasApprovedFileOzo) {
        this.hasApprovedFileOzo = hasApprovedFileOzo;
    }

    public Boolean getHasApprovedRejectedFile() {
        return hasApprovedRejectedFile;
    }

    public void setHasApprovedRejectedFile(Boolean hasApprovedRejectedFile) {
        this.hasApprovedRejectedFile = hasApprovedRejectedFile;
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

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }
}
