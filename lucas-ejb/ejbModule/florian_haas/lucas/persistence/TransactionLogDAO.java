package florian_haas.lucas.persistence;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import florian_haas.lucas.model.*;

public interface TransactionLogDAO extends ReadOnlyDAO<TransactionLog> {

	public List<TransactionLog> findTransactionLogs(Long id, Long accountId, LocalDateTime dateTime, EnumAccountAction action,
			EnumAccountActionType actionType, Long relatedAccountId, BigDecimal amount, BigDecimal previousBankBalance, BigDecimal currentBankBalance,
			Long bankUserId, String comment, Boolean useId, Boolean useAccountId, Boolean useDateTime, Boolean useAction, Boolean useActionType,
			Boolean useRelatedAccountId, Boolean useAmount, Boolean usePreviousBankBalance, Boolean useCurrentBankBalance, Boolean useBankUser,
			Boolean useComment, EnumQueryComparator idComparator, EnumQueryComparator accountIdComparator, EnumQueryComparator dateTimeComparator,
			EnumQueryComparator actionComparator, EnumQueryComparator actionTypeComparator, EnumQueryComparator relatedAccountIdComparator,
			EnumQueryComparator amountComparator, EnumQueryComparator previousBankBalanceComparator, EnumQueryComparator currentBankBalanceComparator,
			EnumQueryComparator bankUserIdComparator, EnumQueryComparator commentComparator);

}
