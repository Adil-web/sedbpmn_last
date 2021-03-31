package kz.kazgisa.data.repositories.dict;

import kz.kazgisa.data.entity.dict.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
}
