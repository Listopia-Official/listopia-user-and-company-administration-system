package florian_haas.lucas.model.validation;

import java.time.LocalDate;
import java.util.*;

import javax.validation.*;

import florian_haas.lucas.model.Taxdata;

public class ValidTaxdataListValidator implements ConstraintValidator<ValidTaxdataList, List<Taxdata>> {

	@Override
	public void initialize(ValidTaxdataList arg0) {}

	@Override
	public boolean isValid(List<Taxdata> arg0, ConstraintValidatorContext arg1) {
		if (arg0 == null) return true;
		Set<LocalDate> dates = new HashSet<>();
		for (Taxdata taxdata : arg0) {
			if (!dates.add(taxdata.getDate())) return false;
		}
		return true;
	}

}
