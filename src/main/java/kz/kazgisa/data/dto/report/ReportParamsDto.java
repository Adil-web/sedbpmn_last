package kz.kazgisa.data.dto.report;

import kz.kazgisa.data.dto.DatesDto;

import java.util.Date;

public class ReportParamsDto extends DatesDto {
    private Date month;
    private Long subserviceId;

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Long getSubserviceId() {
        return subserviceId;
    }

    public void setSubserviceId(Long subserviceId) {
        this.subserviceId = subserviceId;
    }
}
