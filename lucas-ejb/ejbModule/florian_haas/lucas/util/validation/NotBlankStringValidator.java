package florian_haas.lucas.util.validation;

import javax.validation.*;

public class NotBlankStringValidator implements ConstraintValidator<NotBlankString, String> {
	@Override
	public void initialize(NotBlankString constraintAnnotation) {}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) return true;
		return !value.trim().isEmpty();
	}
}
