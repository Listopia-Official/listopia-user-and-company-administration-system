package florian_haas.lucas.business;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

import florian_haas.lucas.database.EnumQueryComparator;
import florian_haas.lucas.model.*;
import florian_haas.lucas.model.validation.*;

@Local
public interface AccountBeanLocal {

	public Long payIn(@ValidEntityId(entityClass = Account.class) Long account, @ValidTransactionAmount BigDecimal amount,
			@ShortComment String comment);

	public Long payOut(@ValidEntityId(entityClass = Account.class) Long account, @ValidTransactionAmount BigDecimal amount,
			@ShortComment String comment);

	public Long transaction(@ValidEntityId(entityClass = Account.class) Long from, @ValidEntityId(entityClass = Account.class) Long to,
			@ValidTransactionAmount BigDecimal amount, @ShortComment String comment);

	public Boolean blockAccount(@ValidEntityId(entityClass = Account.class) Long id);

	public Boolean unblockAccount(@ValidEntityId(entityClass = Account.class) Long id);

	public Boolean protect(@ValidEntityId(entityClass = Account.class) Long id);

	public Boolean unprotect(@ValidEntityId(entityClass = Account.class) Long id);

	public List<Account> findAll();

	public Account findById(@ValidEntityId(entityClass = Account.class) Long id);

	public List<Account> findAccounts(@NotNull Long id, @NotNull Long ownerId, @NotNull EnumAccountOwnerType ownerType,
			@NotNull BigDecimal bankBalance, @NotNull Boolean blocked, @NotNull Boolean isProtected, @NotNull Boolean useId, Boolean useOwnerId,
			@NotNull Boolean useOwnerType, @NotNull Boolean useBankBalance, @NotNull Boolean useBlocked, @NotNull Boolean useIsProtected,
			EnumQueryComparator idComparator, EnumQueryComparator ownerIdComparator, EnumQueryComparator bankBalanceComparator);

}
