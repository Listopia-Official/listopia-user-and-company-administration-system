package florian_haas.lucas.model;

import java.util.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import florian_haas.lucas.model.validation.ValidTimeMillis;
import florian_haas.lucas.validation.TypeNotNull;

@Entity
public class Attendancedata extends EntityBase implements ReadOnlyAttendancedata {

	private static final long serialVersionUID = -2732516380228841229L;

	@OneToOne(optional = false, mappedBy = "attendancedata")
	@NotNull
	private User user;

	@Column(nullable = false)
	@Basic(optional = false)
	@NotNull
	private Boolean isUserInState = Boolean.FALSE;

	@Column(nullable = false)
	@Basic(optional = false)
	@ValidTimeMillis
	private Long timePresentDay = 0L;

	@Column(nullable = false)
	@Basic(optional = false)
	@ValidTimeMillis
	private Long validTimeMissing = 0L;

	@Embedded
	@AttributeOverrides(value = {
			@AttributeOverride(name = "startTime", column = @Column(name = "startTimeIn")), @AttributeOverride(name = "tmpStartTime", column = @Column(name = "tmpStartTimeIn")), @AttributeOverride(name = "duration", column = @Column(name = "durationIn")), @AttributeOverride(name = "tmpDuration", column = @Column(name = "tmpDurationIn")), @AttributeOverride(name = "running", column = @Column(name = "runningIn")) })
	private Stopwatch timeIn = new Stopwatch();

	@Embedded
	@AttributeOverrides(value = {
			@AttributeOverride(name = "startTime", column = @Column(name = "startTimeOut")), @AttributeOverride(name = "tmpStartTime", column = @Column(name = "tmpStartTimeOut")), @AttributeOverride(name = "duration", column = @Column(name = "durationOut")), @AttributeOverride(name = "tmpDuration", column = @Column(name = "tmpDurationOut")), @AttributeOverride(name = "running", column = @Column(name = "runningOut")) })
	private Stopwatch timeOut = new Stopwatch();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "attendancedata")
	@NotNull
	@Valid
	private List<@TypeNotNull AttendanceActivityLog> activityLogs = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "attendancedata")
	@NotNull
	@Valid
	private List<@TypeNotNull AttendanceLog> attendanceLogs = new ArrayList<>();

	Attendancedata() {}

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

	public Boolean addActivityLog(AttendanceActivityLog log) {
		return this.activityLogs.add(log);
	}

	public List<AttendanceLog> getAttendanceLogs() {
		return Collections.unmodifiableList(attendanceLogs);
	}

	public Boolean addAttendanceLog(AttendanceLog log) {
		return this.attendanceLogs.add(log);
	}
}
