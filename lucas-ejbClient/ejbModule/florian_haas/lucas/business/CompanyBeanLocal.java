package florian_haas.lucas.business;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import javax.ejb.Local;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.NotBlank;

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;
import florian_haas.lucas.validation.*;

@Local
public interface CompanyBeanLocal {

	public static final String NAME_NOT_UNIQUE_EXCEPTION_MARKER = "notUniqueName";
	public static final String SECTION_NOT_UNIQUE_EXCEPTION_MARKER = "notUniqueLocation";

	public Long createCompany(@NotNull @NotBlankString String name, @Size(max = 255) @NotBlankString String description,
			@ValidEntityId(entityClass = ReadOnlyRoomSection.class, nullable = true) Long roomSectionId, @NotNull EnumCompanyType companyType,
			@ValidEntityId(entityClass = ReadOnlyCompany.class, nullable = true) Long parentCompanyId, @NotNull Boolean generateJobs,
			List<@ValidEntityId(entityClass = ReadOnlyUser.class) Long> managerUserIds, @NotNull @Min(value = 0) Integer requiredEmployeesCount,
			@NotNull Boolean isAdvisorRequired, @NotBlank String advisorJobName, @NotBlank String advisorJobDescription,
			@NotBlank String managerJobName, @NotBlank String managerJobDescription, @NotBlank String employeeJobName,
			@NotBlank String employeeJobDescription);

	public List<? extends ReadOnlyCompany> findAll();

	public ReadOnlyCompany findById(@ValidEntityId(entityClass = ReadOnlyCompany.class) Long companyId);

	public List<? extends ReadOnlyCompany> findCompanies(@NotNull Long companyId, @NotNull String name, String description, Long roomSectionId,
			@NotNull EnumCompanyType companyType, Long parentCompanyId, @NotNull Long jobId, @NotNull Boolean areEmployeesRequired,
			@NotNull Integer jobCount, @NotNull Boolean useId, @NotNull Boolean useName, @NotNull Boolean useDescription,
			@NotNull Boolean useRoomSectionId, @NotNull Boolean useCompanyType, @NotNull Boolean useParentCompanyId, @NotNull Boolean useJobId,
			@NotNull Boolean useAreEmployeesRequired, @NotNull Boolean useJobCount,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator idComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.TEXT) EnumQueryComparator nameComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.TEXT) EnumQueryComparator descriptionComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator roomSectionIdComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.LOGIC) EnumQueryComparator companyTypeComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator parentCompanyIdComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator jobIdComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator jobCountComparator);

	public Boolean setName(@ValidEntityId(entityClass = ReadOnlyCompany.class) Long companyId, @NotNull @NotBlankString String name);

	public Boolean setDescription(@ValidEntityId(entityClass = ReadOnlyCompany.class) Long companyId,
			@Size(max = 255) @NotBlankString String description);

	public Boolean setSection(@ValidEntityId(entityClass = ReadOnlyCompany.class) Long companyId,
			@ValidEntityId(entityClass = ReadOnlyRoomSection.class, nullable = true) Long roomSectionId);

	public Boolean setCompanyType(@ValidEntityId(entityClass = ReadOnlyCompany.class) Long companyId, @NotNull EnumCompanyType companyType);

	public Boolean setParentCompany(@ValidEntityId(entityClass = ReadOnlyCompany.class) Long companyId,
			@ValidEntityId(entityClass = ReadOnlyCompany.class, nullable = true) Long parentCompanyId);

	public Boolean removeParentCompany(@ValidEntityId(entityClass = ReadOnlyCompany.class) Long companyId);

	public Boolean addTaxdata(@ValidEntityId(entityClass = ReadOnlyCompany.class) Long companyId, @NotNull LocalDate date,
			@NotNull @DecimalMin(value = "0.0", inclusive = true) BigDecimal incomings,
			@NotNull @DecimalMin(value = "0.0", inclusive = true) BigDecimal outgoings);

	public Boolean removeTaxdata(@ValidEntityId(entityClass = ReadOnlyTaxdata.class) Long taxdataId);

	public Boolean setIncomings(@ValidEntityId(entityClass = ReadOnlyTaxdata.class) Long taxdataId,
			@NotNull @DecimalMin(value = "0.0", inclusive = true) BigDecimal incomings);

	public Boolean setOutgoings(@ValidEntityId(entityClass = ReadOnlyTaxdata.class) Long taxdataId,
			@NotNull @DecimalMin(value = "0.0", inclusive = true) BigDecimal outgoings);

	public Long addCompanyCard(@ValidEntityId(entityClass = ReadOnlyCompany.class) Long companyId);

	public Boolean removeCompanyCard(@ValidEntityId(entityClass = ReadOnlyCompanyCard.class) Long companyCardId);

	public Boolean blockCompanyCard(@ValidEntityId(entityClass = ReadOnlyCompanyCard.class) Long companyCardId);

	public Boolean unblockCompanyCard(@ValidEntityId(entityClass = ReadOnlyCompanyCard.class) Long companyCardId);

	public Boolean setValidDate(@ValidEntityId(entityClass = ReadOnlyCompanyCard.class) Long companyCardId, LocalDate validDate);

	public Set<? extends ReadOnlyCompanyCard> getCompanyCards(@ValidEntityId(entityClass = ReadOnlyCompany.class) Long companyId);

	public List<? extends ReadOnlyEmployment> getManagers(@ValidEntityId(entityClass = ReadOnlyCompany.class) Long companyId);

	public List<? extends ReadOnlyEmployment> getAdvisors(@ValidEntityId(entityClass = ReadOnlyCompany.class) Long companyId);

	public List<? extends ReadOnlyEmployment> getEmployees(@ValidEntityId(entityClass = ReadOnlyCompany.class) Long companyId);

	public ReadOnlyCompanyCard findCompanyCardById(@ValidEntityId(entityClass = ReadOnlyCompanyCard.class) Long companyCardId);
}
