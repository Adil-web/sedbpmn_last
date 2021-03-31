package kz.kazgisa.data.repositories.dict;

import kz.kazgisa.data.entity.dict.Communal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunalRepository extends JpaRepository<Communal, Long> {
}
