package florian_haas.lucas.database;

import java.util.List;

import florian_haas.lucas.model.*;

public interface CompanyDAO extends DAO<Company> {

	public List<Company> findCompanies(Long companyId, String name, String description, Long roomSectionId, EnumCompanyType companyType,
			Long parentCompanyId, Integer requiredEmployeesCount, Boolean areEmployeesRequired, Boolean useId, Boolean useName,
			Boolean useDescription, Boolean useRoomSectionId, Boolean useCompanyType, Boolean useParentCompanyId, Boolean useRequiredEmployeesCount,
			Boolean useAreEmployeesRequired, EnumQueryComparator idComparator, EnumQueryComparator nameComparator,
			EnumQueryComparator descriptionComparator, EnumQueryComparator roomSectionComparator, EnumQueryComparator companyTypeComparator,
			EnumQueryComparator parentCompanyIdComparator, EnumQueryComparator requiredEmployeesCountComparator);

	public Boolean isNameUnique(String name);

	public Boolean isRoomSectionUnique(Long roomSectionId);

}
