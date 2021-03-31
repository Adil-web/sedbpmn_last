package kz.kazgisa.data.dto;

import java.util.Date;

public class SignResponse {
    private Boolean signed;
    private String message;
    private String subjectDn;
    private Date certNotBefore;
    private Date certNotAfter;

    public Boolean getSigned() {
        return signed;
    }

    public void setSigned(Boolean signed) {
        this.signed = signed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubjectDn() {
        return subjectDn;
    }

    public void setSubjectDn(String subjectDn) {
        this.subjectDn = subjectDn;
    }

    public Date getCertNotBefore() {
        return certNotBefore;
    }

    public void setCertNotBefore(Date certNotBefore) {
        this.certNotBefore = certNotBefore;
    }

    public Date getCertNotAfter() {
        return certNotAfter;
    }

    public void setCertNotAfter(Date certNotAfter) {
        this.certNotAfter = certNotAfter;
    }
}
