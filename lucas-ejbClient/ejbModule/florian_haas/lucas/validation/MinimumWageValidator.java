package florian_haas.lucas.validation;

import java.math.BigDecimal;

import javax.ejb.EJB;
import javax.validation.*;

import florian_haas.lucas.business.GlobalDataBeanLocal;
import florian_haas.lucas.util.Utils;

public class MinimumWageValidator implements ConstraintValidator<MinimumWage, BigDecimal> {

	@EJB
	private GlobalDataBeanLocal globalData;

	@Override
	public void initialize(MinimumWage arg0) {}

	@Override
	public boolean isValid(BigDecimal arg0, ConstraintValidatorContext arg1) {
		if (arg0 == null) return true;
		BigDecimal minimumWage = globalData.getMinimumWage();
		return Utils.isGreatherEqual(arg0, minimumWage);
	}

}
