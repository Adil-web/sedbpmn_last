package kz.kazgisa.data.entity.dict;

import kz.kazgisa.data.entity.base.LocalNameEntity;
import kz.kazgisa.data.enums.MenuCode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Menu extends LocalNameEntity {

    private MenuCode code;

    @Enumerated(EnumType.STRING)
    public MenuCode getCode() {
        return code;
    }

    public void setCode(MenuCode code) {
        this.code = code;
    }


}
