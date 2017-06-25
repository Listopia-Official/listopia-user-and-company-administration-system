package florian_haas.lucas.web;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.*;

import florian_haas.lucas.business.*;
import florian_haas.lucas.model.*;
import florian_haas.lucas.util.Utils;
import florian_haas.lucas.web.converter.*;
import florian_haas.lucas.web.util.WebUtils;

@Named
@ViewScoped
public class SellItemsBean implements Serializable {

	private static final long serialVersionUID = -8640378143431652519L;

	@NotNull
	private ReadOnlyItem shoppingChartInputItem = null;

	@NotNull
	@Min(1)
	private Integer shoppingChartInputItemCount = 0;

	private List<ReadOnlyItem> shoppingChart = new ArrayList<>();

	private Map<ReadOnlyItem, Integer> shoppingChartCount = new HashMap<>();

	@NotNull
	private ReadOnlyItem shoppingChartSelectedItem = null;

	@NotNull
	private ReadOnlyCompany company = null;

	@NotNull
	private EnumPayType paymentMethod = EnumPayType.ACCOUNT;

	@EJB
	private ItemBeanLocal itemBean;

	public ReadOnlyItem getShoppingChartInputItem() {
		return this.shoppingChartInputItem;
	}

	public void setShoppingChartInputItem(ReadOnlyItem shoppingChartInputItem) {
		this.shoppingChartInputItem = shoppingChartInputItem;
	}

	public Integer getShoppingChartInputItemCount() {
		return this.shoppingChartInputItemCount;
	}

	public void setShoppingChartInputItemCount(Integer shoppingChartInputItemCount) {
		this.shoppingChartInputItemCount = shoppingChartInputItemCount;
	}

	public List<ReadOnlyItem> getShoppingChart() {
		return this.shoppingChart;
	}

	public void setShoppingChart(List<ReadOnlyItem> shoppingChart) {
		this.shoppingChart = shoppingChart;
	}

	public Map<ReadOnlyItem, Integer> getShoppingChartCount() {
		return this.shoppingChartCount;
	}

	public BigDecimal getBigDecimalFromInt(Integer integer) {
		return BigDecimal.valueOf(integer);
	}

	public ReadOnlyItem getShoppingChartSelectedItem() {
		return this.shoppingChartSelectedItem;
	}

	public void setShoppingChartSelectedItem(ReadOnlyItem shoppingChartSelectedItem) {
		this.shoppingChartSelectedItem = shoppingChartSelectedItem;
	}

	public ReadOnlyCompany getCompany() {
		return this.company;
	}

	public void setCompany(ReadOnlyCompany company) {
		this.company = company;
	}

	public EnumPayType getPaymentMethod() {
		return this.paymentMethod;
	}

