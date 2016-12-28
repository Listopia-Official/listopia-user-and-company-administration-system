package florian_haas.lucas.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-28T17:11:34.294+0100")
@StaticMetamodel(Attendancedata.class)
public class Attendancedata_ extends EntityBase_ {
	public static volatile SingularAttribute<Attendancedata, User> user;
	public static volatile SingularAttribute<Attendancedata, Boolean> isUserInState;
	public static volatile SingularAttribute<Attendancedata, Long> timePresentDay;
	public static volatile SingularAttribute<Attendancedata, Long> validTimeMissing;
	public static volatile SingularAttribute<Attendancedata, Stopwatch> timeIn;
	public static volatile SingularAttribute<Attendancedata, Stopwatch> timeOut;
	public static volatile ListAttribute<Attendancedata, AttendanceActivityLog> activityLogs;
	public static volatile ListAttribute<Attendancedata, AttendanceLog> attendanceLogs;
}
