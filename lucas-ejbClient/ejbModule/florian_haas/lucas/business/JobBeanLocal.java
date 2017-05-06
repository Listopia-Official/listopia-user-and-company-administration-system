package florian_haas.lucas.business;

import java.util.List;

import javax.ejb.Local;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.NotBlank;

import florian_haas.lucas.database.*;
import florian_haas.lucas.model.*;
import florian_haas.lucas.validation.*;

@Local
public interface JobBeanLocal {

	public Long createJob(@NotBlank String name, @NotBlankString @Size(max = 255) String description,
			@ValidEntityId(entityClass = ReadOnlyCompany.class) Long companyId, @NotNull EnumEmployeePosition position,
			@ValidSchoolGrade Integer optimalSchoolGrade, @NotNull @Min(0) Integer requiredEmploymentsCount, EnumSalaryClass salaryClass);

	public ReadOnlyJob findById(@ValidEntityId(entityClass = ReadOnlyJob.class) Long jobId);

	public List<? extends ReadOnlyJob> findAll();

	public List<? extends ReadOnlyJob> findJobs(@NotNull Long jobId, @NotNull String name, String description, @NotNull Long companyId,
			EnumSalaryClass salaryClass, Integer optimalSchoolGrade, @NotNull Integer requiredEmployeesCount, @NotNull EnumEmployeePosition position,
			@NotNull Long employmentId, @NotNull Boolean useJobId, @NotNull Boolean useName, @NotNull Boolean useDescription,
			@NotNull Boolean useCompanyId, @NotNull Boolean useSalaryClass, @NotNull Boolean useOptimalSchoolGrade,
			@NotNull Boolean useRequiredEmployeesCount, @NotNull Boolean useEmployeePosition, @NotNull Boolean useEmploymentId,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator jobIdComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.TEXT) EnumQueryComparator nameComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.TEXT) EnumQueryComparator descriptionComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator companyIdComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.LOGIC) EnumQueryComparator salaryClassComparator,

			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator optimalSchoolGradeComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator requiredEmployeesCountComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.LOGIC) EnumQueryComparator positionComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator employmentIdComparator);

	public Boolean setName(@ValidEntityId(entityClass = ReadOnlyJob.class) Long jobId, @NotBlank String name);

	public Boolean setDescription(@ValidEntityId(entityClass = ReadOnlyJob.class) Long jobId, @NotBlankString @Size(max = 255) String description);

	public Boolean setSalaryClass(@ValidEntityId(entityClass = ReadOnlyJob.class) Long jobId, EnumSalaryClass salaryClass);

	public Boolean setOptimalSchoolGrade(@ValidEntityId(entityClass = ReadOnlyJob.class) Long jobId, @ValidSchoolGrade Integer optimalSchoolGrade);

	public Boolean setRequiredEmploymentsCount(@ValidEntityId(entityClass = ReadOnlyJob.class) Long jobId,
			@NotNull @Min(0) Integer requiredEmploymentsCount);

	public Boolean setEmployeePosition(@ValidEntityId(entityClass = ReadOnlyJob.class) Long jobId, @NotNull EnumEmployeePosition employeePosition);

	public Boolean deleteJob(@ValidEntityId(entityClass = ReadOnlyJob.class) Long jobId);

}
