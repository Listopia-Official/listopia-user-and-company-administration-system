package florian_haas.lucas.business;

import java.math.BigDecimal;
import java.util.*;

import javax.ejb.Local;
import javax.validation.constraints.*;

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;
import florian_haas.lucas.validation.*;

@Local
public interface ItemBeanLocal {

	public Long newItem(@NotNull @NotBlankString String name, @NotBlankString String description, @ValidItemPrice BigDecimal fictionalPrice,
			@ValidItemPrice BigDecimal realPrice, @NotNull @Min(0) Integer itemsAvaible);

	public void sell(@ValidEntityIdMapKey(entityClass = ReadOnlyItem.class) Map<Long, @TypeNotNull @TypeMin(1) Integer> items,
			@ValidEntityId(entityClass = ReadOnlyCompany.class) Long companyId, @NotNull EnumPayType payType, @ShortComment String payComment,
			@ShortComment String exchangeBackComment);

	public List<? extends ReadOnlyItem> findAll();

	public ReadOnlyItem findById(@ValidEntityId(entityClass = ReadOnlyItem.class) Long itemId);

	public List<? extends ReadOnlyItem> findItems(@NotNull Long id, @NotNull String name, @NotNull String description, @NotNull Integer itemsAvaible,
			@NotNull BigDecimal fictionalPricePerItem, @NotNull BigDecimal realPricePerItem, @NotNull Boolean useId, @NotNull Boolean useName,
			@NotNull Boolean useDescription, @NotNull Boolean useItemsAvaible, @NotNull Boolean useFictionalPricePerItem,
			@NotNull Boolean useRealPricePerItem, @QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator idComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.TEXT) EnumQueryComparator nameComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.TEXT) EnumQueryComparator descriptionComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator itemsAvaibleComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator fictionalPricePerItemComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator realPricePerItemComparator);

	public Boolean setName(@ValidEntityId(entityClass = ReadOnlyItem.class) Long itemId, @NotNull @NotBlankString String name);

	public Boolean setDescription(@ValidEntityId(entityClass = ReadOnlyItem.class) Long itemId, @NotBlankString String description);

	public Boolean addItemsAvaible(@ValidEntityId(entityClass = ReadOnlyItem.class) Long itemId, @NotNull @Min(1) Integer amount);

	public Boolean subItemsAvaible(@ValidEntityId(entityClass = ReadOnlyItem.class) Long itemId, @NotNull @Min(1) Integer amount);

	public Boolean setFictionalPricePerItem(@ValidEntityId(entityClass = ReadOnlyItem.class) Long itemId, @ValidItemPrice BigDecimal pricePerItem);

	public Boolean setRealPricePerItem(@ValidEntityId(entityClass = ReadOnlyItem.class) Long itemId, @ValidItemPrice BigDecimal pricePerItem);
}
