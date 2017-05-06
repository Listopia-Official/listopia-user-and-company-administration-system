package florian_haas.lucas.persistence;

import java.util.List;

import florian_haas.lucas.model.Attendancedata;
import florian_haas.lucas.persistence.EnumQueryComparator;

public interface AttendancedataDAO extends ReadOnlyDAO<Attendancedata> {
	public List<Attendancedata> findAttendancedata(Long id, Boolean isUserInState, Long timePresentDay, Long validTimeMissing, Boolean useId,
			Boolean useIsUserInState, Boolean useTimePresentDay, Boolean useVaidTimeMissing, EnumQueryComparator idComparator,
			EnumQueryComparator timePresentDayComparator, EnumQueryComparator validTimeMissingComparator);
}
