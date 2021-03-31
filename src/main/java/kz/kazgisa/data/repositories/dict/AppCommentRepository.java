package kz.kazgisa.data.repositories.dict;

import kz.kazgisa.data.entity.dict.AppComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppCommentRepository extends JpaRepository<AppComment, Long> {
}
