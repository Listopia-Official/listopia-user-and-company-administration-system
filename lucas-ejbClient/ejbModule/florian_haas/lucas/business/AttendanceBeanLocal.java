package florian_haas.lucas.business;

import java.util.List;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;
import florian_haas.lucas.validation.*;

@Local
public interface AttendanceBeanLocal {

	public static final String USER_CARD_BLOCKED_EXCEPTION_MARKER = "userCardBlocked";
	public static final String USER_CARD_INVALID_DATE_EXCEPTION_MARKER = "userCardInvalidDate";

	public Boolean scan(@ValidEntityId(entityClass = ReadOnlyIdCard.class) Long idCard);

	public Boolean scanByAttendancedata(@ValidEntityId(entityClass = ReadOnlyAttendancedata.class) Long attendancedataId);

	public void reset(@ValidEntityId(entityClass = ReadOnlyAttendancedata.class) Long attendancedataId);

	public List<? extends ReadOnlyAttendancedata> findAll();

	public ReadOnlyAttendancedata findById(@ValidEntityId(entityClass = ReadOnlyAttendancedata.class) Long id);

	public List<? extends ReadOnlyAttendancedata> findAttendancedata(@NotNull Long id, @NotNull Long userId, @NotNull Boolean isUserInState,
			@NotNull Long timePresentDay, @NotNull Boolean useId, @NotNull Boolean useUserId, @NotNull Boolean useIsUserInState,
			@NotNull Boolean useTimePresentDay, @QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator idComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator userIdComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator timePresentDayComparator);

	public List<? extends ReadOnlyAttendanceActivityLog> getAttendanceActivityLogs(
			@ValidEntityId(entityClass = ReadOnlyAttendancedata.class) Long id);
}
