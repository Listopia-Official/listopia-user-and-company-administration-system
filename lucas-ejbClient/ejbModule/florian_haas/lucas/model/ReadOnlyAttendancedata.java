package florian_haas.lucas.model;

import java.util.List;

public interface ReadOnlyAttendancedata extends ReadOnlyEntity {

	public ReadOnlyUser getUser();

	public Boolean getIsUserInState();

	public Long getRawTimePresentDay();

	public Long getTimePresentDay();

	public List<? extends ReadOnlyAttendanceActivityLog> getActivityLogs();

}
