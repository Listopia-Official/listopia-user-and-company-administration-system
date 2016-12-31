package florian_haas.lucas.util.validation;

import java.util.*;

import javax.validation.*;

public class NotCollCollectionValidator implements ConstraintValidator<NotNullCollection, Collection<?>> {

	@Override
	public void initialize(NotNullCollection constraintAnnotation) {}

	@Override
	public boolean isValid(Collection<?> value, ConstraintValidatorContext context) {
		if (value == null) return true;
		Iterator<?> it = value.iterator();
		while (it.hasNext()) {
			if (it.next() == null) { return false; }
		}
		return true;
	}

}
