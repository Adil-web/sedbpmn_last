package kz.kazgisa.data.dto.report;

import kz.kazgisa.data.entity.user.UserRole;

public class ReportByExecutorDto {
    private UserRole userRole;
    private Integer count;

    public ReportByExecutorDto(UserRole userRole, int size) {
        this.userRole = userRole;
        this.count = size;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
