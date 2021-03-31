package kz.kazgisa.data.dto.report;

public class ReportCommonDto {
    private Long all;
    private Long rejected;
    private Long apz;
    private Long inCommunal;
    private Long fromCommunal;

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
}
