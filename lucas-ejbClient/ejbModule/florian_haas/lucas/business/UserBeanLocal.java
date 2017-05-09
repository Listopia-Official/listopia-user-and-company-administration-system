package florian_haas.lucas.business;

import java.time.LocalDate;
import java.util.*;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;
import florian_haas.lucas.validation.*;

@Local
public interface UserBeanLocal {

	public Long createPupil(@NotNull @NotBlankString String forename, @NotNull @NotBlankString String surname, @NotNull EnumSchoolClass schoolClass,
			List<@NotBlankString @TypeNotNull String> ranks);

	public Long createTeacher(@NotNull @NotBlankString String forename, @NotNull @NotBlankString String surname,
			List<@NotBlankString @TypeNotNull String> ranks);

	public Long createGuest();

	public List<? extends ReadOnlyUser> findAll();

	public ReadOnlyUser findById(@ValidEntityId(entityClass = ReadOnlyUser.class) Long userId);

	public ReadOnlyUserCard findUserCardById(@ValidEntityId(entityClass = ReadOnlyUserCard.class) Long userCardId);

	public List<? extends ReadOnlyUser> findUsers(@NotNull Long userId, String forename, String surname,
			@NotNull List<@TypeNotNull EnumSchoolClass> schoolClasses, @NotNull EnumUserType userType,
			List<@NotBlankString @TypeNotNull String> ranks, @NotNull Integer employmentsCount, @NotNull Boolean useUserId,
			@NotNull Boolean useForename, @NotNull Boolean useSurname, @NotNull Boolean useSchoolClass, @NotNull Boolean useUserType,
			@NotNull Boolean useRanks, @NotNull Boolean useEmploymentsCount,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator userIdComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.TEXT) EnumQueryComparator forenameComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.TEXT) EnumQueryComparator surnameComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.LOGIC) EnumQueryComparator searchUserTypeComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.ARRAY) EnumQueryComparator ranksComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator employmentsCountComparator);

	public Boolean setForename(@ValidEntityId(entityClass = ReadOnlyUser.class) Long userId, @NotBlankString String forename);

	public Boolean setSurname(@ValidEntityId(entityClass = ReadOnlyUser.class) Long userId, @NotBlankString String surname);

	public Boolean setSchoolClass(@ValidEntityId(entityClass = ReadOnlyUser.class) Long userId, EnumSchoolClass schoolClass);

	public Boolean addRank(@ValidEntityId(entityClass = ReadOnlyUser.class) Long userId, @NotNull @NotBlankString String rank);

	public Boolean removeRank(@ValidEntityId(entityClass = ReadOnlyUser.class) Long userId, @NotNull @NotBlankString String rank);

	public Set<? extends ReadOnlyUserCard> getUserCards(@ValidEntityId(entityClass = ReadOnlyUser.class) Long userId);

	public Long addUserCard(@ValidEntityId(entityClass = ReadOnlyUser.class) Long userId);

	public Boolean removeUserCard(@ValidEntityId(entityClass = ReadOnlyUserCard.class) Long userCardId);

	public Boolean blockUserCard(@ValidEntityId(entityClass = ReadOnlyUserCard.class) Long userCardId);

	public Boolean unblockUserCard(@ValidEntityId(entityClass = ReadOnlyUserCard.class) Long userCardId);

	public Boolean setValidDate(@ValidEntityId(entityClass = ReadOnlyUserCard.class) Long userCardId, LocalDate validDate);

	public Boolean setImage(@ValidEntityId(entityClass = ReadOnlyUser.class) Long userId, byte[] image);

	public byte[] getImage(@ValidEntityId(entityClass = ReadOnlyUser.class) Long userId);

	public Boolean setFirstJobRequest(@ValidEntityId(entityClass = ReadOnlyUser.class) Long userId,
			@ValidEntityId(entityClass = ReadOnlyJob.class, nullable = true) Long jobId);

	public Boolean setSecondJobRequest(@ValidEntityId(entityClass = ReadOnlyUser.class) Long userId,
			@ValidEntityId(entityClass = ReadOnlyJob.class, nullable = true) Long jobId);

	public Boolean setThirdJobRequest(@ValidEntityId(entityClass = ReadOnlyUser.class) Long userId,
			@ValidEntityId(entityClass = ReadOnlyJob.class, nullable = true) Long jobId);

}
