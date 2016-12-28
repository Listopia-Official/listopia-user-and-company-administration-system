package florian_haas.lucas.model;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-28T15:17:34.725+0100")
@StaticMetamodel(Visa.class)
public class Visa_ extends EntityBase_ {
	public static volatile SingularAttribute<Visa, User> user;
	public static volatile SingularAttribute<Visa, Boolean> activated;
	public static volatile SingularAttribute<Visa, LocalDate> validDay;
}
