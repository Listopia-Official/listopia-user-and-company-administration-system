package florian_haas.lucas.persistence.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import javax.persistence.criteria.*;

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;

@JPADAO
public class TransactionLogDAOImpl extends ReadOnlyDAOImpl<TransactionLog> implements TransactionLogDAO {

	@Override
	@SuppressWarnings("unchecked")
	public List<TransactionLog> findTransactionLogs(Long id, Long accountId, LocalDateTime dateTime, EnumAccountAction action,
			EnumAccountActionType actionType, Long relatedAccountId, BigDecimal amount, BigDecimal previousBankBalance, BigDecimal currentBankBalance,
			Long bankUserId, String comment, Boolean useId, Boolean useAccountId, Boolean useDateTime, Boolean useAction, Boolean useActionType,
			Boolean useRelatedAccountId, Boolean useAmount, Boolean usePreviousBankBalance, Boolean useCurrentBankBalance, Boolean useBankUser,
			Boolean useComment, EnumQueryComparator idComparator, EnumQueryComparator accountIdComparator, EnumQueryComparator dateTimeComparator,
			EnumQueryComparator actionComparator, EnumQueryComparator actionTypeComparator, EnumQueryComparator relatedAccountIdComparator,
			EnumQueryComparator amountComparator, EnumQueryComparator previousBankBalanceComparator, EnumQueryComparator currentBankBalanceComparator,
			EnumQueryComparator bankUserIdComparator, EnumQueryComparator commentComparator) {
		return this.readOnlyCriteriaQuery((query, root, builder) -> {
			List<Predicate> predicates = new ArrayList<>();

			getSingularRestriction(TransactionLog_.id, id, useId, idComparator, predicates, builder, root);
			getSingularRestriction(Account_.id, accountId, useAccountId, accountIdComparator, predicates, builder,
					root.join(TransactionLog_.account));
			getSingularRestriction(TransactionLog_.dateTime, dateTime, useDateTime, dateTimeComparator, predicates, builder, root);
			getSingularRestriction(TransactionLog_.action, action, useAction, actionComparator, predicates, builder, root);
			getSingularRestriction(TransactionLog_.actionType, actionType, useActionType, actionTypeComparator, predicates, builder, root);
			getSingularRestriction(Account_.id, relatedAccountId, useRelatedAccountId, relatedAccountIdComparator, predicates, builder,
					root.join(TransactionLog_.relatedAccount, JoinType.LEFT));
			getSingularRestriction(TransactionLog_.amount, amount, useAmount, amountComparator, predicates, builder, root);
			getSingularRestriction(TransactionLog_.previousBankBalance, previousBankBalance, usePreviousBankBalance, previousBankBalanceComparator,
					predicates, builder, root);
			Path<BigDecimal> amountPath = root.get(TransactionLog_.amount);
			getSingularRestriction(
					builder.sum(root.get(TransactionLog_.previousBankBalance),
							(Expression<BigDecimal>) (Expression<?>) builder.selectCase()
									.when(builder.equal(root.get(TransactionLog_.action), EnumAccountAction.CREDIT), amountPath)
									.otherwise(builder.neg(amountPath))),
					currentBankBalance, useCurrentBankBalance, currentBankBalanceComparator, predicates, builder, root);
			getSingularRestriction(LoginUser_.id, bankUserId, useBankUser, bankUserIdComparator, predicates, builder,
					root.join(TransactionLog_.bankUser, JoinType.LEFT));
			getSingularRestriction(TransactionLog_.comment, comment, useComment, commentComparator, predicates, builder, root);
			return predicates;
		});
	}
}
