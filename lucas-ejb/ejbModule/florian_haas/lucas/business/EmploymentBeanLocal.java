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

	public Boolean setEmployeePosition(@ValidEntityId(entityClass = User.class) Long employmentId, @NotNull EnumEmployeePosition position);

	public Boolean setSalaryClass(@ValidEntityId(entityClass = Employment.class) Long employmentId, @NotNull EnumSalaryClass salaryClass);

	public Boolean addWorkShift(@ValidEntityId(entityClass = Employment.class) Long employmentId, @NotEmpty EnumWorkShift workShift);

	public Boolean removeWorkShift(@ValidEntityId(entityClass = Employment.class) Long employmentId, @NotEmpty EnumWorkShift workShift);

	public Boolean addAttendancedata(@ValidEntityId(entityClass = Employment.class) Long employmentId, @NotNull LocalDate date);

	public Boolean setAttendancedataDate(@ValidEntityId(entityClass = SalaryAttendancedata.class) Long attendancedataId, @NotNull LocalDate date);

	public Boolean setAttendancedataWasPresent(@ValidEntityId(entityClass = SalaryAttendancedata.class) Long attendancedataId,
			@NotNull EnumWorkShift shift, @NotNull Boolean wasPresent);

	public Boolean removeAttendancedata(@ValidEntityId(entityClass = Employment.class) Long employmentId,
			@ValidEntityId(entityClass = SalaryAttendancedata.class) Long attendancedataKey);

	public void paySalary(@ValidEntityId(entityClass = Company.class) Long company, @NotNull LocalDate date, @NotNull EnumWorkShift shift);

}
