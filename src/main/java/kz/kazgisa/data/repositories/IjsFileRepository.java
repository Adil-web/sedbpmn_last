package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.IjsFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IjsFileRepository extends JpaRepository<IjsFile, Long> {
    List<IjsFile> findByAppId(Long appId);
    @Modifying
    @Transactional
    void deleteByAppId(Long appId);
}
