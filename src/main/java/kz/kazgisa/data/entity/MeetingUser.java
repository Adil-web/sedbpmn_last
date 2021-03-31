package kz.kazgisa.data.entity;

import kz.kazgisa.data.entity.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class MeetingUser extends BaseEntity {
    private Long meetingId;
    private Long userId;
    private MeetingUserStatus status;

    public MeetingUser() {}

    public MeetingUser(Long meetingId, Long userId) {
        this.meetingId = meetingId;
        this.userId = userId;
    }


    public Long getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Long meetingId) {
        this.meetingId = meetingId;
    }

    public   Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @ManyToOne
    @JoinColumn(name = "status_id")
    public MeetingUserStatus getStatus() {
        return status;
    }

    public void setStatus(MeetingUserStatus status) {
        this.status = status;
    }
}
