package florian_haas.lucas.persistence;

import java.math.BigDecimal;
import java.util.List;

import florian_haas.lucas.model.*;

public interface AccountDAO extends ReadOnlyDAO<Account> {
	public List<Account> findAccounts(Long id, Long ownerId, EnumAccountOwnerType ownerType, BigDecimal bankBalance, Boolean blocked,
			Boolean isProtected, Boolean useId, Boolean useOwnerId, Boolean useOwnerType, Boolean useBankBalance, Boolean useBlocked,
			Boolean useIsProtected, EnumQueryComparator idComparator, EnumQueryComparator ownerIdComparator, EnumQueryComparator ownerTypeComparator,
			EnumQueryComparator bankBalanceComparator);

	public List<Account> getAccountsFromData(String data, Integer resultsCount);
}
