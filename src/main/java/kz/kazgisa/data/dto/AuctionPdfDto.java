package kz.kazgisa.data.dto;

import java.util.Date;

public class AuctionPdfDto {
    private String pdfText;
    private Date agreementDate;
    private Date agreementEndDate;
    private Long agreementYears;
    private String reasonRu;
    private String reasonKk;
    private Double rentPayment;

    public String getPdfText() {
        return pdfText;
    }

    public void setPdfText(String pdfText) {
        this.pdfText = pdfText;
    }

    public Date getAgreementDate() {
        return agreementDate;
    }

    public void setAgreementDate(Date agreementDate) {
        this.agreementDate = agreementDate;
    }

    public Date getAgreementEndDate() {
        return agreementEndDate;
    }

    public void setAgreementEndDate(Date agreementEndDate) {
        this.agreementEndDate = agreementEndDate;
    }

    public Long getAgreementYears() {
        return agreementYears;
    }

    public void setAgreementYears(Long agreementYears) {
        this.agreementYears = agreementYears;
    }

    public String getReasonRu() {
        return reasonRu;
    }

    public void setReasonRu(String reasonRu) {
        this.reasonRu = reasonRu;
    }

    public String getReasonKk() {
        return reasonKk;
    }

    public void setReasonKk(String reasonKk) {
        this.reasonKk = reasonKk;
    }

    public Double getRentPayment() {
        return rentPayment;
    }

    public void setRentPayment(Double rentPayment) {
        this.rentPayment = rentPayment;
    }
}
