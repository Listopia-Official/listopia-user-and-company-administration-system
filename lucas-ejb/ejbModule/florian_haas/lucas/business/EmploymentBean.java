package florian_haas.lucas.business;

import java.time.LocalDate;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.validation.Validator;
import javax.validation.executable.*;

import florian_haas.lucas.database.*;
import florian_haas.lucas.model.*;
import florian_haas.lucas.model.validation.ValidEntityId;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@ValidateOnExecution(type = ExecutableType.IMPLICIT)
public class EmploymentBean implements EmploymentBeanLocal {

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
	public Long addDefaultEmployment(@ValidEntityId(entityClass = User.class) Long userId, @ValidEntityId(entityClass = Company.class) Long companyId,
			EnumEmployeePosition position) {
		User user = userDao.findById(userId);
		Company company = companyDao.findById(companyId);
		Employment employment = new Employment(user, company, position);
		user.addEmployment(employment);
		company.addEmployee(employment);
		return employment.getId();
	}

	@Override
	public Long addAdvancedEmployment(@ValidEntityId(entityClass = User.class) Long userId,
			@ValidEntityId(entityClass = Company.class) Long companyId, EnumEmployeePosition position, EnumSalaryClass salaryClass,
			EnumWorkShift... workShifts) {
		User user = userDao.findById(userId);
		Company company = companyDao.findById(companyId);
		Employment employment = new Employment(user, company, position, salaryClass, workShifts);
		user.addEmployment(employment);
		company.addEmployee(employment);
		return employment.getId();
	}

	@Override
	public void removeEmployment(@ValidEntityId(entityClass = Employment.class) Long employmentId) {
		Employment employment = employmentDao.findById(employmentId);
		Company company = employment.getCompany();
		User user = employment.getUser();
		company.removeEmployee(employment);
		user.removeEmployment(employment);
	}

	@Override
	public Boolean setEmployeePosition(@ValidEntityId(entityClass = User.class) Long employmentId, EnumEmployeePosition position) {
		Employment employment = employmentDao.findById(employmentId);
		if (employment.getPosition() == position) return Boolean.FALSE;
		employment.setPosition(position);
		return Boolean.TRUE;
	}

	@Override
	public Boolean setSalaryClass(@ValidEntityId(entityClass = Employment.class) Long employmentId, EnumSalaryClass salaryClass) {
		Employment employment = employmentDao.findById(employmentId);
		if (employment.getSalaryData() != null) {
			if (employment.getSalaryData().getSalaryClass() == salaryClass) return Boolean.FALSE;
			employment.getSalaryData().setSalaryClass(salaryClass);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@Override
	public Boolean addWorkShift(@ValidEntityId(entityClass = Employment.class) Long employmentId, EnumWorkShift workShift) {
		Employment employment = employmentDao.findById(employmentId);
		if (employment.getSalaryData() != null) { return employment.getSalaryData().addWorkShift(workShift); }
		return Boolean.FALSE;
	}

	@Override
	public Boolean removeWorkShift(@ValidEntityId(entityClass = Employment.class) Long employmentId, EnumWorkShift workShift) {
		Employment employment = employmentDao.findById(employmentId);
		if (employment.getSalaryData() != null) { return employment.getSalaryData().removeWorkShift(workShift); }
		return Boolean.FALSE;
	}

	@Override
	public Boolean addAttendancedata(@ValidEntityId(entityClass = Employment.class) Long employmentId, LocalDate date) {
		Employment employment = employmentDao.findById(employmentId);
		if (employment.getSalaryData() != null) {
			SalaryData salaryData = employment.getSalaryData();
			SalaryAttendancedata attendancedata = new SalaryAttendancedata(salaryData, date);
			return salaryData.addAttendancedata(attendancedata);
		}
		return Boolean.FALSE;
	}

	@Override
	public Boolean setAttendancedataDate(@ValidEntityId(entityClass = SalaryAttendancedata.class) Long attendancedataId, LocalDate date) {
		SalaryAttendancedata attendancedata = salaryAttendancedataDAO.findById(attendancedataId);
		if (attendancedata.getDate().equals(date)) return Boolean.FALSE;
		attendancedata.setDate(date);
		return Boolean.TRUE;

	}

	@Override
	public Boolean setAttendancedataWasPresent(@ValidEntityId(entityClass = SalaryAttendancedata.class) Long attendancedataId, EnumWorkShift shift,
			Boolean wasPresent) {
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
	public Boolean removeAttendancedata(@ValidEntityId(entityClass = Employment.class) Long employmentId,
			@ValidEntityId(entityClass = SalaryAttendancedata.class) Long attendancedataKey) {
		Employment employment = employmentDao.findById(employmentId);
		SalaryAttendancedata attendancedata = salaryAttendancedataDAO.findById(attendancedataKey);
		if (employment.getSalaryData() != null) return employment.getSalaryData().removeAttendancedata(attendancedata);
		return Boolean.FALSE;
	}

	@Override
	public void paySalary(@ValidEntityId(entityClass = Company.class) Long companyId, LocalDate date, EnumWorkShift shift) {
		Company company = companyDao.findById(companyId);
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
