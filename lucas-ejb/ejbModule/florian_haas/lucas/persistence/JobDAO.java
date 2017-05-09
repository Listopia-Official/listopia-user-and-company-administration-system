package florian_haas.lucas.persistence;

import java.util.*;

import florian_haas.lucas.model.*;

public interface JobDAO extends DAO<Job> {

	public List<Job> findJobs(Long jobId, String name, String description, Long companyId, EnumSalaryClass salaryClass,
			Integer requiredEmployeesCount, EnumEmployeePosition position, Long employmentId, Integer employmentsCount, Boolean useJobId,
			Boolean useName, Boolean useDescription, Boolean useCompanyId, Boolean useSalaryClass, Boolean useRequiredEmployeesCount,
			Boolean useEmployeePosition, Boolean useEmploymentId, Boolean useEmploymentsCount, EnumQueryComparator jobIdComparator,
			EnumQueryComparator nameComparator, EnumQueryComparator descriptionComparator, EnumQueryComparator companyIdComparator,
			EnumQueryComparator salaryClassComparator, EnumQueryComparator requiredEmployeesCountComparator, EnumQueryComparator positionComparator,
			EnumQueryComparator employmentIdComparator, EnumQueryComparator employmentsCountComparator);

	public Boolean isNameUniqueInJobsOfCompany(Long companyId, String name);

	public Boolean isReferencedInEmployments(Long jobId);

	public List<Job> getEmployeeJobsWhereEmploymentsAreRequired(EnumSet<EnumEmployeePosition> validJobs);

}
