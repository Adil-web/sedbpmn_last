package kz.kazgisa.data.dto.report;

public class ReportSubserviceDto {
    private Long id;
    private String nameRu;
    private int all;
    private int approved;
    private int rejected;
    private int finished;
    private int applied;
    private int registered;
    private int periodFinished;
    private int periodApproved;
    private int periodRejected;
    private boolean highlight=false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public int getAll() {
        return this.registered + this.approved + this.rejected;
    }

    public void setAll(int all) {
        this.all = all;
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

    public int getFinished() {
        return this.approved + this.rejected;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    public int getApplied() {
        return applied;
    }

    public void setApplied(int applied) {
        this.applied = applied;
    }

    public int getRegistered() {
        return registered;
    }

    public void setRegistered(int registered) {
        this.registered = registered;
    }

    public int getPeriodFinished() {
        return periodFinished;
    }

    public void setPeriodFinished(int periodFinished) {
        this.periodFinished = periodFinished;
    }

    public int getPeriodApproved() {
        return periodApproved;
    }

    public void setPeriodApproved(int periodApproved) {
        this.periodApproved = periodApproved;
    }

    public int getPeriodRejected() {
        return periodRejected;
    }

    public void setPeriodRejected(int periodRejected) {
        this.periodRejected = periodRejected;
    }

    public boolean isHighlight() {
        return highlight;
    }

    public void setHighlight(boolean highlight) {
        this.highlight = highlight;
    }
}
