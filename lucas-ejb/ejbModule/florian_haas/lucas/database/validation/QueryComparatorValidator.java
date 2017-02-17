package florian_haas.lucas.database.validation;

import java.util.Arrays;

import javax.validation.*;

import florian_haas.lucas.database.*;

public class QueryComparatorValidator implements ConstraintValidator<QueryComparator, EnumQueryComparator> {

	private EnumQueryComparatorCategory category;

	@Override
	public void initialize(QueryComparator annotation) {
		this.category = annotation.category();
	}

	@Override
	public boolean isValid(EnumQueryComparator value, ConstraintValidatorContext context) {
		return value == null ? true : Arrays.asList(category.getComparators()).contains(value);
	}

}
