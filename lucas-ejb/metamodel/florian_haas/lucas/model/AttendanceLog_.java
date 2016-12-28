package florian_haas.lucas.model;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-28T17:12:51.904+0100")
@StaticMetamodel(AttendanceLog.class)
public class AttendanceLog_ extends EntityBase_ {
	public static volatile SingularAttribute<AttendanceLog, Attendancedata> attendancedata;
	public static volatile SingularAttribute<AttendanceLog, Long> timePresent;
	public static volatile SingularAttribute<AttendanceLog, Long> minTimePresent;
	public static volatile SingularAttribute<AttendanceLog, Long> validTimeMissing;
	public static volatile SingularAttribute<AttendanceLog, LocalDate> date;
}
