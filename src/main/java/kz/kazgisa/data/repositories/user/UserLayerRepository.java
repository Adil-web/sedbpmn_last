package kz.kazgisa.data.repositories.user;

import kz.kazgisa.data.entity.user.UserLayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserLayerRepository extends JpaRepository<UserLayer, Long> {
    List<UserLayer> findByUserId(Long userId);
}
