package florian_haas.lucas.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-28T17:34:12.531+0100")
@StaticMetamodel(Taxdata.class)
public class Taxdata_ extends EntityBase_ {
	public static volatile SingularAttribute<Taxdata, Company> company;
	public static volatile SingularAttribute<Taxdata, LocalDate> date;
	public static volatile SingularAttribute<Taxdata, BigDecimal> incomings;
	public static volatile SingularAttribute<Taxdata, BigDecimal> outgoings;
}
