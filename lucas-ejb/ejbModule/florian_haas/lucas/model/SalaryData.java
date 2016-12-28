package florian_haas.lucas.model;

import java.util.*;

import javax.persistence.*;

@Entity
public class SalaryData extends EntityBase {

	private static final long serialVersionUID = 3541160263919447176L;

	@OneToOne(mappedBy = "salaryData", optional = false)
	@JoinColumn(nullable = false)
	private Employment employment;

	@Basic(optional = false)
	@Column(nullable = false)
	private EnumSalaryClass salaryClass;

	@ElementCollection
	private Set<EnumWorkShift> workShifts = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "salaryData")
	private List<SalaryAttendancedata> attendancedata = new ArrayList<>();

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

	public boolean addWorkShift(EnumWorkShift workShift) {
		return this.workShifts.add(workShift);
	}

	public boolean removeWorkShift(EnumWorkShift workShift) {
		return this.workShifts.remove(workShift);
	}

	public List<SalaryAttendancedata> getAttendancedata() {
		return Collections.unmodifiableList(attendancedata);
	}

	public boolean addAttendancedata(SalaryAttendancedata attendancedata) {
		return this.attendancedata.add(attendancedata);
	}

	public boolean removeAttendancedata(SalaryAttendancedata attendancedata) {
		return this.attendancedata.remove(attendancedata);
	}
}
