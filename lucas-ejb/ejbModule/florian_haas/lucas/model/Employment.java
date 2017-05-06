package florian_haas.lucas.model;

import java.util.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import florian_haas.lucas.validation.*;

@Entity
public class Employment extends EntityBase implements ReadOnlyEmployment {

	private static final long serialVersionUID = 8794256029793389169L;

	@ManyToOne(optional = false)
	@JoinColumn(nullable = false)
	@NotNull
	private User user;

	@ManyToOne(optional = false)
	@JoinColumn(nullable = false)
	@NotNull
	private Job job;

	@ElementCollection(fetch = FetchType.EAGER)
	@NotNull
	private Set<@TypeNotNull EnumWorkShift> workShifts = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "employment")
	@NotNull
	@UniqueValue(fieldName = "date")
	@Valid
	private List<@TypeNotNull SalaryAttendancedata> attendancedata = new ArrayList<>();

	Employment() {}

	public Employment(User user, Job job, Set<EnumWorkShift> workShifts) {
		this.user = user;
		this.job = job;
		if (workShifts != null) this.workShifts.addAll(workShifts);
	}

	public User getUser() {
		return this.user;
	}

	public Job getJob() {
		return this.job;
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

	public Set<EnumWorkShift> getWorkShifts() {
		return Collections.unmodifiableSet(this.workShifts);
	}

	public Boolean addWorkShift(EnumWorkShift workShift) {
		return this.workShifts.add(workShift);
	}

	public Boolean removeWorkShift(EnumWorkShift workShift) {
		return this.workShifts.remove(workShift);
	}

}
