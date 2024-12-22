package poc.mamangment.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import poc.mamangment.config.JwtUtil;
import poc.mamangment.constants.Constants;
import poc.mamangment.exception.POCAPPException;
import poc.mamangment.exception.ServiceException;
import poc.mamangment.model.User;
import poc.mamangment.repository.UserRepository;
import poc.mamangment.reqest.LoginReq;
import poc.mamangment.response.UseAuthResponse;

@Service
public class POCAPPService {

	@Autowired
	private UserRepository userRepository;

	private JwtUtil jwtUtil;

	private AuthenticationManager authenticationManager;

	public ResponseEntity<?> createDatabaseEntry(@Valid User user) throws POCAPPException {
		String email = user.getEmail();
		List<User> users = userRepository.findAll();
		List<String> emailsInDatabase = users.parallelStream().map(userObj -> userObj.getEmail())
				.collect(Collectors.toList());
		if (emailsInDatabase.contains(email)) {
			ServiceException serviceException = new ServiceException(Constants.USER_EXISTS_ERR_CODE,
					Constants.USER_EXISTS_ERR_MSG, HttpStatus.BAD_REQUEST);
			POCAPPException pocappException = new POCAPPException(serviceException);
			throw pocappException;
		}
		String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
		user.setPassword(encodedPassword);
		userRepository.save(user);
		String token = jwtUtil.createToken(user);
		UseAuthResponse response = new UseAuthResponse(email, token);
		return ResponseEntity.ok(response);

	}

	public ResponseEntity<?> login(@Valid LoginReq loginReq) throws POCAPPException {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));
		String email = authentication.getName();
		return null;
	}

}
