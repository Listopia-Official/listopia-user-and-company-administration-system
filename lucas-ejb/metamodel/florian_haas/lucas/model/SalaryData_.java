package florian_haas.lucas.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-28T18:12:41.820+0100")
@StaticMetamodel(SalaryData.class)
public class SalaryData_ extends EntityBase_ {
	public static volatile SingularAttribute<SalaryData, Employment> employment;
	public static volatile SingularAttribute<SalaryData, EnumSalaryClass> salaryClass;
	public static volatile SetAttribute<SalaryData, EnumWorkShift> workShifts;
	public static volatile ListAttribute<SalaryData, SalaryAttendancedata> attendancedata;
}
