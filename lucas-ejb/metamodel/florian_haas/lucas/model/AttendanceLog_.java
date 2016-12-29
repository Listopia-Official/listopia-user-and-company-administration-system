package florian_haas.lucas.model;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-29T12:13:50.717+0100")
@StaticMetamodel(AttendanceLog.class)
public class AttendanceLog_ extends EntityBase_ {
	public static volatile SingularAttribute<AttendanceLog, Attendancedata> attendancedata;
	public static volatile SingularAttribute<AttendanceLog, LocalDate> date;
	public static volatile SingularAttribute<AttendanceLog, Long> timePresent;
	public static volatile SingularAttribute<AttendanceLog, Long> minTimePresent;
	public static volatile SingularAttribute<AttendanceLog, Long> validTimeMissing;
}
