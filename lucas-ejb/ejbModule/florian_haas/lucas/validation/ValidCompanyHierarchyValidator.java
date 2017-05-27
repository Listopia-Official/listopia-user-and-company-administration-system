package florian_haas.lucas.validation;

import javax.validation.*;

import florian_haas.lucas.model.Company;

public class ValidCompanyHierarchyValidator implements ConstraintValidator<ValidCompanyHierarchy, Company> {

	@Override
	public void initialize(ValidCompanyHierarchy annotation) {}

	@Override
	public boolean isValid(Company value, ConstraintValidatorContext context) {
		return (value.getParentCompany() != null ? !value.getParentCompany().equals(value) : true) && isValid(value, (Company) null);
	}

	private boolean isValid(Company start, Company root) {
		if (start == null) return true;
		if (start.equals(root)) return false;
		for (Company child : start.getChildCompanies()) {
			if (!isValid(child, root == null ? start : root)) return false;
		}
		return true;
	}

}
