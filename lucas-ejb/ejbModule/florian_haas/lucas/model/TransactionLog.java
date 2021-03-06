package florian_haas.lucas.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import florian_haas.lucas.validation.*;

@Entity
@ValidTransactionLog
public class TransactionLog extends EntityBase implements ReadOnlyTransactionLog {

	private static final long serialVersionUID = 7946302440312827012L;

	@ManyToOne(optional = false, cascade = {
			CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE })
	@JoinColumn(nullable = false)
	@NotNull
	private Account account;

	@Basic(optional = false)
	@Column(nullable = false)
	@NotNull
	private LocalDateTime dateTime;

	@Basic(optional = false)
	@Column(nullable = false)
	@NotNull
	private EnumAccountAction action;

	@Basic(optional = false)
	@Column(nullable = false)
	@NotNull
	private EnumAccountActionType actionType;

	@OneToOne(optional = true, cascade = {
			CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE })
	@JoinColumn(nullable = true)
	private Account relatedAccount;

	@Basic(optional = false)
	@Column(nullable = false, precision = 38, scale = 7)
	@ValidTransactionAmount
	private BigDecimal amount;

	@Basic(optional = false)
	@Column(nullable = false, precision = 38, scale = 7)
	@ValidBankBalance
	private BigDecimal previousBankBalance;

	@OneToOne(optional = true, cascade = {
			CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE })
	@JoinColumn(nullable = true)
	private LoginUser bankUser;

	@Basic(optional = true)
	@Column(nullable = true)
	@ShortComment
	private String comment;

	TransactionLog() {}

	public TransactionLog(Account account, LocalDateTime dateTime, EnumAccountAction action, EnumAccountActionType actionType, Account relatedAccount,
			BigDecimal amount, BigDecimal prevBankBalance, LoginUser bankUser, String comment) {
		this.account = account;
		this.dateTime = dateTime;
		this.action = action;
		this.actionType = actionType;
		this.relatedAccount = relatedAccount;
		this.amount = amount;
		this.previousBankBalance = prevBankBalance;
		this.bankUser = bankUser;
		this.comment = comment;
	}

	@Override
	public Account getAccount() {
		return account;
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

	@Override
	public Account getRelatedAccount() {
		return relatedAccount;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public BigDecimal getPreviousBankBalance() {
		return this.previousBankBalance;
	}

	public BigDecimal getCurrentBankBalance() {
		return this.previousBankBalance.add(this.action == EnumAccountAction.CREDIT ? this.amount : this.amount.negate());
	}

	public LoginUser getBankUser() {
		return this.bankUser;
	}

	public String getComment() {
		return this.comment;
	}
}
