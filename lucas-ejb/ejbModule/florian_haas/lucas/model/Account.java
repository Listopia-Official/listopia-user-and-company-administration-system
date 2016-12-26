package florian_haas.lucas.model;

import java.math.BigDecimal;
import java.util.*;

public class Account extends EntityBase {

	private static final long serialVersionUID = -8880641005184863518L;

	private AccountOwner owner;
	private BigDecimal bankBalance = BigDecimal.ZERO;
	private Boolean blocked = Boolean.FALSE;
	private List<TransactionLog> transactionLogs = new ArrayList<>();

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

	public boolean addTransactionLog(TransactionLog log) {
		return transactionLogs.add(log);
	}

}
