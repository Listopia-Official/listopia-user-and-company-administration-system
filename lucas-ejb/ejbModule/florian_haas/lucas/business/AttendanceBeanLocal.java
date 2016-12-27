package florian_haas.lucas.business;

import java.util.List;

import javax.ejb.Local;

import florian_haas.lucas.database.EnumQueryComparator;
import florian_haas.lucas.model.Attendancedata;

@Local
public interface AttendanceBeanLocal {

	public boolean scan(Long userCardId);

	public void evaluateAll();

	public List<Attendancedata> findAll();

	public Attendancedata findById(Long id);

	public Attendancedata findByUserId(Long userId);

	public Attendancedata findByUserCardId(Long userCardId);

	public List<Attendancedata> findAttendancedata(Long id, Long userId, Long userCardId, Boolean isUserInState, Long timePresentDay,
			Long validTimeMissing, Boolean useId, Boolean useUserId, Boolean useUserCardId, Boolean useIsUserInState,
			Boolean useTimePresentDay, Boolean useVaidTimeMissing, EnumQueryComparator idComparator,
			EnumQueryComparator userIdComparator, EnumQueryComparator userCardIdComparator,
			EnumQueryComparator timePresentDayComparator, EnumQueryComparator validTimeMissingComparator);

}
