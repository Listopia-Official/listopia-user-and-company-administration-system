package florian_haas.lucas.business;

import java.time.LocalDate;
import java.util.Map;

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

	public SalaryData addAttendanceEntry(Long salaryDataId, Map<EnumWorkShift, Boolean> shifts);

	public SalaryData setAttendanceEntry(Long salaryDataId, LocalDate date, Map<EnumWorkShift, Boolean> shifts);

	public SalaryData removeAttendanceEntry(Long salaryDataId, LocalDate date);

}
