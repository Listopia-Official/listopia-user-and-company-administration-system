package florian_haas.lucas.model;

import java.util.*;

public interface ReadOnlyEmployment extends ReadOnlyEntity {

	public ReadOnlyUser getUser();

	public ReadOnlyJob getJob();

	public List<? extends ReadOnlySalaryAttendancedata> getAttendancedata();

	public Set<EnumWorkShift> getWorkShifts();
}
