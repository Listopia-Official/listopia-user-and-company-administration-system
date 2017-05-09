package florian_haas.lucas.persistence;

import java.util.List;

import florian_haas.lucas.model.*;

public interface CompanyDAO extends DAO<Company> {

	public List<Company> findCompanies(Long companyId, String name, String description, Long roomSectionId, EnumCompanyType companyType,
			Long parentCompanyId, Long jobId, Boolean areEmployeesRequired, Integer jobCount, Boolean useId, Boolean useName, Boolean useDescription,
			Boolean useRoomSectionId, Boolean useCompanyType, Boolean useParentCompanyId, Boolean useJobId, Boolean useAreEmployeesRequired,
			Boolean useJobCount, EnumQueryComparator idComparator, EnumQueryComparator nameComparator, EnumQueryComparator descriptionComparator,
			EnumQueryComparator roomSectionComparator, EnumQueryComparator companyTypeComparator, EnumQueryComparator parentCompanyIdComparator,
			EnumQueryComparator jobIdComparator, EnumQueryComparator jobCountComparator);

	public Boolean isNameUnique(String name);

	public Boolean isRoomSectionUnique(Long roomSectionId);

}
