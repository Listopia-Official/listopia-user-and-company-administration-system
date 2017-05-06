package florian_haas.lucas.business;

import java.util.List;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;
import florian_haas.lucas.validation.*;

@Local
public interface AttendanceBeanLocal {

	public Boolean scan(@ValidEntityId(entityClass = ReadOnlyUserCard.class) Long ReadOnlyUserCardId);

	public List<Long> evaluateAll();

	public List<? extends ReadOnlyAttendancedata> findAll();

	public ReadOnlyAttendancedata findById(@ValidEntityId(entityClass = ReadOnlyAttendancedata.class) Long id);

	public ReadOnlyAttendancedata findByUserId(@ValidEntityId(entityClass = ReadOnlyUser.class) Long userId);

	public ReadOnlyAttendancedata findByUserCardId(@ValidEntityId(entityClass = ReadOnlyUserCard.class) Long ReadOnlyUserCardId);

	public List<? extends ReadOnlyAttendancedata> findAttendancedata(@NotNull Long id, @NotNull Boolean isUserInState, @NotNull Long timePresentDay,
			@NotNull Long validTimeMissing, @NotNull Boolean useId, @NotNull Boolean useIsUserInState, @NotNull Boolean useTimePresentDay,
			@NotNull Boolean useVaidTimeMissing, @QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator idComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator timePresentDayComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator validTimeMissingComparator);

}
