package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.AppMeetingUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppMeetingUserRepository extends JpaRepository<AppMeetingUser, Long> {
    AppMeetingUser findFirstByAppIdAndAndMeetingUserId(Long appId, Long meetingUserId);

//    @Query(value = "select amtu.* from app_meeting_user amtu " +
//            "inner join meeting_user mtu on mtu.id=amtu.meeting_user_id" +
//            "where amtu.app_id=:appId" +
//            "limit 1", nativeQuery = true)
//    AppMeetingUser findForUser(@Param("appId") Long appId, @Param("meetingId") Long meetingId, @Param("userId") Long userId);

    @Query(value = "select * from app_meeting_user where app_id=:appId and " +
            "meeting_user_id=(select id from meeting_user where meeting_id=:meetingId and user_id=:userId limit 1) limit 1", nativeQuery = true)
    AppMeetingUser getAppMeetingUserForApp(@Param("appId") Long appId, @Param("meetingId") Long meetingId, @Param("userId") Long userId);

    List<AppMeetingUser> findByAppIdAndMeetingUserId(Long appId, Long meetingUserId);

    @Query(value = "select * from app_meeting_user where meeting_user_id=:meetingUserId and app_id=:appId limit 1", nativeQuery = true)
    AppMeetingUser getAppMeetingUserByMeetingUser(@Param("meetingUserId") Long meetingUserId, @Param("appId") Long appId);
}
