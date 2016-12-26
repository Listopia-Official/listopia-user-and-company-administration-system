package florian_haas.lucas.model;

import java.time.LocalDateTime;

public class AttendanceActivityLog extends EntityBase {

	private static final long serialVersionUID = 2823825483838434950L;

	private Attendancedata attendancedata;
	private LocalDateTime dateTime;
	private EnumAttendanceAction action;
	private Long time;

	public AttendanceActivityLog(Attendancedata attendancedata, LocalDateTime dateTime, EnumAttendanceAction action, Long time) {
		this.attendancedata = attendancedata;
		this.dateTime = dateTime;
		this.action = action;
		this.time = time;
	}

	public Attendancedata getAttendancedata() {
		return attendancedata;
	}

	public LocalDateTime getDateTime() {
		return this.dateTime;
	}

	public EnumAttendanceAction getAction() {
		return this.action;
	}

	public Long getTime() {
		return this.time;
	}

}
