package florian_haas.lucas.business;

import java.util.List;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

import florian_haas.lucas.database.*;
import florian_haas.lucas.database.validation.QueryComparator;
import florian_haas.lucas.model.*;
import florian_haas.lucas.model.validation.ValidEntityId;

@Local
public interface AttendanceBeanLocal {

	public Boolean scan(@ValidEntityId(entityClass = UserCard.class) Long userCardId);

	public List<Long> evaluateAll();

	public List<Attendancedata> findAll();

	public Attendancedata findById(@ValidEntityId(entityClass = Attendancedata.class) Long id);

	public Attendancedata findByUserId(@ValidEntityId(entityClass = User.class) Long userId);

	public Attendancedata findByUserCardId(@ValidEntityId(entityClass = UserCard.class) Long userCardId);

	public List<Attendancedata> findAttendancedata(@NotNull Long id, @NotNull Boolean isUserInState, @NotNull Long timePresentDay,
			@NotNull Long validTimeMissing, @NotNull Boolean useId, @NotNull Boolean useIsUserInState, @NotNull Boolean useTimePresentDay,
			@NotNull Boolean useVaidTimeMissing, @QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator idComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator timePresentDayComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator validTimeMissingComparator);

}
