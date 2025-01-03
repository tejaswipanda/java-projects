package blockchain.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import blockchain.practice.exception.BlockChainAppException;
import blockchain.practice.model.Auction;
import blockchain.practice.service.BlockchainService;

@RestController
public class BlockchainController {

	@Autowired
	private BlockchainService blockchainService;

	@PostMapping("/saveAuctionInfo")
	public ResponseEntity<?> saveAuctionInfo(@RequestBody Auction auction) throws BlockChainAppException {
		return blockchainService.saveAuctionInfo(auction);
	}
}
