package florian_haas.lucas.business;

import java.time.LocalDate;
import java.util.List;

import javax.ejb.*;
import javax.inject.Inject;
import javax.validation.executable.*;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import florian_haas.lucas.database.*;
import florian_haas.lucas.model.*;
import florian_haas.lucas.util.Secured;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@ValidateOnExecution(type = ExecutableType.IMPLICIT)
@Secured
public class UserBean implements UserBeanLocal {

	@JPADAO
	@Inject
	private UserDAO userDao;

	@JPADAO
	@Inject
	private UserCardDAO userCardDao;

	@Override
	@RequiresPermissions({
			"user:createPupil" })
	public Long createPupil(String forename, String surname, Integer schoolGrade, String schoolClass, List<String> ranks) {
		User pupil = new User(forename, surname, schoolGrade, schoolClass, ranks);
		userDao.persist(pupil);
		return pupil.getId();
	}

	@Override
	@RequiresPermissions({
			"user:createTeacher" })
	public Long createTeacher(String forename, String surname, List<String> ranks) {
		User teacher = new User(forename, surname, null, null, ranks);
		userDao.persist(teacher);
		return teacher.getId();
	}

	@Override
	@RequiresPermissions({
			"user:createGuest" })
	public Long createGuest() {
		User guest = new User(null, null, null, null, null);
		userDao.persist(guest);
		return guest.getId();
	}

	@Override
	@RequiresPermissions({
			"user:findAll" })
	public List<User> findAll() {
		return userDao.findAll();
	}

	@Override
	@RequiresPermissions({
			"user:findById" })
	public User findById(Long userId) {
		return userDao.findById(userId);
	}

	@Override
	@RequiresPermissions({
			"user:findDynamic" })
	public List<User> findUsers(Long userId, String forename, String surname, Integer schoolGrade, String schoolClass, EnumUserType userType,
			List<String> ranks, Boolean useUserId, Boolean useForename, Boolean useSurname, Boolean useSchoolGrade, Boolean useSchoolClass,
			Boolean useUserType, Boolean useRanks, EnumQueryComparator userIdComparator, EnumQueryComparator forenameComparator,
			EnumQueryComparator surnameComparator, EnumQueryComparator schoolGradeComparator, EnumQueryComparator schoolClassComparator,
			EnumQueryComparator ranksComparator) {
		return userDao.findUsers(userId, forename, surname, schoolGrade, schoolClass, userType, ranks, useUserId, useForename, useSurname,
				useSchoolGrade, useSchoolClass, useUserType, useRanks, userIdComparator, forenameComparator, surnameComparator, schoolGradeComparator,
				schoolClassComparator, ranksComparator);
	}

	@Override
	@RequiresPermissions({
			"user:setForename" })
	public Boolean setForename(Long userId, String forename) {
		User user = userDao.findById(userId);
		if (user.getForename().equals(forename)) return Boolean.FALSE;
		user.setForename(forename);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions({
			"user:setSurname" })
	public Boolean setSurname(Long userId, String surname) {
		User user = userDao.findById(userId);
		if (user.getSurname().equals(surname)) return Boolean.FALSE;
		user.setSurname(surname);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions({
			"user:setSchoolGrade" })
	public Boolean setSchoolGrade(Long userId, Integer schoolGrade) {
		User user = userDao.findById(userId);
		if (user.getSchoolGrade().equals(schoolGrade)) return Boolean.FALSE;
		user.setSchoolGrade(schoolGrade);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions({
			"user:setSchoolClass" })
	public Boolean setSchoolClass(Long userId, String schoolClass) {
		User user = userDao.findById(userId);
		if (user.getSchoolClass().equals(schoolClass)) return Boolean.FALSE;
		user.setSchoolClass(schoolClass);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions({
			"user:addRank" })
	public Boolean addRank(Long userId, String rank) {
		User user = userDao.findById(userId);
		return user.addRank(rank);
	}

	@Override
	@RequiresPermissions({
			"user:removeRank" })
	public Boolean removeRank(Long userId, String rank) {
		User user = userDao.findById(userId);
		return user.removeRank(rank);
	}

	@Override
	@RequiresPermissions({
			"user:addUserCard" })
	public Boolean addUserCard(Long userId) {
		User user = userDao.findById(userId);
		UserCard userCard = new UserCard(user);
		return user.addUserCard(userCard);
	}

	@Override
	@RequiresPermissions({
			"user:blockUserCard" })
	public Boolean blockUserCard(Long userCardId) {
		UserCard userCard = userCardDao.findById(userCardId);
		if (userCard.getBlocked()) { return Boolean.FALSE; }
		userCard.setBlocked(Boolean.TRUE);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions({
			"user:unblockUserCard" })
	public Boolean unblockUserCard(Long userCardId) {
		UserCard userCard = userCardDao.findById(userCardId);
		if (!userCard.getBlocked()) { return Boolean.FALSE; }
		userCard.setBlocked(Boolean.FALSE);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions({
			"user:setValidDate" })
	public Boolean setValidDate(Long userCardId, LocalDate validDate) {
		UserCard userCard = userCardDao.findById(userCardId);
		if (userCard.getValidDay().equals(validDate)) return Boolean.FALSE;
		userCard.setValidDay(validDate);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions({
			"user:findUserCardById" })
	public UserCard findUserCardById(Long userCardId) {
		return userCardDao.findById(userCardId);
	}

}
