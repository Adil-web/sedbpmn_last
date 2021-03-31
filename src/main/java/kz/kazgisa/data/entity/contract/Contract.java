package kz.kazgisa.data.entity.contract;

import kz.kazgisa.data.entity.App;
import kz.kazgisa.data.entity.base.BaseEntity;
import kz.kazgisa.data.entity.dict.ContractSubject;
import kz.kazgisa.data.entity.user.User;
import kz.kazgisa.data.enums.PaymentType;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Contract extends BaseEntity {
    private ContractSubject subject;
    private ContractStatusHistory statusHistory;
    private String number;
    private Date date;
    private Date createDate;
    private Date dueDate;
    private BigDecimal sum;
    private Contract prevContract;
    private String body;
    private ContractParty organizer;
    private ContractParty customer;
    private User author;
    private App app;
    private Boolean deleted;
    private PaymentType paymentType;
    private BigDecimal preSum;
    private Date preDate;
    private Date preFactDate;
    private BigDecimal mainSum;
    private Date mainDate;
    private Date mainFactDate;
    private Integer installmentMonths;
    private Integer lastDayOfPayment;
    private BigDecimal fineSize;
    private Long preLateDays;
    private Long mainLateDays;


    @ManyToOne
    public ContractSubject getSubject() {
        return subject;
    }

    public void setSubject(ContractSubject subject) {
        this.subject = subject;
    }

    @ManyToOne
    public ContractStatusHistory getStatusHistory() {
        return statusHistory;
    }

    public void setStatusHistory(ContractStatusHistory statusHistory) {
        this.statusHistory = statusHistory;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }


    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    @ManyToOne
    public Contract getPrevContract() {
        return prevContract;
    }

    public void setPrevContract(Contract prevContract) {
        this.prevContract = prevContract;
    }

    @Column(columnDefinition = "text")
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @ManyToOne
    public ContractParty getOrganizer() {
        return organizer;
    }

    public void setOrganizer(ContractParty organizer) {
        this.organizer = organizer;
    }

    @ManyToOne
    public ContractParty getCustomer() {
        return customer;
    }

    public void setCustomer(ContractParty customer) {
        this.customer = customer;
    }

    @ManyToOne
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @ManyToOne
    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(columnDefinition = "boolean default false")
    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Enumerated(EnumType.STRING)
    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public BigDecimal getPreSum() {
        return preSum;
    }

    public void setPreSum(BigDecimal preSum) {
        this.preSum = preSum;
    }

    public Date getPreDate() {
        return preDate;
    }

    public void setPreDate(Date preDate) {
        this.preDate = preDate;
    }

    public BigDecimal getMainSum() {
        return mainSum;
    }

    public void setMainSum(BigDecimal mainSum) {
        this.mainSum = mainSum;
    }

    public Date getMainDate() {
        return mainDate;
    }

    public void setMainDate(Date mainDate) {
        this.mainDate = mainDate;
    }

    public Integer getInstallmentMonths() {
        return installmentMonths;
    }

    public void setInstallmentMonths(Integer installmentMonths) {
        this.installmentMonths = installmentMonths;
    }

    public Integer getLastDayOfPayment() {
        return lastDayOfPayment;
    }

    public void setLastDayOfPayment(Integer lastDayOfPayment) {
        this.lastDayOfPayment = lastDayOfPayment;
    }

    public BigDecimal getFineSize() {
        return fineSize;
    }

    public void setFineSize(BigDecimal fineSize) {
        this.fineSize = fineSize;
    }

    public Date getPreFactDate() {
        return preFactDate;
    }

    public void setPreFactDate(Date preFactDate) {
        this.preFactDate = preFactDate;
    }

    public Date getMainFactDate() {
        return mainFactDate;
    }

    public void setMainFactDate(Date mainFactDate) {
        this.mainFactDate = mainFactDate;
    }

    @Transient
    public Long getPreLateDays() {
        return preLateDays;
    }

    public void setPreLateDays(Long preLateDays) {
        this.preLateDays = preLateDays;
    }

    @Transient
    public Long getMainLateDays() {
        return mainLateDays;
    }

    public void setMainLateDays(Long mainLateDays) {
        this.mainLateDays = mainLateDays;
    }
}
