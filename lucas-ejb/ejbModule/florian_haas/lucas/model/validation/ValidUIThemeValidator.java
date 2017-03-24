package florian_haas.lucas.model.validation;

import javax.enterprise.inject.spi.CDI;
import javax.validation.*;

import florian_haas.lucas.business.GlobalDataBeanLocal;

public class ValidUIThemeValidator implements ConstraintValidator<ValidUITheme, String> {

	private GlobalDataBeanLocal globalData = CDI.current().select(GlobalDataBeanLocal.class).get();

	@Override
	public void initialize(ValidUITheme constraintAnnotation) {}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) return true;
		return globalData.getUIThemes().contains(value);
	}

}
