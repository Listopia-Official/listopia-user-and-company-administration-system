package florian_haas.lucas.business;

import java.util.*;

import javax.ejb.Local;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.NotBlank;

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;
import florian_haas.lucas.validation.*;

@Local
public interface JobBeanLocal {

	public Long createJob(@NotBlank String name, @NotBlankString @Size(max = 255) String description,
			@ValidEntityId(entityClass = ReadOnlyCompany.class) Long companyId, @NotNull EnumEmployeePosition position,
			@NotNull @Min(0) Integer requiredEmploymentsCount, EnumSalaryClass salaryClass);

	public ReadOnlyJob findById(@ValidEntityId(entityClass = ReadOnlyJob.class) Long jobId);

	public List<? extends ReadOnlyJob> findAll();

	public List<? extends ReadOnlyJob> findJobs(@NotNull Long jobId, @NotNull String name, String description, Long companyId,
			EnumSalaryClass salaryClass, @NotNull Integer requiredEmployeesCount, @NotNull EnumEmployeePosition position, Long employmentId,
			@NotNull Integer employmentsCount, @NotNull Boolean areEmploymentsRequired, @NotNull Boolean useJobId, @NotNull Boolean useName,
			@NotNull Boolean useDescription, @NotNull Boolean useCompanyId, @NotNull Boolean useSalaryClass,
			@NotNull Boolean useRequiredEmployeesCount, @NotNull Boolean useEmployeePosition, @NotNull Boolean useEmploymentId,
			@NotNull Boolean useEmploymentsCount, @NotNull Boolean useAreEmploymentsRequired,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator jobIdComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.TEXT) EnumQueryComparator nameComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.TEXT) EnumQueryComparator descriptionComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.LOGIC) EnumQueryComparator companyIdComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.LOGIC) EnumQueryComparator salaryClassComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator requiredEmployeesCountComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.LOGIC) EnumQueryComparator positionComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.LOGIC) EnumQueryComparator employmentIdComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator employmentsCountComparator);

	public Boolean setName(@ValidEntityId(entityClass = ReadOnlyJob.class) Long jobId, @NotBlank String name);

	public Boolean setDescription(@ValidEntityId(entityClass = ReadOnlyJob.class) Long jobId, @NotBlankString @Size(max = 255) String description);

	public Boolean setSalaryClass(@ValidEntityId(entityClass = ReadOnlyJob.class) Long jobId, EnumSalaryClass salaryClass);

	public Boolean setRequiredEmploymentsCount(@ValidEntityId(entityClass = ReadOnlyJob.class) Long jobId,
			@NotNull @Min(0) Integer requiredEmploymentsCount);

	public Boolean setEmployeePosition(@ValidEntityId(entityClass = ReadOnlyJob.class) Long jobId, @NotNull EnumEmployeePosition employeePosition);

	public Boolean deleteJob(@ValidEntityId(entityClass = ReadOnlyJob.class) Long jobId);

	public List<? extends ReadOnlyJob> getJobsByData(@NotNull String data, @NotNull @Min(1) Integer resultsCount);

	public Integer computeMissingEmployments(@NotNull Set<@TypeNotNull EnumEmployeePosition> validJobs,
			@NotNull Set<@TypeNotNull EnumCompanyType> validCompanyTypes);

	public List<? extends ReadOnlyEmployment> getEmployments(@ValidEntityId(entityClass = ReadOnlyJob.class) Long jobId);

}
