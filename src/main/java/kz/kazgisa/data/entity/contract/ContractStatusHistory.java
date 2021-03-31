package kz.kazgisa.data.entity.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kz.kazgisa.data.entity.base.BaseEntity;
import kz.kazgisa.data.enums.ContractStatus;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.ZonedDateTime;

@Entity
public class ContractStatusHistory extends BaseEntity {
    private ContractStatus status;
    private ZonedDateTime dateTime;
    private String comment;
    private Contract contract;

    @Enumerated(EnumType.STRING)
    public ContractStatus getStatus() {
        return status;
    }

    public void setStatus(ContractStatus status) {
        this.status = status;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
}
