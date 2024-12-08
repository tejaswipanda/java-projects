package poc.mamangment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class POCForm {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Solution solution;
	private String status;
	private String duration;
	private String businessUnit;
	private String account;
	private String description;
	private String projectName;
	private String spocEmailId;
	private String lastModifiedDate;
	private String approvedTime;
	@Transient
	private int verificationCode;
	private boolean isVerified;

}
