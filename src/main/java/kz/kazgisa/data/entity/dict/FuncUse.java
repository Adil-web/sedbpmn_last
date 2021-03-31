package kz.kazgisa.data.entity.dict;

import kz.kazgisa.data.entity.base.LocalNameEntity;

import javax.persistence.Entity;

// funkcionalnoe naznachenie
@Entity
public class FuncUse extends LocalNameEntity {
    private String code;
    private String codeAuzr;
    private String parentCode;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeAuzr() {
        return codeAuzr;
    }

    public void setCodeAuzr(String codeAuzr) {
        this.codeAuzr = codeAuzr;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
}
