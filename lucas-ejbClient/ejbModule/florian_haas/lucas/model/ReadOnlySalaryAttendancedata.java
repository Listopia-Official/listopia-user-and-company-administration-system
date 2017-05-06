package florian_haas.lucas.model;

import java.time.LocalDate;
import java.util.Map;

public interface ReadOnlySalaryAttendancedata extends ReadOnlyEntity {
	public ReadOnlyEmployment getEmployment();

	public LocalDate getDate();

	public Map<EnumWorkShift, Boolean> getWorkShifts();
}
