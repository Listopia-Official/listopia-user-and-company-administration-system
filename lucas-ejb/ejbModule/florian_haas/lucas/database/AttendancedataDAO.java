package florian_haas.lucas.database;

import java.util.List;

import florian_haas.lucas.model.Attendancedata;

public interface AttendancedataDAO {
	public List<Attendancedata> findAttendancedata(Long id, Boolean isUserInState, Long timePresentDay, Long validTimeMissing, Boolean useId,
			Boolean useIsUserInState, Boolean useTimePresentDay, Boolean useVaidTimeMissing, EnumQueryComparator idComparator,
			EnumQueryComparator timePresentDayComparator, EnumQueryComparator validTimeMissingComparator);
}
