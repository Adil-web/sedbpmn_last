package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.AppFile;
import kz.kazgisa.data.enums.FileCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppFileRepository extends JpaRepository<AppFile, Long> {
    List<AppFile> findByAppId(Long appId);

    List<AppFile> findByAppIdAndFileCategory(Long appId, FileCategory fileCategory);

    AppFile findFirstByName(String id);

    AppFile findFirstByAppIdAndFileCategory(Long id, FileCategory resultFile);
}
