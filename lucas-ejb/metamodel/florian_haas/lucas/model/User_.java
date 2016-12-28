package florian_haas.lucas.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-28T17:25:09.788+0100")
@StaticMetamodel(User.class)
public class User_ extends AccountOwner_ {
	public static volatile SingularAttribute<User, String> forename;
	public static volatile SingularAttribute<User, String> surname;
	public static volatile SingularAttribute<User, Integer> schoolGrade;
	public static volatile SingularAttribute<User, String> schoolClass;
	public static volatile ListAttribute<User, String> ranks;
	public static volatile SingularAttribute<User, Attendancedata> attendancedata;
	public static volatile SetAttribute<User, UserCard> userCards;
	public static volatile SetAttribute<User, Visa> visa;
	public static volatile SetAttribute<User, Employment> employments;
}
