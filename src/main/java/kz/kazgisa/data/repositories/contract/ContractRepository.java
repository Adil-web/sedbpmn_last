package kz.kazgisa.data.repositories.contract;

import kz.kazgisa.data.entity.contract.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.DoubleStream;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    Page<Contract> findByDeletedFalse(Pageable pageable);

    Page<Contract> findByPreFactDateIsNullOrMainFactDateIsNullOrderByDateAsc(Pageable page);

    Page<Contract> findByPreFactDateIsNullOrMainFactDateIsNull(Pageable pageable);

}
