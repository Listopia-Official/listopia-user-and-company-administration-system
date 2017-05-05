package florian_haas.lucas.business;

import java.time.LocalDate;
import java.util.*;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

import florian_haas.lucas.database.*;
import florian_haas.lucas.database.validation.QueryComparator;
import florian_haas.lucas.model.*;
import florian_haas.lucas.model.validation.ValidEntityId;
import florian_haas.lucas.util.validation.TypeNotNull;

@Local
public interface EmploymentBeanLocal {

	public Long createEmployment(@ValidEntityId(entityClass = User.class) Long userId, @ValidEntityId(entityClass = Job.class) Long jobId,
			Set<@TypeNotNull EnumWorkShift> workShifts);

	public Boolean removeEmployment(@ValidEntityId(entityClass = Employment.class) Long employmentId);

	public Boolean addAttendancedata(@ValidEntityId(entityClass = Employment.class) Long employmentId, @NotNull LocalDate date);

	public Boolean setAttendancedataDate(@ValidEntityId(entityClass = SalaryAttendancedata.class) Long attendancedataId, @NotNull LocalDate date);

	public Boolean setAttendancedataWasPresent(@ValidEntityId(entityClass = SalaryAttendancedata.class) Long attendancedataId,
			@NotNull EnumWorkShift shift, @NotNull Boolean wasPresent);

	public Boolean removeAttendancedata(@ValidEntityId(entityClass = Employment.class) Long employmentId,
			@ValidEntityId(entityClass = SalaryAttendancedata.class) Long attendancedataKey);

	public void paySalary(@ValidEntityId(entityClass = Company.class) Long company, @NotNull LocalDate date, @NotNull EnumWorkShift shift);

	public Employment findById(@ValidEntityId(entityClass = Employment.class) Long employmentId);

	public Boolean addWorkShift(@ValidEntityId(entityClass = Employment.class) Long employmentId, @NotNull EnumWorkShift shift);

	public Boolean removeWorkShift(@ValidEntityId(entityClass = Employment.class) Long employmentId, @NotNull EnumWorkShift shift);

	public List<Employment> findAll();

	public List<Employment> findEmployments(@NotNull Long employmentId, @NotNull Long userId, @NotNull Long jobId,
			Set<@TypeNotNull EnumWorkShift> shifts, @NotNull Boolean useEmploymentId, @NotNull Boolean useUserId, @NotNull Boolean useJobId,
			@NotNull Boolean useWorkShifts,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator employmentIdComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator userIdComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator jobIdComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.ARRAY) EnumQueryComparator workShiftsComparator);

	public Set<EnumWorkShift> getWorkShifts(@ValidEntityId(entityClass = Employment.class) Long employmentId);

}
