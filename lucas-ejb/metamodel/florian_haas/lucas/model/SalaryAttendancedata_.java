package florian_haas.lucas.model;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-28T18:10:06.354+0100")
@StaticMetamodel(SalaryAttendancedata.class)
public class SalaryAttendancedata_ extends EntityBase_ {
	public static volatile SingularAttribute<SalaryAttendancedata, SalaryData> salaryData;
	public static volatile SingularAttribute<SalaryAttendancedata, LocalDate> date;
	public static volatile SingularAttribute<SalaryAttendancedata, EnumWorkShift> workShift;
	public static volatile SingularAttribute<SalaryAttendancedata, Boolean> wasPresent;
}
