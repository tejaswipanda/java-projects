package poc.mamangment.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import poc.mamangment.constants.Constants;
import poc.mamangment.exception.POCAPPException;
import poc.mamangment.exception.ServiceException;
import poc.mamangment.model.User;
import poc.mamangment.repository.UserRepository;

@Service
public class POCAPPService {

	@Autowired
	private UserRepository userRepository;

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
		return null;

	}

}
