package florian_haas.lucas.validation;

import javax.validation.*;

import florian_haas.lucas.model.*;

public class ValidCompanyHierarchyValidator implements ConstraintValidator<ValidCompanyHierarchy, Company> {

	@Override
	public void initialize(ValidCompanyHierarchy annotation) {}

	@Override
	public boolean isValid(Company value, ConstraintValidatorContext context) {
		if (value == null) return true;
		if (value.getParentCompany() != null && value.getParentCompany().equals(value)) return false;
		for (ReadOnlyCompany child : value.getChildCompanies()) {
			if (child.equals(value)) return false;
		}
		return true;
	}

}
