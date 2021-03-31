package kz.kazgisa.data.entity.contract;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kz.kazgisa.data.entity.base.BaseEntity;
import org.hibernate.annotations.Fetch;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class ContractExecution extends BaseEntity    {

    private Contract contract;
    private Date dueDate;
    private String comment;
    private Date factDate;
    private BigDecimal sum;
    private BigDecimal factSum;
    private Boolean executed;
    private String specialText;

    @ManyToOne
    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getFactDate() {
        return factDate;
    }

    public void setFactDate(Date factDate) {
        this.factDate = factDate;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public BigDecimal getFactSum() {
        return factSum;
    }

    public void setFactSum(BigDecimal factSum) {
        this.factSum = factSum;
    }

    public Boolean getExecuted() {
        return executed;
    }

    public void setExecuted(Boolean executed) {
        this.executed = executed;
    }


    public String getSpecialText() {
        return specialText;
    }

    public void setSpecialText(String specialText) {
        this.specialText = specialText;
    }
}
