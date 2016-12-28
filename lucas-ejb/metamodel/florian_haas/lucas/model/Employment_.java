package florian_haas.lucas.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-28T17:14:10.249+0100")
@StaticMetamodel(Employment.class)
public class Employment_ extends EntityBase_ {
	public static volatile SingularAttribute<Employment, User> user;
	public static volatile SingularAttribute<Employment, Company> company;
	public static volatile SingularAttribute<Employment, EnumEmployeePosition> position;
	public static volatile SingularAttribute<Employment, SalaryData> salaryData;
}
