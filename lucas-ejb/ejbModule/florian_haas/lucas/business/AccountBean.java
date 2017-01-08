package florian_haas.lucas.business;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.validation.*;
import javax.validation.executable.*;

import florian_haas.lucas.database.*;
import florian_haas.lucas.model.*;
import florian_haas.lucas.model.validation.*;
import florian_haas.lucas.util.Utils;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@ValidateOnExecution(type = ExecutableType.IMPLICIT)
public class AccountBean implements AccountBeanLocal {

	@Inject
	@JPADAO
	private AccountDAO accountDao;

	@Resource
	private Validator validator;

	@Override
	public Account payIn(@ValidEntityId(entityClass = Account.class) Long id, BigDecimal amount, String comment) {
		return transaction(amount, id, null, comment);
	}

	@Override
	public Account payOut(@ValidEntityId(entityClass = Account.class) Long id, BigDecimal amount, String comment) {
		return transaction(amount, id, null, comment);
	}

	@Override
	public Account transaction(@ValidEntityId(entityClass = Account.class) Long from, @ValidEntityId(entityClass = Account.class) Long to,
			BigDecimal amount, String comment) {
		return transaction(amount, from, to, comment);
	}

	private Account transaction(BigDecimal amount, Long account1Id, Long account2Id, String comment) {
		Account account1 = accountDao.findById(account1Id);
		Account account2 = null;
		if (account2Id != null) {
			account2 = accountDao.findById(account2Id);
		}

		Set<ConstraintViolation<Account>> violations = validator.validate(account1, UnblockedAccountRequiredValidationGroup.class);
		if (!violations.isEmpty()) throw new ConstraintViolationException(violations);

		if (account2 != null) violations = validator.validate(account2, UnblockedAccountRequiredValidationGroup.class);
		if (!violations.isEmpty()) throw new ConstraintViolationException(violations);

		BigDecimal transactionAmount = amount;
		EnumAccountActionType type = EnumAccountActionType.BANK;

		if (account2 != null) {
			type = EnumAccountActionType.TRANSACTION;
			transactionAmount = transactionAmount.negate();
		}

		final EnumAccountAction action = Utils.isLessThanZero(transactionAmount) ? EnumAccountAction.DEBIT : EnumAccountAction.CREDIT;

		final BigDecimal prevBankBalance1 = account1.getBankBalance();
		account1.setBankBalance(account1.getBankBalance().add(transactionAmount));

		transactionAmount = transactionAmount.abs();

		final LocalDateTime dateTime = LocalDateTime.now();
		// TODO: User in Transactionlog
		account1.getTransactionLogs()
				.add(new TransactionLog(account1, dateTime, action, type, account2, transactionAmount, prevBankBalance1, null, comment));

		if (account2 != null) {
			final BigDecimal prevBankBalance2 = account2.getBankBalance();
			account2.setBankBalance(account2.getBankBalance().add(transactionAmount));
			// TODO: User in Transactionlog
			account2.getTransactionLogs().add(new TransactionLog(account2, dateTime, EnumAccountAction.CREDIT, EnumAccountActionType.TRANSACTION,
					account1, transactionAmount, prevBankBalance2, null, comment));
		}
		accountDao.getEntityManager().flush();
		return account1;
	}

	@Override
	public Boolean blockAccount(@ValidEntityId(entityClass = Account.class) Long id) {
		Account account = accountDao.findById(id);
		if (account.getBlocked() == Boolean.TRUE) {
			return Boolean.FALSE;
		} else {
			account.setBlocked(Boolean.TRUE);
			return Boolean.TRUE;
		}
	}

	@Override
	public Boolean unblockAccount(@ValidEntityId(entityClass = Account.class) Long id) {
		Account account = accountDao.findById(id);
		if (account.getBlocked() == Boolean.TRUE) {
			account.setBlocked(Boolean.FALSE);
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	@Override
	public List<Account> findAll() {
		return accountDao.findAll();
	}

	@Override
	public Account findById(@ValidEntityId(entityClass = Account.class) Long id) {
		return accountDao.findById(id);
	}

	@Override
	public List<Account> findAccounts(Long id, EnumAccountOwnerType ownerType, BigDecimal bankBalance, Boolean blocked, Boolean useId,
			Boolean useOwnerType, Boolean useBankBalance, Boolean useBlocked, EnumQueryComparator idComparator,
			EnumQueryComparator bankBalanceComparator) {
		return accountDao.findAccounts(id, ownerType, bankBalance, blocked, useId, useOwnerType, useBankBalance, useBlocked, idComparator,
				bankBalanceComparator);
	}

}
