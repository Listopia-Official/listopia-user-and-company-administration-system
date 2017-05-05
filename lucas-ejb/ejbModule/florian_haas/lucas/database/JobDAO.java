package florian_haas.lucas.database;

import java.util.List;

import florian_haas.lucas.model.*;

public interface JobDAO extends DAO<Job> {

	public List<Job> findJobs(Long jobId, String name, String description, Long companyId, EnumSalaryClass salaryClass, Integer optimalSchoolGrade,
			Integer requiredEmployeesCount, EnumEmployeePosition position, Long employmentId, Boolean useJobId, Boolean useName,
			Boolean useDescription, Boolean useCompanyId, Boolean useSalaryClass, Boolean useOptimalSchoolGrade, Boolean useRequiredEmployeesCount,
			Boolean useEmployeePosition, Boolean useEmploymentId, EnumQueryComparator jobIdComparator, EnumQueryComparator nameComparator,
			EnumQueryComparator descriptionComparator, EnumQueryComparator companyIdComparator, EnumQueryComparator salaryClassComparator,
			EnumQueryComparator optimalSchoolGradeComparator, EnumQueryComparator requiredEmployeesCountComparator,
			EnumQueryComparator positionComparator, EnumQueryComparator employmentIdComparator);

	public Boolean isNameUniqueInJobsOfCompany(Long companyId, String name);

	public Boolean isReferencedInEmployments(Long jobId);

}
