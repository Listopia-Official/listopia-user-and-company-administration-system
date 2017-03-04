package florian_haas.lucas.business;

import static florian_haas.lucas.security.EnumPermission.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.validation.*;
import javax.validation.executable.*;

import org.apache.shiro.authz.AuthorizationException;

import florian_haas.lucas.database.*;
import florian_haas.lucas.model.*;
import florian_haas.lucas.model.validation.*;
import florian_haas.lucas.security.*;
import florian_haas.lucas.util.Utils;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@ValidateOnExecution(type = ExecutableType.IMPLICIT)
@Secured
public class AccountBean implements AccountBeanLocal {

	@Inject
	@JPADAO
	private AccountDAO accountDao;

	@Inject
	@JPADAO
	private AccountOwnerDAO accountOwnerDao;

	@EJB
	private GlobalDataBeanLocal globalData;

	@EJB
	private LoginBeanLocal loginBean;

	@Resource
	private Validator validator;

	@EJB
	private EntityBeanLocal entityBean;

	@Override
	@RequiresPermissions(ACCOUNT_PAY_IN)

	public Long payIn(Long id, BigDecimal amount, String comment) {
		return transaction(amount, id, null, comment);
	}

	@Override
	@RequiresPermissions(ACCOUNT_PAY_OUT)
	public Long payOut(Long id, BigDecimal amount, String comment) {
		return transaction(amount.negate(), id, null, comment);
	}

	@Override
	@RequiresPermissions(ACCOUNT_TRANSACTION)
	public Long transaction(Long from, Long to, BigDecimal amount, String comment) {
		return transaction(amount, from, to, comment);
	}

	private Long transaction(BigDecimal amount, Long account1Id, Long account2Id, String comment) {
		Account account1 = accountDao.findById(account1Id);
		Account account2 = null;
		if (account2Id != null) {
			account2 = accountDao.findById(account2Id);
		}

		if (account1.getIsProtected() && !loginBean.getSubject().isPermitted(ACCOUNT_TRANSACTION_FROM_PROTECTED.getPermissionString()))
			throw new AuthorizationException(
					loginBean.getSubject().getPrincipal() + " is not permitted to start a transaction from a protected account");

		if (account2 != null && account2.getIsProtected()
				&& !loginBean.getSubject().isPermitted(ACCOUNT_TRANSACTION_TO_PROTECTED.getPermissionString()))
			throw new AuthorizationException(
					loginBean.getSubject().getPrincipal() + " is not permitted to start a transaction to a protected account");

		if (Utils.isGreatherThan(amount.abs(), globalData.getTransactionLimit())
				&& !loginBean.getSubject().isPermitted(ACCOUNT_IGNORE_TRANSACTION_LIMIT.getPermissionString()))
			throw new AuthorizationException(loginBean.getSubject().getPrincipal() + " is not permitted to ignore transaction limit");

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
		User user = null;
		try {
			Long userId = Long.valueOf(loginBean.getSubject().getPrincipal().toString());
			if (userId != null) {
				if (entityBean.exists(userId, User.class)) {
					LoginUser tmp = loginBean.findLoginUserById(userId);
					if (tmp != null) {
						user = tmp.getUser();
					}
				}
			}
		}
		catch (NumberFormatException e) {}
		account1.addTransactionLog(
				new TransactionLog(account1, dateTime, action, type, account2, transactionAmount, prevBankBalance1, user, comment));

		if (account2 != null) {
			final BigDecimal prevBankBalance2 = account2.getBankBalance();
			account2.setBankBalance(account2.getBankBalance().add(transactionAmount));

			account2.addTransactionLog(new TransactionLog(account2, dateTime, EnumAccountAction.CREDIT, EnumAccountActionType.TRANSACTION, account1,
					transactionAmount, prevBankBalance2, user, comment));
		}
		accountDao.getEntityManager().flush();
		return account1.getId();
	}

	@Override
	@RequiresPermissions(ACCOUNT_BLOCK)
	public Boolean blockAccount(Long id) {
		Account account = accountDao.findById(id);
		if (account.getBlocked() == Boolean.TRUE) {
			return Boolean.FALSE;
		} else {
			account.setBlocked(Boolean.TRUE);
			return Boolean.TRUE;
		}
	}

	@Override
	@RequiresPermissions(ACCOUNT_UNBLOCK)
	public Boolean unblockAccount(Long id) {
		Account account = accountDao.findById(id);
		if (account.getBlocked() == Boolean.TRUE) {
			account.setBlocked(Boolean.FALSE);
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	@Override
	@RequiresPermissions(ACCOUNT_PROTECT)
	public Boolean protect(Long id) {
		Account account = accountDao.findById(id);
		if (account.getIsProtected() == Boolean.FALSE) {
			account.setIsProtected(Boolean.TRUE);
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	@Override
	@RequiresPermissions(ACCOUNT_UNPROTECT)
	public Boolean unprotect(Long id) {
		Account account = accountDao.findById(id);
		if (account.getIsProtected() == Boolean.TRUE) {
			account.setIsProtected(Boolean.FALSE);
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	@Override
	@RequiresPermissions(ACCOUNT_FIND_ALL)
	public List<Account> findAll() {
		return accountDao.findAll();
	}

	@Override
	@RequiresPermissions(ACCOUNT_FIND_BY_ID)
	public Account findById(Long id) {
		return accountDao.findById(id);
	}

	@Override
	@RequiresPermissions(ACCOUNT_FIND_DYNAMIC)
	public List<Account> findAccounts(Long id, Long ownerId, EnumAccountOwnerType ownerType, BigDecimal bankBalance, Boolean blocked,
			Boolean isProtected, Boolean useId, Boolean useOwnerId, Boolean useOwnerType, Boolean useBankBalance, Boolean useBlocked,
			Boolean useProtected, EnumQueryComparator idComparator, EnumQueryComparator ownerIdComparator, EnumQueryComparator ownerTypeComparator,
			EnumQueryComparator bankBalanceComparator) {
		return accountDao.findAccounts(id, ownerId, ownerType, bankBalance, blocked, isProtected, useId, useOwnerId, useOwnerType, useBankBalance,
				useBlocked, useProtected, idComparator, ownerIdComparator, ownerTypeComparator, bankBalanceComparator);
	}

	@Override
	@RequiresPermissions(ACCOUNT_FIND_ACCOUNT_OWNER)
	public AccountOwner findAccountOwnerById(@ValidEntityId(entityClass = AccountOwner.class) Long id) {
		return accountOwnerDao.findById(id);
	}
}
