package florian_haas.lucas.model;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-28T17:57:31.667+0100")
@StaticMetamodel(GlobalData.class)
public class GlobalData_ extends EntityBase_ {
	public static volatile MapAttribute<GlobalData, EnumSalaryClass, BigDecimal> salaries;
	public static volatile SingularAttribute<GlobalData, Long> minTimePresent;
}
