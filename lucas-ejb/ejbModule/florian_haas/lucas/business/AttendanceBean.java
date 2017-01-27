package florian_haas.lucas.business;

import java.time.*;
import java.util.*;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.validation.*;
import javax.validation.executable.*;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import florian_haas.lucas.database.*;
import florian_haas.lucas.model.*;
import florian_haas.lucas.model.validation.ValidEntityId;
import florian_haas.lucas.util.Secured;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@ValidateOnExecution(type = ExecutableType.IMPLICIT)
@Secured
public class AttendanceBean implements AttendanceBeanLocal {

	@Inject
	@JPADAO
	private AttendancedataDAO attendanceDao;

	@EJB
	private UserBeanLocal userBean;

	@EJB
	private GlobalDataBeanLocal globalData;

	@Resource
	private Validator validator;

	@Override
	@RequiresPermissions({
			"attendance:scan" })
	public Boolean scan(@ValidEntityId(entityClass = UserCard.class) Long userCardId) {
		UserCard userCard = userBean.findUserCardById(userCardId);
		if (userCard.getBlocked() || (userCard.getValidDay() != null && !userCard.getValidDay().equals(LocalDate.now()))) {
			User user = userCard.getUser();
			if (user.getUserType() != EnumUserType.TEACHER) {
				Attendancedata attendancedata = user.getAttendancedata();
				if (attendancedata != null) {
					if (attendancedata.getIsUserInState() == Boolean.TRUE) {
						enter(attendancedata);
					} else if (attendancedata.getIsUserInState() == Boolean.FALSE) {
						leave(attendancedata);
					}
					return Boolean.TRUE;
				}
			}
		}
		return Boolean.FALSE;
	}

	private void enter(Attendancedata attendancedata) {
		attendancedata.getTimeIn().start();
		attendancedata.getTimeOut().stop();
		Long duration = attendancedata.getTimeOut().getTmpDuration();
		EnumAttendanceAction action = EnumAttendanceAction.ENTER;
		attendancedata.setIsUserInState(Boolean.TRUE);
		addLog(attendancedata, action, duration);
	}

	private void leave(Attendancedata attendancedata) {
		attendancedata.getTimeIn().stop();
		attendancedata.getTimeOut().start();
		Long duration = attendancedata.getTimeIn().getTmpDuration();
		attendancedata.setTimePresentDay(attendancedata.getTimePresentDay() + duration);
		EnumAttendanceAction action = EnumAttendanceAction.LEAVE;
		attendancedata.setIsUserInState(Boolean.FALSE);
		addLog(attendancedata, action, duration);
	}

	private void addLog(Attendancedata attendancedata, EnumAttendanceAction action, Long duration) {
		AttendanceActivityLog log = new AttendanceActivityLog(attendancedata, LocalDateTime.now(), action, duration);
		Set<ConstraintViolation<AttendanceActivityLog>> violations = validator.validate(log);
		if (!violations.isEmpty()) throw new ConstraintViolationException(violations);
		attendancedata.addActivityLog(log);
	}

	@Override
	@RequiresPermissions({
			"attendance:evaluateAll" })
	public List<Long> evaluateAll() {
		List<Long> ids = attendanceDao.findAllIds();
		List<Long> requirementsNotMet = new ArrayList<>();
		for (Long id : ids) {
			Attendancedata attendancedata = attendanceDao.findById(id);
			if (attendancedata != null) {
				leave(attendancedata);
				attendancedata.getTimeOut().stop();
				attendancedata.getTimeIn().reset();
				attendancedata.getTimeOut().reset();
				AttendanceLog log = new AttendanceLog(attendancedata, LocalDate.now(), attendancedata.getTimePresentDay(),
						globalData.getMinTimePresent(), attendancedata.getValidTimeMissing());
				attendancedata.addAttendanceLog(log);
				attendancedata.setTimePresentDay(0l);
				if (!log.hasMetRequirements()) {
					requirementsNotMet.add(attendancedata.getUser().getId());
				}
			}
		}
		return requirementsNotMet;
	}

	@Override
	@RequiresPermissions({
			"attendance:findAll" })
	public List<Attendancedata> findAll() {
		return attendanceDao.findAll();
	}

	@Override
	@RequiresPermissions({
			"attendance:findById" })
	public Attendancedata findById(@ValidEntityId(entityClass = Attendancedata.class) Long id) {
		return attendanceDao.findById(id);
	}

	@Override
	public Attendancedata findByUserId(@ValidEntityId(entityClass = User.class) Long userId) {
		return userBean.findById(userId).getAttendancedata();
	}

	@Override
	@RequiresPermissions({
			"attendancefindByUserCardId" })
	public Attendancedata findByUserCardId(@ValidEntityId(entityClass = UserCard.class) Long userCardId) {
		return userBean.findUserCardById(userCardId).getUser().getAttendancedata();
	}

	@Override
	@RequiresPermissions({
			"attendance:findDynamic" })
	public List<Attendancedata> findAttendancedata(Long id, Boolean isUserInState, Long timePresentDay, Long validTimeMissing, Boolean useId,
			Boolean useIsUserInState, Boolean useTimePresentDay, Boolean useVaidTimeMissing, EnumQueryComparator idComparator,
			EnumQueryComparator timePresentDayComparator, EnumQueryComparator validTimeMissingComparator) {
		return attendanceDao.findAttendancedata(id, isUserInState, timePresentDay, validTimeMissing, useId, useIsUserInState, useTimePresentDay,
				useVaidTimeMissing, idComparator, timePresentDayComparator, validTimeMissingComparator);
	}

}
