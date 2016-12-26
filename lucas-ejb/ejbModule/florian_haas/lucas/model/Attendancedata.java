package florian_haas.lucas.model;

import java.util.*;

import florian_haas.lucas.util.Stopwatch;

public class Attendancedata extends EntityBase {

	private static final long serialVersionUID = -2732516380228841229L;

	private User user;
	private Boolean isUserInState = Boolean.FALSE;
	private Long timePresentDay = 0L;
	private Long validTimeMissing = 0L;

	private Stopwatch timeIn = new Stopwatch();
	private Stopwatch timeOut = new Stopwatch();

	private List<AttendanceActivityLog> activityLogs = new ArrayList<>();
	private List<AttendanceLog> attendanceLogs = new ArrayList<>();

	public Attendancedata(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public Boolean getIsUserInState() {
		return this.isUserInState;
	}

	public void setIsUserInState(Boolean isUserInState) {
		this.isUserInState = isUserInState;
	}

	public Long getTimePresentDay() {
		return this.timePresentDay;
	}

	public void setTimePresentDay(Long timePresentDay) {
		this.timePresentDay = timePresentDay;
	}

	public Long getValidTimeMissing() {
		return this.validTimeMissing;
	}

	public void setValidTimeMissing(Long validTimeMissing) {
		this.validTimeMissing = validTimeMissing;
	}

	public Stopwatch getTimeIn() {
		return this.timeIn;
	}

	public Stopwatch getTimeOut() {
		return this.timeOut;
	}

	public List<AttendanceActivityLog> getActivityLogs() {
		return Collections.unmodifiableList(activityLogs);
	}

	public boolean addActivityLog(AttendanceActivityLog log) {
		return this.activityLogs.add(log);
	}

	public List<AttendanceLog> getAttendanceLogs() {
		return Collections.unmodifiableList(attendanceLogs);
	}

	public boolean addAttendanceLog(AttendanceLog log) {
		return this.attendanceLogs.add(log);
	}
}
