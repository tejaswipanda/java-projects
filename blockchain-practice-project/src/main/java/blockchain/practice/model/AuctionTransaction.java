package blockchain.practice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuctionTransaction {
	@Id
	@JsonIgnore
	private String transactionId;
	private int amount;
	@ManyToOne
	@JsonProperty(access = Access.WRITE_ONLY)
	private Auction auction;

	public AuctionTransaction(String transactionId, int amount) {
		super();
		this.transactionId = transactionId;
		this.amount = amount;
	}

}
