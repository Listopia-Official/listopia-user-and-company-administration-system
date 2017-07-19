package florian_haas.lucas.web;

import java.math.BigDecimal;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.*;

import org.apache.shiro.SecurityUtils;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

import florian_haas.lucas.business.*;
import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;
import florian_haas.lucas.security.EnumPermission;
import florian_haas.lucas.util.Utils;
import florian_haas.lucas.validation.*;
import florian_haas.lucas.web.converter.AccountConverter;
import florian_haas.lucas.web.util.WebUtils;

@Named
@ViewScoped
public class AccountBean extends BaseBean<ReadOnlyAccount> {

	public AccountBean() {
		super(BASE_NAME, 6);
	}

	public static final String BASE_NAME = "account";

	private static final long serialVersionUID = 7962757114064858876L;

	@EJB
	private AccountBeanLocal accountBean;

	@EJB
	private EntityBeanLocal entityBean;

	@EJB
	private GlobalDataBeanLocal globalDataBean;

	@NotNull
	private Boolean useSearchAccountId = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchAccountIdComparator = EnumQueryComparator.EQUAL;

	@NotNull
	@Min(0)
	private Long searchAccountId = 0l;

	@NotNull
	private Boolean useSearchAccountOwnerId = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchAccountOwnerIdComparator = EnumQueryComparator.EQUAL;

	@NotNull
	private ReadOnlyAccountOwner searchAccountOwner = null;

