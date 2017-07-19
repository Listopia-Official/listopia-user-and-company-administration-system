package florian_haas.lucas.business;

import static florian_haas.lucas.security.EnumPermission.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.validation.Validator;
import javax.validation.executable.*;

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;
import florian_haas.lucas.security.*;
import florian_haas.lucas.util.Utils;

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

	@EJB
	private LoginBeanLocal loginBean;

	@Resource
	private Validator validator;

	@Override
	@RequiresPermissions(ITEM_SELL)
	public void sell(Map<Long, Integer> items, Long companyId, EnumPayType payType, String payComment, String exchangeComment) {
		Company company = companyDao.findById(companyId);
		if (globalData.getWarehouse() == null) throw new LucasException("No warehouse was specified!", ItemBeanLocal.NO_WAREHOUSE_EXCEPTION_MARKER);
		Company warehouse = (Company) globalData.getWarehouse();
		BigDecimal toPay = BigDecimal.ZERO;
		BigDecimal toExchangeBack = BigDecimal.ZERO;
		for (Long itemId : items.keySet()) {
			Integer count = items.get(itemId);
			Item item = itemDao.findById(itemId);
			Integer availableCount = item.getItemsAvailable();
			if ((availableCount - count) < 0) throw new LucasException("The amount of items is greather than the count of available items",
					ItemBeanLocal.ILLEGAL_AMOUNT_EXCEPTION_MARKER, item.getId(), availableCount, count);
			item.setItemsAvailable(availableCount - count);
			PurchaseLog log = new PurchaseLog(company, LocalDateTime.now(), item, payType, count, item.getFictionalPricePerItem(),
					item.getRealPricePerItem());
			if (payType == EnumPayType.ACCOUNT) {
				toPay = toPay.add(item.getFictionalPricePerItem().multiply(new BigDecimal(count)));
				toExchangeBack = toExchangeBack.add(item.getRealPricePerItem().multiply(new BigDecimal(count)));
			}
			item.addPurchaseLog(log);
			company.addPurchaseLog(log);
		}
		if (payType == EnumPayType.ACCOUNT) {
			accountBean.transaction(company.getAccount().getId(), warehouse.getAccount().getId(), toPay, payComment);
			accountBean.exchangeFictionalCurrencyToReal(warehouse.getAccount().getId(), toExchangeBack, Boolean.FALSE, exchangeComment);
		}

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
			BigDecimal fictionalPricePerItem, BigDecimal realPricePerItem, Boolean hasToBeOrdered, Boolean useId, Boolean useName,
			Boolean useDescription, Boolean useItemsAvaible, Boolean useFictionalPricePerItem, Boolean useRealPricePerItem, Boolean useHasToBeOrdered,
			EnumQueryComparator idComparator, EnumQueryComparator nameComparator, EnumQueryComparator descriptionComparator,
			EnumQueryComparator itemsAvaibleComparator, EnumQueryComparator fictionalPricePerItemComparator,
			EnumQueryComparator realPricePerItemComparator) {
		return (!useId && !useName && !useDescription && !useItemsAvaible && !useFictionalPricePerItem && !useFictionalPricePerItem
				&& !useHasToBeOrdered && !loginBean.getSubject().isPermitted(EnumPermission.EMPLOYMENT_FIND_ALL.getPermissionString()))
						? new ArrayList<>()
						: itemDao.findItems(itemId, name, description, itemsAvaible, fictionalPricePerItem, realPricePerItem, hasToBeOrdered, useId,
								useName, useDescription, useItemsAvaible, useFictionalPricePerItem, useRealPricePerItem, useHasToBeOrdered,
								idComparator, nameComparator, descriptionComparator, itemsAvaibleComparator, fictionalPricePerItemComparator,
								realPricePerItemComparator);
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
		if ((item.getDescription() != null && item.getDescription().equals(description)) || (item.getDescription() == null && description == null))
			return Boolean.FALSE;
		item.setDescription(description);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(ITEM_SET_REAL_PRICE_PER_ITEM)
	public Boolean setRealPricePerItem(Long itemId, BigDecimal realPricePerItem) {
		Item item = itemDao.findById(itemId);
		if (Utils.isEqual(item.getRealPricePerItem(), realPricePerItem)) return Boolean.FALSE;
		checkWetherItemPriceIsValid(item.getFictionalPricePerItem(), realPricePerItem);
		item.setRealPricePerItem(realPricePerItem);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(ITEM_SET_FICTIONAL_PRICE_PER_ITEM)
	public Boolean setFictionalPricePerItem(Long itemId, BigDecimal fictionalPricePerItem) {
		Item item = itemDao.findById(itemId);
		if (Utils.isEqual(item.getFictionalPricePerItem(), fictionalPricePerItem)) return Boolean.FALSE;
		checkWetherItemPriceIsValid(fictionalPricePerItem, item.getRealPricePerItem());
		item.setFictionalPricePerItem(fictionalPricePerItem);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(ITEM_ADD_ITEMS_AVAIBLE)
	public Boolean addItemsAvailable(Long itemId, Integer amount) {
		Item item = itemDao.findById(itemId);
		item.setItemsAvailable(item.getItemsAvailable() + amount);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(ITEM_SUB_ITEMS_AVAIBLE)
	public Boolean subItemsAvailable(Long itemId, Integer amount) {
		Item item = itemDao.findById(itemId);
		if (item.getItemsAvailable() - amount < 0)
			throw new LucasException("The amount is greater than the count of available items", ILLEGAL_AMOUNT_EXCEPTION_MARKER);
		item.setItemsAvailable(item.getItemsAvailable() - amount);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(ITEM_CREATE)
	public Long newItem(String name, String description, BigDecimal fictionalPrice, BigDecimal realPrice, Integer itemsAvaible) {
		checkIsItemNameUnique(name);
		checkWetherItemPriceIsValid(fictionalPrice, realPrice);
		Item item = new Item(name, description, fictionalPrice, realPrice, itemsAvaible);
		itemDao.persist(item);
		return item.getId();
	}

	private void checkIsItemNameUnique(String name) {
		if (!itemDao.isNameUnique(name)) throw new LucasException("The item name is used by another item", NOT_UNIQUE_NAME_EXCEPTION_MARKER);
	}

	private void checkWetherItemPriceIsValid(BigDecimal fictionalPrice, BigDecimal realPrice) {
		if (Utils.isGreatherThan(realPrice, fictionalPrice))
			throw new LucasException("The realprice is greater than the fictional price", ILLEGAL_ITEM_PRICE_EXCEPTION_MARKER);
	}

	@Override
	@RequiresPermissions(ITEM_REMOVE)
	public Boolean removeItem(Long itemId) {
		Item item = itemDao.findById(itemId);
		for (PurchaseLog log : item.getPurchaseLogs()) {
			log.getCompany().removePurchaseLog(log);
		}
		itemDao.delete(itemDao.findById(itemId));
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(ITEM_FIND_BY_DATA)
	public List<? extends ReadOnlyItem> getItemsByData(String data, Integer resultsCount) {
		return itemDao.getItemsFromData(data, resultsCount);
	}

	@Override
	@RequiresPermissions(ITEM_GET_PURCHASE_LOGS)
	public List<? extends ReadOnlyPurchaseLog> getPurchaseLogs(Long itemId) {
		return itemDao.findById(itemId).getPurchaseLogs();
	}

}
