package florian_haas.lucas.web;

import java.io.Serializable;
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
import florian_haas.lucas.database.*;
import florian_haas.lucas.database.validation.QueryComparator;
import florian_haas.lucas.model.*;
import florian_haas.lucas.model.validation.*;
import florian_haas.lucas.security.EnumPermission;
import florian_haas.lucas.util.Utils;
import florian_haas.lucas.web.util.WebUtils;
import florian_haas.lucas.web.util.converter.AccountOwnerConverter;

@Named("accountBean")
@ViewScoped
public class AccountBean implements Serializable {

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
	@Min(0)
	private Long searchAccountOwnerId = 0l;

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

	private List<Account> searchAccountResults = new ArrayList<>();

	private List<Account> selectedAccounts = new ArrayList<>();

	public List<Account> getSearchAccountResults() {
		return searchAccountResults;
	}

	public void setSearchAccountResults(List<Account> searchAccountResults) {
		this.searchAccountResults = searchAccountResults;
	}

	public List<Account> getSelectedAccounts() {
		return selectedAccounts;
	}

	public void setSelectedAccounts(List<Account> selectedAccounts) {
		this.selectedAccounts = selectedAccounts;
	}

	public void searchAccounts() {
		WebUtils.executeTask((params) -> {
			List<Account> results = accountBean.findAccounts(searchAccountId, searchAccountOwnerId, searchAccountOwnerType, searchAccountBankBalance,
					searchAccountBlocked, searchAccountIsProtected, useSearchAccountId, useSearchAccountOwnerId, useSearchAccountOwnerType,
					useSearchAccountBankBalance, useSearchAccountBlocked, useSearchAccountIsProtected, searchAccountIdComparator,
					searchAccountOwnerIdComparator, searchAccountOwnerTypeComparator, searchAccountBankBalanceComparator);
			searchAccountResults.clear();
			selectedAccounts.clear();
			searchAccountResults.addAll(results);
			params.add(results.size());
			return true;
		}, "lucas.application.accountScreen.searchAccount.message");
	}

	public void refreshAccounts() {
		WebUtils.executeTask((params) -> {
			WebUtils.refreshEntities(Account.class, searchAccountResults, selectedAccounts, accountBean::findById, false);
			params.add(searchAccountResults.size());
			return true;
		}, "lucas.application.accountScreen.refreshAccounts");
	}

	private List<Boolean> resultsDatatableColumns = Arrays.asList(true, true, true, true, true, true);

	public void onToggle(ToggleEvent e) {
		resultsDatatableColumns.set((Integer) e.getData() - 1, e.getVisibility() == Visibility.VISIBLE);
	}

