package florian_haas.lucas.business;

import static florian_haas.lucas.security.EnumPermission.*;

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
import florian_haas.lucas.model.validation.NotNullWarehouseRequired;
import florian_haas.lucas.security.*;
import florian_haas.lucas.util.Utils;

@Stateless
@ValidateOnExecution(type = ExecutableType.IMPLICIT)
@Secured
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
	@RequiresPermissions(ITEM_SELL)
	public void sell(Map<Long, Integer> items, Long companyId, EnumPayType payType) {
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
	@RequiresPermissions(ITEM_FIND_ALL)
	public List<Item> findAll() {
		return itemDao.findAll();
	}

	@Override
	@RequiresPermissions(ITEM_FIND_BY_ID)
	public Item findById(Long itemId) {
		return itemDao.findById(itemId);
	}

	@Override
	@RequiresPermissions(ITEM_FIND_DYNAMIC)
	public List<Item> findItems(Long id, String name, String description, Integer itemsAvaible, BigDecimal pricePerItem, Boolean useId,
			Boolean useName, Boolean useDescription, Boolean useItemsAvaible, Boolean usePricePerItem, EnumQueryComparator idComparator,
			EnumQueryComparator nameComparator, EnumQueryComparator descriptionComparator, EnumQueryComparator itemsAvaibleComparator,
			EnumQueryComparator pricePerItemComparator) {
		return itemDao.findItems(id, name, description, itemsAvaible, pricePerItem, useId, useName, useDescription, useItemsAvaible, usePricePerItem,
				idComparator, nameComparator, descriptionComparator, itemsAvaibleComparator, pricePerItemComparator);
	}

	@Override
	@RequiresPermissions(ITEM_SET_NAME)
	public Boolean setName(Long itemId, String name) {
		Item item = itemDao.findById(itemId);
		if (item.getName().equals(name)) return Boolean.FALSE;
		checkIsItemNameUnique(name);
		item.setName(name);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(ITEM_SET_DESCRIPTION)
	public Boolean setDescription(Long itemId, String description) {
		Item item = itemDao.findById(itemId);
		if (item.getDescription().equals(description)) return Boolean.FALSE;
		item.setDescription(description);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(ITEM_SET_PRICE_PER_ITEM)
	public Boolean setPricePerItem(Long itemId, BigDecimal pricePerItem) {
		Item item = itemDao.findById(itemId);
		if (Utils.isEqual(item.getPricePerItem(), pricePerItem)) return Boolean.FALSE;
		item.setPricePerItem(pricePerItem);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(ITEM_ADD_ITEMS_AVAIBLE)
	public Boolean addItemsAvaible(Long itemId, Integer amount) {
		Item item = itemDao.findById(itemId);
		item.setItemsAvaible(item.getItemsAvaible() + amount);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(ITEM_SUB_ITEMS_AVAIBLE)
	public Boolean subItemsAvaible(Long itemId, Integer amount) {
		Item item = itemDao.findById(itemId);
		item.setItemsAvaible(item.getItemsAvaible() - amount);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(ITEM_CREATE)
	public Long newItem(String name, String description, BigDecimal price, Integer itemsAvaible) {
		checkIsItemNameUnique(name);
		Item item = new Item(name, description, price, itemsAvaible);
		itemDao.persist(item);
		return item.getId();
	}

	private void checkIsItemNameUnique(String name) {
		if (!itemDao.isNameUnique(name)) throw new LucasException("The item name is used by another item");
	}

}
