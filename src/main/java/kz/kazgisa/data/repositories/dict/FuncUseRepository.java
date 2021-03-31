package kz.kazgisa.data.repositories.dict;

import kz.kazgisa.data.entity.dict.FuncUse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncUseRepository extends JpaRepository<FuncUse, Long> {
}
