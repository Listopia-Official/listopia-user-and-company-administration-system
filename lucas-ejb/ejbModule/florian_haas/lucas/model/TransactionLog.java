package florian_haas.lucas.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionLog extends EntityBase {

	private static final long serialVersionUID = 7946302440312827012L;

	private Account account;
	private LocalDateTime dateTime;
	private EnumAccountAction action;
	private EnumAccountActionType actionType;
	private Account relatedAccount;
	private BigDecimal amount;
	private BigDecimal previousBankBalance;
	private User bankUser;
	private String comment;

	public TransactionLog(Account account, LocalDateTime dateTime, EnumAccountAction action, EnumAccountActionType actionType,
			Account relatedAccount, BigDecimal amount, BigDecimal prevBankBalance, User bankUser, String comment) {
		this.account = account;
		this.dateTime = dateTime;
		this.action = action;
		this.actionType = actionType;
		this.relatedAccount = relatedAccount;
		this.amount = action == EnumAccountAction.DEBIT ? amount.negate() : amount;
		this.previousBankBalance = prevBankBalance;
		this.bankUser = bankUser;
		this.comment = comment;
	}

	public Account getAccount() {
		return this.account;
	}

	public LocalDateTime getDateTime() {
		return this.dateTime;
	}

	public EnumAccountAction getAction() {
		return this.action;
	}

	public EnumAccountActionType getActionType() {
		return this.actionType;
	}

	public Account getRelatedAccount() {
		return this.relatedAccount;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public BigDecimal getPreviousBankBalance() {
		return this.previousBankBalance;
	}

	public BigDecimal getCurrentBankBalance() {
		return this.previousBankBalance.add(this.amount);
	}

	public User getBankUser() {
		return this.bankUser;
	}

	public String getComment() {
		return this.comment;
	}
}