	public void setPaymentMethod(EnumPayType paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public void addItemToChart() {
		if (shoppingChartInputItem != null) {
			WebUtils.executeTask((params) -> {
				params.add(WebUtils.getAsString(shoppingChartInputItem, ItemConverter.CONVERTER_ID));
				if (!shoppingChart.contains(shoppingChartInputItem)) {
					shoppingChart.add(shoppingChartInputItem);
					shoppingChartCount.put(shoppingChartInputItem, shoppingChartInputItemCount);
					params.add(shoppingChartInputItemCount);
					shoppingChartInputItem = null;
					shoppingChartInputItemCount = 0;
					return true;
				}
				return false;
			}, "lucas.application.sellItemsScreen.addItemToChart.message");
		}
	}

	public void editSelectedItem() {
		if (shoppingChartSelectedItem != null) {
			if (shoppingChart.contains(shoppingChartSelectedItem)) {
				shoppingChart.remove(shoppingChartSelectedItem);
				shoppingChartInputItem = shoppingChartSelectedItem;
				shoppingChartInputItemCount = shoppingChartCount.remove(shoppingChartSelectedItem);
				shoppingChartInputItemCount = shoppingChartInputItemCount == null ? 0 : shoppingChartInputItemCount;
				shoppingChartSelectedItem = null;
			}
		}
	}

	public void removeSelectedItem() {
		WebUtils.executeTask(params -> {
			if (shoppingChartSelectedItem != null) {
				if (shoppingChart.contains(shoppingChartSelectedItem)) {
					params.add(WebUtils.getAsString(shoppingChartSelectedItem, ItemConverter.CONVERTER_ID));
					shoppingChart.remove(shoppingChartSelectedItem);
					shoppingChartCount.remove(shoppingChartSelectedItem);
					shoppingChartSelectedItem = null;
					return true;
				}
			}
			return false;
		}, "lucas.application.sellItemsScreen.removeItemFromChart.message");
	}

	public BigDecimal getTotalPrice() {
		BigDecimal price = BigDecimal.ZERO;
		for (ReadOnlyItem item : shoppingChart) {
			price = price.add(item.getFictionalPricePerItem().multiply(BigDecimal.valueOf(shoppingChartCount.get(item))));
		}
		return price;
	}

	public void pay() {
		if (WebUtils.executeTask(params -> {
			if (!shoppingChart.isEmpty()) {
				Map<Long, Integer> items = new HashMap<>();
				for (ReadOnlyItem item : shoppingChart) {
					if (shoppingChartCount.containsKey(item)) {
						items.put(item.getId(), shoppingChartCount.get(item));
					} else {
						WebUtils.addDefaultTranslatedWarningMessage("lucas.application.sellItemsScreen.pay.message.notSpecified",
								WebUtils.getAsString(item, ItemConverter.CONVERTER_ID));
					}
				}
				itemBean.sell(items, company.getId(), paymentMethod,
						WebUtils.getTranslatedMessage("lucas.application.sellItemsScreen.pay.transactionComment", shoppingChart.size()),
						WebUtils.getTranslatedMessage("lucas.application.sellItemsScreen.pay.moneyExchangeComment", shoppingChart.size()));
				return true;
			}
			return false;
		}, "lucas.application.sellItemsScreen.pay.message", (exception, params) -> {
			String message = exception.getMessage();
			switch (exception.getMark()) {
				case florian_haas.lucas.business.ItemBeanLocal.ILLEGAL_AMOUNT_EXCEPTION_MARKER:
					String item = WebUtils.getAsString(itemBean.findById((Long) exception.getParams().get(0)), ItemConverter.CONVERTER_ID);
					message = WebUtils.getTranslatedMessage("lucas.application.sellItemsScreen.pay.message.illegalAmount", item,
							exception.getParams().get(1), exception.getParams().get(2));
					break;
				case florian_haas.lucas.business.ItemBeanLocal.NO_WAREHOUSE_EXCEPTION_MARKER:
					message = WebUtils.getTranslatedMessage("lucas.application.sellItemsScreen.pay.message.noWarehouse");
					break;
				case AccountBeanLocal.NO_PERMISSION_FOR_TRANSACTION_FROM_PROTECTED_EXCEPTION_MARKER:
					message = WebUtils.getTranslatedMessage("lucas.application.sellItemsScreen.pay.message.fail.fromProtected", params.get(3));
					break;
				case AccountBeanLocal.NO_PERMISSION_FOR_TRANSACTION_TO_PROTECTED_EXCEPTION_MARKER:
					message = WebUtils.getTranslatedMessage("lucas.application.sellItemsScreen.pay.message.fail.toProtected");
					break;
				case AccountBeanLocal.NO_PERMISSION_FOR_EXCEEDING_TRANSACTION_LIMIT_EXCEPTION_MARKER:
					message = WebUtils.getTranslatedMessage("lucas.application.sellItemsScreen.pay.message.fail.transactionLimitExceeded");
					break;
				case AccountBeanLocal.FROM_BLOCKED_EXCEPTION_MARKER:
					message = WebUtils.getTranslatedMessage("lucas.application.sellItemsScreen.pay.message.fail.fromBlocked", params.get(3));
					break;
				case AccountBeanLocal.TO_BLOCKED_EXCEPTION_MARKER:
					message = WebUtils.getTranslatedMessage("lucas.application.sellItemsScreen.pay.message.fail.toBlocked");
					break;
				case AccountBeanLocal.TRANSACTION_AMOUNT_GREATER_THAN_BANK_BALANCE_EXCEPTION_MARKER:
					message = WebUtils.getTranslatedMessage("lucas.application.sellItemsScreen.pay.message.fail.amountGreaterThanBankBalance",
							params.get(3));
					break;
				case AccountBeanLocal.SAME_ACCOUNT_AS_SOURCE_AND_TARGET_EXCEPTION_MARKER:
					message = WebUtils.getTranslatedMessage("lucas.application.sellItemsScreen.pay.message.fail.sameAccountAsSourceAndTarget");
					break;
				case AccountBeanLocal.NOT_ENOUGH_MONEY_IN_CIRCULATION_EXCEPTION_MARKER:
					message = WebUtils.getTranslatedMessage("lucas.application.sellItemsScreen.pay.message.fail.notEnoughMoneyInCirculation");
					break;
				case AccountBeanLocal.NOT_ENOUGH_REAL_MONEY_EXCEPTION_MARKER:
					message = WebUtils.getTranslatedMessage("lucas.application.sellItemsScreen.pay.message.fail.notEnoughRealMoney");
					break;
			}
			return message;
		}, Utils.asList(shoppingChart.size(),
				WebUtils.getCurrencyAsString(getTotalPrice()), WebUtils.getTranslatedMessage(paymentMethod == EnumPayType.ACCOUNT
						? "lucas.application.sellItemsScreen.pay.message.success2" : "lucas.application.sellItemsScreen.pay.message.success1"),
				WebUtils.getAsString(company, CompanyConverter.CONVERTER_ID)))) {
			reset();
		}
	}

	public void reset() {
		shoppingChartInputItem = null;
		shoppingChartInputItemCount = 0;
		shoppingChart = new ArrayList<>();
		shoppingChartCount = new HashMap<>();
		shoppingChartSelectedItem = null;
		company = null;
		paymentMethod = EnumPayType.ACCOUNT;
	}

}
