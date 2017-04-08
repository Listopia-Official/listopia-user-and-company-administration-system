package florian_haas.lucas.model.validation;

import javax.enterprise.inject.spi.CDI;
import javax.validation.*;

import florian_haas.lucas.business.CompanyBeanLocal;
import florian_haas.lucas.model.Company;

public class UniqueCompanyRoomSectionCombinationValidator implements ConstraintValidator<UniqueCompanyRoomSectionCombination, Company> {

	private CompanyBeanLocal companyBean = CDI.current().select(CompanyBeanLocal.class).get();

	@Override
	public void initialize(UniqueCompanyRoomSectionCombination annotation) {}

	@Override
	public boolean isValid(Company value, ConstraintValidatorContext context) {
		return value == null ? true : companyBean.existsLocation(value.getRoom(), value.getSection());
	}

}
