package florian_haas.lucas.validation;

import java.lang.reflect.Field;
import java.util.*;

import javax.validation.*;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue, Collection<?>> {

	private String fieldName;

	@Override
	public void initialize(UniqueValue annotation) {
		fieldName = annotation.fieldName();
	}

	@Override
	public boolean isValid(Collection<?> collection, ConstraintValidatorContext context) {
		if (collection == null) return true;
		Set<Object> objects = new HashSet<>();
		for (Object object : collection) {
			try {
				Field field = object.getClass().getDeclaredField(fieldName);
				if (field.isAccessible()) {
					field.setAccessible(true);
				}
				if (!objects.add(field.get(object))) return false;
			}
			catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

}
