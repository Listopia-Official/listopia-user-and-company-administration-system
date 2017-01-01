package florian_haas.lucas.model;

import java.math.BigDecimal;
import java.util.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

import florian_haas.lucas.model.validation.*;
import florian_haas.lucas.util.validation.TypeNotNull;

@Entity
public class Account extends EntityBase {

	private static final long serialVersionUID = -8880641005184863518L;

	@OneToOne(mappedBy = "account", optional = false)
	@JoinColumn(nullable = false)
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

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "account")
	@Valid
	@NotNull
	private List<@TypeNotNull TransactionLog> transactionLogs = new ArrayList<>();

	Account() {}

	Account(AccountOwner owner) {
		this.owner = owner;
	}

	public AccountOwner getOwner() {
		return owner;
	}

	public BigDecimal getBankBalance() {
		return bankBalance;
	}

	public void setBankBalance(BigDecimal bankBalance) {
		this.bankBalance = bankBalance;
	}

	public Boolean getBlocked() {
		return blocked;
	}

	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}

	public List<TransactionLog> getTransactionLogs() {
		return Collections.unmodifiableList(transactionLogs);
	}

	public Boolean addTransactionLog(TransactionLog log) {
		return transactionLogs.add(log);
	}

}
