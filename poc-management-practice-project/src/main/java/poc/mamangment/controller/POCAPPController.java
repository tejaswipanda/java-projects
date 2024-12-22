package poc.mamangment.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import poc.mamangment.exception.POCAPPException;
import poc.mamangment.model.User;
import poc.mamangment.reqest.LoginReq;
import poc.mamangment.service.EmailService;
import poc.mamangment.service.POCAPPService;

@RestController
@RequestMapping("poc/management")
@Validated
public class POCAPPController {

	@Autowired
	private POCAPPService pocappService;

	@Autowired
	private EmailService emailService;

	private static final Logger LOGGER = LoggerFactory.getLogger(POCAPPController.class);

	@PostMapping("/signUp")
	@CrossOrigin
	public ResponseEntity<?> userSignUp(@RequestBody @Valid User user) throws POCAPPException {
		LOGGER.info("User is valid. Creating database entry");
		return pocappService.createDatabaseEntry(user);
	}

	@PostMapping("/login")
	@CrossOrigin
	public ResponseEntity<?> login(@RequestBody @Valid LoginReq loginReq) throws POCAPPException {
		LOGGER.info("User is valid. Creating database entry");
		return pocappService.login(loginReq);
	}

	@PostMapping("/resendVerificationMail/{code}/{mail}")
	@CrossOrigin
	public ResponseEntity<?> resendVerificationMail(long code, String mail) throws POCAPPException {
		return emailService.resendVerificationMail(code, mail);
	}
}
