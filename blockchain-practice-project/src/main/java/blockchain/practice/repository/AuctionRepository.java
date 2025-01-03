package blockchain.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import blockchain.practice.model.Auction;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, String> {

}
