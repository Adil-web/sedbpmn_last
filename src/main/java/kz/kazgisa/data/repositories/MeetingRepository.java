package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    List<Meeting> findByFinished(Boolean finished);

    @Query(value = "select mt.* from meeting mt inner join meeting_user mu on mt.id=mu.meeting_id where mu.user_id=:userId",nativeQuery = true)
    List<Meeting> findByUserId(@Param("userId") Long userId);

    @Query(value = "select mt.* from meeting mt inner join meeting_user mu on mt.id=mu.meeting_id where mu.user_id=:userId and mt.finished=false",nativeQuery = true)
    List<Meeting> findActiveByUserId(@Param("userId") Long userId);
}
