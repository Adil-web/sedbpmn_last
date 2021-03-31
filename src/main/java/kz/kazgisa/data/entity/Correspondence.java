package kz.kazgisa.data.entity;

import kz.kazgisa.data.entity.base.LocalNameEntity;
import kz.kazgisa.data.entity.dict.CorrespondenceCategory;
import kz.kazgisa.data.entity.user.User;
import kz.kazgisa.data.enums.CorrespondenceType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Correspondence extends LocalNameEntity {
    private CorrespondenceCategory category;
    private CorrespondenceType type;
    private Date regDate;
    private String sender;
    private String receiver;
    private String body;
    private boolean executable;
    private User executor;
    private Date executeDueDate;
    private boolean executed;
    private Date outDate;
    private String outNumber;
    private String regNumber;
    private String outBody;
    private User author;

    @ManyToOne
    public CorrespondenceCategory getCategory() {
        return category;
    }

    public void setCategory(CorrespondenceCategory type) {
        this.category = type;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    @Column(columnDefinition = "text")
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @ManyToOne
    public User getExecutor() {
        return executor;
    }

    public void setExecutor(User executor) {
        this.executor = executor;
    }

    public boolean isExecutable() {
        return executable;
    }

    public void setExecutable(boolean executable) {
        this.executable = executable;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    public CorrespondenceType getType() {
        return type;
    }

    public void setType(CorrespondenceType type) {
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Date getExecuteDueDate() {
        return executeDueDate;
    }

    public void setExecuteDueDate(Date executeDueDate) {
        this.executeDueDate = executeDueDate;
    }

    public Date getOutDate() {
        return outDate;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    public String getOutNumber() {
        return outNumber;
    }

    public void setOutNumber(String outNumber) {
        this.outNumber = outNumber;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    @Column(columnDefinition = "text")
    public String getOutBody() {
        return outBody;
    }

    public void setOutBody(String outBody) {
        this.outBody = outBody;
    }

    @ManyToOne
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
