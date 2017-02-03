package florian_haas.lucas.business;

import java.time.LocalDate;
import java.util.List;

import javax.ejb.Local;
import javax.validation.constraints.*;

import florian_haas.lucas.database.EnumQueryComparator;
import florian_haas.lucas.model.*;
import florian_haas.lucas.model.validation.ValidEntityId;
import florian_haas.lucas.util.validation.*;

@Local
public interface UserBeanLocal {

	public Long createPupil(@NotNull @NotBlankString String forename, @NotNull @NotBlankString String surname, @NotNull @Min(1) Integer schoolGrade,
			@NotNull @NotBlankString String schoolClass, List<@NotBlankString @TypeNotNull String> ranks);

	public Long createTeacher(@NotNull @NotBlankString String forename, @NotNull @NotBlankString String surname,
			List<@NotBlankString @TypeNotNull String> ranks);

	public Long createGuest();

	public List<User> findAll();

	public User findById(@ValidEntityId(entityClass = User.class) Long userId);

	public UserCard findUserCardById(@ValidEntityId(entityClass = UserCard.class) Long userCardId);

	public List<User> findUsers(@NotNull Long userId, @NotNull String forename, @NotNull String surname, @NotNull Integer schoolGrade,
			@NotNull String schoolClass, @NotNull EnumUserType userType, @NotNull List<@NotBlankString @TypeNotNull String> ranks,
			@NotNull Boolean useUserId, @NotNull Boolean useForename, @NotNull Boolean useSurname, @NotNull Boolean useSchoolGrade,
			@NotNull Boolean useSchoolClass, @NotNull Boolean useUserType, @NotNull Boolean useRanks, EnumQueryComparator userIdComparator,
			EnumQueryComparator forenameComparator, EnumQueryComparator surnameComparator, EnumQueryComparator schoolGradeComparator,
			EnumQueryComparator schoolClassComparator, EnumQueryComparator ranksComparator);

	public Boolean setForename(@ValidEntityId(entityClass = User.class) Long userId, @NotBlankString String forename);

	public Boolean setSurname(@ValidEntityId(entityClass = User.class) Long userId, @NotBlankString String surname);

	public Boolean setSchoolGrade(@ValidEntityId(entityClass = User.class) Long userId, @Min(1) Integer schoolGrade);

	public Boolean setSchoolClass(@ValidEntityId(entityClass = User.class) Long userId, @NotBlankString String schoolClass);

	public Boolean addRank(@ValidEntityId(entityClass = User.class) Long userId, @NotNull @NotBlankString String rank);

	public Boolean removeRank(@ValidEntityId(entityClass = User.class) Long userId, @NotNull @NotBlankString String rank);

	public Boolean addUserCard(@ValidEntityId(entityClass = User.class) Long userId);

	public Boolean blockUserCard(@ValidEntityId(entityClass = UserCard.class) Long userCardId);

	public Boolean unblockUserCard(@ValidEntityId(entityClass = UserCard.class) Long userCardId);

	public Boolean setValidDate(@ValidEntityId(entityClass = UserCard.class) Long userCardId, LocalDate validDate);

	public Boolean setImage(@ValidEntityId(entityClass = UserCard.class) Long userId, byte[] image);

}
