package florian_haas.lucas.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ReadOnlyTransactionLog extends ReadOnlyEntity {

	public ReadOnlyAccount getAccount();

	public LocalDateTime getDateTime();

	public EnumAccountAction getAction();

	public EnumAccountActionType getActionType();

	public ReadOnlyAccount getRelatedAccount();

	public BigDecimal getAmount();

	public BigDecimal getPreviousBankBalance();

	public BigDecimal getCurrentBankBalance();

	public ReadOnlyLoginUser getBankUser();

	public String getComment();

}
