package kz.kazgisa.data.entity;

import kz.kazgisa.data.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "land_bases_new")
public class LandBasesNew extends BaseEntity {

    private String act;
    private String iin;
    private String fullName;
    private String cadastre;
    private String right1;
    private String general;
    private String quotient;
    private String specialPurpose;
    private String basisOfIssue;
    private String basisOfIssueNumber;
    private String location;
    private String locationPlot;
    private String locationNumber;
    private String landStatus;
    private Long   landStatusNumber;
    private Date   dateOfIssue1;
    private Date   basisOfIssueDate1;

    private Long id;

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public String getIin() {
        return iin;
    }

    public void setIin(String iin) {
        this.iin = iin;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCadastre() {
        return cadastre;
    }

    public void setCadastre(String cadastre) {
        this.cadastre = cadastre;
    }

    public String getRight1() {
        return right1;
    }

    public void setRight1(String right1) {
        this.right1 = right1;
    }

    public String getGeneral() {
        return general;
    }

    public void setGeneral(String general) {
        this.general = general;
    }

    public String getQuotient() {
        return quotient;
    }

    public void setQuotient(String quotient) {
        this.quotient = quotient;
    }

    public String getSpecialPurpose() {
        return specialPurpose;
    }

    public void setSpecialPurpose(String specialPurpose) {
        this.specialPurpose = specialPurpose;
    }

    public String getBasisOfIssue() {
        return basisOfIssue;
    }

    public void setBasisOfIssue(String basisOfIssue) {
        this.basisOfIssue = basisOfIssue;
    }

    public String getBasisOfIssueNumber() {
        return basisOfIssueNumber;
    }

    public void setBasisOfIssueNumber(String basisOfIssueNumber) {
        this.basisOfIssueNumber = basisOfIssueNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationPlot() {
        return locationPlot;
    }

    public void setLocationPlot(String locationPlot) {
        this.locationPlot = locationPlot;
    }

    public String getLocationNumber() {
        return locationNumber;
    }

    public void setLocationNumber(String locationNumber) {
        this.locationNumber = locationNumber;
    }

    public String getLandStatus() {
        return landStatus;
    }

    public void setLandStatus(String landStatus) {
        this.landStatus = landStatus;
    }

    public Long getLandStatusNumber() {
        return landStatusNumber;
    }

    public void setLandStatusNumber(Long landStatusNumber) {
        this.landStatusNumber = landStatusNumber;
    }

    public Date getDateOfIssue1() {
        return dateOfIssue1;
    }

    public void setDateOfIssue1(Date dateOfIssue1) {
        this.dateOfIssue1 = dateOfIssue1;
    }

    public Date getBasisOfIssueDate1() {
        return basisOfIssueDate1;
    }

    public void setBasisOfIssueDate1(Date basisOfIssueDate1) {
        this.basisOfIssueDate1 = basisOfIssueDate1;
    }

    public LandBasesNew() {
    }
    public LandBasesNew(Long id, String act ,String  iin  ,String fullName ,String cadastre ,String right1  ,String general , String quotient ,String specialPurpose , String basisOfIssue ,String  basisOfIssueNumber ,String location  ,String locationPlot  , String  locationNumber ,String  landStatus  ,Long  landStatusNumber  ,Date  dateOfIssue1  ,Date basisOfIssueDate1 ) {
        this.id = id;
        this.act=act;
        this.iin=iin;
        this.fullName=fullName;
        this.cadastre=cadastre;
        this.right1=right1;
        this.general=general;
        this.quotient=quotient;
        this.specialPurpose=specialPurpose;
        this.basisOfIssue=basisOfIssue;
        this.basisOfIssueNumber=basisOfIssueNumber;
        this.location=location;
        this.locationPlot=locationPlot;
        this.locationNumber=locationNumber;
        this.landStatus=landStatus;
        this.landStatusNumber=landStatusNumber;
        this.dateOfIssue1=dateOfIssue1;
        this.basisOfIssueDate1=basisOfIssueDate1;

    }

    @Override
    public String toString() {
        return "LandBasesNew{" + "id=" + id +" , act=" + act+ "iin=,"+iin+" , fullName="+fullName+ ", cadastre"+cadastre + ", right1=" +right1 + ", general=" +general + ", quotient="+quotient + ",specialPurpose="+specialPurpose + ", basisOfIssue="+basisOfIssue+
                ", basisOfIssueNumber="+basisOfIssueNumber + ", location="+location + ", locationPlot="+locationPlot+ ", locationNumber=" +locationNumber+ ",landStatus="+  ", basisOfIssueNumber="+basisOfIssueNumber + ", location="+location + ", locationPlot="+locationPlot+ ", locationNumber=" +locationNumber+ ",landStatus=" +landStatus+", landStatusNumber="+landStatusNumber + ", dateOfIssue1="+dateOfIssue1 + ", basisOfIssueDate1=" +basisOfIssueDate1+'}';
    }

}
