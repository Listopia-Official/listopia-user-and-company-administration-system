package florian_haas.lucas.business;

import static florian_haas.lucas.security.EnumPermission.*;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.executable.*;

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;
import florian_haas.lucas.security.*;

@Stateless
@ValidateOnExecution(type = ExecutableType.IMPLICIT)
@Secured
public class JobBean implements JobBeanLocal {

	@Inject
	@JPADAO
	private JobDAO jobDao;

	@Inject
	@JPADAO
	private CompanyDAO companyDao;

	@Override
	@RequiresPermissions(JOB_CREATE)
	public Long createJob(String name, String description, Long companyId, EnumEmployeePosition position, Integer requiredEmploymentsCount,
			EnumSalaryClass salaryClass) {
		return addJobHelper(new Job(name, description, companyDao.findById(companyId), position, requiredEmploymentsCount, salaryClass));
	}

	private Long addJobHelper(Job job) {
		checkIsNameUnique(job.getCompany().getId(), job.getName());
		job.getCompany().addJob(job);
		companyDao.flush();
		return job.getId();
	}

	@Override
	@RequiresPermissions(JOB_FIND_BY_ID)
	public Job findById(Long jobId) {
		return jobDao.findById(jobId);
	}

	@Override
	@RequiresPermissions(JOB_FIND_ALL)
	public List<? extends ReadOnlyJob> findAll() {
		return jobDao.findAll();
	}

	@Override
	@RequiresPermissions(JOB_FIND_DYNAMIC)
	public List<? extends ReadOnlyJob> findJobs(Long jobId, String name, String description, Long companyId, EnumSalaryClass salaryClass,
			Integer requiredEmployeesCount, EnumEmployeePosition position, Long employmentId, Boolean useJobId, Boolean useName,
			Boolean useDescription, Boolean useCompanyId, Boolean useSalaryClass, Boolean useRequiredEmployeesCount, Boolean useEmployeePosition,
			Boolean useEmploymentId, EnumQueryComparator jobIdComparator, EnumQueryComparator nameComparator,
			EnumQueryComparator descriptionComparator, EnumQueryComparator companyIdComparator, EnumQueryComparator salaryClassComparator,
			EnumQueryComparator requiredEmployeesCountComparator, EnumQueryComparator positionComparator,
			EnumQueryComparator employmentIdComparator) {
		return jobDao.findJobs(jobId, name, description, companyId, salaryClass, requiredEmployeesCount, position, employmentId, useJobId, useName,
				useDescription, useCompanyId, useSalaryClass, useRequiredEmployeesCount, useEmployeePosition, useEmploymentId, jobIdComparator,
				nameComparator, descriptionComparator, companyIdComparator, salaryClassComparator, requiredEmployeesCountComparator,
				positionComparator, employmentIdComparator);
	}

	@Override
	@RequiresPermissions(JOB_SET_NAME)
	public Boolean setName(Long jobId, String name) {
		Job job = jobDao.findById(jobId);
		if (job.getName().equals(name)) return Boolean.FALSE;
		checkIsNameUnique(job.getCompany().getId(), name);
		job.setName(name);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(JOB_SET_DESCRIPTION)
	public Boolean setDescription(Long jobId, String description) {
		Job job = jobDao.findById(jobId);
		if ((job.getDescription() == null && description == null) || (job.getDescription() != null && job.getDescription().equals(description)))
			return Boolean.FALSE;
		job.setDescription(description);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(JOB_SET_SALARY_CLASS)
	public Boolean setSalaryClass(Long jobId, EnumSalaryClass salaryClass) {
		Job job = jobDao.findById(jobId);
		if ((job.getSalaryClass() == null && salaryClass == null) || (job.getSalaryClass() != null && job.getSalaryClass() == salaryClass))
			return Boolean.FALSE;
		job.setSalaryClass(salaryClass);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(JOB_SET_REQUIRED_EMPLOYMENT_COUNT)
	public Boolean setRequiredEmploymentsCount(Long jobId, Integer requiredEmploymentsCount) {
		Job job = jobDao.findById(jobId);
		if (job.getRequiredEmploymentsCount().equals(requiredEmploymentsCount)) return Boolean.FALSE;
		job.setRequiredEmploymentsCount(requiredEmploymentsCount);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(JOB_SET_EMPLOYEE_POSITION)
	public Boolean setEmployeePosition(Long jobId, EnumEmployeePosition employeePosition) {
		Job job = jobDao.findById(jobId);
		if (job.getEmployeePosition().equals(employeePosition)) return Boolean.FALSE;
		job.setEmployeePosition(employeePosition);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(JOB_DELETE)
	public Boolean deleteJob(Long jobId) {
		if (jobDao.isReferencedInEmployments(jobId)) return Boolean.FALSE;
		jobDao.delete(jobDao.findById(jobId));
		return Boolean.TRUE;
	}

	private void checkIsNameUnique(Long companyId, String name) {
		if (!jobDao.isNameUniqueInJobsOfCompany(companyId, name)) throw new LucasException("The name is used by another job of the same company");
	}
}
