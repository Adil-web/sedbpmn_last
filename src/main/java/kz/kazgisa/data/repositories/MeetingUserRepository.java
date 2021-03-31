package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.MeetingUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingUserRepository extends JpaRepository<MeetingUser, Long> {
    List<MeetingUser> findByMeetingId(Long meetingId);
    List<MeetingUser> findByMeetingIdAndUserId(Long meetingId, Long userId);
    MeetingUser findFirstByMeetingIdAndUserId(Long meetingId, Long userId);
}
