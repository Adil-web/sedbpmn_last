package kz.kazgisa.data.dto.report;

public class ReportReceivedDto {
    private Long all;
    private Long rejected;
    private Long apz;
    private Long communal;
    private Long inCommunal;
    private Long fromCommunal;
    private Long communalRejected;
    private Long communalApproved;
    private Long communalOthers;

    public Long getAll() {
        return all;
    }

    public void setAll(Long all) {
        this.all = all;
    }

    public Long getRejected() {
        return rejected;
    }

    public void setRejected(Long rejected) {
        this.rejected = rejected;
    }

    public Long getApz() {
        return apz;
    }

    public void setApz(Long apz) {
        this.apz = apz;
    }

    public Long getCommunal() {
        return communal;
    }

    public void setCommunal(Long communal) {
        this.communal = communal;
    }

    public Long getInCommunal() {
        return inCommunal;
    }

    public void setInCommunal(Long inCommunal) {
        this.inCommunal = inCommunal;
    }

    public Long getFromCommunal() {
        return fromCommunal;
    }

    public void setFromCommunal(Long fromCommunal) {
        this.fromCommunal = fromCommunal;
    }

    public Long getCommunalRejected() {
        return communalRejected;
    }

    public void setCommunalRejected(Long communalRejected) {
        this.communalRejected = communalRejected;
    }

    public Long getCommunalApproved() {
        return communalApproved;
    }

    public void setCommunalApproved(Long communalApproved) {
        this.communalApproved = communalApproved;
    }

    public Long getCommunalOthers() {
        return communalOthers;
    }

    public void setCommunalOthers(Long communalOthers) {
        this.communalOthers = communalOthers;
    }
}
