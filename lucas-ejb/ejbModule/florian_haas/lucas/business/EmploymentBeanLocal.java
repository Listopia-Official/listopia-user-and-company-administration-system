package florian_haas.lucas.business;

import java.time.LocalDate;

import javax.ejb.Local;

import florian_haas.lucas.model.*;

@Local
public interface EmploymentBeanLocal {

	public Employment addDefaultEmployment(Long userId, Long companyId, EnumEmployeePosition position);

	public Employment addAdvancedEmployment(Long userId, Long companyId, EnumEmployeePosition position, EnumSalaryClass salaryClass,
			EnumWorkShift... workShifts);

	public void removeEmployment(Long employmentId);

	public Employment setEmployeePosition(Long employmentId, EnumEmployeePosition position);

	public SalaryData setSalaryClass(Long salaryDataId, EnumSalaryClass salaryClass);

	public SalaryData setWorkShifts(Long salaryDataId, EnumWorkShift... workShifts);

	public SalaryData addAttendancedata(Long salaryDataId, LocalDate date, EnumWorkShift workShift, Boolean wasPresent);

	public SalaryData setAttendancedataDate(Long attendancedataId, LocalDate date);

	public SalaryData setAttendancedataShift(Long attendancedataId, EnumWorkShift shift);

	public SalaryData setAttendancedataWasPresent(Long attendancedataId, Boolean wasPresent);

	public SalaryData removeAttendancedata(Long salaryDataId, Long attendancedataKey);

	public void paySalary(Company company, LocalDate date, EnumWorkShift shift);

}
