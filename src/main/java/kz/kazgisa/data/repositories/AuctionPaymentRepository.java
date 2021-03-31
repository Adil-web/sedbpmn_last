package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.AuctionPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionPaymentRepository extends JpaRepository<AuctionPayment, Long> {
    List<AuctionPayment> findByAppId(Long appId);
}
