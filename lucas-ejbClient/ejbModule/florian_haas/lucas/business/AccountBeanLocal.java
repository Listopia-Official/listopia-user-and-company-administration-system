package florian_haas.lucas.business;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.ejb.Local;
import javax.validation.constraints.*;

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;
import florian_haas.lucas.validation.*;

@Local
public interface AccountBeanLocal {

	public static final String NO_PERMISSION_FOR_TRANSACTION_FROM_PROTECTED_EXCEPTION_MARKER = "noTransactionFromProtected";
	public static final String NO_PERMISSION_FOR_TRANSACTION_TO_PROTECTED_EXCEPTION_MARKER = "noTransactionToProtected";
	public static final String NO_PERMISSION_FOR_EXCEEDING_TRANSACTION_LIMIT_EXCEPTION_MARKER = "illegalTransactionAmount";
	public static final String FROM_BLOCKED_EXCEPTION_MARKER = "fromIsBlocked";
	public static final String TO_BLOCKED_EXCEPTION_MARKER = "toIsBlocked";
	public static final String TRANSACTION_AMOUNT_GREATER_THAN_BANK_BALANCE_EXCEPTION_MARKER = "transactionAmountGreaterThanBankBalance";
	public static final String SAME_ACCOUNT_AS_SOURCE_AND_TARGET_EXCEPTION_MARKER = "sameAccountAsSourceAndTarget";
	public static final String NOT_ENOUGH_MONEY_IN_CIRCULATION_EXCEPTION_MARKER = "notEnoughMoneyInCirculation";
	public static final String NOT_ENOUGH_REAL_MONEY_EXCEPTION_MARKER = "notEnoughRealMoney";

	public void payIn(@ValidEntityId(entityClass = ReadOnlyAccount.class) Long account, @ValidTransactionAmount BigDecimal amount,
			@ShortComment String comment);

	public Boolean payOut(@ValidEntityId(entityClass = ReadOnlyAccount.class) Long account, @ValidNullableTransactionAmount BigDecimal amount,
			@ShortComment String comment);

	public Boolean transaction(@ValidEntityId(entityClass = ReadOnlyAccount.class) Long from,
			@ValidEntityId(entityClass = ReadOnlyAccount.class) Long to, @ValidNullableTransactionAmount BigDecimal amount,
			@ShortComment String comment);

	public void undoTransaction(@ValidEntityId(entityClass = ReadOnlyTransactionLog.class) Long transactionLogId, @ShortComment String comment);

	public Boolean blockAccount(@ValidEntityId(entityClass = ReadOnlyAccount.class) Long id);

	public Boolean unblockAccount(@ValidEntityId(entityClass = ReadOnlyAccount.class) Long id);

	public Boolean protect(@ValidEntityId(entityClass = ReadOnlyAccount.class) Long id);

	public Boolean unprotect(@ValidEntityId(entityClass = ReadOnlyAccount.class) Long id);

	public List<? extends ReadOnlyAccount> findAll();

	public ReadOnlyAccount findById(@ValidEntityId(entityClass = ReadOnlyAccount.class) Long id);

	public ReadOnlyAccount findByIdIfExists(Long id);

	public List<? extends ReadOnlyAccount> findAccounts(@NotNull Long id, Long ownerId, @NotNull EnumAccountOwnerType ownerType,
			@NotNull BigDecimal bankBalance, @NotNull Boolean blocked, @NotNull Boolean isProtected, @NotNull Boolean useId, Boolean useOwnerId,
			@NotNull Boolean useOwnerType, @NotNull Boolean useBankBalance, @NotNull Boolean useBlocked, @NotNull Boolean useIsProtected,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator idComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.LOGIC) EnumQueryComparator ownerIdComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.LOGIC) EnumQueryComparator ownerTypeComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator bankBalanceComparator);

	public ReadOnlyAccountOwner findAccountOwnerById(@ValidEntityId(entityClass = ReadOnlyAccountOwner.class) Long id);

	public ReadOnlyAccountOwner findAccountOwnerByIdIfExists(Long id);

	public ReadOnlyTransactionLog findTransactionLogById(@ValidEntityId(entityClass = ReadOnlyTransactionLog.class) Long id);

	public List<? extends ReadOnlyTransactionLog> findTransactionLogs(@NotNull Long id, @NotNull Long accountId, @NotNull LocalDateTime dateTime,
			@NotNull EnumAccountAction action, @NotNull EnumAccountActionType actionType, Long relatedAccountId, @NotNull BigDecimal amount,
			@NotNull BigDecimal previousBankBalance, @NotNull BigDecimal currentBankBalance, Long bankUserId, String comment, @NotNull Boolean useId,
			@NotNull Boolean useAccountId, @NotNull Boolean useDateTime, @NotNull Boolean useAction, @NotNull Boolean useActionType,
			@NotNull Boolean useRelatedAccountId, @NotNull Boolean useAmount, @NotNull Boolean usePreviousBankBalance,
			@NotNull Boolean useCurrentBankBalance, @NotNull Boolean useBankUser, @NotNull Boolean useComment,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator idComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.LOGIC) EnumQueryComparator accountIdComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator dateTimeComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.LOGIC) EnumQueryComparator actionComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.LOGIC) EnumQueryComparator actionTypeComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.LOGIC) EnumQueryComparator relatedAccountIdComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator amountComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator previousBankBalanceComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator currentBankBalanceComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.LOGIC) EnumQueryComparator bankUserIdComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.TEXT) EnumQueryComparator commentComparator);

	public List<? extends ReadOnlyAccount> getAccountsByData(@NotNull String data, @NotNull @Min(1) Integer resultsCount);

	public List<? extends ReadOnlyAccountOwner> getAccountOwnersByData(@NotNull String data, @NotNull @Min(1) Integer resultsCount);

	public BigDecimal getTotalMoneyInAccounts();

	public void exchangeRealCurrencyToFictional(@ValidTransactionAmount BigDecimal amount,
			@ValidEntityId(entityClass = ReadOnlyAccount.class, nullable = true) Long transferToAccount, @ShortComment String transactionComment);

	public Boolean exchangeFictionalCurrencyToReal(@ValidEntityId(entityClass = ReadOnlyAccount.class, nullable = true) Long fromAccount,
			@ValidTransactionAmount BigDecimal amount, @NotNull Boolean all, @ShortComment String transactionComment);

}
