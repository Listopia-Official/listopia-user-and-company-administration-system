package florian_haas.lucas.model;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
public class AttendanceLog extends EntityBase {

	private static final long serialVersionUID = -4820073122825186188L;

	@ManyToOne(optional = false)
	@JoinColumn(nullable = false)
	private Attendancedata attendancedata;

	@Basic(optional = false)
	@Column(nullable = false)
	private LocalDate date;

	@Basic(optional = false)
	@Column(nullable = false)
	private Long timePresent;

	@Basic(optional = false)
	@Column(nullable = false)
	private Long minTimePresent;

	@Basic(optional = false)
	@Column(nullable = false)
	private Long validTimeMissing;

	AttendanceLog() {}

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
