package florian_haas.lucas.validation;

import javax.validation.*;

public class NotBlankCharArrayValidator implements ConstraintValidator<NotBlankCharArray, char[]> {

	@Override
	public void initialize(NotBlankCharArray annotation) {}

	@Override
	public boolean isValid(char[] value, ConstraintValidatorContext context) {
		if (value == null) return true;
		return !new String(value).trim().isEmpty();
	}

}
