package kz.kazgisa.data.repositories.dict;

import kz.kazgisa.data.entity.dict.EstateObjectType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstateObjectTypeRepository extends JpaRepository<EstateObjectType, Long> {
}
