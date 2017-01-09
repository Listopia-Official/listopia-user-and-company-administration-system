package florian_haas.lucas.business;

import java.math.BigDecimal;
import java.util.*;

import javax.ejb.Local;
import javax.validation.constraints.*;

import florian_haas.lucas.database.EnumQueryComparator;
import florian_haas.lucas.model.*;
import florian_haas.lucas.model.validation.*;
import florian_haas.lucas.util.validation.*;

@Local
public interface ItemBeanLocal {

	public void sell(Map<Long, @TypeNotNull @TypeMin(1) Integer> items, @ValidEntityId(entityClass = Company.class) Long companyId,
			@NotNull EnumPayType payType);

	public List<Item> findAll();

	public Item findById(@ValidEntityId(entityClass = Item.class) Long itemId);

	public List<Item> findItems(@NotNull Long id, @NotNull String name, @NotNull String description, @NotNull Integer itemsAvaible,
			@NotNull BigDecimal pricePerItem, @NotNull Boolean useId, @NotNull Boolean useName, @NotNull Boolean useDescription,
			@NotNull Boolean useItemsAvaible, @NotNull Boolean usePricePerItem, EnumQueryComparator idComparator, EnumQueryComparator nameComparator,
			EnumQueryComparator descriptionComparator, EnumQueryComparator itemsAvaibleComparator, EnumQueryComparator pricePerItemComparator);

	public Item setName(@ValidEntityId(entityClass = Item.class) Long itemId, @NotNull @NotBlankString String name);

	public Item setDescription(@ValidEntityId(entityClass = Item.class) Long itemId, @NotBlankString String description);

	public Item addItemsAvaible(@ValidEntityId(entityClass = Item.class) Long itemId, @NotNull @Min(1) Integer amount);

	public Item subItemsAvaible(@ValidEntityId(entityClass = Item.class) Long itemId, @NotNull @Min(1) Integer amount);

	public Item setPricePerItem(@ValidEntityId(entityClass = Item.class) Long itemId, @ValidItemPrice BigDecimal pricePerItem);
}
