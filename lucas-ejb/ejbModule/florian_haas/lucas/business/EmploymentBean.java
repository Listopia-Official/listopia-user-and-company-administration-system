package florian_haas.lucas.business;

import static florian_haas.lucas.security.EnumPermission.*;

import java.time.LocalDate;
import java.util.*;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.validation.Validator;
import javax.validation.executable.*;

import org.apache.shiro.authz.annotation.Logical;

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;
import florian_haas.lucas.security.*;

@Stateless
@ValidateOnExecution(type = ExecutableType.IMPLICIT)
@Secured
public class EmploymentBean implements EmploymentBeanLocal {

	@Inject
	@JPADAO
	private JobDAO jobDao;

	@Inject
	@JPADAO
	private CompanyDAO companyDao;

	@Inject
	@JPADAO
	private UserDAO userDao;

	@Inject
	@JPADAO
	private EmploymentDAO employmentDao;

	@Inject
	@JPADAO
	private SalaryAttendancedataDAO salaryAttendancedataDAO;

	@EJB
	private AccountBeanLocal accountBean;

	@EJB
	private GlobalDataBeanLocal globalDataBean;

	@Resource
	private Validator validator;

	@Override
	@RequiresPermissions(value = {
			EMPLOYMENT_CREATE, COMPANY_CREATE }, logical = Logical.OR)
	public Long createEmployment(Long userId, Long jobId, Set<EnumWorkShift> workShifts) {
		User user = userDao.findById(userId);
		Job job = jobDao.findById(jobId);
		Employment employment = new Employment(user, job, workShifts);
		user.addEmployment(employment);
		job.addEmployment(employment);
		jobDao.flush();
		return employment.getId();
	}

	@Override
	@RequiresPermissions(EMPLOYMENT_REMOVE)
	public Boolean removeEmployment(Long employmentId) {
		Employment employment = employmentDao.findById(employmentId);
		return employment.getJob().removeEmployment(employment) && employment.getUser().removeEmployment(employment);
	}

	@Override
	@RequiresPermissions(EMPLOYMENT_ADD_ATTENDANCEDATA)
	public Boolean addAttendancedata(Long employmentId, LocalDate date) {
		Employment employment = employmentDao.findById(employmentId);
		return employment.addAttendancedata(new SalaryAttendancedata(employment, date));
	}

	@Override
	@RequiresPermissions(EMPLOYMENT_SET_ATTENDANCEDATA_DATE)
	public Boolean setAttendancedataDate(Long attendancedataId, LocalDate date) {
		SalaryAttendancedata attendancedata = salaryAttendancedataDAO.findById(attendancedataId);
		if (attendancedata.getDate().equals(date)) return Boolean.FALSE;
		attendancedata.setDate(date);
		return Boolean.TRUE;

	}

