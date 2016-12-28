package florian_haas.lucas.model;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-28T17:13:06.323+0100")
@StaticMetamodel(AttendanceActivityLog.class)
public class AttendanceActivityLog_ extends EntityBase_ {
	public static volatile SingularAttribute<AttendanceActivityLog, Attendancedata> attendancedata;
	public static volatile SingularAttribute<AttendanceActivityLog, LocalDateTime> dateTime;
	public static volatile SingularAttribute<AttendanceActivityLog, EnumAttendanceAction> action;
	public static volatile SingularAttribute<AttendanceActivityLog, Long> time;
}
