package florian_haas.lucas.model;

import java.math.BigDecimal;
import java.util.List;

public interface ReadOnlyAccount extends ReadOnlyEntity {

	public ReadOnlyAccountOwner getOwner();

	public BigDecimal getBankBalance();

	public Boolean getBlocked();

	public Boolean getIsProtected();

	public List<? extends ReadOnlyTransactionLog> getTransactionLogs();

}
