package kz.kazgisa.data.dto;

import java.util.Date;

public class ValidApplicantDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String secondName;
    private String iin;
    private Date regDate;
    private UserDto regUser;
    private Boolean deleted;
    private Date deletedDate;
    private UserDto deletedUser;
    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getIin() {
        return iin;
    }

    public void setIin(String iin) {
        this.iin = iin;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public UserDto getRegUser() {
        return regUser;
    }

    public void setRegUser(UserDto regUser) {
        this.regUser = regUser;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Date getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }

    public UserDto getDeletedUser() {
        return deletedUser;
    }

    public void setDeletedUser(UserDto deletedUser) {
        this.deletedUser = deletedUser;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
