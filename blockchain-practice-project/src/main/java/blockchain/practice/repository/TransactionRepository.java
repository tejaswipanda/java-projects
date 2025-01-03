package blockchain.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import blockchain.practice.model.AuctionTransaction;

@Repository
public interface TransactionRepository extends JpaRepository<AuctionTransaction, String> {

}
