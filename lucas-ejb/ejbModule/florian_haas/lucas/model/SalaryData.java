package florian_haas.lucas.model;

import java.time.LocalDate;
import java.util.*;

import florian_haas.lucas.util.Utils;

public class SalaryData extends EntityBase {

	private static final long serialVersionUID = 3541160263919447176L;

	private Employment employment;
	private EnumSalaryClass salaryClass;
	private Set<EnumWorkShift> workShifts = new HashSet<>();
	private Map<LocalDate, Map<EnumWorkShift, Boolean>> attendancedata = new HashMap<>();

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

	public Map<LocalDate, Map<EnumWorkShift, Boolean>> getAttendancedata() {
		return Utils.deepUnmodifiableMap(attendancedata);
	}

	public void addAttendanceEntry(LocalDate date, Map<EnumWorkShift, Boolean> shifts) {
		attendancedata.put(date, shifts);
	}

	public void setAttendanceEntry(LocalDate date, Map<EnumWorkShift, Boolean> shifts) {
		attendancedata.replace(date, shifts);
	}

	public void removeAttendanceEntry(LocalDate date) {
		this.attendancedata.remove(date);
	}

}
