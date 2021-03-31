package kz.kazgisa.data.dto;

import java.util.Date;

public interface AppDutyView {
    Long getId();
    String getSubserviceShortNameRu();
    String getOrgName();
    String getFirstName();
    String getLastName();
    String getSecondName();
    String getIin();
    String getBin();
    String getCadastreNumber();
    String getGeometry();
    String getAddress();
    String getPhone();
    Date getCreateDate();
    Date getFactEndDate();
    Boolean getApproved();
//    String getStatus();
}
