package kz.kazgisa.data.entity.contract;

import kz.kazgisa.data.entity.base.BaseEntity;
import kz.kazgisa.data.entity.dict.ContractSubject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class ContractTemplate extends BaseEntity {
    private String nameRu;
    private String nameKk;
    private String text;
    private ContractSubject contractSubject;

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public String getNameKk() {
        return nameKk;
    }

    public void setNameKk(String nameKk) {
        this.nameKk = nameKk;
    }

    @Column(columnDefinition = "text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @ManyToOne
    public ContractSubject getContractSubject() {
        return contractSubject;
    }

    public void setContractSubject(ContractSubject contractSubject) {
        this.contractSubject = contractSubject;
    }
}
