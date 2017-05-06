package florian_haas.lucas.model;

import java.time.LocalDateTime;

public interface ReadOnlyAttendanceActivityLog extends ReadOnlyEntity {

	public ReadOnlyAttendancedata getAttendancedata();

	public LocalDateTime getDateTime();

	public EnumAttendanceAction getAction();

	public Long getTime();

}
