package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.MeetingApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingAppRepository extends JpaRepository<MeetingApp, Long> {
    List<MeetingApp> findByMeetingId(Long meetingId);
    List<MeetingApp> findByMeetingIdOrderByOrderNumAsc(Long meetingId);
    List<MeetingApp> findByMeetingIdAndAppId(Long meetingId, Long appId);
    MeetingApp findFirstByAppId(Long appId);
    MeetingApp findFirstByAppIdAndMeetingId(Long appId, Long meetingId);
}
