package florian_haas.lucas.model;

import java.util.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import florian_haas.lucas.model.validation.UniqueValue;
import florian_haas.lucas.util.validation.TypeNotNull;

@Entity
public class SalaryData extends EntityBase {

	private static final long serialVersionUID = 3541160263919447176L;

	@OneToOne(mappedBy = "salaryData", optional = false)
	@JoinColumn(nullable = false)
	@NotNull
	private Employment employment;

	@Basic(optional = false)
	@Column(nullable = false)
	@NotNull
	private EnumSalaryClass salaryClass;

	@ElementCollection
	@NotNull
	private Set<@TypeNotNull EnumWorkShift> workShifts = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "salaryData")
	@NotNull
	@UniqueValue(fieldName = "date")
	@Valid
	private List<@TypeNotNull SalaryAttendancedata> attendancedata = new ArrayList<>();

	SalaryData() {}

	public SalaryData(Employment employment, EnumSalaryClass salaryClass, EnumWorkShift... workShifts) {
		this.employment = employment;
		this.salaryClass = salaryClass;
		this.workShifts.addAll(Arrays.asList(workShifts));
	}

	public EnumSalaryClass getSalaryClass() {
		return this.salaryClass;
	}

	public void setSalaryClass(EnumSalaryClass salaryClass) {
		this.salaryClass = salaryClass;
	}

	public Employment getEmployment() {
		return this.employment;
	}

	public Set<EnumWorkShift> getWorkShifts() {
		return Collections.unmodifiableSet(this.workShifts);
	}

	public Boolean addWorkShift(EnumWorkShift workShift) {
		return this.workShifts.add(workShift);
	}

	public Boolean removeWorkShift(EnumWorkShift workShift) {
		return this.workShifts.remove(workShift);
	}

	public List<SalaryAttendancedata> getAttendancedata() {
		return Collections.unmodifiableList(attendancedata);
	}

	public Boolean addAttendancedata(SalaryAttendancedata attendancedata) {
		return this.attendancedata.add(attendancedata);
	}

	public Boolean removeAttendancedata(SalaryAttendancedata attendancedata) {
		return this.attendancedata.remove(attendancedata);
	}
}
