package florian_haas.lucas.business;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.ejb.Local;

import florian_haas.lucas.database.EnumQueryComparator;
import florian_haas.lucas.model.*;

@Local
public interface CompanyBeanLocal {

	public Company createCompany(String name, String description, String room, String section, EnumCompanyType companyType,
			List<Employment> managers, Integer requiredEmployeesCount);

	public List<Company> findAll();

	public Company findById(Long companyId);

	public List<Company> findCompanies(Long companyId, String name, String description, String room, Integer section,
			EnumCompanyType companyType, Long parentCompanyId, Integer requiredEmployeesCount, Boolean areEmployeesRequired,
			Boolean useId, Boolean useName, Boolean useDescription, Boolean useRoom, Boolean useSection, Boolean useCompanyType,
			Boolean useParentCompanyId, Boolean useRequiredEmployeesCount, Boolean useAreEmployeesRequired,
			EnumQueryComparator idComparator, EnumQueryComparator nameComparator, EnumQueryComparator descriptionComparator,
			EnumQueryComparator roomComparator, EnumQueryComparator sectionComparator, EnumQueryComparator companyTypeComparator,
			EnumQueryComparator parentCompanyIdComparator, EnumQueryComparator requiredEmployeesCountComparator);

	public Company setName(Long companyId, String name);

	public Company setDescription(Long companyId, String description);

	public Company setRoom(Long companyId, String room);

	public Company setSection(Long companyId, Integer section);

	public Company setCompanyType(Long companyId, EnumCompanyType companyType);

	public Company setParentCompany(Long companyId, Long parentCompanyId);

	public Company setRequiredEmployeesCount(Long companyId, Integer requiredEmployeesCount);

	public Company addTaxdata(Long companyId, LocalDate date, BigDecimal incomings, BigDecimal outgoings);

	public Taxdata setDate(Long taxdataId, LocalDate date);

	public Taxdata setIncomings(Long taxdataId, BigDecimal incomings);

	public Taxdata setOutgoings(Long taxdataId, BigDecimal outgoings);
}
