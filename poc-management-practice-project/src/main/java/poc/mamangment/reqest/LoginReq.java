package poc.mamangment.reqest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LoginReq {
	private String email;
	private String password;
	private boolean isAdmin;

	public LoginReq(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

}
