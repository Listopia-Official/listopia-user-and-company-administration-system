package florian_haas.lucas.business;

import java.util.List;

import javax.ejb.Local;
import javax.validation.constraints.*;

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

	public List<? extends ReadOnlyUser> findUsers(@NotNull Long userId, String forename, String surname, @NotNull List<EnumSchoolClass> schoolClasses,
			@NotNull EnumUserType userType, List<@NotBlankString @TypeNotNull String> ranks, @NotNull Integer employmentsCount, Long employmentId,
			@NotNull Boolean useUserId, @NotNull Boolean useForename, @NotNull Boolean useSurname, @NotNull Boolean useSchoolClass,
			@NotNull Boolean useUserType, @NotNull Boolean useRanks, @NotNull Boolean useEmploymentsCount, @NotNull Boolean useEmploymentId,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator userIdComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.TEXT) EnumQueryComparator forenameComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.TEXT) EnumQueryComparator surnameComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.LOGIC) EnumQueryComparator searchUserTypeComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.ARRAY) EnumQueryComparator ranksComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator employmentsCountComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.LOGIC) EnumQueryComparator employmentIdComparator);

	public Boolean setForename(@ValidEntityId(entityClass = ReadOnlyUser.class) Long userId, @NotBlankString String forename);

	public Boolean setSurname(@ValidEntityId(entityClass = ReadOnlyUser.class) Long userId, @NotBlankString String surname);

	public Boolean setSchoolClass(@ValidEntityId(entityClass = ReadOnlyUser.class) Long userId, EnumSchoolClass schoolClass);

	public Boolean addRank(@ValidEntityId(entityClass = ReadOnlyUser.class) Long userId, @NotNull @NotBlankString String rank);

	public Boolean removeRank(@ValidEntityId(entityClass = ReadOnlyUser.class) Long userId, @NotNull @NotBlankString String rank);

	public Boolean setImage(@ValidEntityId(entityClass = ReadOnlyUser.class) Long userId, byte[] image);

	public byte[] getImage(@ValidEntityId(entityClass = ReadOnlyUser.class) Long userId);

	public Boolean setFirstJobRequest(@ValidEntityId(entityClass = ReadOnlyUser.class) Long userId,
			@ValidEntityId(entityClass = ReadOnlyJob.class, nullable = true) Long jobId);

	public Boolean setSecondJobRequest(@ValidEntityId(entityClass = ReadOnlyUser.class) Long userId,
			@ValidEntityId(entityClass = ReadOnlyJob.class, nullable = true) Long jobId);

	public Boolean setThirdJobRequest(@ValidEntityId(entityClass = ReadOnlyUser.class) Long userId,
			@ValidEntityId(entityClass = ReadOnlyJob.class, nullable = true) Long jobId);

	public List<? extends ReadOnlyUser> getUsersByData(@NotNull String data, @NotNull @Min(1) Integer resultsCount);

}
