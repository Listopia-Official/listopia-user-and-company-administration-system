package florian_haas.lucas.business;

import static florian_haas.lucas.security.EnumPermission.*;

import java.time.*;
import java.util.*;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.validation.*;
import javax.validation.executable.*;

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;
import florian_haas.lucas.security.*;

@Stateless
@ValidateOnExecution(type = ExecutableType.IMPLICIT)
@Secured
public class AttendanceBean implements AttendanceBeanLocal {

	@Inject
	@JPADAO
	private AttendancedataDAO attendanceDao;

	@EJB
	private UserBeanLocal userBean;

	@Inject
	@JPADAO
	private IdCardDAO idCardDao;

	@EJB
	private GlobalDataBeanLocal globalData;

	@Resource
	private Validator validator;

	@Override
	@RequiresPermissions(ATTENDANCE_SCAN)
	public Boolean scan(Long idCardId) {
		ReadOnlyIdCard idCard = idCardDao.findById(idCardId);
		if (idCard.getOwner().getOwnerType() == EnumAccountOwnerType.USER && idCard.getBlocked()
				|| (idCard.getValidDay() != null && !idCard.getValidDay().equals(LocalDate.now()))) {
			ReadOnlyUser user = (ReadOnlyUser) idCard.getOwner();
			if (user.getUserType() != EnumUserType.TEACHER) {
				ReadOnlyAttendancedata attendancedata = user.getAttendancedata();
				if (attendancedata != null) {
					if (attendancedata.getIsUserInState() == Boolean.TRUE) {
						enter((Attendancedata) attendancedata);
					} else if (attendancedata.getIsUserInState() == Boolean.FALSE) {
						leave((Attendancedata) attendancedata);
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
	@RequiresPermissions(ATTENDANCE_EVALUATE_ALL)
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
	@RequiresPermissions(ATTENDANCE_FIND_ALL)
	public List<? extends ReadOnlyAttendancedata> findAll() {
		return attendanceDao.findAll();
	}

	@Override
	@RequiresPermissions(ATTENDANCE_FIND_BY_ID)
	public Attendancedata findById(Long id) {
		return attendanceDao.findById(id);
	}

	@Override
	@RequiresPermissions(ATTENDANCE_FIND_DYNAMIC)
	public List<? extends ReadOnlyAttendancedata> findAttendancedata(Long id, Boolean isUserInState, Long timePresentDay, Long validTimeMissing,
			Boolean useId, Boolean useIsUserInState, Boolean useTimePresentDay, Boolean useVaidTimeMissing, EnumQueryComparator idComparator,
			EnumQueryComparator timePresentDayComparator, EnumQueryComparator validTimeMissingComparator) {
		return attendanceDao.findAttendancedata(id, isUserInState, timePresentDay, validTimeMissing, useId, useIsUserInState, useTimePresentDay,
				useVaidTimeMissing, idComparator, timePresentDayComparator, validTimeMissingComparator);
	}

}
