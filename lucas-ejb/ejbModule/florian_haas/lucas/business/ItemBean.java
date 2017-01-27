package florian_haas.lucas.business;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.validation.*;
import javax.validation.executable.*;

import florian_haas.lucas.database.*;
import florian_haas.lucas.model.*;
import florian_haas.lucas.model.validation.*;
import florian_haas.lucas.util.Utils;
import florian_haas.lucas.util.validation.*;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@ValidateOnExecution(type = ExecutableType.IMPLICIT)
public class ItemBean implements ItemBeanLocal {

	@JPADAO
	@Inject
	private ItemDAO itemDao;

	@EJB
	private CompanyBeanLocal companyBean;

	@EJB
	private GlobalDataBeanLocal globalData;

	@EJB
	private AccountBeanLocal accountBean;

	@Resource
	private Validator validator;

	@Override
	public void sell(@ValidEntityIdMapKey(entityClass = Item.class) Map<Long, @TypeNotNull @TypeMin(1) Integer> items,
			@ValidEntityId(entityClass = Company.class) Long companyId, EnumPayType payType) {
		Company company = companyBean.findById(companyId);
		GlobalData data = globalData.getInstance();
		Set<ConstraintViolation<GlobalData>> violations = validator.validate(data, NotNullWarehouseRequired.class);
		if (!violations.isEmpty()) throw new ConstraintViolationException(violations);
		Company warehouse = globalData.getWarehouse();
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
	public Boolean setName(@ValidEntityId(entityClass = Item.class) Long itemId, @NotBlankString String name) {
		Item item = itemDao.findById(itemId);
		if (item.getName().equals(name)) return Boolean.FALSE;
		item.setName(name);
		return Boolean.TRUE;
	}

	@Override
	public Boolean setDescription(@ValidEntityId(entityClass = Item.class) Long itemId, @NotBlankString String description) {
		Item item = itemDao.findById(itemId);
		if (item.getDescription().equals(description)) return Boolean.FALSE;
		item.setDescription(description);
		return Boolean.TRUE;
	}

	@Override
	public Boolean setPricePerItem(@ValidEntityId(entityClass = Item.class) Long itemId, BigDecimal pricePerItem) {
		Item item = itemDao.findById(itemId);
		if (Utils.isEqual(item.getPricePerItem(), pricePerItem)) return Boolean.FALSE;
		item.setPricePerItem(pricePerItem);
		return Boolean.TRUE;
	}

	@Override
	public Boolean addItemsAvaible(@ValidEntityId(entityClass = Item.class) Long itemId, Integer amount) {
		Item item = itemDao.findById(itemId);
		item.setItemsAvaible(item.getItemsAvaible() + amount);
		return Boolean.TRUE;
	}

	@Override
	public Boolean subItemsAvaible(@ValidEntityId(entityClass = Item.class) Long itemId, Integer amount) {
		Item item = itemDao.findById(itemId);
		item.setItemsAvaible(item.getItemsAvaible() - amount);
		return Boolean.TRUE;
	}

	@Override
	public Long newItem(@NotBlankString String name, @NotBlankString String description, BigDecimal price, Integer itemsAvaible) {
		Item item = new Item(name, description, price, itemsAvaible);
		itemDao.persist(item);
		return item.getId();
	}

}
