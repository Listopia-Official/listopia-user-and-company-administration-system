package florian_haas.lucas.database;

import java.math.BigDecimal;
import java.util.List;

import florian_haas.lucas.model.*;

public interface AccountDAO extends ReadOnlyDAO<Account> {
	public List<Account> findAccounts(Long id, EnumAccountOwnerType ownerType, BigDecimal bankBalance, Boolean blocked, Boolean useId,
			Boolean useOwnerType, Boolean useBankBalance, Boolean useBlocked, EnumQueryComparator idComparator,
			EnumQueryComparator bankBalanceComparator);
}
