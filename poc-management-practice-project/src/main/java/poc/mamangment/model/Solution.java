package poc.mamangment.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import poc.mamangment.requestValidator.ValidateEmail;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Solution {

	@Id
	private String name;
	private String description;
	private String version;
	private String category;
	private String icon;
	@OneToMany(mappedBy = "solution")
	@JsonIgnore
	private List<POCForm> pocForms;
	private String lastModifiedDate;
	@ValidateEmail
	private String adminEmailId;
	private String approvedTime;
	@Transient
	private int adminPassword;
	private boolean fileName;


}
