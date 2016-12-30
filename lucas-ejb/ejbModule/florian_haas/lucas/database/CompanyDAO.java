package florian_haas.lucas.database;

import java.util.List;

import florian_haas.lucas.model.*;

public interface CompanyDAO extends DAO<Company> {

	public List<Company> findCompanies(Long companyId, String name, String description, String room, Integer section,
			EnumCompanyType companyType, Long parentCompanyId, Integer requiredEmployeesCount, Boolean areEmployeesRequired,
			Boolean useId, Boolean useName, Boolean useDescription, Boolean useRoom, Boolean useSection, Boolean useCompanyType,
			Boolean useParentCompanyId, Boolean useRequiredEmployeesCount, Boolean useAreEmployeesRequired,
			EnumQueryComparator idComparator, EnumQueryComparator nameComparator, EnumQueryComparator descriptionComparator,
			EnumQueryComparator roomComparator, EnumQueryComparator sectionComparator, EnumQueryComparator companyTypeComparator,
			EnumQueryComparator parentCompanyIdComparator, EnumQueryComparator requiredEmployeesCountComparator);

}
