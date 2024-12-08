package poc.mamangment.requestValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import poc.mamangment.constants.Constants;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Constraint(validatedBy = ValidateUserEmail.class)
public @interface ValidateEmail {
	
	public String message() default Constants.EMAIL_VALIDATOR_MSG;
	Class<?>[] groups() default{};
	Class<? extends Payload>[] payload() default{};

}
