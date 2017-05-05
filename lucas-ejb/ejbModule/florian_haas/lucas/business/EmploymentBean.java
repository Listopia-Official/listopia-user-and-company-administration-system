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

import florian_haas.lucas.database.*;
import florian_haas.lucas.model.*;
import florian_haas.lucas.model.validation.ValidEntityId;
import florian_haas.lucas.security.*;

@Stateless
@ValidateOnExecution(type = ExecutableType.IMPLICIT)
@Secured
public class EmploymentBean implements EmploymentBeanLocal {

	@Inject
	@JPADAO
	private JobDAO jobDao;

	@EJB
	private CompanyBeanLocal companyBean;

	@EJB
	private UserBeanLocal userBean;

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
		User user = userBean.findById(userId);
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
		Company company = companyBean.findById(companyId);
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
	public Employment findById(@ValidEntityId(entityClass = Employment.class) Long employmentId) {
		return employmentDao.findById(employmentId);
	}

	@Override
	@RequiresPermissions(EMPLOYMENT_FIND_ALL)
	public List<Employment> findAll() {
		return employmentDao.findAll();
	}

	@Override
	@RequiresPermissions(EMPLOYMENT_FIND_DYNAMIC)
	public List<Employment> findEmployments(Long employmentId, Long userId, Long jobId, Set<EnumWorkShift> workShifts, Boolean useEmploymentId,
			Boolean useUserId, Boolean useJobId, Boolean useWorkShifts, EnumQueryComparator employmentIdComparator,
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
}
