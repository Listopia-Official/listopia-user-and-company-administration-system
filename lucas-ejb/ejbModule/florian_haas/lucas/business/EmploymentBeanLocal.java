package florian_haas.lucas.business;

import java.time.LocalDate;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import florian_haas.lucas.model.*;
import florian_haas.lucas.model.validation.ValidEntityId;

@Local
public interface EmploymentBeanLocal {

	public Employment addDefaultEmployment(@ValidEntityId(entityClass = User.class) Long userId,
			@ValidEntityId(entityClass = Company.class) Long companyId, @NotNull EnumEmployeePosition position);

	public Employment addAdvancedEmployment(@ValidEntityId(entityClass = User.class) Long userId,
			@ValidEntityId(entityClass = Company.class) Long companyId, @NotNull EnumEmployeePosition position, @NotNull EnumSalaryClass salaryClass,
			@NotEmpty EnumWorkShift... workShifts);

	public void removeEmployment(@ValidEntityId(entityClass = Employment.class) Long employmentId);

	public Employment setEmployeePosition(@ValidEntityId(entityClass = User.class) Long employmentId, @NotNull EnumEmployeePosition position);

	public SalaryData setSalaryClass(@ValidEntityId(entityClass = SalaryData.class) Long salaryDataId, @NotNull EnumSalaryClass salaryClass);

	public SalaryData setWorkShifts(@ValidEntityId(entityClass = SalaryData.class) Long salaryDataId, @NotEmpty EnumWorkShift... workShifts);

	public SalaryData addAttendancedata(@ValidEntityId(entityClass = SalaryData.class) Long salaryDataId, @NotNull LocalDate date,
			@NotNull EnumWorkShift workShift, @NotNull Boolean wasPresent);

	public SalaryData setAttendancedataDate(@ValidEntityId(entityClass = SalaryAttendancedata.class) Long attendancedataId, @NotNull LocalDate date);

	public SalaryData setAttendancedataShift(@ValidEntityId(entityClass = SalaryAttendancedata.class) Long attendancedataId,
			@NotNull EnumWorkShift shift);

	public SalaryData setAttendancedataWasPresent(@ValidEntityId(entityClass = SalaryAttendancedata.class) Long attendancedataId,
			@NotNull Boolean wasPresent);

	public SalaryData removeAttendancedata(@ValidEntityId(entityClass = SalaryData.class) Long salaryDataId,
			@ValidEntityId(entityClass = SalaryAttendancedata.class) Long attendancedataKey);

	public void paySalary(@ValidEntityId(entityClass = Company.class) Company company, @NotNull LocalDate date, @NotNull EnumWorkShift shift);

}
