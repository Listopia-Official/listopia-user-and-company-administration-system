package florian_haas.lucas.model;

import java.time.LocalDate;

public interface ReadOnlyAttendanceLog extends ReadOnlyEntity {

	public ReadOnlyAttendancedata getAttendancedata();

	public LocalDate getDate();

	public Long getTimePresent();

	public Long getMinTimePresent();

	public Long getValidTimeMissing();

	public Boolean hasMetRequirements();

}
