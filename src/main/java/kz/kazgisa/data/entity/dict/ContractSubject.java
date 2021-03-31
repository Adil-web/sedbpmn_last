package kz.kazgisa.data.entity.dict;

import kz.kazgisa.data.entity.base.LocalNameEntity;
import kz.kazgisa.data.enums.ContractSubjectType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class ContractSubject extends LocalNameEntity {
    private ContractSubjectType subjectType;

    @Enumerated(EnumType.STRING)
    public ContractSubjectType getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(ContractSubjectType subjectType) {
        this.subjectType = subjectType;
    }
}
