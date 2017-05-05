package florian_haas.lucas.business;

import java.time.LocalDate;
import java.util.*;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

import florian_haas.lucas.database.*;
import florian_haas.lucas.database.validation.QueryComparator;
import florian_haas.lucas.model.*;
import florian_haas.lucas.model.validation.ValidEntityId;
import florian_haas.lucas.util.validation.*;

@Local
public interface UserBeanLocal {

	public Long createPupil(@NotNull @NotBlankString String forename, @NotNull @NotBlankString String surname, @NotNull EnumSchoolClass schoolClass,
			List<@NotBlankString @TypeNotNull String> ranks);

	public Long createTeacher(@NotNull @NotBlankString String forename, @NotNull @NotBlankString String surname,
			List<@NotBlankString @TypeNotNull String> ranks);

	public Long createGuest();

	public List<User> findAll();

	public User findById(@ValidEntityId(entityClass = User.class) Long userId);

	public UserCard findUserCardById(@ValidEntityId(entityClass = UserCard.class) Long userCardId);

	public List<User> findUsers(@NotNull Long userId, String forename, String surname, @NotNull List<@TypeNotNull EnumSchoolClass> schoolClasses,
			@NotNull EnumUserType userType, List<@NotBlankString @TypeNotNull String> ranks, @NotNull Boolean useUserId, @NotNull Boolean useForename,
			@NotNull Boolean useSurname, @NotNull Boolean useSchoolClass, @NotNull Boolean useUserType, @NotNull Boolean useRanks,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator userIdComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.TEXT) EnumQueryComparator forenameComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.TEXT) EnumQueryComparator surnameComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.LOGIC) EnumQueryComparator searchUserTypeComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.ARRAY) EnumQueryComparator ranksComparator);

	public Boolean setForename(@ValidEntityId(entityClass = User.class) Long userId, @NotBlankString String forename);

	public Boolean setSurname(@ValidEntityId(entityClass = User.class) Long userId, @NotBlankString String surname);

	public Boolean setSchoolClass(@ValidEntityId(entityClass = User.class) Long userId, EnumSchoolClass schoolClass);

	public Boolean addRank(@ValidEntityId(entityClass = User.class) Long userId, @NotNull @NotBlankString String rank);

	public Boolean removeRank(@ValidEntityId(entityClass = User.class) Long userId, @NotNull @NotBlankString String rank);

	public Set<UserCard> getUserCards(@ValidEntityId(entityClass = User.class) Long userId);

	public Long addUserCard(@ValidEntityId(entityClass = User.class) Long userId);

	public Boolean removeUserCard(@ValidEntityId(entityClass = UserCard.class) Long userCardId);

	public Boolean blockUserCard(@ValidEntityId(entityClass = UserCard.class) Long userCardId);

	public Boolean unblockUserCard(@ValidEntityId(entityClass = UserCard.class) Long userCardId);

	public Boolean setValidDate(@ValidEntityId(entityClass = UserCard.class) Long userCardId, LocalDate validDate);

	public Boolean setImage(@ValidEntityId(entityClass = User.class) Long userId, byte[] image);

	public byte[] getImage(@ValidEntityId(entityClass = User.class) Long userId);

	public Boolean setFirstJobRequest(@ValidEntityId(entityClass = User.class) Long userId,
			@ValidEntityId(entityClass = Job.class, nullable = true) Long jobId);

	public Boolean setSecondJobRequest(@ValidEntityId(entityClass = User.class) Long userId,
			@ValidEntityId(entityClass = Job.class, nullable = true) Long jobId);

	public Boolean setThirdJobRequest(@ValidEntityId(entityClass = User.class) Long userId,
			@ValidEntityId(entityClass = Job.class, nullable = true) Long jobId);

}
