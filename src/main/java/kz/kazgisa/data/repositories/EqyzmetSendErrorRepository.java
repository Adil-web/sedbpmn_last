package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.EqyzmetSendError;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EqyzmetSendErrorRepository extends JpaRepository<EqyzmetSendError, Long> {
}
