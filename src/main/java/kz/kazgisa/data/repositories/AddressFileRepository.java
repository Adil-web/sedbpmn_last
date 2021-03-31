package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.AddressFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AddressFileRepository extends JpaRepository<AddressFile, Long> {
    AddressFile findFirstByAppId(Long appId);
    @Modifying
    @Transactional
    void deleteByAppId(Long appId);
}
