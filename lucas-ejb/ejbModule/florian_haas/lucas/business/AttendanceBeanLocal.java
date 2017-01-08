package florian_haas.lucas.business;

import java.util.List;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

import florian_haas.lucas.database.EnumQueryComparator;
import florian_haas.lucas.model.*;
import florian_haas.lucas.model.validation.ValidEntityId;

@Local
public interface AttendanceBeanLocal {

	public Boolean scan(@ValidEntityId(entityClass = UserCard.class) Long userCardId);

	public void evaluateAll();

	public List<Attendancedata> findAll();

	public Attendancedata findById(@ValidEntityId(entityClass = Attendancedata.class) Long id);

	public Attendancedata findByUserId(@ValidEntityId(entityClass = User.class) Long userId);

	public Attendancedata findByUserCardId(@ValidEntityId(entityClass = UserCard.class) Long userCardId);

	public List<Attendancedata> findAttendancedata(@NotNull Long id, @NotNull Long userId, @NotNull Long userCardId, @NotNull Boolean isUserInState,
			@NotNull Long timePresentDay, @NotNull Long validTimeMissing, @NotNull Boolean useId, @NotNull Boolean useUserId,
			@NotNull Boolean useUserCardId, @NotNull Boolean useIsUserInState, @NotNull Boolean useTimePresentDay,
			@NotNull Boolean useVaidTimeMissing, EnumQueryComparator idComparator, EnumQueryComparator userIdComparator,
			EnumQueryComparator userCardIdComparator, EnumQueryComparator timePresentDayComparator, EnumQueryComparator validTimeMissingComparator);

}
