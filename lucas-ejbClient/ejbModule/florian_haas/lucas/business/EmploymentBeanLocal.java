package florian_haas.lucas.business;

import java.time.LocalDate;
import java.util.*;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;
import florian_haas.lucas.validation.*;

@Local
public interface EmploymentBeanLocal {

	public Long createEmployment(@ValidEntityId(entityClass = ReadOnlyUser.class) Long userId,
			@ValidEntityId(entityClass = ReadOnlyJob.class) Long jobId, Set<@TypeNotNull EnumWorkShift> workShifts);

	public Boolean removeEmployment(@ValidEntityId(entityClass = ReadOnlyEmployment.class) Long employmentId);

	public Boolean addAttendancedata(@ValidEntityId(entityClass = ReadOnlyEmployment.class) Long employmentId, @NotNull LocalDate date);

	public Boolean setAttendancedataDate(@ValidEntityId(entityClass = ReadOnlySalaryAttendancedata.class) Long attendancedataId,
			@NotNull LocalDate date);

	public Boolean setAttendancedataWasPresent(@ValidEntityId(entityClass = ReadOnlySalaryAttendancedata.class) Long attendancedataId,
			@NotNull EnumWorkShift shift, @NotNull Boolean wasPresent);

	public Boolean removeAttendancedata(@ValidEntityId(entityClass = ReadOnlyEmployment.class) Long employmentId,
			@ValidEntityId(entityClass = ReadOnlySalaryAttendancedata.class) Long attendancedataKey);

	public void paySalary(@ValidEntityId(entityClass = ReadOnlyCompany.class) Long company, @NotNull LocalDate date, @NotNull EnumWorkShift shift);

	public ReadOnlyEmployment findById(@ValidEntityId(entityClass = ReadOnlyEmployment.class) Long employmentId);

	public Boolean addWorkShift(@ValidEntityId(entityClass = ReadOnlyEmployment.class) Long employmentId, @NotNull EnumWorkShift shift);

	public Boolean removeWorkShift(@ValidEntityId(entityClass = ReadOnlyEmployment.class) Long employmentId, @NotNull EnumWorkShift shift);

	public List<? extends ReadOnlyEmployment> findAll();

	public List<? extends ReadOnlyEmployment> findEmployments(@NotNull Long employmentId, @NotNull Long userId, @NotNull Long jobId,
			Set<@TypeNotNull EnumWorkShift> shifts, @NotNull Boolean useEmploymentId, @NotNull Boolean useUserId, @NotNull Boolean useJobId,
			@NotNull Boolean useWorkShifts,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator employmentIdComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator userIdComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator jobIdComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.ARRAY) EnumQueryComparator workShiftsComparator);

	public Set<EnumWorkShift> getWorkShifts(@ValidEntityId(entityClass = ReadOnlyEmployment.class) Long employmentId);

}
