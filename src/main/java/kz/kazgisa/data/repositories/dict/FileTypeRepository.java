package kz.kazgisa.data.repositories.dict;

import kz.kazgisa.data.entity.FileType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileTypeRepository  extends JpaRepository<FileType, Long>, JpaSpecificationExecutor<FileType> {
    List<FileType> findBySubserviceIdOrderById(Long subserviceId);
}



