package blockchain.practice.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import blockchain.practice.exception.BlockChainAppException;
import blockchain.practice.exception.ServiceException;
import blockchain.practice.model.Auction;
import blockchain.practice.repository.AuctionRepository;
import jakarta.transaction.Transactional;

@Service
public class BlockchainService {

	private static List<Auction> auctions = new ArrayList<Auction>();

	private static final Logger LOGGER = LoggerFactory.getLogger(BlockchainService.class);

	@Autowired
	private AuctionRepository auctionRepository;

	@Transactional
	public ResponseEntity<?> saveAuctionInfo(Auction auction) throws BlockChainAppException {
		List<Auction> databaseAuctions = new ArrayList<Auction>();
		try {
			if (auctions.isEmpty()) {
				databaseAuctions = auctionRepository.findAll();
				auctions.addAll(databaseAuctions);
				auction.setPreviousHash(0);
			} else {
				auction.setPreviousHash(auctions.get(auctions.size() - 1).getAuctionHash());
			}
			auction.setAuctionHash(auction.hashCode());
			auction.setId(String.valueOf(UUID.randomUUID()));
			auction.setDate(String.valueOf(new Date()));
			auction.getTransactions().forEach(transaction -> {
				transaction.setAuction(new Auction(auction.getId()));
				transaction.setTransactionId(String.valueOf(UUID.randomUUID()));
			});
			auctions.add(auction);
			LOGGER.info("Saving the auction entity");
			auctionRepository.save(auction);

		} catch (Exception e) {
			LOGGER.error("Exception occured at saveAuctionInfo method of BlockchainService class: " + e);
			if (e instanceof BlockChainAppException) {
				throw new BlockChainAppException(
						new ServiceException("100", "Unable to save auction", HttpStatus.INTERNAL_SERVER_ERROR));
			}
			throw e;

		}
		return ResponseEntity.ok("Auction Saved!!!");
	}

}
