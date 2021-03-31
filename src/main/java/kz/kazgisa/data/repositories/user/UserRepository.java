package kz.kazgisa.data.repositories.user;

import kz.kazgisa.data.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.DoubleStream;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByUsername(String username);

    User findFirstByEmail(String email);

    List<User> findByOrganizationId(Long id);

    Page<User> findByRegionId(Long regionId,Pageable pageable);

    Page<User> findByUsernameContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrSecondNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String username, String firstName, String lastName, String secondName, String email, Pageable pageable);
}
