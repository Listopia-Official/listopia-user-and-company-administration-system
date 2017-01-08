package florian_haas.lucas.business;

import java.util.List;

import javax.ejb.Local;
import javax.validation.constraints.*;

import florian_haas.lucas.database.EnumQueryComparator;
import florian_haas.lucas.model.*;
import florian_haas.lucas.model.validation.ValidEntityId;
import florian_haas.lucas.util.validation.*;

@Local
public interface UserBeanLocal {

	public User createPupil(@NotNull @NotBlankString String forename, @NotNull @NotBlankString String surname, @NotNull @Min(1) Integer schoolGrade,
			@NotNull @NotBlankString String schoolClass, List<@NotBlankString @TypeNotNull String> ranks);

	public User createTeacher(@NotNull @NotBlankString String forename, @NotNull @NotBlankString String surname,
			List<@NotBlankString @TypeNotNull String> ranks);

	public User createGuest();

	public List<User> findAll();

	public User findById(@ValidEntityId(entityClass = User.class) Long userId);

	public List<User> findUsers(@NotNull Long userId, @NotNull String forename, @NotNull String surname, @NotNull Integer schoolGrade,
			@NotNull String schoolClass, @NotNull EnumUserType userType, @NotNull List<@NotBlankString @TypeNotNull String> ranks,
			@NotNull Boolean useUserId, @NotNull Boolean useForename, @NotNull Boolean useSurname, @NotNull Boolean useSchoolGrade,
			@NotNull Boolean useSchoolClass, @NotNull Boolean useUserType, @NotNull Boolean useRanks, EnumQueryComparator userIdComparator,
			EnumQueryComparator forenameComparator, EnumQueryComparator surnameComparator, EnumQueryComparator schoolGradeComparator,
			EnumQueryComparator schoolClassComparator, EnumQueryComparator ranksComparator);

	public User setForename(@ValidEntityId(entityClass = User.class) Long userId, String forename);

	public User setSurname(@ValidEntityId(entityClass = User.class) Long userId, String surname);

	public User setSchoolGrade(@ValidEntityId(entityClass = User.class) Long userId, @Min(1) Integer schoolGrade);

	public User setSchoolClass(@ValidEntityId(entityClass = User.class) Long userId, @NotBlankString String schoolClass);

	public User addRank(@ValidEntityId(entityClass = User.class) Long userId, @NotNull @NotBlankString String rank);

	public User removeRank(@ValidEntityId(entityClass = User.class) Long userId, @NotNull @NotBlankString String rank);

	public User addUserCard(@ValidEntityId(entityClass = User.class) Long userId);

	public Boolean blockUserCard(@ValidEntityId(entityClass = UserCard.class) Long userCardId);

	public Boolean unblockUserCard(@ValidEntityId(entityClass = UserCard.class) Long userCardId);

	public User addVisa(@ValidEntityId(entityClass = User.class) Long userId);

	public Boolean activateVisa(@ValidEntityId(entityClass = Visa.class) Long visaId);

	public Boolean deactivateVisa(@ValidEntityId(entityClass = Visa.class) Long visaId);

}
