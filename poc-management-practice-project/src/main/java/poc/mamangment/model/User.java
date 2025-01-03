package poc.mamangment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import poc.mamangment.requestValidator.ValidateEmail;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

	@Id
	@ValidateEmail
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private boolean isAdmin;

}
