package florian_haas.lucas.business;

import java.time.LocalDate;
import java.util.List;

import javax.ejb.*;
import javax.inject.Inject;
import javax.validation.executable.*;

import florian_haas.lucas.database.*;
import florian_haas.lucas.model.*;
import florian_haas.lucas.model.validation.ValidEntityId;
import florian_haas.lucas.util.validation.*;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@ValidateOnExecution(type = ExecutableType.IMPLICIT)
public class UserBean implements UserBeanLocal {

	@JPADAO
	@Inject
	private UserDAO userDao;

	@JPADAO
	@Inject
	private UserCardDAO userCardDao;

	@Override
	public Long createPupil(@NotBlankString String forename, @NotBlankString String surname, Integer schoolGrade, @NotBlankString String schoolClass,
			List<@NotBlankString @TypeNotNull String> ranks) {
		User pupil = new User(forename, surname, schoolGrade, schoolClass, ranks);
		userDao.persist(pupil);
		return pupil.getId();
	}

	@Override
	public Long createTeacher(@NotBlankString String forename, @NotBlankString String surname, List<@NotBlankString @TypeNotNull String> ranks) {
		User teacher = new User(forename, surname, null, null, ranks);
		userDao.persist(teacher);
		return teacher.getId();
	}

	@Override
	public Long createGuest() {
		User guest = new User(null, null, null, null, null);
		userDao.persist(guest);
		return guest.getId();
	}

	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}

	@Override
	public User findById(@ValidEntityId(entityClass = User.class) Long userId) {
		return userDao.findById(userId);
	}

	@Override
	public List<User> findUsers(Long userId, String forename, String surname, Integer schoolGrade, String schoolClass, EnumUserType userType,
			List<@NotBlankString @TypeNotNull String> ranks, Boolean useUserId, Boolean useForename, Boolean useSurname, Boolean useSchoolGrade,
			Boolean useSchoolClass, Boolean useUserType, Boolean useRanks, EnumQueryComparator userIdComparator,
			EnumQueryComparator forenameComparator, EnumQueryComparator surnameComparator, EnumQueryComparator schoolGradeComparator,
			EnumQueryComparator schoolClassComparator, EnumQueryComparator ranksComparator) {
		return userDao.findUsers(userId, forename, surname, schoolGrade, schoolClass, userType, ranks, useUserId, useForename, useSurname,
				useSchoolGrade, useSchoolClass, useUserType, useRanks, userIdComparator, forenameComparator, surnameComparator, schoolGradeComparator,
				schoolClassComparator, ranksComparator);
	}

	@Override
	public Boolean setForename(@ValidEntityId(entityClass = User.class) Long userId, @NotBlankString String forename) {
		User user = userDao.findById(userId);
		if (user.getForename().equals(forename)) return Boolean.FALSE;
		user.setForename(forename);
		return Boolean.TRUE;
	}

	@Override
	public Boolean setSurname(@ValidEntityId(entityClass = User.class) Long userId, @NotBlankString String surname) {
		User user = userDao.findById(userId);
		if (user.getSurname().equals(surname)) return Boolean.FALSE;
		user.setSurname(surname);
		return Boolean.TRUE;
	}

	@Override
	public Boolean setSchoolGrade(@ValidEntityId(entityClass = User.class) Long userId, Integer schoolGrade) {
		User user = userDao.findById(userId);
		if (user.getSchoolGrade().equals(schoolGrade)) return Boolean.FALSE;
		user.setSchoolGrade(schoolGrade);
		return Boolean.TRUE;
	}

	@Override
	public Boolean setSchoolClass(@ValidEntityId(entityClass = User.class) Long userId, @NotBlankString String schoolClass) {
		User user = userDao.findById(userId);
		if (user.getSchoolClass().equals(schoolClass)) return Boolean.FALSE;
		user.setSchoolClass(schoolClass);
		return Boolean.TRUE;
	}

	@Override
	public Boolean addRank(@ValidEntityId(entityClass = User.class) Long userId, @NotBlankString String rank) {
		User user = userDao.findById(userId);
		return user.addRank(rank);
	}

	@Override
	public Boolean removeRank(@ValidEntityId(entityClass = User.class) Long userId, @NotBlankString String rank) {
		User user = userDao.findById(userId);
		return user.removeRank(rank);
	}

	@Override
	public Boolean addUserCard(@ValidEntityId(entityClass = User.class) Long userId) {
		User user = userDao.findById(userId);
		UserCard userCard = new UserCard(user);
		return user.addUserCard(userCard);
	}

	@Override
	public Boolean blockUserCard(@ValidEntityId(entityClass = UserCard.class) Long userCardId) {
		UserCard userCard = userCardDao.findById(userCardId);
		if (userCard.getBlocked()) { return Boolean.FALSE; }
		userCard.setBlocked(Boolean.TRUE);
		return Boolean.TRUE;
	}

	@Override
	public Boolean unblockUserCard(@ValidEntityId(entityClass = UserCard.class) Long userCardId) {
		UserCard userCard = userCardDao.findById(userCardId);
		if (!userCard.getBlocked()) { return Boolean.FALSE; }
		userCard.setBlocked(Boolean.FALSE);
		return Boolean.TRUE;
	}

	@Override
	public Boolean setValidDate(@ValidEntityId(entityClass = UserCard.class) Long userCardId, LocalDate validDate) {
		UserCard userCard = userCardDao.findById(userCardId);
		if (userCard.getValidDay().equals(validDate)) return Boolean.FALSE;
		userCard.setValidDay(validDate);
		return Boolean.TRUE;
	}

	@Override
	public UserCard findUserCardById(@ValidEntityId(entityClass = UserCard.class) Long userCardId) {
		return userCardDao.findById(userCardId);
	}

}
