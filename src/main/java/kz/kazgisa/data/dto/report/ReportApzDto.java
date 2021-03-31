package kz.kazgisa.data.dto.report;

import kz.kazgisa.data.entity.user.User;

public class ReportApzDto {
    private User user;
    private int approved;
    private int rejected;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getApproved() {
        return approved;
    }

    public void setApproved(int approved) {
        this.approved = approved;
    }

    public int getRejected() {
        return rejected;
    }

    public void setRejected(int rejected) {
        this.rejected = rejected;
    }
}
