package kz.kazgisa.data.repositories.user;

import kz.kazgisa.data.entity.user.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(value = "select * from role where id in " +
            "(select role_id from user_role where user_id=:userId)", nativeQuery = true)
    List<Role> getByUserId(@Param("userId") Long userId);

    Page<Role> findByNameRuContainingIgnoreCase(String name, Pageable pageable);

    Page<Role> findByIdIn(List<Long> map, Pageable pageable);

    Page<Role> findByIdInOrderByNumAsc(List<Long> map, Pageable pageable);
}