	@Override
	@RequiresPermissions(EMPLOYMENT_SET_ATTENDANCEDATA_WAS_PRESENT)
	public Boolean setAttendancedataWasPresent(Long attendancedataId, EnumWorkShift shift, Boolean wasPresent) {
		SalaryAttendancedata attendancedata = salaryAttendancedataDAO.findById(attendancedataId);
		if (attendancedata.getWorkShifts().containsKey(shift)) {
			if (attendancedata.getWorkShifts().get(shift) != wasPresent) {
				attendancedata.setWasPresent(shift, wasPresent);
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;

	}

	@Override
	@RequiresPermissions(EMPLOYMENT_REMOVE_ATTENDANCEDATA)
	public Boolean removeAttendancedata(Long employmentId, Long attendancedataKey) {
		Employment employment = employmentDao.findById(employmentId);
		return employment.removeAttendancedata(salaryAttendancedataDAO.findById(attendancedataKey));
	}

	@Override
	@RequiresPermissions(EMPLOYMENT_PAY_SALARY)
	public void paySalary(Long companyId, LocalDate date, EnumWorkShift shift) {
		Company company = companyDao.findById(companyId);
		company.getEmployees().forEach(employee -> {
			Job job = employee.getJob();
			if (!employee.getWorkShifts().isEmpty() && job.getSalaryClass() != null) {
				for (SalaryAttendancedata attendancedata : employee.getAttendancedata()) {
					if (attendancedata.getDate().equals(date) && attendancedata.getWorkShifts().containsKey(shift)
							&& attendancedata.getWorkShifts().get(shift) == Boolean.TRUE) {
						accountBean.transaction(company.getAccount().getId(), employee.getUser().getAccount().getId(),
								globalDataBean.getSalaries().get(employee.getJob().getSalaryClass()),
								"Salary from " + company.getName() + " at " + date.toString() + " for shift " + shift.name());
						break;
					}
				}
			}
		});
	}

	@Override
	@RequiresPermissions(EMPLOYMENT_FIND_BY_ID)
	public ReadOnlyEmployment findById(Long employmentId) {
		return employmentDao.findById(employmentId);
	}

	@Override
	@RequiresPermissions(EMPLOYMENT_FIND_ALL)
	public List<? extends ReadOnlyEmployment> findAll() {
		return employmentDao.findAll();
	}

	@Override
	@RequiresPermissions(EMPLOYMENT_FIND_DYNAMIC)
	public List<? extends ReadOnlyEmployment> findEmployments(Long employmentId, Long userId, Long jobId, Set<EnumWorkShift> workShifts,
			Boolean useEmploymentId, Boolean useUserId, Boolean useJobId, Boolean useWorkShifts, EnumQueryComparator employmentIdComparator,
			EnumQueryComparator userIdComparator, EnumQueryComparator jobIdComparator, EnumQueryComparator workShiftsComparator) {
		return employmentDao.findEmployments(employmentId, userId, jobId, workShifts, useEmploymentId, useUserId, useJobId, useWorkShifts,
				employmentIdComparator, userIdComparator, jobIdComparator, workShiftsComparator);
	}

	@Override
	@RequiresPermissions(EMPLOYMENT_ADD_WORK_SHIFT)
	public Boolean addWorkShift(Long employmentId, EnumWorkShift shift) {
		Employment employment = employmentDao.findById(employmentId);
		return employment.addWorkShift(shift);
	}

	@Override
	@RequiresPermissions(EMPLOYMENT_REMOVE_WORK_SHIFT)
	public Boolean removeWorkShift(Long employmentId, EnumWorkShift shift) {
		Employment employment = employmentDao.findById(employmentId);
		return employment.removeWorkShift(shift);
	}

	@Override
	@RequiresPermissions(EMPLOYMENT_FIND_BY_ID)
	public Set<EnumWorkShift> getWorkShifts(Long employmentId) {
		return employmentDao.findById(employmentId).getWorkShifts();
	}

	@Override
	@RequiresPermissions(EMPLOYMENT_DISTRIBUTE_JOBS)
	public List<Integer> distributeJobs(EnumSet<EnumUserType> permittedUserTypes, EnumSet<EnumEmployeePosition> validJobs) {
		Integer distributedJobs = 0;
		List<User> relevantUsers = userDao.getAllUsersWithNoEmployments(permittedUserTypes);
		Collections.shuffle(relevantUsers);
		LinkedList<User> freeUsersPool = new LinkedList<>();
		for (User user : relevantUsers) {
			if (!createEmploymentIfPossible(user, user.getFirstJobRequest(), validJobs)) {
				if (!createEmploymentIfPossible(user, user.getSecondJobRequest(), validJobs)) {
					if (!createEmploymentIfPossible(user, user.getThirdJobRequest(), validJobs)) {
						freeUsersPool.add(user);
					} else {
						distributedJobs++;
					}
				} else {
					distributedJobs++;
				}
			} else {
				distributedJobs++;
			}
		}
		relevantUsers.clear();
		List<Job> freeJobs = jobDao.getEmployeeJobsWhereEmploymentsAreRequired(validJobs);
		if (!freeJobs.isEmpty()) {
			Collections.shuffle(freeUsersPool);
			Iterator<Job> it = freeJobs.iterator();
			while (it.hasNext()) {
				Job job = it.next();
				for (int i = job.getEmployments().size(); i < job.getRequiredEmploymentsCount() & !freeUsersPool.isEmpty(); i++) {
					User user = freeUsersPool.removeFirst();
					if (user != null) {
						createEmploymentIfPossible(user, job, validJobs);
						distributedJobs++;
					}
				}
				if (job.getEmployments().size() >= job.getRequiredEmploymentsCount()) it.remove();
			}
		}
		Integer remainingJobs = 0;
		Integer remainingUsers = freeUsersPool.size();
		freeUsersPool.clear();
		for (Job job : freeJobs) {
			if (job.areEmployeesRequiredForJob()) {
				remainingJobs += (job.getRequiredEmploymentsCount() - job.getEmployments().size());
			}
		}
		freeJobs.clear();
		return Collections.unmodifiableList(Arrays.asList(distributedJobs, remainingJobs, remainingUsers));
	}

	private Boolean createEmploymentIfPossible(User user, Job job, EnumSet<EnumEmployeePosition> validJobs) {
		Boolean ret = Boolean.FALSE;
		if (job != null && job.areEmployeesRequiredForJob() && validJobs.contains(job.getEmployeePosition())) {
			createEmployment(user.getId(), job.getId(), null);
			ret = Boolean.TRUE;
		}
		return ret;
	}
}
