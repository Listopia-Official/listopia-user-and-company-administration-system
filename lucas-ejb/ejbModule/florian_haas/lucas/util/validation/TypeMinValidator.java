package florian_haas.lucas.util.validation;

import javax.validation.*;

public class TypeMinValidator implements ConstraintValidator<TypeMin, Number> {
	private long minValue;

	public void initialize(TypeMin annotation) {
		this.minValue = annotation.value();
	}

	public boolean isValid(Number value, ConstraintValidatorContext constraintValidatorContext) {
		if (value == null) return true;
		return value.longValue() >= minValue;
	}
}
