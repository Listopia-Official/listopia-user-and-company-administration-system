package florian_haas.lucas.database;

import java.math.BigDecimal;
import java.util.List;

import florian_haas.lucas.model.Item;

public interface ItemDAO extends ReadOnlyDAO<Item> {
	public List<Item> findItems(Long itemId, String name, String description, Integer itemsAvaible, BigDecimal pricePerItem,
			Boolean useId, Boolean useName, Boolean useDescription, Boolean useItemsAvaible, Boolean usePricePerItem,
			EnumQueryComparator idComparator, EnumQueryComparator nameComparator, EnumQueryComparator descriptionComparator,
			EnumQueryComparator itemsAvaibleComparator, EnumQueryComparator pricePerItemComparator);
}
