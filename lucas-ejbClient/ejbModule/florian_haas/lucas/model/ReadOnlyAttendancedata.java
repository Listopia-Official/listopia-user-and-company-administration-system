package florian_haas.lucas.model;

import java.util.List;

public interface ReadOnlyAttendancedata extends ReadOnlyEntity {

	public ReadOnlyUser getUser();

	public Boolean getIsUserInState();

	public Long getTimePresentDay();

	public Long getValidTimeMissing();

	public List<? extends ReadOnlyAttendanceActivityLog> getActivityLogs();

	public List<? extends ReadOnlyAttendanceLog> getAttendanceLogs();

}
