package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.CorrespondenceFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CorrespondenceFileRepository extends JpaRepository<CorrespondenceFile, Long> {
    CorrespondenceFile findFirstByName(String id);

    List<CorrespondenceFile> findByCorrespondenceId(Long id);
}
