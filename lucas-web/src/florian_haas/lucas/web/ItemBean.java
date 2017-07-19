package florian_haas.lucas.web;

import java.math.BigDecimal;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.NotBlank;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

import florian_haas.lucas.business.ItemBeanLocal;
import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;
import florian_haas.lucas.security.EnumPermission;
import florian_haas.lucas.validation.*;
import florian_haas.lucas.web.converter.ItemConverter;
import florian_haas.lucas.web.util.WebUtils;

@Named
@ViewScoped
public class ItemBean extends BaseBean<ReadOnlyItem> {

	public static final String BASE_NAME = "item";

	private static final long serialVersionUID = -897326768889144895L;

	@EJB
	private ItemBeanLocal itemBean;

	public ItemBean() {
		super(BASE_NAME, 7);
	}

	@NotNull
	@Min(0)
	private Long searchItemId = 0l;

	@NotNull
	private Boolean useSearchItemId = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchItemIdComparator = EnumQueryComparator.EQUAL;

	@NotBlank
	private String searchItemName = "";

	@NotNull
	private Boolean useSearchItemName = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.TEXT)
	private EnumQueryComparator searchItemNameComparator = EnumQueryComparator.EQUAL;

	@NotBlankString
	@Size(max = 255)
	private String searchItemDescription = null;

	@NotNull
	private Boolean useSearchItemDescription = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.TEXT)
	private EnumQueryComparator searchItemDescriptionComparator = EnumQueryComparator.EQUAL;

	@NotNull
	@Min(0)
	private Integer searchItemItemsAvailable = 0;

	@NotNull
	private Boolean useSearchItemItemsAvailable = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchItemItemsAvailableComparator = EnumQueryComparator.EQUAL;

	@ValidItemPrice
	private BigDecimal searchItemFictionalPrice = BigDecimal.ZERO;

	@NotNull
	private Boolean useSearchItemFictionalPrice = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchItemFictionalPriceComparator = EnumQueryComparator.EQUAL;

	@ValidItemPrice
	private BigDecimal searchItemRealPrice = BigDecimal.ZERO;

	@NotNull
	private Boolean useSearchItemRealPrice = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchItemRealPriceComparator = EnumQueryComparator.EQUAL;

	@NotNull
	private Boolean searchItemHasToBeOrdered = Boolean.FALSE;

	@NotNull
	private Boolean useSearchItemHasToBeOrdered = Boolean.FALSE;

	public Long getSearchItemId() {
		return this.searchItemId;
	}

	public void setSearchItemId(Long searchItemId) {
		this.searchItemId = searchItemId;
	}

	public Boolean getUseSearchItemId() {
		return this.useSearchItemId;
	}

	public void setUseSearchItemId(Boolean useSearchItemId) {
		this.useSearchItemId = useSearchItemId;
	}

	public EnumQueryComparator getSearchItemIdComparator() {
		return this.searchItemIdComparator;
	}

	public void setSearchItemIdComparator(EnumQueryComparator searchItemIdComparator) {
		this.searchItemIdComparator = searchItemIdComparator;
	}

	public String getSearchItemName() {
		return this.searchItemName;
	}

	public void setSearchItemName(String searchItemName) {
		this.searchItemName = searchItemName;
	}

	public Boolean getUseSearchItemName() {
		return this.useSearchItemName;
	}

	public void setUseSearchItemName(Boolean useSearchItemName) {
		this.useSearchItemName = useSearchItemName;
	}

	public EnumQueryComparator getSearchItemNameComparator() {
		return this.searchItemNameComparator;
	}

	public void setSearchItemNameComparator(EnumQueryComparator searchItemNameComparator) {
		this.searchItemNameComparator = searchItemNameComparator;
	}

	public String getSearchItemDescription() {
		return this.searchItemDescription;
	}

	public void setSearchItemDescription(String searchItemDescription) {
		this.searchItemDescription = searchItemDescription;
	}

	public Boolean getUseSearchItemDescription() {
		return this.useSearchItemDescription;
	}

	public void setUseSearchItemDescription(Boolean useSearchItemDescription) {
		this.useSearchItemDescription = useSearchItemDescription;
	}

	public EnumQueryComparator getSearchItemDescriptionComparator() {
		return this.searchItemDescriptionComparator;
	}

	public void setSearchItemDescriptionComparator(EnumQueryComparator searchItemDescriptionComparator) {
		this.searchItemDescriptionComparator = searchItemDescriptionComparator;
	}

	public Integer getSearchItemItemsAvailable() {
		return this.searchItemItemsAvailable;
	}

	public void setSearchItemItemsAvailable(Integer searchItemItemsAvailable) {
		this.searchItemItemsAvailable = searchItemItemsAvailable;
	}

	public Boolean getUseSearchItemItemsAvailable() {
		return this.useSearchItemItemsAvailable;
	}

	public void setUseSearchItemItemsAvailable(Boolean useSearchItemItemsAvailable) {
		this.useSearchItemItemsAvailable = useSearchItemItemsAvailable;
	}

	public EnumQueryComparator getSearchItemItemsAvailableComparator() {
		return this.searchItemItemsAvailableComparator;
	}

	public void setSearchItemItemsAvailableComparator(EnumQueryComparator searchItemItemsAvailableComparator) {
		this.searchItemItemsAvailableComparator = searchItemItemsAvailableComparator;
	}

	public BigDecimal getSearchItemFictionalPrice() {
		return this.searchItemFictionalPrice;
	}

	public void setSearchItemFictionalPrice(BigDecimal searchItemFictionalPrice) {
		this.searchItemFictionalPrice = searchItemFictionalPrice;
	}

	public Boolean getUseSearchItemFictionalPrice() {
		return this.useSearchItemFictionalPrice;
	}

	public void setUseSearchItemFictionalPrice(Boolean useSearchItemFictionalPrice) {
		this.useSearchItemFictionalPrice = useSearchItemFictionalPrice;
	}

	public EnumQueryComparator getSearchItemFictionalPriceComparator() {
		return this.searchItemFictionalPriceComparator;
	}

	public void setSearchItemFictionalPriceComparator(EnumQueryComparator searchItemFictionalPriceComparator) {
		this.searchItemFictionalPriceComparator = searchItemFictionalPriceComparator;
	}

	public BigDecimal getSearchItemRealPrice() {
		return this.searchItemRealPrice;
	}

	public void setSearchItemRealPrice(BigDecimal searchItemRealPrice) {
		this.searchItemRealPrice = searchItemRealPrice;
	}

	public Boolean getUseSearchItemRealPrice() {
		return this.useSearchItemRealPrice;
	}

	public void setUseSearchItemRealPrice(Boolean useSearchItemRealPrice) {
		this.useSearchItemRealPrice = useSearchItemRealPrice;
	}

	public EnumQueryComparator getSearchItemRealPriceComparator() {
		return this.searchItemRealPriceComparator;
	}

	public void setSearchItemRealPriceComparator(EnumQueryComparator searchItemRealPriceComparator) {
		this.searchItemRealPriceComparator = searchItemRealPriceComparator;
	}

	public Boolean getSearchItemHasToBeOrdered() {
		return this.searchItemHasToBeOrdered;
	}

	public void setSearchItemHasToBeOrdered(Boolean searchItemHasToBeOrdered) {
		this.searchItemHasToBeOrdered = searchItemHasToBeOrdered;
	}

	public Boolean getUseSearchItemHasToBeOrdered() {
		return this.useSearchItemHasToBeOrdered;
	}

	public void setUseSearchItemHasToBeOrdered(Boolean useSearchItemHasToBeOrdered) {
		this.useSearchItemHasToBeOrdered = useSearchItemHasToBeOrdered;
	}

	@Override
	public EnumPermission getFindDynamicPermission() {
		return EnumPermission.ITEM_FIND_DYNAMIC;
	}

	@Override
	public EnumPermission getPrintPermission() {
		return EnumPermission.ITEM_PRINT;
	}

	@Override
	public EnumPermission getExportPermission() {
		return EnumPermission.ITEM_EXPORT;
	}

	@Override
	protected List<? extends ReadOnlyItem> searchEntities() {
		return itemBean.findItems(searchItemId, searchItemName, searchItemDescription, searchItemItemsAvailable, searchItemFictionalPrice,
				searchItemRealPrice, searchItemHasToBeOrdered, useSearchItemId, useSearchItemName, useSearchItemDescription,
				useSearchItemItemsAvailable, useSearchItemFictionalPrice, useSearchItemRealPrice, useSearchItemHasToBeOrdered, searchItemIdComparator,
				searchItemNameComparator, searchItemDescriptionComparator, searchItemItemsAvailableComparator, searchItemFictionalPriceComparator,
				searchItemRealPriceComparator);
	}

	@Override
	protected ReadOnlyItem entityGetter(Long entityId) {
		return itemBean.findById(entityId);
	}

	/*
	 * -------------------- Create Item Dialog Start --------------------
	 */

	@NotBlank
	private String createItemDialogName = "";

	@NotBlankString
	@Size(max = 255)
	private String createItemDialogDescription = null;

	@NotNull
	@Min(0)
	private Integer createItemDialogItemsAvailable = 0;

	@ValidItemPrice
	private BigDecimal createItemDialogFictionalPrice = BigDecimal.ZERO;

	@ValidItemPrice
	private BigDecimal createItemDialogRealPrice = BigDecimal.ZERO;

	public String getCreateItemDialogName() {
		return this.createItemDialogName;
	}

	public void setCreateItemDialogName(String createItemDialogName) {
		this.createItemDialogName = createItemDialogName;
	}

	public String getCreateItemDialogDescription() {
		return this.createItemDialogDescription;
	}

	public void setCreateItemDialogDescription(String createItemDialogDescription) {
		this.createItemDialogDescription = createItemDialogDescription;
	}

	public Integer getCreateItemDialogItemsAvailable() {
		return this.createItemDialogItemsAvailable;
	}

	public void setCreateItemDialogItemsAvailable(Integer createItemDialogItemsAvailable) {
		this.createItemDialogItemsAvailable = createItemDialogItemsAvailable;
	}

	public BigDecimal getCreateItemDialogFictionalPrice() {
		return this.createItemDialogFictionalPrice;
	}

	public void setCreateItemDialogFictionalPrice(BigDecimal createItemDialogFictionalPrice) {
		this.createItemDialogFictionalPrice = createItemDialogFictionalPrice;
	}

	public BigDecimal getCreateItemDialogRealPrice() {
		return this.createItemDialogRealPrice;
	}

	public void setCreateItemDialogRealPrice(BigDecimal createItemDialogRealPrice) {
		this.createItemDialogRealPrice = createItemDialogRealPrice;
	}

	public void initCreateItemDialog() {
		createItemDialogName = "";
		createItemDialogDescription = null;
		createItemDialogItemsAvailable = 0;
		createItemDialogFictionalPrice = BigDecimal.ZERO;
		createItemDialogRealPrice = BigDecimal.ZERO;
	}

	public void createItem() {
		WebUtils.executeTask((params) -> {
			params.add(WebUtils.getAsString(itemBean.findById(itemBean.newItem(createItemDialogName, createItemDialogDescription,
					createItemDialogFictionalPrice, createItemDialogRealPrice, createItemDialogItemsAvailable)), ItemConverter.CONVERTER_ID));
			return true;
		}, "lucas.application.itemScreen.createItem.message", (exception, params) -> {
			switch (exception.getMark()) {
				case florian_haas.lucas.business.ItemBeanLocal.NOT_UNIQUE_NAME_EXCEPTION_MARKER:
					return WebUtils.getTranslatedMessage("lucas.application.itemScreen.createItem.message.notUniqueName", createItemDialogName);
				case florian_haas.lucas.business.ItemBeanLocal.ILLEGAL_ITEM_PRICE_EXCEPTION_MARKER:
					return WebUtils.getTranslatedMessage("lucas.application.itemScreen.createItem.message.illegalPrice");
			}
			return exception.getMessage();
		});
	}

	/*
	 * -------------------- Create Item Dialog End --------------------
	 */

	/*
	 * -------------------- Edit Item Dialog Start --------------------
	 */

	private ReadOnlyItem editItemDialogSelectedItem = null;

	@NotBlank
	private String editItemDialogName = "";

	@NotBlankString
	@Size(max = 255)
	private String editItemDialogDescription = null;

	@ValidItemPrice
	private BigDecimal editItemDialogFictionalPrice = BigDecimal.ZERO;

	@ValidItemPrice
	private BigDecimal editItemDialogRealPrice = BigDecimal.ZERO;

	public String getEditItemDialogName() {
		return this.editItemDialogName;
	}

	public void setEditItemDialogName(String editItemDialogName) {
		this.editItemDialogName = editItemDialogName;
	}

	public String getEditItemDialogDescription() {
		return this.editItemDialogDescription;
	}

	public void setEditItemDialogDescription(String editItemDialogDescription) {
		this.editItemDialogDescription = editItemDialogDescription;
	}

	public BigDecimal getEditItemDialogFictionalPrice() {
		return this.editItemDialogFictionalPrice;
	}

	public void setEditItemDialogFictionalPrice(BigDecimal editItemDialogFictionalPrice) {
		this.editItemDialogFictionalPrice = editItemDialogFictionalPrice;
	}

	public BigDecimal getEditItemDialogRealPrice() {
		return this.editItemDialogRealPrice;
	}

	public void setEditItemDialogRealPrice(BigDecimal editItemDialogRealPrice) {
		this.editItemDialogRealPrice = editItemDialogRealPrice;
	}

	public void initEditItemDialog() {
		if (!selectedEntities.isEmpty()) {
			editItemDialogSelectedItem = selectedEntities.get(0);
			editItemDialogName = editItemDialogSelectedItem.getName();
			editItemDialogDescription = editItemDialogSelectedItem.getDescription();
			editItemDialogFictionalPrice = editItemDialogSelectedItem.getFictionalPricePerItem();
			editItemDialogRealPrice = editItemDialogSelectedItem.getRealPricePerItem();
		}
	}

	public void editItem() {
		WebUtils.executeTask((params) -> {
			Long id = editItemDialogSelectedItem.getId();
			if (WebUtils.isPermitted(EnumPermission.ITEM_SET_NAME)) {
				itemBean.setName(id, editItemDialogName);
			}
			if (WebUtils.isPermitted(EnumPermission.ITEM_SET_DESCRIPTION)) {
				itemBean.setDescription(id, editItemDialogDescription);
			}
			if (WebUtils.isPermitted(EnumPermission.ITEM_SET_FICTIONAL_PRICE_PER_ITEM)) {
				itemBean.setFictionalPricePerItem(id, editItemDialogFictionalPrice);
			}
			if (WebUtils.isPermitted(EnumPermission.ITEM_SET_REAL_PRICE_PER_ITEM)) {
				itemBean.setRealPricePerItem(id, editItemDialogRealPrice);
			}
			ReadOnlyItem tmp2 = itemBean.findById(id);
			params.add(WebUtils.getAsString(tmp2, ItemConverter.CONVERTER_ID));
			WebUtils.refreshEntities(ReadOnlyItem.class, searchResults, selectedEntities, tmp2, itemBean::findById, true);
			return true;
		}, "lucas.application.itemScreen.editItem.message", (exception, params) -> {
			switch (exception.getMark()) {
				case florian_haas.lucas.business.ItemBeanLocal.NOT_UNIQUE_NAME_EXCEPTION_MARKER:
					return WebUtils.getTranslatedMessage("lucas.application.itemScreen.editItem.message.notUniqueName", editItemDialogName);
				case florian_haas.lucas.business.ItemBeanLocal.ILLEGAL_ITEM_PRICE_EXCEPTION_MARKER:
					return WebUtils.getTranslatedMessage("lucas.application.itemScreen.editItem.message.illegalPrice");
			}
			return exception.getMessage();
		});
	}

	/*
	 * -------------------- Edit Item Dialog End --------------------
	 */

	/*
	 * -------------------- Manage Stocks Dialog Start --------------------
	 */

	@NotNull
	@Min(1)
	private Integer manageStocksDialogAmount = 1;

	@NotNull
	private EnumStockManagerMode manageStocksDialogMode = EnumStockManagerMode.ADD;

	public Integer getManageStocksDialogAmount() {
		return this.manageStocksDialogAmount;
	}

	public void setManageStocksDialogAmount(Integer manageStocksDialogAmount) {
		this.manageStocksDialogAmount = manageStocksDialogAmount;
	}

	public EnumStockManagerMode getManageStocksDialogMode() {
		return this.manageStocksDialogMode;
	}

	public void setManageStocksDialogMode(EnumStockManagerMode manageStocksDialogMode) {
		this.manageStocksDialogMode = manageStocksDialogMode;
	}

	public List<EnumStockManagerMode> getLegalModes() {
		ArrayList<EnumStockManagerMode> ret = new ArrayList<>();
		if (WebUtils.isPermitted(EnumPermission.ITEM_ADD_ITEMS_AVAIBLE)) ret.add(EnumStockManagerMode.ADD);
		if (WebUtils.isPermitted(EnumPermission.ITEM_SUB_ITEMS_AVAIBLE)) ret.add(EnumStockManagerMode.SUBTRACT);
		return ret;
	}

	public void initManageStocksDialog() {
		manageStocksDialogAmount = 0;
		List<EnumStockManagerMode> modes = getLegalModes();
		manageStocksDialogMode = modes.isEmpty() ? null : modes.get(0);
	}

	public void manageStocks() {
		if (!selectedEntities.isEmpty()) {
			WebUtils.executeTask((params) -> {
				Long id = selectedEntities.get(0).getId();

				switch (manageStocksDialogMode) {
					case ADD:
						itemBean.addItemsAvailable(id, manageStocksDialogAmount);
						params.add(WebUtils.getTranslatedMessage("lucas.application.itemScreen.manageStocks.message.success2",
								manageStocksDialogAmount));
						break;
					case SUBTRACT:
						itemBean.subItemsAvailable(id, manageStocksDialogAmount);
						params.add(WebUtils.getTranslatedMessage("lucas.application.itemScreen.manageStocks.message.success3",
								manageStocksDialogAmount));
						break;
				}

				ReadOnlyItem tmp2 = itemBean.findById(id);
				params.add(WebUtils.getAsString(tmp2, ItemConverter.CONVERTER_ID));
				WebUtils.refreshEntities(ReadOnlyItem.class, searchResults, selectedEntities, tmp2, itemBean::findById, true);
				return true;
			}, "lucas.application.itemScreen.manageStocks.message", (exception, params) -> {
				if (exception.getMark().equals(florian_haas.lucas.business.ItemBeanLocal.ILLEGAL_AMOUNT_EXCEPTION_MARKER))
					return WebUtils.getTranslatedMessage("lucas.application.itemScreen.manageStocks.message.illegalAmount");
				return exception.getMessage();
			});
		}
	}

	/*
	 * -------------------- Manage Stocks Dialog End --------------------
	 */

	/*
	 * -------------------- Show Purchase Logs Dialog Start --------------------
	 */

	private ReadOnlyItem showPurchaseLogsDialogSelectedItem = null;

	private List<ReadOnlyPurchaseLog> showPurchaseLogsDialogLogs = new ArrayList<>();

	private List<Boolean> showPurchaseLogsDialogLogsColumns = Arrays.asList(true, true, true, true, true, true, true, true, true);

	public ReadOnlyItem getShowPurchaseLogsDialogSelectedItem() {
		return this.showPurchaseLogsDialogSelectedItem;
	}

	public List<ReadOnlyPurchaseLog> getShowPurchaseLogsDialogLogs() {
		return this.showPurchaseLogsDialogLogs;
	}

	public List<Boolean> getShowPurchaseLogsDialogLogsColumns() {
		return this.showPurchaseLogsDialogLogsColumns;
	}

	public void initShowPurchaseLogsDialog() {
		if (!selectedEntities.isEmpty()) {
			showPurchaseLogsDialogSelectedItem = selectedEntities.get(0);
			showPurchaseLogsDialogLogs.clear();
			showPurchaseLogsDialogLogs.addAll(itemBean.getPurchaseLogs(showPurchaseLogsDialogSelectedItem.getId()));
			Collections.fill(showPurchaseLogsDialogLogsColumns, true);
		}
	}

	public void purchaseLogDialogOnToggle(ToggleEvent e) {
		showPurchaseLogsDialogLogsColumns.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
	}

	/*
	 * -------------------- Show Purchase Logs Dialog End --------------------
	 */

	public void deleteSelectedItems() {
		Iterator<ReadOnlyItem> it = selectedEntities.iterator();
		while (it.hasNext()) {
			ReadOnlyItem item = it.next();
			WebUtils.executeTask(params -> {
				params.add(WebUtils.getAsString(item, ItemConverter.CONVERTER_ID));
				Boolean ret = itemBean.removeItem(item.getId());
				if (ret) {
					searchResults.remove(item);
					it.remove();
				}
				return ret;
			}, "lucas.application.itemScreen.removeItems.message");
		}
	}
}