	public List<Boolean> getResultsDatatableColumns() {
		return this.resultsDatatableColumns;
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

	public Long getSearchAccountOwnerId() {
		return searchAccountOwnerId;
	}

	public void setSearchAccountOwnerId(Long searchAccountOwnerId) {
		this.searchAccountOwnerId = searchAccountOwnerId;
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
		for (Account account : selectedAccounts) {
			WebUtils.executeTask(params -> {
				accountBean.payIn(account.getId(), payInDialogTransactionAmount, payInDialogComment);
				params.add(WebUtils.getCurrencyAsString(payInDialogTransactionAmount));
				params.add(payInDialogComment != null ? payInDialogComment : "");
				return true;
			}, "lucas.application.accountScreen.payIn.message",
					Utils.asList(WebUtils.getAsString(account.getOwner(), AccountOwnerConverter.CONVERTER_ID)));
		}
		WebUtils.refreshEntities(Account.class, searchAccountResults, selectedAccounts, accountBean::findById, true);
	}

	/*
	 * -------------------- Pay In Dialog End --------------------
	 */

	/*
	 * -------------------- Pay Out Dialog Start --------------------
	 */

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

	public void resetPayOutDialog() {
		payOutDialogTransactionAmount = BigDecimal.ZERO;
		payOutDialogComment = null;
	}

	public void payOut() {
		for (Account account : selectedAccounts) {
			WebUtils.executeTask(params -> {
				accountBean.payOut(account.getId(), payOutDialogTransactionAmount, payOutDialogComment);
				params.add(WebUtils.getCurrencyAsString(payOutDialogTransactionAmount));
				params.add(payOutDialogComment != null ? payOutDialogComment : "");
				return true;
			}, "lucas.application.accountScreen.payOut.message",
					Utils.asList(WebUtils.getAsString(account.getOwner(), AccountOwnerConverter.CONVERTER_ID)));
		}
		WebUtils.refreshEntities(Account.class, searchAccountResults, selectedAccounts, accountBean::findById, true);
	}

	/*
	 * -------------------- Pay Out Dialog End --------------------
	 */

	/*
	 * -------------------- Transaction Dialog Start --------------------
	 */

	@ValidTransactionAmount
	private BigDecimal transactionDialogTransactionAmount = BigDecimal.ZERO;

	@ShortComment
	private String transactionDialogComment = null;

	@ValidEntityId(entityClass = AccountOwner.class)
	private Long transactionDialogToId = null;

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

	public Long getTransactionDialogToId() {
		return transactionDialogToId;
	}

	public void setTransactionDialogToId(Long transactionDialogToId) {
		this.transactionDialogToId = transactionDialogToId;
	}

	public String getAccountOwnerFromCurrentId() {
		AccountOwner owner = transactionDialogToId != null
				? entityBean.exists(transactionDialogToId, AccountOwner.class) ? accountBean.findAccountOwnerById(transactionDialogToId) : null
				: null;
		return WebUtils.getAsString(owner, AccountOwnerConverter.CONVERTER_ID);
	}

	public void resetTransactionDialog() {
		transactionDialogTransactionAmount = BigDecimal.ZERO;
		transactionDialogComment = null;
		transactionDialogToId = null;
	}

	public void transaction() {
		AccountOwner owner = accountBean.findAccountOwnerById(transactionDialogToId);
		for (Account account : selectedAccounts) {
			WebUtils.executeTask(params -> {
				accountBean.transaction(account.getId(), owner.getAccount().getId(), transactionDialogTransactionAmount, transactionDialogComment);
				params.add(WebUtils.getCurrencyAsString(transactionDialogTransactionAmount));
				params.add(transactionDialogComment != null ? transactionDialogComment : "");
				return true;
			}, "lucas.application.accountScreen.transaction.message",
					Utils.asList(WebUtils.getAsString(account.getOwner(), AccountOwnerConverter.CONVERTER_ID),
							WebUtils.getAsString(owner, AccountOwnerConverter.CONVERTER_ID)));
		}
		WebUtils.refreshEntities(Account.class, searchAccountResults, selectedAccounts, Arrays.asList(owner.getAccount()), accountBean::findById,
				true);
	}

	/*
	 * -------------------- Transaction Dialog End --------------------
	 */

	public void block() {
		for (Account account : selectedAccounts) {
			WebUtils.executeTask(params -> {
				return accountBean.blockAccount(account.getId());
			}, "lucas.application.accountScreen.block.message",
					Utils.asList(WebUtils.getAsString(account.getOwner(), AccountOwnerConverter.CONVERTER_ID)));
		}
		WebUtils.refreshEntities(Account.class, searchAccountResults, selectedAccounts, accountBean::findById, true);
	}

	public void unblock() {
		for (Account account : selectedAccounts) {
			WebUtils.executeTask(params -> {
				return accountBean.unblockAccount(account.getId());
			}, "lucas.application.accountScreen.unblock.message",
					Utils.asList(WebUtils.getAsString(account.getOwner(), AccountOwnerConverter.CONVERTER_ID)));
		}
		WebUtils.refreshEntities(Account.class, searchAccountResults, selectedAccounts, accountBean::findById, true);
	}

	public void protect() {
		for (Account account : selectedAccounts) {
			WebUtils.executeTask(params -> {
				return accountBean.protect(account.getId());
			}, "lucas.application.accountScreen.protect.message",
					Utils.asList(WebUtils.getAsString(account.getOwner(), AccountOwnerConverter.CONVERTER_ID)));
		}
		WebUtils.refreshEntities(Account.class, searchAccountResults, selectedAccounts, accountBean::findById, true);
	}

	public void unprotect() {
		for (Account account : selectedAccounts) {
			WebUtils.executeTask(params -> {
				return accountBean.unprotect(account.getId());
			}, "lucas.application.accountScreen.unprotect.message",
					Utils.asList(WebUtils.getAsString(account.getOwner(), AccountOwnerConverter.CONVERTER_ID)));
		}
		WebUtils.refreshEntities(Account.class, searchAccountResults, selectedAccounts, accountBean::findById, true);
	}

	/*
	 * -------------------- Transaction Log Dialog Start --------------------
	 */

	private Account transactionLogDialogSelectedAccount = null;

	private List<TransactionLog> transactionLogsDialogTransactionLogs = new ArrayList<>();

	private List<Boolean> transactionLogsDialogDatatableColumns = Arrays.asList(true, true, true, true, true, true, true, true, true, true);

	public Account getTransactionLogDialogSelectedAccount() {
		return this.transactionLogDialogSelectedAccount;
	}

	public List<TransactionLog> getTransactionLogsDialogTransactionLogs() {
		return this.transactionLogsDialogTransactionLogs;
	}

	public void setTransactionLogsDialogTransactionLogs(List<TransactionLog> transactionLogsDialogTransactionLogs) {
		this.transactionLogsDialogTransactionLogs = transactionLogsDialogTransactionLogs;
	}

	public List<Boolean> getTransactionLogsDialogDatatableColumns() {
		return this.transactionLogsDialogDatatableColumns;
	}

	public void initTransactionLogDialog() {
		if (!selectedAccounts.isEmpty()) {
			transactionLogDialogSelectedAccount = selectedAccounts.get(0);
			transactionLogsDialogTransactionLogs.clear();
			transactionLogsDialogTransactionLogs.addAll(transactionLogDialogSelectedAccount.getTransactionLogs());
			Collections.fill(transactionLogsDialogDatatableColumns, true);
		}
	}

	public void transactionLogDialogOnToggle(ToggleEvent e) {
		transactionLogsDialogDatatableColumns.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
	}
	/*
	 * -------------------- Transaction Log Dialog End --------------------
	 */
}
