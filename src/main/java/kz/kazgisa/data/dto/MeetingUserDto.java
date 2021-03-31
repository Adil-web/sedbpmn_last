package kz.kazgisa.data.dto;

import kz.kazgisa.data.entity.MeetingUserStatus;

public class MeetingUserDto extends UserDto {
    private MeetingUserStatus status;

    public MeetingUserStatus getStatus() {
        return status;
    }

    public void setStatus(MeetingUserStatus status) {
        this.status = status;
    }
}
