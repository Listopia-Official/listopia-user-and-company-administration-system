package florian_haas.lucas.business;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

import florian_haas.lucas.database.*;
import florian_haas.lucas.model.*;
import florian_haas.lucas.validation.*;

@Local
public interface AccountBeanLocal {

	public Long payIn(@ValidEntityId(entityClass = ReadOnlyAccount.class) Long account, @ValidTransactionAmount BigDecimal amount,
			@ShortComment String comment);

	public Long payOut(@ValidEntityId(entityClass = ReadOnlyAccount.class) Long account, @ValidTransactionAmount BigDecimal amount,
			@ShortComment String comment);

	public Long transaction(@ValidEntityId(entityClass = ReadOnlyAccount.class) Long from,
			@ValidEntityId(entityClass = ReadOnlyAccount.class) Long to, @ValidTransactionAmount BigDecimal amount, @ShortComment String comment);

	public Boolean blockAccount(@ValidEntityId(entityClass = ReadOnlyAccount.class) Long id);

	public Boolean unblockAccount(@ValidEntityId(entityClass = ReadOnlyAccount.class) Long id);

	public Boolean protect(@ValidEntityId(entityClass = ReadOnlyAccount.class) Long id);

	public Boolean unprotect(@ValidEntityId(entityClass = ReadOnlyAccount.class) Long id);

	public List<? extends ReadOnlyAccount> findAll();

	public ReadOnlyAccount findById(@ValidEntityId(entityClass = ReadOnlyAccount.class) Long id);

	public List<? extends ReadOnlyAccount> findAccounts(@NotNull Long id, @NotNull Long ownerId, @NotNull EnumAccountOwnerType ownerType,
			@NotNull BigDecimal bankBalance, @NotNull Boolean blocked, @NotNull Boolean isProtected, @NotNull Boolean useId, Boolean useOwnerId,
			@NotNull Boolean useOwnerType, @NotNull Boolean useBankBalance, @NotNull Boolean useBlocked, @NotNull Boolean useIsProtected,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator idComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator ownerIdComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.LOGIC) EnumQueryComparator ownerTypeComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator bankBalanceComparator);

	public ReadOnlyAccountOwner findAccountOwnerById(@ValidEntityId(entityClass = ReadOnlyAccountOwner.class) Long id);

}
