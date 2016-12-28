package florian_haas.lucas.model;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
public class AttendanceActivityLog extends EntityBase {

	private static final long serialVersionUID = 2823825483838434950L;

	@ManyToOne(optional = false)
	@JoinColumn(nullable = false)
	private Attendancedata attendancedata;

	@Basic(optional = false)
	@Column(nullable = false)
	private LocalDateTime dateTime;

	@Basic(optional = false)
	@Column(nullable = false)
	private EnumAttendanceAction action;

	@Basic(optional = false)
	@Column(nullable = false)
	private Long time;

	AttendanceActivityLog() {}

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
