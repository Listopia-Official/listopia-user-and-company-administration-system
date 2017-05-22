package florian_haas.lucas.web;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.*;

import florian_haas.lucas.business.*;
import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;
import florian_haas.lucas.security.EnumPermission;
import florian_haas.lucas.util.Utils;
import florian_haas.lucas.validation.*;

@Named
@ViewScoped
public class TransactionLogBean extends BaseBean<ReadOnlyTransactionLog> {

	private static final long serialVersionUID = 1674970990233017621L;

	@EJB
	private AccountBeanLocal accountBean;

	@EJB
	private GlobalDataBeanLocal globalDataBean;

	public TransactionLogBean() {
		super("transactionLog", 11);
		this.getResultsDatatableColumns().set(6, Boolean.FALSE);
		this.getResultsDatatableColumns().set(8, Boolean.FALSE);
	}

	@NotNull
	@Min(0)
	private Long searchTransactionLogId = 0l;

	@NotNull
	private Boolean useSearchTransactionLogId = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchTransactionLogIdComparator = EnumQueryComparator.EQUAL;

	@NotNull
	private ReadOnlyAccount searchTransactionLogAccount = null;

	@NotNull
	private Boolean useSearchTransactionLogAccountId = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.LOGIC)
	private EnumQueryComparator searchTransactionLogAccountIdComparator = EnumQueryComparator.EQUAL;

	@NotNull
	private Date searchTransactionLogDateTime = Date.from(Instant.now());

	@NotNull
	private Boolean useSearchTransactionLogDateTime = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchTransactionLogDateTimeComparator = EnumQueryComparator.EQUAL;

	@NotNull
	private EnumAccountAction searchTransactionLogAction = EnumAccountAction.CREDIT;

	@NotNull
	private Boolean useSearchTransactionLogAction = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.LOGIC)
	private EnumQueryComparator searchTransactionLogActionComparator = EnumQueryComparator.EQUAL;

	@NotNull
	private EnumAccountActionType searchTransactionLogActionType = EnumAccountActionType.BANK;

	@NotNull
	private Boolean useSearchTransactionLogActionType = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.LOGIC)
	private EnumQueryComparator searchTransactionLogActionTypeComparator = EnumQueryComparator.EQUAL;

	private ReadOnlyAccount searchTransactionLogRelatedAccount = null;

	@NotNull
	private Boolean useSearchTransactionLogRelatedAccountId = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.LOGIC)
	private EnumQueryComparator searchTransactionLogRelatedAccountIdComparator = EnumQueryComparator.EQUAL;

	@ValidTransactionAmount
	private BigDecimal searchTransactionLogAmount = BigDecimal.ONE;

	@NotNull
	private Boolean useSearchTransactionLogAmount = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchTransactionLogAmountComparator = EnumQueryComparator.EQUAL;

	@ValidBankBalance
	private BigDecimal searchTransactionLogPreviousBankBalance = BigDecimal.TEN;

	@NotNull
	private Boolean useSearchTransactionLogPreviousBankBalance = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchTransactionLogPreviousBankBalanceComparator = EnumQueryComparator.EQUAL;

	@ValidBankBalance
	private BigDecimal searchTransactionLogCurrentBankBalance = BigDecimal.TEN;

	@NotNull
	private Boolean useSearchTransactionLogCurrentBankBalance = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchTransactionLogCurrentBankBalanceComparator = EnumQueryComparator.EQUAL;

	private ReadOnlyLoginUser searchTransactionLogBankUser = null;

	@NotNull
	private Boolean useSearchTransactionLogBankUserId = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.LOGIC)
	private EnumQueryComparator searchTransactionLogBankUserComparator = EnumQueryComparator.EQUAL;

	@ShortComment
	private String searchTransactionLogComment;

	@NotNull
	private Boolean useSearchTransactionLogComment = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.TEXT)
	private EnumQueryComparator searchTransactionLogCommentComparator = EnumQueryComparator.EQUAL;

	public Long getSearchTransactionLogId() {
		return this.searchTransactionLogId;
	}

	public void setSearchTransactionLogId(Long searchTransactionLogId) {
		this.searchTransactionLogId = searchTransactionLogId;
	}

	public Boolean getUseSearchTransactionLogId() {
		return this.useSearchTransactionLogId;
	}

	public void setUseSearchTransactionLogId(Boolean useSearchTransactionLogId) {
		this.useSearchTransactionLogId = useSearchTransactionLogId;
	}

	public EnumQueryComparator getSearchTransactionLogIdComparator() {
		return this.searchTransactionLogIdComparator;
	}

	public void setSearchTransactionLogIdComparator(EnumQueryComparator searchTransactionLogIdComparator) {
		this.searchTransactionLogIdComparator = searchTransactionLogIdComparator;
	}

	public ReadOnlyAccount getSearchTransactionLogAccount() {
		return this.searchTransactionLogAccount;
	}

	public void setSearchTransactionLogAccount(ReadOnlyAccount searchTransactionLogAccount) {
		this.searchTransactionLogAccount = searchTransactionLogAccount;
	}

	public Boolean getUseSearchTransactionLogAccountId() {
		return this.useSearchTransactionLogAccountId;
	}

	public void setUseSearchTransactionLogAccountId(Boolean useSearchTransactionLogAccountId) {
		this.useSearchTransactionLogAccountId = useSearchTransactionLogAccountId;
	}

	public EnumQueryComparator getSearchTransactionLogAccountIdComparator() {
		return this.searchTransactionLogAccountIdComparator;
	}

	public void setSearchTransactionLogAccountIdComparator(EnumQueryComparator searchTransactionLogAccountIdComparator) {
		this.searchTransactionLogAccountIdComparator = searchTransactionLogAccountIdComparator;
	}

	public Date getSearchTransactionLogDateTime() {
		return this.searchTransactionLogDateTime;
	}

	public void setSearchTransactionLogDateTime(Date searchTransactionLogDateTime) {
		this.searchTransactionLogDateTime = searchTransactionLogDateTime;
	}

	public Boolean getUseSearchTransactionLogDateTime() {
		return this.useSearchTransactionLogDateTime;
	}

	public void setUseSearchTransactionLogDateTime(Boolean useSearchTransactionLogDateTime) {
		this.useSearchTransactionLogDateTime = useSearchTransactionLogDateTime;
	}

	public EnumQueryComparator getSearchTransactionLogDateTimeComparator() {
		return this.searchTransactionLogDateTimeComparator;
	}

	public void setSearchTransactionLogDateTimeComparator(EnumQueryComparator searchTransactionLogDateTimeComparator) {
		this.searchTransactionLogDateTimeComparator = searchTransactionLogDateTimeComparator;
	}

	public EnumAccountAction getSearchTransactionLogAction() {
		return this.searchTransactionLogAction;
	}

	public void setSearchTransactionLogAction(EnumAccountAction searchTransactionLogAction) {
		this.searchTransactionLogAction = searchTransactionLogAction;
	}

	public Boolean getUseSearchTransactionLogAction() {
		return this.useSearchTransactionLogAction;
	}

	public void setUseSearchTransactionLogAction(Boolean useSearchTransactionLogAction) {
		this.useSearchTransactionLogAction = useSearchTransactionLogAction;
	}

	public EnumQueryComparator getSearchTransactionLogActionComparator() {
		return this.searchTransactionLogActionComparator;
	}

	public void setSearchTransactionLogActionComparator(EnumQueryComparator searchTransactionLogActionComparator) {
		this.searchTransactionLogActionComparator = searchTransactionLogActionComparator;
	}

	public EnumAccountActionType getSearchTransactionLogActionType() {
		return this.searchTransactionLogActionType;
	}

	public void setSearchTransactionLogActionType(EnumAccountActionType searchTransactionLogActionType) {
		this.searchTransactionLogActionType = searchTransactionLogActionType;
	}

	public Boolean getUseSearchTransactionLogActionType() {
		return this.useSearchTransactionLogActionType;
	}

	public void setUseSearchTransactionLogActionType(Boolean useSearchTransactionLogActionType) {
		this.useSearchTransactionLogActionType = useSearchTransactionLogActionType;
	}

	public EnumQueryComparator getSearchTransactionLogActionTypeComparator() {
		return this.searchTransactionLogActionTypeComparator;
	}

	public void setSearchTransactionLogActionTypeComparator(EnumQueryComparator searchTransactionLogActionTypeComparator) {
		this.searchTransactionLogActionTypeComparator = searchTransactionLogActionTypeComparator;
	}

	public ReadOnlyAccount getSearchTransactionLogRelatedAccount() {
		return this.searchTransactionLogRelatedAccount;
	}

	public void setSearchTransactionLogRelatedAccount(ReadOnlyAccount searchTransactionLogRelatedAccount) {
		this.searchTransactionLogRelatedAccount = searchTransactionLogRelatedAccount;
	}

	public Boolean getUseSearchTransactionLogRelatedAccountId() {
		return this.useSearchTransactionLogRelatedAccountId;
	}

	public void setUseSearchTransactionLogRelatedAccountId(Boolean useSearchTransactionLogRelatedAccountId) {
		this.useSearchTransactionLogRelatedAccountId = useSearchTransactionLogRelatedAccountId;
	}

	public EnumQueryComparator getSearchTransactionLogRelatedAccountIdComparator() {
		return this.searchTransactionLogRelatedAccountIdComparator;
	}

	public void setSearchTransactionLogRelatedAccountIdComparator(EnumQueryComparator searchTransactionLogRelatedAccountIdComparator) {
		this.searchTransactionLogRelatedAccountIdComparator = searchTransactionLogRelatedAccountIdComparator;
	}

	public BigDecimal getSearchTransactionLogAmount() {
		return this.searchTransactionLogAmount;
	}

	public void setSearchTransactionLogAmount(BigDecimal searchTransactionLogAmount) {
		this.searchTransactionLogAmount = searchTransactionLogAmount;
	}

	public Boolean getUseSearchTransactionLogAmount() {
		return this.useSearchTransactionLogAmount;
	}

	public void setUseSearchTransactionLogAmount(Boolean useSearchTransactionLogAmount) {
		this.useSearchTransactionLogAmount = useSearchTransactionLogAmount;
	}

	public EnumQueryComparator getSearchTransactionLogAmountComparator() {
		return this.searchTransactionLogAmountComparator;
	}

	public void setSearchTransactionLogAmountComparator(EnumQueryComparator searchTransactionLogAmountComparator) {
		this.searchTransactionLogAmountComparator = searchTransactionLogAmountComparator;
	}

	public BigDecimal getSearchTransactionLogPreviousBankBalance() {
		return this.searchTransactionLogPreviousBankBalance;
	}

	public void setSearchTransactionLogPreviousBankBalance(BigDecimal searchTransactionLogPreviousBankBalance) {
		this.searchTransactionLogPreviousBankBalance = searchTransactionLogPreviousBankBalance;
	}

	public Boolean getUseSearchTransactionLogPreviousBankBalance() {
		return this.useSearchTransactionLogPreviousBankBalance;
	}

	public void setUseSearchTransactionLogPreviousBankBalance(Boolean useSearchTransactionLogPreviousBankBalance) {
		this.useSearchTransactionLogPreviousBankBalance = useSearchTransactionLogPreviousBankBalance;
	}

	public EnumQueryComparator getSearchTransactionLogPreviousBankBalanceComparator() {
		return this.searchTransactionLogPreviousBankBalanceComparator;
	}

	public void setSearchTransactionLogPreviousBankBalanceComparator(EnumQueryComparator searchTransactionLogPreviousBankBalanceComparator) {
		this.searchTransactionLogPreviousBankBalanceComparator = searchTransactionLogPreviousBankBalanceComparator;
	}

	public BigDecimal getSearchTransactionLogCurrentBankBalance() {
		return this.searchTransactionLogCurrentBankBalance;
	}

	public void setSearchTransactionLogCurrentBankBalance(BigDecimal searchTransactionLogCurrentBankBalance) {
		this.searchTransactionLogCurrentBankBalance = searchTransactionLogCurrentBankBalance;
	}

	public Boolean getUseSearchTransactionLogCurrentBankBalance() {
		return this.useSearchTransactionLogCurrentBankBalance;
	}

	public void setUseSearchTransactionLogCurrentBankBalance(Boolean useSearchTransactionLogCurrentBankBalance) {
		this.useSearchTransactionLogCurrentBankBalance = useSearchTransactionLogCurrentBankBalance;
	}

	public EnumQueryComparator getSearchTransactionLogCurrentBankBalanceComparator() {
		return this.searchTransactionLogCurrentBankBalanceComparator;
	}

	public void setSearchTransactionLogCurrentBankBalanceComparator(EnumQueryComparator searchTransactionLogCurrentBankBalanceComparator) {
		this.searchTransactionLogCurrentBankBalanceComparator = searchTransactionLogCurrentBankBalanceComparator;
	}

	public ReadOnlyLoginUser getSearchTransactionLogBankUser() {
		return this.searchTransactionLogBankUser;
	}

	public void setSearchTransactionLogBankUser(ReadOnlyLoginUser searchTransactionLogBankUser) {
		this.searchTransactionLogBankUser = searchTransactionLogBankUser;
	}

	public Boolean getUseSearchTransactionLogBankUserId() {
		return this.useSearchTransactionLogBankUserId;
	}

	public void setUseSearchTransactionLogBankUserId(Boolean useSearchTransactionLogBankUserId) {
		this.useSearchTransactionLogBankUserId = useSearchTransactionLogBankUserId;
	}

	public EnumQueryComparator getSearchTransactionLogBankUserComparator() {
		return this.searchTransactionLogBankUserComparator;
	}

	public void setSearchTransactionLogBankUserComparator(EnumQueryComparator searchTransactionLogBankUserComparator) {
		this.searchTransactionLogBankUserComparator = searchTransactionLogBankUserComparator;
	}

	public String getSearchTransactionLogComment() {
		return this.searchTransactionLogComment;
	}

	public void setSearchTransactionLogComment(String searchTransactionLogComment) {
		this.searchTransactionLogComment = searchTransactionLogComment;
	}

	public Boolean getUseSearchTransactionLogComment() {
		return this.useSearchTransactionLogComment;
	}

	public void setUseSearchTransactionLogComment(Boolean useSearchTransactionLogComment) {
		this.useSearchTransactionLogComment = useSearchTransactionLogComment;
	}

	public EnumQueryComparator getSearchTransactionLogCommentComparator() {
		return this.searchTransactionLogCommentComparator;
	}

	public void setSearchTransactionLogCommentComparator(EnumQueryComparator searchTransactionLogCommentComparator) {
		this.searchTransactionLogCommentComparator = searchTransactionLogCommentComparator;
	}

	@Override
	public EnumPermission getFindDynamicPermission() {
		return EnumPermission.ACCOUNT_VIEW_TRANSACTION_LOGS;
	}

	@Override
	public EnumPermission getPrintPermission() {
		return EnumPermission.ACCOUNT_PRINT_TRANSACTION_LOGS;
	}

	@Override
	public EnumPermission getExportPermission() {
		return EnumPermission.ACCOUNT_EXPORT_TRANSACTION_LOGS;
	}

	@Override
	protected List<? extends ReadOnlyTransactionLog> searchEntities() {
		return accountBean.findTransactionLogs(searchTransactionLogId, searchTransactionLogAccount != null ? searchTransactionLogAccount.getId() : 0l,
				Utils.asLocalDateTime(searchTransactionLogDateTime), searchTransactionLogAction, searchTransactionLogActionType,
				searchTransactionLogRelatedAccount != null ? searchTransactionLogRelatedAccount.getId() : null, searchTransactionLogAmount,
				searchTransactionLogPreviousBankBalance, searchTransactionLogCurrentBankBalance,
				searchTransactionLogBankUser != null ? searchTransactionLogBankUser.getId() : null, searchTransactionLogComment,
				useSearchTransactionLogId, useSearchTransactionLogAccountId, useSearchTransactionLogDateTime, useSearchTransactionLogAction,
				useSearchTransactionLogActionType, useSearchTransactionLogRelatedAccountId, useSearchTransactionLogAmount,
				useSearchTransactionLogPreviousBankBalance, useSearchTransactionLogCurrentBankBalance, useSearchTransactionLogBankUserId,
				useSearchTransactionLogComment, searchTransactionLogIdComparator, searchTransactionLogAccountIdComparator,
				searchTransactionLogDateTimeComparator, searchTransactionLogActionComparator, searchTransactionLogActionTypeComparator,
				searchTransactionLogRelatedAccountIdComparator, searchTransactionLogAmountComparator,
				searchTransactionLogPreviousBankBalanceComparator, searchTransactionLogCurrentBankBalanceComparator,
				searchTransactionLogBankUserComparator, searchTransactionLogCommentComparator);
	}

	@Override
	protected ReadOnlyTransactionLog entityGetter(Long entityId) {
		return accountBean.findTransactionLogById(entityId);
	}

	public String getCurrencySymbol() {
		return globalDataBean.getCurrencySymbol();
	}

}
