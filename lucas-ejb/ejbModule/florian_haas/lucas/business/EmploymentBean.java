package florian_haas.lucas.business;

import static florian_haas.lucas.security.EnumPermission.*;

import java.time.LocalDate;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.validation.Validator;
import javax.validation.executable.*;

import florian_haas.lucas.database.*;
import florian_haas.lucas.model.*;
import florian_haas.lucas.security.*;

@Stateless
@ValidateOnExecution(type = ExecutableType.IMPLICIT)
@Secured
public class EmploymentBean implements EmploymentBeanLocal {

	@EJB
	private CompanyBeanLocal companyBean;

	@EJB
	private UserBeanLocal userBean;

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
	@RequiresPermissions(EMPLOYMENT_CREATE_DEFAULT)
	public Long addDefaultEmployment(Long userId, Long companyId, EnumEmployeePosition position) {
		User user = userBean.findById(userId);
		Company company = companyBean.findById(companyId);
		Employment employment = new Employment(user, company, position);
		user.addEmployment(employment);
		company.addEmployee(employment);
		return employment.getId();
	}

	@Override
	@RequiresPermissions(EMPLOYMENT_CREATE_ADVANCED)
	public Long addAdvancedEmployment(Long userId, Long companyId, EnumEmployeePosition position, EnumSalaryClass salaryClass,
			EnumWorkShift... workShifts) {
		User user = userBean.findById(userId);
		Company company = companyBean.findById(companyId);
		Employment employment = new Employment(user, company, position, salaryClass, workShifts);
		user.addEmployment(employment);
		company.addEmployee(employment);
		return employment.getId();
	}

	@Override
	@RequiresPermissions(EMPLOYMENT_REMOVE)
	public void removeEmployment(Long employmentId) {
		Employment employment = employmentDao.findById(employmentId);
		Company company = employment.getCompany();
		User user = employment.getUser();
		company.removeEmployee(employment);
		user.removeEmployment(employment);
	}

	@Override
	@RequiresPermissions(EMPLOYMENT_SET_EMPLOYEE_POSITION)
	public Boolean setEmployeePosition(Long employmentId, EnumEmployeePosition position) {
		Employment employment = employmentDao.findById(employmentId);
		if (employment.getPosition() == position) return Boolean.FALSE;
		employment.setPosition(position);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(EMPLOYMENT_SET_SALARY_CLASS)
	public Boolean setSalaryClass(Long employmentId, EnumSalaryClass salaryClass) {
		Employment employment = employmentDao.findById(employmentId);
		if (employment.getSalaryData() != null) {
			if (employment.getSalaryData().getSalaryClass() == salaryClass) return Boolean.FALSE;
			employment.getSalaryData().setSalaryClass(salaryClass);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@Override
	@RequiresPermissions(EMPLOYMENT_ADD_WORK_SHIFT)
	public Boolean addWorkShift(Long employmentId, EnumWorkShift workShift) {
		Employment employment = employmentDao.findById(employmentId);
		if (employment.getSalaryData() != null) { return employment.getSalaryData().addWorkShift(workShift); }
		return Boolean.FALSE;
	}

	@Override
	@RequiresPermissions(EMPLOYMENT_REMOVE_WORK_SHIFT)
	public Boolean removeWorkShift(Long employmentId, EnumWorkShift workShift) {
		Employment employment = employmentDao.findById(employmentId);
		if (employment.getSalaryData() != null) { return employment.getSalaryData().removeWorkShift(workShift); }
		return Boolean.FALSE;
	}

	@Override
	@RequiresPermissions(EMPLOYMENT_ADD_ATTENDANCEDATA)
	public Boolean addAttendancedata(Long employmentId, LocalDate date) {
		Employment employment = employmentDao.findById(employmentId);
		if (employment.getSalaryData() != null) {
			SalaryData salaryData = employment.getSalaryData();
			SalaryAttendancedata attendancedata = new SalaryAttendancedata(salaryData, date);
			return salaryData.addAttendancedata(attendancedata);
		}
		return Boolean.FALSE;
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
		SalaryAttendancedata attendancedata = salaryAttendancedataDAO.findById(attendancedataKey);
		if (employment.getSalaryData() != null) return employment.getSalaryData().removeAttendancedata(attendancedata);
		return Boolean.FALSE;
	}

	@Override
	@RequiresPermissions(EMPLOYMENT_PAY_SALARY)
	public void paySalary(Long companyId, LocalDate date, EnumWorkShift shift) {
		Company company = companyBean.findById(companyId);
		company.getEmployees().forEach(employee -> {
			if (employee.getSalaryData() != null) {
				SalaryData salaryData = employee.getSalaryData();
				for (SalaryAttendancedata attendancedata : salaryData.getAttendancedata()) {
					if (attendancedata.getDate().equals(date) && attendancedata.getWorkShifts().containsKey(shift)
							&& attendancedata.getWorkShifts().get(shift) == Boolean.TRUE) {
						accountBean.transaction(company.getAccount().getId(), employee.getUser().getAccount().getId(),
								globalDataBean.getSalaries().get(salaryData.getSalaryClass()),
								"Salary from " + company.getName() + " at " + date.toString() + " for shift " + shift.name());
						break;
					}
				}
			}
		});
	}
}
