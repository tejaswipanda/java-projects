package poc.mamangment.requestValidator;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidateUserEmail implements ConstraintValidator<ValidateEmail, String>{

	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@" 
		        + "[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$";
		 return Pattern.compile(regexPattern)
			      .matcher(email)
			      .matches();
	}

}
