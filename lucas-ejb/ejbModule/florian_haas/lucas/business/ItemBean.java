package florian_haas.lucas.business;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import javax.ejb.*;
import javax.inject.Inject;
import javax.validation.executable.*;

import florian_haas.lucas.database.*;
import florian_haas.lucas.model.*;
import florian_haas.lucas.model.validation.ValidEntityId;
import florian_haas.lucas.util.validation.*;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@ValidateOnExecution(type = ExecutableType.IMPLICIT)
public class ItemBean implements ItemBeanLocal {

	@JPADAO
	@Inject
	private ItemDAO itemDao;

	@JPADAO
	@Inject
	private CompanyDAO companyDao;

	@EJB
	private AccountBeanLocal accountBean;

	// TODO check for invalid item id's
	@Override
	public void sell(Map<Long, @TypeNotNull @TypeMin(1) Integer> items, @ValidEntityId(entityClass = Company.class) Long companyId,
			@ValidEntityId(entityClass = Company.class) Long warehouseId, EnumPayType payType) {
		Company company = companyDao.findById(companyId);
		Company warehouse = companyDao.findById(warehouseId);
		items.forEach((itemId, count) -> {
			Item item = itemDao.findById(itemId);
			item.setItemsAvaible(item.getItemsAvaible() - count);
			PurchaseLog log = new PurchaseLog(company, LocalDateTime.now(), item, payType, count, item.getPricePerItem());
			if (payType == EnumPayType.ACCOUNT) {
				accountBean.transaction(company.getAccount().getId(), warehouse.getAccount().getId(),
						item.getPricePerItem().multiply(new BigDecimal(count)),
						company.getName() + " bought " + count + " " + item.getName() + " at warehouse");
			}
			item.addPurchaseLog(log);
			company.addPurchaseLog(log);
		});

	}

	@Override
	public List<Item> findAll() {
		return itemDao.findAll();
	}

	@Override
	public Item findById(@ValidEntityId(entityClass = Item.class) Long itemId) {
		return itemDao.findById(itemId);
	}

	@Override
	public List<Item> findItems(Long id, String name, String description, Integer itemsAvaible, BigDecimal pricePerItem, Boolean useId,
			Boolean useName, Boolean useDescription, Boolean useItemsAvaible, Boolean usePricePerItem, EnumQueryComparator idComparator,
			EnumQueryComparator nameComparator, EnumQueryComparator descriptionComparator, EnumQueryComparator itemsAvaibleComparator,
			EnumQueryComparator pricePerItemComparator) {
		return itemDao.findItems(id, name, description, itemsAvaible, pricePerItem, useId, useName, useDescription, useItemsAvaible, usePricePerItem,
				idComparator, nameComparator, descriptionComparator, itemsAvaibleComparator, pricePerItemComparator);
	}

	@Override
	public Item setName(@ValidEntityId(entityClass = Item.class) Long itemId, @NotBlankString String name) {
		Item item = itemDao.findById(itemId);
		item.setName(name);
		return item;
	}

	@Override
	public Item setDescription(@ValidEntityId(entityClass = Item.class) Long itemId, @NotBlankString String description) {
		Item item = itemDao.findById(itemId);
		item.setDescription(description);
		return item;
	}

	@Override
	public Item setPricePerItem(@ValidEntityId(entityClass = Item.class) Long itemId, BigDecimal pricePerItem) {
		Item item = itemDao.findById(itemId);
		item.setPricePerItem(pricePerItem);
		return item;
	}

	@Override
	public Item addItemsAvaible(@ValidEntityId(entityClass = Item.class) Long itemId, Integer amount) {
		Item item = itemDao.findById(itemId);
		item.setItemsAvaible(item.getItemsAvaible() + amount);
		return item;
	}

	@Override
	public Item subItemsAvaible(@ValidEntityId(entityClass = Item.class) Long itemId, Integer amount) {
		Item item = itemDao.findById(itemId);
		item.setItemsAvaible(item.getItemsAvaible() - amount);
		return item;
	}

}
