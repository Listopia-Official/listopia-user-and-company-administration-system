package florian_haas.lucas.model;

import java.time.LocalDate;
import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import florian_haas.lucas.util.validation.TypeNotNull;

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

	@ElementCollection(fetch = FetchType.EAGER)
	@Column(nullable = false)
	@NotNull
	private Map<EnumWorkShift, @TypeNotNull Boolean> workShifts = new HashMap<>();

	SalaryAttendancedata() {}

	public SalaryAttendancedata(SalaryData salaryData, LocalDate date) {
		this.salaryData = salaryData;
		this.date = date;
		salaryData.getWorkShifts().forEach(shift -> {
			workShifts.put(shift, Boolean.FALSE);
		});
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

	public Map<EnumWorkShift, Boolean> getWorkShifts() {
		return Collections.unmodifiableMap(this.workShifts);
	}

	public void setWasPresent(EnumWorkShift shift, Boolean wasPresent) {
		if (workShifts.get(shift) != null) {
			workShifts.replace(shift, wasPresent);
		}
	}

}
