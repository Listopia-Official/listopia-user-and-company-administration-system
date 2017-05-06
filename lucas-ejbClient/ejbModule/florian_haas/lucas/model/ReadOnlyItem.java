package florian_haas.lucas.model;

import java.math.BigDecimal;
import java.util.List;

public interface ReadOnlyItem extends ReadOnlyEntity {

	public String getName();

	public String getDescription();

	public Integer getItemsAvaible();

	public BigDecimal getPricePerItem();

	public Boolean getHasToBeOrdered();

	public List<? extends ReadOnlyPurchaseLog> getPurchaseLogs();
}
