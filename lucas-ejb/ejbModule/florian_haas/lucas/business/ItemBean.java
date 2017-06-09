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

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;
import florian_haas.lucas.security.*;
import florian_haas.lucas.util.Utils;
import florian_haas.lucas.validation.NotNullWarehouseRequired;

@Stateless
@ValidateOnExecution(type = ExecutableType.IMPLICIT)
@Secured
public class ItemBean implements ItemBeanLocal {

	@JPADAO
	@Inject
	private ItemDAO itemDao;

	@Inject
	@JPADAO
	private CompanyDAO companyDao;

	@EJB
	private GlobalDataBeanLocal globalData;

	@EJB
	private AccountBeanLocal accountBean;

	@Resource
	private Validator validator;

	@Override
	@RequiresPermissions(ITEM_SELL)
	public void sell(Map<Long, Integer> items, Long companyId, EnumPayType payType, String payComment, String exchangeComment) {
		Company company = companyDao.findById(companyId);
		Set<ConstraintViolation<GlobalData>> violations = validator.validate((GlobalData) globalData.getInstance(), NotNullWarehouseRequired.class);
		if (!violations.isEmpty()) throw new ConstraintViolationException(violations);
		Company warehouse = (Company) globalData.getWarehouse();
		items.forEach((itemId, count) -> {
			Item item = itemDao.findById(itemId);
			item.setItemsAvaible(item.getItemsAvaible() - count);
			PurchaseLog log = new PurchaseLog(company, LocalDateTime.now(), item, payType, count, item.getFictionalPricePerItem(),
					item.getRealPricePerItem());
			if (payType == EnumPayType.ACCOUNT) {
				accountBean.transaction(company.getAccount().getId(), warehouse.getAccount().getId(),
						item.getFictionalPricePerItem().multiply(new BigDecimal(count)), payComment);
				accountBean.exchangeFictionalCurrencyToReal(warehouse.getAccount().getId(), item.getRealPricePerItem(), Boolean.FALSE,
						exchangeComment);
			}
			item.addPurchaseLog(log);
			company.addPurchaseLog(log);
		});

	}

	@Override
	@RequiresPermissions(ITEM_FIND_ALL)
	public List<? extends ReadOnlyItem> findAll() {
		return itemDao.findAll();
	}

	@Override
	@RequiresPermissions(ITEM_FIND_BY_ID)
	public Item findById(Long itemId) {
		return itemDao.findById(itemId);
	}

	@Override
	@RequiresPermissions(ITEM_FIND_DYNAMIC)
	public List<? extends ReadOnlyItem> findItems(Long itemId, String name, String description, Integer itemsAvaible,
			BigDecimal fictionalPricePerItem, BigDecimal realPricePerItem, Boolean useId, Boolean useName, Boolean useDescription,
			Boolean useItemsAvaible, Boolean useFictionalPricePerItem, Boolean useRealPricePerItem, EnumQueryComparator idComparator,
			EnumQueryComparator nameComparator, EnumQueryComparator descriptionComparator, EnumQueryComparator itemsAvaibleComparator,
			EnumQueryComparator fictionalPricePerItemComparator, EnumQueryComparator realPricePerItemComparator) {
		return itemDao.findItems(itemId, name, description, itemsAvaible, fictionalPricePerItem, realPricePerItem, useId, useName, useDescription,
				useItemsAvaible, useFictionalPricePerItem, useRealPricePerItem, idComparator, nameComparator, descriptionComparator,
				itemsAvaibleComparator, fictionalPricePerItemComparator, realPricePerItemComparator);
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
	@RequiresPermissions(ITEM_SET_REAL_PRICE_PER_ITEM)
	public Boolean setRealPricePerItem(Long itemId, BigDecimal realPricePerItem) {
		Item item = itemDao.findById(itemId);
		if (Utils.isEqual(item.getRealPricePerItem(), realPricePerItem)) return Boolean.FALSE;
		item.setRealPricePerItem(realPricePerItem);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(ITEM_SET_FICTIONAL_PRICE_PER_ITEM)
	public Boolean setFictionalPricePerItem(Long itemId, BigDecimal fictionalPricePerItem) {
		Item item = itemDao.findById(itemId);
		if (Utils.isEqual(item.getFictionalPricePerItem(), fictionalPricePerItem)) return Boolean.FALSE;
		item.setFictionalPricePerItem(fictionalPricePerItem);
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
	public Long newItem(String name, String description, BigDecimal fictionalPrice, BigDecimal realPrice, Integer itemsAvaible) {
		checkIsItemNameUnique(name);
		Item item = new Item(name, description, fictionalPrice, realPrice, itemsAvaible);
		itemDao.persist(item);
		return item.getId();
	}

	private void checkIsItemNameUnique(String name) {
		if (!itemDao.isNameUnique(name)) throw new LucasException("The item name is used by another item");
	}

}
