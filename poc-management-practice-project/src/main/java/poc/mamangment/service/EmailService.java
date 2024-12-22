package poc.mamangment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	public ResponseEntity<?> resendVerificationMail(long code, String mail) {
		String content = "Resent! Verification code is: " + code;
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("tejaswipanda@gmail.com");
		mailMessage.setTo(mail);
		mailMessage.setText(content);
		mailMessage.setSubject("Resend Verification Mail");
		javaMailSender.send(mailMessage);
		return ResponseEntity.ok("Sent");
	}

}
