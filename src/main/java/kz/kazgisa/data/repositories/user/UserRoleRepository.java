package kz.kazgisa.data.repositories.user;

import kz.kazgisa.data.entity.Organization;
import kz.kazgisa.data.entity.user.UserRole;
import kz.kazgisa.data.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    List<UserRole> findByUsername(String username);

    List<UserRole> findBySubserviceId(Long id);

    List<UserRole> findByRoleId(Long roleId);

    List<UserRole> findByRoleIdAndSubserviceId(Long roleId, Long subserviceId);

    UserRole findFirstBySubserviceIdAndRoleName(Long subservice, String name);

    UserRole findFirstByUsernameInAndRoleName(List<String> users    , String name);

    UserRole findFirstByRoleName(String roleName);

    UserRole findFirstByRoleNameAndCurrentTrue(String role);

    List<UserRole> findByRoleNameContaining(String role);

    List<UserRole> findBySubserviceIdOrderByRoleNumAsc(Long id);

    List<UserRole> findByRoleNameAndCurrentTrue(String group);

    @Modifying
    @Query(value = "update user_role set current=false where subservice_id=:subserviceId and role_id=:roleId",nativeQuery = true)
    void updateCurrent(@Param("subserviceId") Long subserviceId, @Param("roleId") Long roleId);

    List<UserRole> findByRoleName(String group);
}
