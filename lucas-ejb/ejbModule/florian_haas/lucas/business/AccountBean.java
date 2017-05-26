package florian_haas.lucas.business;

import static florian_haas.lucas.security.EnumPermission.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.validation.Validator;
import javax.validation.executable.*;

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;
import florian_haas.lucas.security.*;
import florian_haas.lucas.util.Utils;

@Stateless
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

	@Inject
	@JPADAO
	private TransactionLogDAO transactionLogDao;

	@EJB
	private UserBeanLocal userBean;

	@EJB
	private CompanyBeanLocal companyBean;

	@Override
	@RequiresPermissions(ACCOUNT_PAY_IN)
	public void payIn(Long id, BigDecimal amount, String comment) {
		transaction(amount, id, null, comment);
	}

	@Override
	@RequiresPermissions(ACCOUNT_PAY_OUT)
	public Boolean payOut(Long id, BigDecimal amount, String comment) {
		return transaction(amount != null ? amount.negate() : null, id, null, comment);
	}

	@Override
	@RequiresPermissions(ACCOUNT_TRANSACTION)
	public Boolean transaction(Long from, Long to, BigDecimal amount, String comment) {
		return transaction(amount, from, to, comment);
	}

	private Boolean transaction(BigDecimal amount, Long account1Id, Long account2Id, String comment) {
		Account account1 = accountDao.findById(account1Id);
		Account account2 = null;
		if (account2Id != null) {
			account2 = accountDao.findById(account2Id);
		}

		if (account1.getIsProtected() && !loginBean.getSubject().isPermitted(ACCOUNT_TRANSACTION_FROM_PROTECTED.getPermissionString()))
			throw new LucasException(loginBean.getSubject().getPrincipal() + " is not permitted to start a transaction from a protected account",
					NO_PERMISSION_FOR_TRANSACTION_FROM_PROTECTED_EXCEPTION_MARKER);

		if (account2 != null && account2.getIsProtected()
				&& !loginBean.getSubject().isPermitted(ACCOUNT_TRANSACTION_TO_PROTECTED.getPermissionString()))
			throw new LucasException(loginBean.getSubject().getPrincipal() + " is not permitted to start a transaction to a protected account",
					NO_PERMISSION_FOR_TRANSACTION_TO_PROTECTED_EXCEPTION_MARKER);

		BigDecimal transactionAmount = amount == null ? account2 == null ? account1.getBankBalance().negate() : account1.getBankBalance() : amount;

		if (!Utils.isZero(transactionAmount)) {
			if (Utils.isGreatherThan(transactionAmount.abs(), globalData.getTransactionLimit())
					&& !loginBean.getSubject().isPermitted(ACCOUNT_IGNORE_TRANSACTION_LIMIT.getPermissionString()))
				throw new LucasException(loginBean.getSubject().getPrincipal() + " is not permitted to ignore transaction limit",
						NO_PERMISSION_FOR_EXCEEDING_TRANSACTION_LIMIT);

			if (account1.getBlocked()) throw new LucasException("Account (transaction source) is blocked", FROM_BLOCKED);

			if (account2 != null && account2.getBlocked()) throw new LucasException("Account (transaction target) is blocked", TO_BLOCKED);

			EnumAccountActionType type = EnumAccountActionType.BANK;

			if (account2 != null) {
				type = EnumAccountActionType.TRANSACTION;
				transactionAmount = transactionAmount.negate();
			}

			final EnumAccountAction action = Utils.isLessThanZero(transactionAmount) ? EnumAccountAction.DEBIT : EnumAccountAction.CREDIT;

			final BigDecimal prevBankBalance1 = account1.getBankBalance();

			if (action == EnumAccountAction.DEBIT && Utils.isLessThanZero(prevBankBalance1.add(transactionAmount)))
				throw new LucasException("The transaction amount is greater than the bank balabnce of the account",
						TRANSACTION_AMOUNT_GREATER_THAN_BANK_BALANCE);

			account1.setBankBalance(account1.getBankBalance().add(transactionAmount));

			transactionAmount = transactionAmount.abs();

			final LocalDateTime dateTime = LocalDateTime.now();
			ReadOnlyLoginUser user = loginBean.findLoginUserByUsername(loginBean.getSubject().getPrincipal().toString());

			account1.addTransactionLog(
					new TransactionLog(account1, dateTime, action, type, account2, transactionAmount, prevBankBalance1, (LoginUser) user, comment));

			if (account2 != null) {
				final BigDecimal prevBankBalance2 = account2.getBankBalance();
				account2.setBankBalance(account2.getBankBalance().add(transactionAmount));

				account2.addTransactionLog(new TransactionLog(account2, dateTime, EnumAccountAction.CREDIT, EnumAccountActionType.TRANSACTION,
						account1, transactionAmount, prevBankBalance2, (LoginUser) user, comment));
			}
			accountDao.flush();
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
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
	public List<? extends ReadOnlyAccount> findAll() {
		return accountDao.findAll();
	}

	@Override
	@RequiresPermissions(ACCOUNT_FIND_BY_ID)
	public Account findById(Long id) {
		return accountDao.findById(id);
	}

	@Override
	@RequiresPermissions(ACCOUNT_FIND_DYNAMIC)
	public List<? extends ReadOnlyAccount> findAccounts(Long id, Long ownerId, EnumAccountOwnerType ownerType, BigDecimal bankBalance,
			Boolean blocked, Boolean isProtected, Boolean useId, Boolean useOwnerId, Boolean useOwnerType, Boolean useBankBalance, Boolean useBlocked,
			Boolean useProtected, EnumQueryComparator idComparator, EnumQueryComparator ownerIdComparator, EnumQueryComparator ownerTypeComparator,
			EnumQueryComparator bankBalanceComparator) {
		return accountDao.findAccounts(id, ownerId, ownerType, bankBalance, blocked, isProtected, useId, useOwnerId, useOwnerType, useBankBalance,
				useBlocked, useProtected, idComparator, ownerIdComparator, ownerTypeComparator, bankBalanceComparator);
	}

	@Override
	@RequiresPermissions(ACCOUNT_FIND_ACCOUNT_OWNER)
	public AccountOwner findAccountOwnerById(Long id) {
		return accountOwnerDao.findById(id);
	}

	@Override
	@RequiresPermissions(ACCOUNT_FIND_TRANSACTION_LOG_BY_ID)
	public TransactionLog findTransactionLogById(Long id) {
		return transactionLogDao.findById(id);
	}

	@Override
	@RequiresPermissions(ACCOUNT_VIEW_TRANSACTION_LOGS)
	public List<? extends ReadOnlyTransactionLog> findTransactionLogs(Long id, Long accountId, LocalDateTime dateTime, EnumAccountAction action,
			EnumAccountActionType actionType, Long relatedAccountId, BigDecimal amount, BigDecimal previousBankBalance, BigDecimal currentBankBalance,
			Long bankUserId, String comment, Boolean useId, Boolean useAccountId, Boolean useDateTime, Boolean useAction, Boolean useActionType,
			Boolean useRelatedAccountId, Boolean useAmount, Boolean usePreviousBankBalance, Boolean useCurrentBankBalance, Boolean useBankUser,
			Boolean useComment, EnumQueryComparator idComparator, EnumQueryComparator accountIdComparator, EnumQueryComparator dateTimeComparator,
			EnumQueryComparator actionComparator, EnumQueryComparator actionTypeComparator, EnumQueryComparator relatedAccountIdComparator,
			EnumQueryComparator amountComparator, EnumQueryComparator previousBankBalanceComparator, EnumQueryComparator currentBankBalanceComparator,
			EnumQueryComparator bankUserIdComparator, EnumQueryComparator commentComparator) {
		return transactionLogDao.findTransactionLogs(id, accountId, dateTime, action, actionType, relatedAccountId, amount, previousBankBalance,
				currentBankBalance, bankUserId, comment, useId, useAccountId, useDateTime, useAction, useActionType, useRelatedAccountId, useAmount,
				usePreviousBankBalance, useCurrentBankBalance, useBankUser, useComment, idComparator, accountIdComparator, dateTimeComparator,
				actionComparator, actionTypeComparator, relatedAccountIdComparator, amountComparator, previousBankBalanceComparator,
				currentBankBalanceComparator, bankUserIdComparator, commentComparator);
	}

	@Override
	@RequiresPermissions(ACCOUNT_FIND_BY_ID)
	public ReadOnlyAccount findByIdIfExists(Long id) {
		return accountDao.findById(id);
	}

	@Override
	@RequiresPermissions(ACCOUNT_FIND_ACCOUNT_OWNER)
	public ReadOnlyAccountOwner findAccountOwnerByIdIfExists(Long id) {
		return accountOwnerDao.findById(id);
	}

	@Override
	@RequiresPermissions(ACCOUNT_FIND_BY_DATA)
	public List<? extends ReadOnlyAccount> getAccountsByData(String data, Integer resultsCount) {
		return accountDao.getAccountsFromData(data, resultsCount);
	}

	@Override
	@RequiresPermissions(ACCOUNT_FIND_OWNER_BY_DATA)
	public List<? extends ReadOnlyAccountOwner> getAccountOwnersByData(String data, Integer resultsCount) {
		return accountOwnerDao.getAccountOwnersFromData(data, resultsCount);
	}

	@Override
	@RequiresPermissions(ACCOUNT_UNDO_TRANSACTION)
	public void undoTransaction(Long transactionLogId, String comment) {
		TransactionLog log = transactionLogDao.findById(transactionLogId);
		Account relatedAccount = log.getRelatedAccount();
		if (relatedAccount == null) {
			transaction(log.getAction() == EnumAccountAction.CREDIT ? log.getAmount().negate() : log.getAmount(), log.getAccount().getId(), null,
					comment);
		} else {
			transaction(log.getAmount().abs(), relatedAccount.getId(), log.getAccount().getId(), comment);
		}
	}
}
