package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.IzhsOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IzhsOrdersRepository extends JpaRepository<IzhsOrder, Long>, JpaSpecificationExecutor<IzhsOrder> {
    IzhsOrder findFirstByIin(String iin);
}
