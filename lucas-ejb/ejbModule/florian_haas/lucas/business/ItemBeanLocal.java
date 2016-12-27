package florian_haas.lucas.business;

import java.math.BigDecimal;
import java.util.*;

import javax.ejb.Local;

import florian_haas.lucas.database.EnumQueryComparator;
import florian_haas.lucas.model.*;

@Local
public interface ItemBeanLocal {

	public void sell(Map<Long, Integer> items, Long companyId, EnumPayType payType);

	public List<Item> findAll();

	public Item findById(Long itemId);

	public List<Item> findItems(Long id, String name, String description, Integer itemsAvaible, BigDecimal pricePerItem,
			Boolean hasToBeOrdered, Boolean useId, Boolean useName, Boolean useDescription, Boolean useItemsAvaible,
			Boolean usePricePerItem, Boolean useHasToBeOrdered, EnumQueryComparator idComparator, EnumQueryComparator nameComparator,
			EnumQueryComparator descriptionComparator, EnumQueryComparator itemsAvaibleComparator,
			EnumQueryComparator pricePerItemComparator);

	public Item setName(Long itemId, String name);

	public Item setDescription(Long itemId, String description);

	public Item setItemsAvaible(Long itemId, Integer itemsAvaible);

	public Item setPricePerItem(Long itemId, BigDecimal pricePerItem);
}
