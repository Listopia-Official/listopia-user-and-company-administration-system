package florian_haas.lucas.model;

import java.time.LocalDate;

public class AttendanceLog extends EntityBase {

	private static final long serialVersionUID = -4820073122825186188L;

	private Attendancedata attendancedata;
	private LocalDate date;
	private Long timePresent;
	private Long minTimePresent;
	private Long validTimeMissing;

	public AttendanceLog(Attendancedata attendancedata, LocalDate date, Long timePresent, Long minTimePresent, Long validTimeMissing) {
		this.attendancedata = attendancedata;
		this.date = date;
		this.timePresent = timePresent;
		this.minTimePresent = minTimePresent;
		this.validTimeMissing = validTimeMissing;
	}

	public Attendancedata getAttendancedata() {
		return this.attendancedata;
	}

	public LocalDate getDate() {
		return this.date;
	}

	public Long getTimePresent() {
		return this.timePresent;
	}

	public Long getMinTimePresent() {
		return this.minTimePresent;
	}

	public Long getValidTimeMissing() {
		return this.validTimeMissing;
	}

	public boolean hasMetRequirements() {
		return (timePresent + validTimeMissing) >= minTimePresent;
	}

}
