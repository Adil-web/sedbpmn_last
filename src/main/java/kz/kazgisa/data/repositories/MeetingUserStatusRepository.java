package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.MeetingUserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingUserStatusRepository extends JpaRepository<MeetingUserStatus, Long> {
}
