package florian_haas.lucas.business;

import static florian_haas.lucas.security.EnumPermission.*;

import java.time.*;
import java.util.*;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.validation.Validator;
import javax.validation.executable.*;

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;
import florian_haas.lucas.security.*;
import florian_haas.lucas.validation.ValidEntityId;

@Stateless
@ValidateOnExecution(type = ExecutableType.IMPLICIT)
@Secured
public class AttendanceBean implements AttendanceBeanLocal {

	@Inject
	@JPADAO
	private AttendancedataDAO attendanceDao;

	@EJB
	private UserBeanLocal userBean;

	@EJB
	private LoginBeanLocal loginBean;

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
		if (idCard.getBlocked()) throw new LucasException("The id card is blocked", AttendanceBeanLocal.USER_CARD_BLOCKED_EXCEPTION_MARKER);
		if (idCard.getValidDay() != null && !idCard.getValidDay().equals(LocalDate.now()))
			throw new LucasException("The valid date for the id card isn't equal to the current date",
					AttendanceBeanLocal.USER_CARD_INVALID_DATE_EXCEPTION_MARKER);
		User user = (User) idCard.getOwner();
		return this.scanByAttendancedata(user.getAttendancedata().getId());
	}

	@Override
	@RequiresPermissions(ATTENDANCE_SCAN)
	public Boolean scanByAttendancedata(@ValidEntityId(entityClass = ReadOnlyAttendancedata.class) Long attendancedataId) {
		Attendancedata attendancedata = attendanceDao.findById(attendancedataId);
		if (attendancedata.getIsUserInState() == Boolean.TRUE) {
			leave((Attendancedata) attendancedata);
			return Boolean.FALSE;
		} else {
			enter((Attendancedata) attendancedata);
			return Boolean.TRUE;
		}
	}

	private void enter(Attendancedata attendancedata) {
		attendancedata.getTimeIn().start();
		attendancedata.getTimeOut().stop();
		Long duration = attendancedata.getTimeOut().getDuration();
		attendancedata.getTimeOut().reset();
		EnumAttendanceAction action = EnumAttendanceAction.ENTER;
		attendancedata.setIsUserInState(Boolean.TRUE);
		addLog(attendancedata, action, duration);
	}

	private void leave(Attendancedata attendancedata) {
		attendancedata.getTimeIn().stop();
		attendancedata.getTimeOut().start();
		Long duration = attendancedata.getTimeIn().getDuration();
		attendancedata.getTimeIn().reset();
		attendancedata.setTimePresentDay(attendancedata.getRawTimePresentDay() + duration);
		EnumAttendanceAction action = EnumAttendanceAction.LEAVE;
		attendancedata.setIsUserInState(Boolean.FALSE);
		addLog(attendancedata, action, duration);
	}

	private void addLog(Attendancedata attendancedata, EnumAttendanceAction action, Long duration) {
		AttendanceActivityLog log = new AttendanceActivityLog(attendancedata, LocalDateTime.now(), action, duration);
		attendancedata.addActivityLog(log);
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
	public List<? extends ReadOnlyAttendancedata> findAttendancedata(Long id, Long userId, Boolean isUserInState, Long timePresentDay, Boolean useId,
			Boolean useUserId, Boolean useIsUserInState, Boolean useTimePresentDay, EnumQueryComparator idComparator,
			EnumQueryComparator userIdComparator, EnumQueryComparator timePresentDayComparator) {
		return (!useId && !useUserId && !useIsUserInState && !useTimePresentDay
				&& !loginBean.getSubject().isPermitted(EnumPermission.ATTENDANCE_FIND_ALL.getPermissionString())) ? new ArrayList<>()
						: attendanceDao.findAttendancedata(id, userId, isUserInState, timePresentDay, useId, useUserId, useIsUserInState,
								useTimePresentDay, idComparator, userIdComparator, timePresentDayComparator);
	}

	@Override
	@RequiresPermissions(ATTENDANCE_GET_ACTIVITY_LOGS)
	public List<? extends ReadOnlyAttendanceActivityLog> getAttendanceActivityLogs(Long id) {
		return attendanceDao.findById(id).getActivityLogs();
	}

	@Override
	@RequiresPermissions(ATTENDANCE_RESET)
	public void reset(Long attendancedataId) {
		Attendancedata data = attendanceDao.findById(attendancedataId);
		data.getTimeIn().stop();
		data.getTimeIn().reset();
		data.getTimeOut().stop();
		data.getTimeOut().reset();
		data.setIsUserInState(Boolean.FALSE);
		data.setTimePresentDay(0l);
		data.resetLogs();
	}

}
