package blockchain.practice.model;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Auction {

	@Id
	private String id;
	private String buyerMobileNumber;
	private String sellerMobileNumber;
	@JsonIgnore
	private String date;
	@OneToMany(mappedBy = "auction", cascade = CascadeType.ALL)
	private List<AuctionTransaction> transactions;
	private int previousHash;
	private int auctionHash;

	public Auction(String id, String buyerMobileNumber, String sellerMobileNumber, String date,
			List<AuctionTransaction> transactions, int previousHash) {
		super();
		this.id = id;
		this.buyerMobileNumber = buyerMobileNumber;
		this.sellerMobileNumber = sellerMobileNumber;
		this.date = date;
		this.transactions = transactions;
		this.previousHash = previousHash;
		Object[] contents = { transactions.hashCode(), previousHash };
		this.auctionHash = Arrays.hashCode(contents);
	}

	public Auction(String id) {
		super();
		this.id = id;
	}

}
