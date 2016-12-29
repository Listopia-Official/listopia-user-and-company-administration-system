package florian_haas.lucas.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-29T12:13:50.821+0100")
@StaticMetamodel(PurchaseLog.class)
public class PurchaseLog_ extends EntityBase_ {
	public static volatile SingularAttribute<PurchaseLog, Company> company;
	public static volatile SingularAttribute<PurchaseLog, Item> item;
	public static volatile SingularAttribute<PurchaseLog, LocalDateTime> dateTime;
	public static volatile SingularAttribute<PurchaseLog, EnumPayType> payType;
	public static volatile SingularAttribute<PurchaseLog, Integer> count;
	public static volatile SingularAttribute<PurchaseLog, BigDecimal> currentPrice;
}
