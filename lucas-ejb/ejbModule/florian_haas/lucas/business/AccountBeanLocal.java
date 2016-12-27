package florian_haas.lucas.business;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Local;

import florian_haas.lucas.database.EnumQueryComparator;
import florian_haas.lucas.model.*;

@Local
public interface AccountBeanLocal {

	public Account payIn(Long account, BigDecimal amount, String comment);

	public Account payOut(Long account, BigDecimal amount, String comment);

	public Account transaction(Long from, Long to, BigDecimal amount, String comment);

	public boolean blockAccount(Long id);

	public boolean unblockAccount(Long id);

	public List<Account> findAll();

	public Account findById(Long id);

	public List<Account> findAccounts(Long id, EnumAccountOwnerType ownerType, BigDecimal bankBalance, Boolean blocked, Boolean useId,
			Boolean useOwnerType, Boolean useBankBalance, Boolean useBlocked, EnumQueryComparator idComparator,
			EnumQueryComparator bankBalanceComparator);

}