	@NotNull
	private Boolean useSearchAccountOwnerType = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.LOGIC)
	private EnumQueryComparator searchAccountOwnerTypeComparator = EnumQueryComparator.EQUAL;

	@NotNull
	private EnumAccountOwnerType searchAccountOwnerType = EnumAccountOwnerType.USER;

	@NotNull
	private Boolean useSearchAccountBankBalance = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchAccountBankBalanceComparator = EnumQueryComparator.EQUAL;

	@ValidBankBalance
	private BigDecimal searchAccountBankBalance = BigDecimal.ZERO;

	@NotNull
	private Boolean useSearchAccountBlocked = Boolean.FALSE;

	@NotNull
	private Boolean searchAccountBlocked = Boolean.FALSE;

	@NotNull
	private Boolean useSearchAccountIsProtected = Boolean.FALSE;

	@NotNull
	private Boolean searchAccountIsProtected = Boolean.FALSE;

	/*
	 * -------------------- Transaction Log Dialog End --------------------
	 */

	@Override
	public EnumPermission getFindDynamicPermission() {
		return EnumPermission.ACCOUNT_FIND_DYNAMIC;
	}

	@Override
	public EnumPermission getPrintPermission() {
		return EnumPermission.ACCOUNT_PRINT;
	}

	@Override
	public EnumPermission getExportPermission() {
		return EnumPermission.ACCOUNT_EXPORT;
	}

	@Override
	protected List<? extends ReadOnlyAccount> searchEntities() {
		return accountBean.findAccounts(searchAccountId, searchAccountOwner != null ? searchAccountOwner.getId() : null, searchAccountOwnerType,
				searchAccountBankBalance, searchAccountBlocked, searchAccountIsProtected, useSearchAccountId, useSearchAccountOwnerId,
				useSearchAccountOwnerType, useSearchAccountBankBalance, useSearchAccountBlocked, useSearchAccountIsProtected,
				searchAccountIdComparator, searchAccountOwnerIdComparator, searchAccountOwnerTypeComparator, searchAccountBankBalanceComparator);
	}

	@Override
	protected ReadOnlyAccount entityGetter(Long entityId) {
		return accountBean.findById(entityId);
	}

	public Boolean getUseSearchAccountId() {
		return useSearchAccountId;
	}

	public void setUseSearchAccountId(Boolean useSearchAccountId) {
		this.useSearchAccountId = useSearchAccountId;
	}

	public EnumQueryComparator getSearchAccountIdComparator() {
		return searchAccountIdComparator;
	}

	public void setSearchAccountIdComparator(EnumQueryComparator searchAccountIdComparator) {
		this.searchAccountIdComparator = searchAccountIdComparator;
	}

	public Long getSearchAccountId() {
		return searchAccountId;
	}

	public void setSearchAccountId(Long searchAccountId) {
		this.searchAccountId = searchAccountId;
	}

	public Boolean getUseSearchAccountOwnerId() {
		return useSearchAccountOwnerId;
	}

	public void setUseSearchAccountOwnerId(Boolean useSearchAccountOwnerId) {
		this.useSearchAccountOwnerId = useSearchAccountOwnerId;
	}

	public EnumQueryComparator getSearchAccountOwnerIdComparator() {
		return searchAccountOwnerIdComparator;
	}

	public void setSearchAccountOwnerIdComparator(EnumQueryComparator searchAccountOwnerIdComparator) {
		this.searchAccountOwnerIdComparator = searchAccountOwnerIdComparator;
	}

	public ReadOnlyAccountOwner getSearchAccountOwner() {
		return searchAccountOwner;
	}

	public void setSearchAccountOwner(ReadOnlyAccountOwner searchAccountOwner) {
		this.searchAccountOwner = searchAccountOwner;
	}

	public EnumAccountOwnerType getSearchAccountOwnerType() {
		return searchAccountOwnerType;
	}

	public void setSearchAccountOwnerType(EnumAccountOwnerType searchAccountOwnerType) {
		this.searchAccountOwnerType = searchAccountOwnerType;
	}

	public Boolean getUseSearchAccountOwnerType() {
		return useSearchAccountOwnerType;
	}

	public void setUseSearchAccountOwnerType(Boolean useSearchAccountOwnerType) {
		this.useSearchAccountOwnerType = useSearchAccountOwnerType;
	}

	public EnumQueryComparator getSearchAccountOwnerTypeComparator() {
		return searchAccountOwnerTypeComparator;
	}

	public void setSearchAccountOwnerTypeComparator(EnumQueryComparator searchAccountOwnerTypeComparator) {
		this.searchAccountOwnerTypeComparator = searchAccountOwnerTypeComparator;
	}

	public Boolean getUseSearchAccountBankBalance() {
		return useSearchAccountBankBalance;
	}

	public void setUseSearchAccountBankBalance(Boolean useSearchAccountBankBalance) {
		this.useSearchAccountBankBalance = useSearchAccountBankBalance;
	}

	public EnumQueryComparator getSearchAccountBankBalanceComparator() {
		return searchAccountBankBalanceComparator;
	}

	public void setSearchAccountBankBalanceComparator(EnumQueryComparator searchAccountBankBalanceComparator) {
		this.searchAccountBankBalanceComparator = searchAccountBankBalanceComparator;
	}

	public BigDecimal getSearchAccountBankBalance() {
		return searchAccountBankBalance;
	}

	public void setSearchAccountBankBalance(BigDecimal searchAccountBankBalance) {
		this.searchAccountBankBalance = searchAccountBankBalance;
	}

	public Boolean getUseSearchAccountBlocked() {
		return useSearchAccountBlocked;
	}

	public void setUseSearchAccountBlocked(Boolean useSearchAccountBlocked) {
		this.useSearchAccountBlocked = useSearchAccountBlocked;
	}

	public Boolean getSearchAccountBlocked() {
		return searchAccountBlocked;
	}

	public void setSearchAccountBlocked(Boolean searchAccountBlocked) {
		this.searchAccountBlocked = searchAccountBlocked;
	}

	public Boolean getUseSearchAccountIsProtected() {
		return useSearchAccountIsProtected;
	}

	public void setUseSearchAccountIsProtected(Boolean useSearchAccountBankIsProtected) {
		this.useSearchAccountIsProtected = useSearchAccountBankIsProtected;
	}

	public Boolean getSearchAccountIsProtected() {
		return searchAccountIsProtected;
	}

	public void setSearchAccountIsProtected(Boolean searchAccountIsProtected) {
		this.searchAccountIsProtected = searchAccountIsProtected;
	}

	public String getCurrencySymbol() {
		return globalDataBean.getCurrencySymbol();
	}

	public String getRealCurrencySymbol() {
		return globalDataBean.getRealCurrencySymbol();
	}

	public BigDecimal getTransactionLimit() {
		return SecurityUtils.getSubject().isPermitted(EnumPermission.ACCOUNT_IGNORE_TRANSACTION_LIMIT.getPermissionString())
				? new BigDecimal(Integer.toString(Integer.MAX_VALUE)) : globalDataBean.getTransactionLimit();
	}

	/*
	 * -------------------- Pay In Dialog Start --------------------
	 */

	@ValidTransactionAmount
	private BigDecimal payInDialogTransactionAmount = BigDecimal.ZERO;

	@ShortComment
	private String payInDialogComment = null;

	public BigDecimal getPayInDialogTransactionAmount() {
		return payInDialogTransactionAmount;
	}

	public void setPayInDialogTransactionAmount(BigDecimal payInDialogTransactionAmount) {
		this.payInDialogTransactionAmount = payInDialogTransactionAmount;
	}

	public String getPayInDialogComment() {
		return payInDialogComment;
	}

	public void setPayInDialogComment(String payInDialogComment) {
		this.payInDialogComment = payInDialogComment;
	}

	public void resetPayInDialog() {
		payInDialogTransactionAmount = BigDecimal.ZERO;
		payInDialogComment = null;
	}

	public void payIn() {
		for (ReadOnlyAccount account : selectedEntities) {
			WebUtils.executeTask(params -> {
				accountBean.payIn(account.getId(), payInDialogTransactionAmount, payInDialogComment);
				params.add(WebUtils.getCurrencyAsString(payInDialogTransactionAmount));
				params.add(payInDialogComment != null ? payInDialogComment : "");
				return true;
			}, "lucas.application.accountScreen.payIn.message", (exception, params) -> {
				String key = null;
				switch (exception.getMark()) {
					case AccountBeanLocal.NO_PERMISSION_FOR_TRANSACTION_FROM_PROTECTED_EXCEPTION_MARKER:
					case AccountBeanLocal.NO_PERMISSION_FOR_TRANSACTION_TO_PROTECTED_EXCEPTION_MARKER:
						key = "lucas.application.accountScreen.payIn.message.fail.protected";
						break;
					case AccountBeanLocal.NO_PERMISSION_FOR_EXCEEDING_TRANSACTION_LIMIT_EXCEPTION_MARKER:
						key = "lucas.application.accountScreen.payIn.message.fail.transactionLimitExceeded";
						break;
					case AccountBeanLocal.FROM_BLOCKED_EXCEPTION_MARKER:
					case AccountBeanLocal.TO_BLOCKED_EXCEPTION_MARKER:
						key = "lucas.application.accountScreen.payIn.message.fail.blocked";
						break;
					case AccountBeanLocal.NOT_ENOUGH_MONEY_IN_CIRCULATION_EXCEPTION_MARKER:
						key = "lucas.application.accountScreen.payIn.message.fail.notEnoughMoneyInCirculation";
						break;
				}
				return key != null ? WebUtils.getTranslatedMessage(key, params.toArray(new Object[params.size()])) : null;
			}, Utils.asList(WebUtils.getAsString(account, AccountConverter.CONVERTER_ID)));
		}
		WebUtils.refreshEntities(ReadOnlyAccount.class, searchResults, selectedEntities, accountBean::findById, true);
	}

	/*
	 * -------------------- Pay In Dialog End --------------------
	 */

	/*
	 * -------------------- Pay Out Dialog Start --------------------
	 */

	@NotNull
	private Boolean payOutDialogAll = Boolean.FALSE;

	@ValidTransactionAmount
	private BigDecimal payOutDialogTransactionAmount = BigDecimal.ZERO;

	@ShortComment
	private String payOutDialogComment = null;

	public BigDecimal getPayOutDialogTransactionAmount() {
		return payOutDialogTransactionAmount;
	}

	public void setPayOutDialogTransactionAmount(BigDecimal payOutDialogTransactionAmount) {
		this.payOutDialogTransactionAmount = payOutDialogTransactionAmount;
	}

	public String getPayOutDialogComment() {
		return payOutDialogComment;
	}

	public void setPayOutDialogComment(String payOutDialogComment) {
		this.payOutDialogComment = payOutDialogComment;
	}

	public Boolean getPayOutDialogAll() {
		return this.payOutDialogAll;
	}

	public void setPayOutDialogAll(Boolean payOutDialogAll) {
		this.payOutDialogAll = payOutDialogAll;
	}

	public void resetPayOutDialog() {
		payOutDialogAll = Boolean.FALSE;
		payOutDialogTransactionAmount = BigDecimal.ZERO;
		payOutDialogComment = null;
	}

	public void payOut() {
		for (ReadOnlyAccount account : selectedEntities) {
			WebUtils.executeTask(params -> {
				params.add(WebUtils.getCurrencyAsString(payOutDialogAll ? account.getBankBalance() : payOutDialogTransactionAmount));
				params.add(payOutDialogComment != null ? payOutDialogComment : "");
				params.add(getCurrencySymbol());
				return accountBean.payOut(account.getId(), payOutDialogAll ? null : payOutDialogTransactionAmount, payOutDialogComment);
			}, "lucas.application.accountScreen.payOut.message", (exception, params) -> {
				String key = null;
				switch (exception.getMark()) {
					case AccountBeanLocal.NO_PERMISSION_FOR_TRANSACTION_FROM_PROTECTED_EXCEPTION_MARKER:
					case AccountBeanLocal.NO_PERMISSION_FOR_TRANSACTION_TO_PROTECTED_EXCEPTION_MARKER:
						key = "lucas.application.accountScreen.payOut.message.fail.protected";
						break;
					case AccountBeanLocal.NO_PERMISSION_FOR_EXCEEDING_TRANSACTION_LIMIT_EXCEPTION_MARKER:
						key = "lucas.application.accountScreen.payOut.message.fail.transactionLimitExceeded";
						break;
					case AccountBeanLocal.FROM_BLOCKED_EXCEPTION_MARKER:
					case AccountBeanLocal.TO_BLOCKED_EXCEPTION_MARKER:
						key = "lucas.application.accountScreen.payOut.message.fail.blocked";
						break;
					case AccountBeanLocal.TRANSACTION_AMOUNT_GREATER_THAN_BANK_BALANCE_EXCEPTION_MARKER:
						key = "lucas.application.accountScreen.payOut.message.fail.amountGreaterThanBankBalance";
						break;
				}
				return key != null ? WebUtils.getTranslatedMessage(key, params.toArray(new Object[params.size()])) : null;
			}, Utils.asList(WebUtils.getAsString(account, AccountConverter.CONVERTER_ID)));
		}
		WebUtils.refreshEntities(ReadOnlyAccount.class, searchResults, selectedEntities, accountBean::findById, true);
	}

	/*
	 * -------------------- Pay Out Dialog End --------------------
	 */

	/*
	 * -------------------- Transaction Dialog Start --------------------
	 */

	@NotNull
	private Boolean transactionDialogAll = Boolean.FALSE;

	@ValidTransactionAmount
	private BigDecimal transactionDialogTransactionAmount = BigDecimal.ZERO;

	@ShortComment
	private String transactionDialogComment = null;

	@NotNull
	private ReadOnlyAccount transactionDialogToAccount = null;

	public BigDecimal getTransactionDialogTransactionAmount() {
		return transactionDialogTransactionAmount;
	}

	public void setTransactionDialogTransactionAmount(BigDecimal transactionDialogTransactionAmount) {
		this.transactionDialogTransactionAmount = transactionDialogTransactionAmount;
	}

	public String getTransactionDialogComment() {
		return transactionDialogComment;
	}

	public void setTransactionDialogComment(String transactionDialogComment) {
		this.transactionDialogComment = transactionDialogComment;
	}

	public ReadOnlyAccount getTransactionDialogToAccount() {
		return transactionDialogToAccount;
	}

	public void setTransactionDialogToAccount(ReadOnlyAccount transactionDialogToAccount) {
		this.transactionDialogToAccount = transactionDialogToAccount;
	}

	public Boolean getTransactionDialogAll() {
		return this.transactionDialogAll;
	}

	public void setTransactionDialogAll(Boolean transactionDialogAll) {
		this.transactionDialogAll = transactionDialogAll;
	}

	public void resetTransactionDialog() {
		transactionDialogTransactionAmount = BigDecimal.ZERO;
		transactionDialogComment = null;
		transactionDialogToAccount = null;
		transactionDialogAll = Boolean.FALSE;
	}

	public void transaction() {
		for (ReadOnlyAccount account : selectedEntities) {
			WebUtils.executeTask(params -> {
				params.add(WebUtils.getCurrencyAsString(transactionDialogAll ? account.getBankBalance() : transactionDialogTransactionAmount));
				params.add(transactionDialogComment != null ? transactionDialogComment : "");
				params.add(getCurrencySymbol());
				return accountBean.transaction(account.getId(), transactionDialogToAccount.getId(),
						transactionDialogAll ? null : transactionDialogTransactionAmount, transactionDialogComment);
			}, "lucas.application.accountScreen.transaction.message", (exception, params) -> {
				String key = null;
				switch (exception.getMark()) {
					case AccountBeanLocal.NO_PERMISSION_FOR_TRANSACTION_FROM_PROTECTED_EXCEPTION_MARKER:
						key = "lucas.application.accountScreen.transaction.message.fail.fromProtected";
						break;
					case AccountBeanLocal.NO_PERMISSION_FOR_TRANSACTION_TO_PROTECTED_EXCEPTION_MARKER:
						key = "lucas.application.accountScreen.transaction.message.fail.toProtected";
						break;
					case AccountBeanLocal.NO_PERMISSION_FOR_EXCEEDING_TRANSACTION_LIMIT_EXCEPTION_MARKER:
						key = "lucas.application.accountScreen.transaction.message.fail.transactionLimitExceeded";
						break;
					case AccountBeanLocal.FROM_BLOCKED_EXCEPTION_MARKER:
						key = "lucas.application.accountScreen.transaction.message.fail.fromBlocked";
						break;
					case AccountBeanLocal.TO_BLOCKED_EXCEPTION_MARKER:
						key = "lucas.application.accountScreen.transaction.message.fail.toBlocked";
						break;
					case AccountBeanLocal.TRANSACTION_AMOUNT_GREATER_THAN_BANK_BALANCE_EXCEPTION_MARKER:
						key = "lucas.application.accountScreen.transaction.message.fail.amountGreaterThanBankBalance";
						break;
					case AccountBeanLocal.SAME_ACCOUNT_AS_SOURCE_AND_TARGET_EXCEPTION_MARKER:
						key = "lucas.application.accountScreen.transaction.message.fail.sameAccountAsSourceAndTarget";
						break;
				}
				return key != null ? WebUtils.getTranslatedMessage(key, params.toArray(new Object[params.size()])) : null;
			}, Utils.asList(WebUtils.getAsString(account, AccountConverter.CONVERTER_ID),
					WebUtils.getAsString(transactionDialogToAccount, AccountConverter.CONVERTER_ID)));
		}
		WebUtils.refreshEntities(ReadOnlyAccount.class, searchResults, selectedEntities, Arrays.asList(transactionDialogToAccount),
				accountBean::findById, true);
	}

	/*
	 * -------------------- Transaction Dialog End --------------------
	 */

	public void block() {
		for (ReadOnlyAccount account : selectedEntities) {
			WebUtils.executeTask(params -> {
				return accountBean.blockAccount(account.getId());
			}, "lucas.application.accountScreen.block.message", Utils.asList(WebUtils.getAsString(account, AccountConverter.CONVERTER_ID)));
		}
		WebUtils.refreshEntities(ReadOnlyAccount.class, searchResults, selectedEntities, accountBean::findById, true);
	}

	public void unblock() {
		for (ReadOnlyAccount account : selectedEntities) {
			WebUtils.executeTask(params -> {
				return accountBean.unblockAccount(account.getId());
			}, "lucas.application.accountScreen.unblock.message", Utils.asList(WebUtils.getAsString(account, AccountConverter.CONVERTER_ID)));
		}
		WebUtils.refreshEntities(ReadOnlyAccount.class, searchResults, selectedEntities, accountBean::findById, true);
	}

	public void protect() {
		for (ReadOnlyAccount account : selectedEntities) {
			WebUtils.executeTask(params -> {
				return accountBean.protect(account.getId());
			}, "lucas.application.accountScreen.protect.message", Utils.asList(WebUtils.getAsString(account, AccountConverter.CONVERTER_ID)));
		}
		WebUtils.refreshEntities(ReadOnlyAccount.class, searchResults, selectedEntities, accountBean::findById, true);
	}

	public void unprotect() {
		for (ReadOnlyAccount account : selectedEntities) {
			WebUtils.executeTask(params -> {
				return accountBean.unprotect(account.getId());
			}, "lucas.application.accountScreen.unprotect.message", Utils.asList(WebUtils.getAsString(account, AccountConverter.CONVERTER_ID)));
		}
		WebUtils.refreshEntities(ReadOnlyAccount.class, searchResults, selectedEntities, accountBean::findById, true);
	}

	/*
	 * -------------------- Transaction Log Dialog Start --------------------
	 */

	private ReadOnlyAccount transactionLogDialogSelectedAccount = null;

	private List<ReadOnlyTransactionLog> transactionLogsDialogTransactionLogs = new ArrayList<>();

	private List<Boolean> transactionLogsDialogDatatableColumns = Arrays.asList(true, true, true, true, true, true, true, true, true, true);

	public ReadOnlyAccount getTransactionLogDialogSelectedAccount() {
		return this.transactionLogDialogSelectedAccount;
	}

	public List<ReadOnlyTransactionLog> getTransactionLogsDialogTransactionLogs() {
		return this.transactionLogsDialogTransactionLogs;
	}

	public void setTransactionLogsDialogTransactionLogs(List<ReadOnlyTransactionLog> transactionLogsDialogTransactionLogs) {
		this.transactionLogsDialogTransactionLogs = transactionLogsDialogTransactionLogs;
	}

	public List<Boolean> getTransactionLogsDialogDatatableColumns() {
		return this.transactionLogsDialogDatatableColumns;
	}

	public void initTransactionLogDialog() {
		if (!selectedEntities.isEmpty()) {
			transactionLogDialogSelectedAccount = selectedEntities.get(0);
			transactionLogsDialogTransactionLogs.clear();
			transactionLogsDialogTransactionLogs.addAll(transactionLogDialogSelectedAccount.getTransactionLogs());
			Collections.fill(transactionLogsDialogDatatableColumns, true);
		}
	}

	public void transactionLogDialogOnToggle(ToggleEvent e) {
		transactionLogsDialogDatatableColumns.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
	}

	/*
	 * -------------------- Currency Exchange Dialog Start --------------------
	 */

	@ValidTransactionAmount
	private BigDecimal currencyExchangeDialogAmount = BigDecimal.ZERO;

	private ReadOnlyAccount currencyExchangeDialogToAccount = null;

	public BigDecimal getCurrencyExchangeDialogAmount() {
		return this.currencyExchangeDialogAmount;
	}

	public void setCurrencyExchangeDialogAmount(BigDecimal currencyExchangeDialogAmount) {
		this.currencyExchangeDialogAmount = currencyExchangeDialogAmount;
	}

	public ReadOnlyAccount getCurrencyExchangeDialogToAccount() {
		return this.currencyExchangeDialogToAccount;
	}

	public void setCurrencyExchangeDialogToAccount(ReadOnlyAccount currencyExchangeDialogToAccount) {
		this.currencyExchangeDialogToAccount = currencyExchangeDialogToAccount;
	}

	public void resetCurrencyExchangeDialog() {
		currencyExchangeDialogAmount = BigDecimal.ZERO;
		currencyExchangeDialogToAccount = null;
	}

	public void exchangeCurrency() {
		WebUtils.executeTask((params) -> {
			params.add(WebUtils.getRealCurrencyAsString(currencyExchangeDialogAmount));
			params.add(WebUtils.getCurrencyAsString(globalDataBean.getRateOfExchange().multiply(currencyExchangeDialogAmount).abs()));
			String accountAsString = WebUtils.getAsString(currencyExchangeDialogToAccount, AccountConverter.CONVERTER_ID);
			params.add(currencyExchangeDialogToAccount == null ? ""
					: WebUtils.getTranslatedMessage("lucas.application.accountScreen.exchangeCurrency.message.success2", accountAsString));
			params.add(accountAsString);
			accountBean.exchangeRealCurrencyToFictional(currencyExchangeDialogAmount,
					currencyExchangeDialogToAccount != null ? currencyExchangeDialogToAccount.getId() : null,
					WebUtils.getTranslatedMessage("lucas.application.accountScreen.exchangeCurrency.payInComment"));
			return true;
		}, "lucas.application.accountScreen.exchangeCurrency.message", (exception, params) -> {
			String key = null;
			switch (exception.getMark()) {
				case AccountBeanLocal.NO_PERMISSION_FOR_TRANSACTION_FROM_PROTECTED_EXCEPTION_MARKER:
				case AccountBeanLocal.NO_PERMISSION_FOR_TRANSACTION_TO_PROTECTED_EXCEPTION_MARKER:
					key = "lucas.application.accountScreen.exchangeCurrency.message.fail.protected";
					break;
				case AccountBeanLocal.NO_PERMISSION_FOR_EXCEEDING_TRANSACTION_LIMIT_EXCEPTION_MARKER:
					key = "lucas.application.accountScreen.exchangeCurrency.message.fail.transactionLimitExceeded";
					break;
				case AccountBeanLocal.FROM_BLOCKED_EXCEPTION_MARKER:
				case AccountBeanLocal.TO_BLOCKED_EXCEPTION_MARKER:
					key = "lucas.application.accountScreen.exchangeCurrency.message.fail.blocked";
					break;
			}
			return key != null ? WebUtils.getTranslatedMessage(key, params.toArray(new Object[params.size()])) : null;
		});
	}

	/*
	 * -------------------- Currency Exchange Dialog End --------------------
	 */

	/*
	 * -------------------- Currency Back Exchange Dialog Start --------------------
	 */

	@ValidTransactionAmount
	private BigDecimal currencyBackExchangeDialogAmount = BigDecimal.ZERO;

	private ReadOnlyAccount currencyBackExchangeDialogFromAccount = null;

	@NotNull
	private Boolean currencyBackExchangeDialogExchangeAll = Boolean.FALSE;

	public BigDecimal getCurrencyBackExchangeDialogAmount() {
		return currencyBackExchangeDialogAmount;
	}

	public void setCurrencyBackExchangeDialogAmount(BigDecimal currencyBackExchangeDialogAmount) {
		this.currencyBackExchangeDialogAmount = currencyBackExchangeDialogAmount;
	}

	public ReadOnlyAccount getCurrencyBackExchangeDialogFromAccount() {
		return currencyBackExchangeDialogFromAccount;
	}

	public void setCurrencyBackExchangeDialogFromAccount(ReadOnlyAccount currencyBackExchangeDialogFromAccount) {
		this.currencyBackExchangeDialogFromAccount = currencyBackExchangeDialogFromAccount;
	}

	public Boolean getCurrencyBackExchangeDialogExchangeAll() {
		return this.currencyBackExchangeDialogExchangeAll;
	}

	public void setCurrencyBackExchangeDialogExchangeAll(Boolean currencyBackExchangeDialogExchangeAll) {
		this.currencyBackExchangeDialogExchangeAll = currencyBackExchangeDialogExchangeAll;
	}

	public void resetCurrencyBackExchangeDialog() {
		currencyBackExchangeDialogAmount = BigDecimal.ZERO;
		currencyBackExchangeDialogFromAccount = null;
		currencyBackExchangeDialogExchangeAll = Boolean.FALSE;
	}

	public void exchangeCurrencyBack() {
		WebUtils.executeTask((params) -> {
			currencyBackExchangeDialogAmount = currencyBackExchangeDialogFromAccount != null && currencyBackExchangeDialogExchangeAll
					? Utils.isZero(currencyBackExchangeDialogFromAccount.getBankBalance()) ? BigDecimal.ONE
							: currencyBackExchangeDialogFromAccount.getBankBalance()
					: currencyBackExchangeDialogAmount;
			params.add(WebUtils.getCurrencyAsString(currencyBackExchangeDialogAmount));
			params.add(WebUtils.getRealCurrencyAsString(globalDataBean.getRateOfBackExchange().multiply(currencyBackExchangeDialogAmount).abs()));
			String accountAsString = WebUtils.getAsString(currencyBackExchangeDialogFromAccount, AccountConverter.CONVERTER_ID);
			params.add(currencyBackExchangeDialogFromAccount == null ? ""
					: WebUtils.getTranslatedMessage("lucas.application.accountScreen.exchangeCurrencyBack.message.success2", accountAsString));
			params.add(accountAsString);
			params.add(getCurrencySymbol());
			return accountBean.exchangeFictionalCurrencyToReal(
					currencyBackExchangeDialogFromAccount != null ? currencyBackExchangeDialogFromAccount.getId() : null,
					currencyBackExchangeDialogAmount, currencyBackExchangeDialogExchangeAll,
					WebUtils.getTranslatedMessage("lucas.application.accountScreen.exchangeCurrencyBack.payOutComment"));
		}, "lucas.application.accountScreen.exchangeCurrencyBack.message", (exception, params) -> {
			String key = null;
			switch (exception.getMark()) {
				case AccountBeanLocal.NO_PERMISSION_FOR_TRANSACTION_FROM_PROTECTED_EXCEPTION_MARKER:
				case AccountBeanLocal.NO_PERMISSION_FOR_TRANSACTION_TO_PROTECTED_EXCEPTION_MARKER:
					key = "lucas.application.accountScreen.exchangeCurrencyBack.message.fail.protected";
					break;
				case AccountBeanLocal.NO_PERMISSION_FOR_EXCEEDING_TRANSACTION_LIMIT_EXCEPTION_MARKER:
					key = "lucas.application.accountScreen.exchangeCurrencyBack.message.fail.transactionLimitExceeded";
					break;
				case AccountBeanLocal.FROM_BLOCKED_EXCEPTION_MARKER:
				case AccountBeanLocal.TO_BLOCKED_EXCEPTION_MARKER:
					key = "lucas.application.accountScreen.exchangeCurrencyBack.message.fail.blocked";
					break;
				case AccountBeanLocal.TRANSACTION_AMOUNT_GREATER_THAN_BANK_BALANCE_EXCEPTION_MARKER:
					key = "lucas.application.accountScreen.exchangeCurrencyBack.message.fail.amountGreaterThanBankBalance";
					break;
				case AccountBeanLocal.NOT_ENOUGH_MONEY_IN_CIRCULATION_EXCEPTION_MARKER:
					key = "lucas.application.accountScreen.exchangeCurrencyBack.message.fail.notEnoughMoneyInCirculation";
					break;
				case AccountBeanLocal.NOT_ENOUGH_REAL_MONEY_EXCEPTION_MARKER:
					key = "lucas.application.accountScreen.exchangeCurrencyBack.message.fail.notEnoughRealMoney";
					break;
			}
			return key != null ? WebUtils.getTranslatedMessage(key, params.toArray(new Object[params.size()])) : null;
		});
	}

	/*
	 * -------------------- Currency Back Exchange Dialog End --------------------
	 */

	public String showReferencedUsers() {
		navigateToBeanSingle(UserBean.BASE_NAME,
				(account) -> account.getOwner().getOwnerType() == EnumAccountOwnerType.USER ? account.getOwner().getId() : null);
		return "/users?faces-redirect=true";
	}

	public String showReferencedCompanies() {
		navigateToBeanSingle(CompanyBean.BASE_NAME,
				(account) -> account.getOwner().getOwnerType() == EnumAccountOwnerType.COMPANY ? account.getOwner().getId() : null);
		return "/companies?faces-redirect=true";
	}

	public String showReferencedTransactionLogs() {
		navigateToBean(TransactionLogBean.BASE_NAME, (account) -> {
			List<Long> ids = new ArrayList<>();
			for (ReadOnlyTransactionLog log : account.getTransactionLogs()) {
				ids.add(log.getId());
			}
			return ids;
		});
		return "/transactionLogs?faces-redirect=true";
	}
}
