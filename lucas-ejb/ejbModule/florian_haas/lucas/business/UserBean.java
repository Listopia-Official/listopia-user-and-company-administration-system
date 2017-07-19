package florian_haas.lucas.business;

import static florian_haas.lucas.security.EnumPermission.*;

import java.util.*;
import java.util.function.*;

import javax.ejb.*;
import javax.inject.Inject;
import javax.validation.executable.*;

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;
import florian_haas.lucas.security.*;

@Stateless
@ValidateOnExecution(type = ExecutableType.IMPLICIT)
@Secured
public class UserBean implements UserBeanLocal {

	@JPADAO
	@Inject
	private UserDAO userDao;

	@JPADAO
	@Inject
	private JobDAO jobDao;

	@EJB
	private LoginBeanLocal loginBean;

	@Override
	@RequiresPermissions(USER_CREATE_PUPIL)
	public Long createPupil(String forename, String surname, EnumSchoolClass schoolClass, List<String> ranks) {
		User pupil = new User(forename, surname, schoolClass, ranks);
		userDao.persist(pupil);
		return pupil.getId();
	}

	@Override
	@RequiresPermissions(USER_CREATE_TEACHER)
	public Long createTeacher(String forename, String surname, List<String> ranks) {
		User teacher = new User(forename, surname, null, ranks);
		userDao.persist(teacher);
		return teacher.getId();
	}

	@Override
	@RequiresPermissions(USER_CREATE_GUEST)
	public Long createGuest() {
		User guest = new User(null, null, null, null);
		userDao.persist(guest);
		return guest.getId();
	}

	@Override
	@RequiresPermissions(USER_FIND_ALL)
	public List<? extends ReadOnlyUser> findAll() {
		return userDao.findAll();
	}

	@Override
	@RequiresPermissions(USER_FIND_BY_ID)
	public User findById(Long userId) {
		return userDao.findById(userId);
	}

	@Override
	@RequiresPermissions(USER_FIND_DYNAMIC)
	public List<? extends ReadOnlyUser> findUsers(Long userId, String forename, String surname, List<EnumSchoolClass> schoolClasses,
			EnumUserType userType, List<String> ranks, Integer employmentsCount, Long employmentId, Boolean useUserId, Boolean useForename,
			Boolean useSurname, Boolean useSchoolClass, Boolean useUserType, Boolean useRanks, Boolean useEmploymentsCount, Boolean useEmploymentId,
			EnumQueryComparator userIdComparator, EnumQueryComparator forenameComparator, EnumQueryComparator surnameComparator,
			EnumQueryComparator searchUserTypeComparator, EnumQueryComparator ranksComparator, EnumQueryComparator employmentsCountComparator,
			EnumQueryComparator employmentIdComparator) {
		return (!useUserId && !useForename && !useSurname && !useSchoolClass && !useUserType && !useRanks && !useEmploymentsCount && !useEmploymentId
				&& !loginBean.getSubject().isPermitted(EnumPermission.ROOM_FIND_ALL.getPermissionString()))
						? new ArrayList<>()
						: userDao.findUsers(userId, forename, surname, schoolClasses, userType, ranks, employmentsCount, employmentId, useUserId,
								useForename, useSurname, useSchoolClass, useUserType, useRanks, useEmploymentsCount, useEmploymentId,
								userIdComparator, forenameComparator, surnameComparator, searchUserTypeComparator, ranksComparator,
								employmentsCountComparator, employmentIdComparator);
	}

	@Override
	@RequiresPermissions(USER_SET_FORENAME)
	public Boolean setForename(Long userId, String forename) {
		User user = userDao.findById(userId);
		if (user.getForename() != null && user.getForename().equals(forename)) return Boolean.FALSE;
		user.setForename(forename);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(USER_SET_SURNAME)
	public Boolean setSurname(Long userId, String surname) {
		User user = userDao.findById(userId);
		if (user.getSurname() != null && user.getSurname().equals(surname)) return Boolean.FALSE;
		user.setSurname(surname);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(USER_SET_SCHOOL_CLASS)
	public Boolean setSchoolClass(Long userId, EnumSchoolClass schoolClass) {
		User user = userDao.findById(userId);
		if (user.getSchoolClass() != null && user.getSchoolClass().equals(schoolClass)) return Boolean.FALSE;
		user.setSchoolClass(schoolClass);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(USER_ADD_RANK)
	public Boolean addRank(Long userId, String rank) {
		User user = userDao.findById(userId);
		return user.addRank(rank);
	}

	@Override
	@RequiresPermissions(USER_REMOVE_RANK)
	public Boolean removeRank(Long userId, String rank) {
		User user = userDao.findById(userId);
		return user.removeRank(rank);
	}

	@Override
	@RequiresPermissions(USER_SET_IMAGE)
	public Boolean setImage(Long userId, byte[] image) {
		User user = userDao.findById(userId);
		if (Arrays.equals(user.getImage(), image)) return Boolean.FALSE;
		user.setImage(image);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(USER_GET_IMAGE_FROM_ID)
	public byte[] getImage(Long userId) {
		return userDao.getImageFromId(userId);
	}

	@Override
	@RequiresPermissions(USER_SET_JOB_REQUESTS)
	public Boolean setFirstJobRequest(Long userId, Long jobId) {
		User user = userDao.findById(userId);
		return setJobRequestHelper(user::getFirstJobRequest, user::setFirstJobRequest, jobId);
	}

	@Override
	@RequiresPermissions(USER_SET_JOB_REQUESTS)
	public Boolean setSecondJobRequest(Long userId, Long jobId) {
		User user = userDao.findById(userId);
		return setJobRequestHelper(user::getSecondJobRequest, user::setSecondJobRequest, jobId);
	}

	@Override
	@RequiresPermissions(USER_SET_JOB_REQUESTS)
	public Boolean setThirdJobRequest(Long userId, Long jobId) {
		User user = userDao.findById(userId);
		return setJobRequestHelper(user::getThirdJobRequest, user::setThirdJobRequest, jobId);
	}

	private Boolean setJobRequestHelper(Supplier<Job> requestGetter, Consumer<Job> requestSetter, Long jobId) {
		Job value = jobId != null ? jobDao.findById(jobId) : null;
		Job currentValue = requestGetter.get();
		if ((value == null && currentValue == null) || (currentValue != null && currentValue.equals(value))) return Boolean.FALSE;
		requestSetter.accept(value);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(USER_FIND_BY_DATA)
	public List<? extends ReadOnlyUser> getUsersByData(String data, Integer resultsCount) {
		return userDao.getUsersFromData(data, resultsCount);
	}

}
