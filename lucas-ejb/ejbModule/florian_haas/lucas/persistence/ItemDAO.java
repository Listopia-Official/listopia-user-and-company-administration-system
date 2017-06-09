package florian_haas.lucas.persistence;

import java.math.BigDecimal;
import java.util.List;

import florian_haas.lucas.model.Item;

public interface ItemDAO extends DAO<Item> {
	public List<Item> findItems(Long itemId, String name, String description, Integer itemsAvaible, BigDecimal fictionalPricePerItem,
			BigDecimal realPricePerItem, Boolean useId, Boolean useName, Boolean useDescription, Boolean useItemsAvaible,
			Boolean useFictionalPricePerItem, Boolean useRealPricePerItem, EnumQueryComparator idComparator, EnumQueryComparator nameComparator,
			EnumQueryComparator descriptionComparator, EnumQueryComparator itemsAvaibleComparator,
			EnumQueryComparator fictionalPricePerItemComparator, EnumQueryComparator realPricePerItemComparator);

	public Boolean isNameUnique(String name);
}
