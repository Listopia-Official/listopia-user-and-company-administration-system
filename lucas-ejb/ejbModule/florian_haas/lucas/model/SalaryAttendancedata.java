package florian_haas.lucas.model;

import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class SalaryAttendancedata extends EntityBase {

	private static final long serialVersionUID = 6663270725797916280L;

	@ManyToOne(optional = false)
	@JoinColumn(nullable = false)
	@NotNull
	private SalaryData salaryData;

	@Basic(optional = false)
	@Column(nullable = false)
	@NotNull
	private LocalDate date;

	@Basic(optional = false)
	@Column(nullable = false)
	@NotNull
	private EnumWorkShift workShift;

	@Basic(optional = false)
	@Column(nullable = false)
	@NotNull
	private Boolean wasPresent = Boolean.FALSE;

	SalaryAttendancedata() {}

	public SalaryAttendancedata(SalaryData salaryData, LocalDate date, EnumWorkShift workShift, Boolean wasPresent) {
		this.salaryData = salaryData;
		this.date = date;
		this.workShift = workShift;
		this.wasPresent = wasPresent;
	}

	public SalaryData getSalaryData() {
		return this.salaryData;
	}

	public LocalDate getDate() {
		return this.date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public EnumWorkShift getWorkShift() {
		return this.workShift;
	}

	public void setWorkShift(EnumWorkShift workShift) {
		this.workShift = workShift;
	}

	public Boolean getWasPresent() {
		return this.wasPresent;
	}

	public void setWasPresent(Boolean wasPresent) {
		this.wasPresent = wasPresent;
	}

}
