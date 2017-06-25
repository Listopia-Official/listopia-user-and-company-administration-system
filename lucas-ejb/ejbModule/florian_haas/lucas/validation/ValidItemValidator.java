package florian_haas.lucas.validation;

import javax.validation.*;

import florian_haas.lucas.model.Item;
import florian_haas.lucas.util.Utils;

public class ValidItemValidator implements ConstraintValidator<ValidItem, Item> {

	@Override
	public void initialize(ValidItem item) {}

	@Override
	public boolean isValid(Item value, ConstraintValidatorContext context) {
		return value == null ? true : Utils.isLessEqual(value.getRealPricePerItem(), value.getFictionalPricePerItem());
	}

}
