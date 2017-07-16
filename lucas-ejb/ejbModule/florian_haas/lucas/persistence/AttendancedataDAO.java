package florian_haas.lucas.persistence;

import java.util.List;

import florian_haas.lucas.model.Attendancedata;

public interface AttendancedataDAO extends ReadOnlyDAO<Attendancedata> {
	public List<Attendancedata> findAttendancedata(Long id, Long userId, Boolean isUserInState, Long timePresentDay, Boolean useId, Boolean useUserId,
			Boolean useIsUserInState, Boolean useTimePresentDay, EnumQueryComparator idComparator, EnumQueryComparator userIdComparator,
			EnumQueryComparator timePresentDayComparator);
}
