package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.HeaderFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeaderFileRepository extends JpaRepository<HeaderFile, Long> {
}
