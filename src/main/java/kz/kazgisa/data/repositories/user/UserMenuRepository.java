package kz.kazgisa.data.repositories.user;

import kz.kazgisa.data.entity.user.UserMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMenuRepository extends JpaRepository<UserMenu, Long> {
    List<UserMenu> findByUserId(Long id);

    @Modifying
    void deleteByUserId(Long id);
}
