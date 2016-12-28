package florian_haas.lucas.model;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-28T17:36:20.298+0100")
@StaticMetamodel(Item.class)
public class Item_ extends EntityBase_ {
	public static volatile SingularAttribute<Item, String> name;
	public static volatile SingularAttribute<Item, String> description;
	public static volatile SingularAttribute<Item, Integer> itemsAvaible;
	public static volatile SingularAttribute<Item, BigDecimal> pricePerItem;
	public static volatile ListAttribute<Item, PurchaseLog> purchaseLogs;
}
