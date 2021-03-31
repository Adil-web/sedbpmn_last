package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.EskizFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EskizFileRepository extends JpaRepository<EskizFile, Long> {
    List<EskizFile> findByAppId(Long appId);
    EskizFile findFirstByGuid(String guid);

    @Query(value = "select id,app_id,name,file_category,guid,content_type,object_id,size,upload_date,null as file from eskiz_file where app_id=:appId", nativeQuery = true)
    List<EskizFile> getEskizFiles(@Param("appId") Long appId);
}
