package florian_haas.lucas.persistence;

import java.math.BigDecimal;
import java.util.List;

import florian_haas.lucas.model.Item;
import florian_haas.lucas.persistence.EnumQueryComparator;

public interface ItemDAO extends DAO<Item> {
	public List<Item> findItems(Long itemId, String name, String description, Integer itemsAvaible, BigDecimal pricePerItem, Boolean useId,
			Boolean useName, Boolean useDescription, Boolean useItemsAvaible, Boolean usePricePerItem, EnumQueryComparator idComparator,
			EnumQueryComparator nameComparator, EnumQueryComparator descriptionComparator, EnumQueryComparator itemsAvaibleComparator,
			EnumQueryComparator pricePerItemComparator);

	public Boolean isNameUnique(String name);
}
