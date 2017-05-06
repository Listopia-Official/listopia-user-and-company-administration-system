package florian_haas.lucas.model;

import java.math.BigDecimal;
import java.util.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

import florian_haas.lucas.validation.*;

@Entity
public class Account extends EntityBase implements ReadOnlyAccount {

	private static final long serialVersionUID = -8880641005184863518L;

	@OneToOne(mappedBy = "account", optional = false)
	@NotNull
	private AccountOwner owner;

	@Basic(optional = false)
	@Column(nullable = false, scale = 7, precision = 38)
	@ValidBankBalance
	private BigDecimal bankBalance = BigDecimal.ZERO;

	@Basic(optional = false)
	@Column(nullable = false)
	@AssertFalse(groups = UnblockedAccountRequiredValidationGroup.class)
	@NotNull
	private Boolean blocked = Boolean.FALSE;

	@Basic(optional = false)
	@Column(nullable = false)
	@NotNull
	private Boolean isProtected = Boolean.FALSE;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "account")
	@Valid
	@NotNull
	private List<@TypeNotNull TransactionLog> transactionLogs = new ArrayList<>();

	Account() {}

	Account(AccountOwner owner) {
		this.owner = owner;
	}

	@Override
	public AccountOwner getOwner() {
		return this.owner;
	}

	@Override
	public BigDecimal getBankBalance() {
		return this.bankBalance;
	}

	public void setBankBalance(BigDecimal bankBalance) {
		this.bankBalance = bankBalance;
	}

	@Override
	public Boolean getBlocked() {
		return this.blocked;
	}

	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}

	@Override
	public List<TransactionLog> getTransactionLogs() {
		return Collections.unmodifiableList(this.transactionLogs);
	}

	public Boolean addTransactionLog(TransactionLog log) {
		return this.transactionLogs.add(log);
	}

	@Override
	public Boolean getIsProtected() {
		return this.isProtected;
	}

	public void setIsProtected(Boolean isProtected) {
		this.isProtected = isProtected;
	}

}
