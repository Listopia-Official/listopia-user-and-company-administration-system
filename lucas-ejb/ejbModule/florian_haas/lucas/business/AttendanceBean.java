package florian_haas.lucas.business;

import java.time.*;
import java.util.*;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.validation.*;
import javax.validation.executable.*;

import florian_haas.lucas.database.*;
import florian_haas.lucas.model.*;
import florian_haas.lucas.model.validation.ValidEntityId;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@ValidateOnExecution(type = ExecutableType.IMPLICIT)
public class AttendanceBean implements AttendanceBeanLocal {

	@Inject
	@JPADAO
	private AttendancedataDAO attendanceDao;

	@Inject
	@JPADAO
	private UserCardDAO userCardDao;

	@Inject
	@JPADAO
	private UserDAO userDao;

	@EJB
	private GlobalDataBeanLocal globalData;

	@Resource
	private Validator validator;

	@Override
	public Boolean scan(@ValidEntityId(entityClass = UserCard.class) Long userCardId) {
		User user = userCardDao.findById(userCardId).getUser();
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
	public Integer evaluateAll() {
		List<Long> ids = attendanceDao.findAllIds();
		Integer c = 0;
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
				if (!log.hasMetRequirements()) {
					c++;
				}
			}
		}
		return c;

	}

	@Override
	public List<Attendancedata> findAll() {
		return attendanceDao.findAll();
	}

	@Override
	public Attendancedata findById(@ValidEntityId(entityClass = Attendancedata.class) Long id) {
		return attendanceDao.findById(id);
	}

	@Override
	public Attendancedata findByUserId(@ValidEntityId(entityClass = User.class) Long userId) {
		return userDao.findById(userId).getAttendancedata();
	}

	@Override
	public Attendancedata findByUserCardId(@ValidEntityId(entityClass = UserCard.class) Long userCardId) {
		return userCardDao.findById(userCardId).getUser().getAttendancedata();
	}

	@Override
	public List<Attendancedata> findAttendancedata(Long id, Boolean isUserInState, Long timePresentDay, Long validTimeMissing, Boolean useId,
			Boolean useIsUserInState, Boolean useTimePresentDay, Boolean useVaidTimeMissing, EnumQueryComparator idComparator,
			EnumQueryComparator timePresentDayComparator, EnumQueryComparator validTimeMissingComparator) {
		return attendanceDao.findAttendancedata(id, isUserInState, timePresentDay, validTimeMissing, useId, useIsUserInState, useTimePresentDay,
				useVaidTimeMissing, idComparator, timePresentDayComparator, validTimeMissingComparator);
	}

}
