package florian_haas.lucas.database.impl;

import java.math.BigDecimal;
import java.util.*;

import javax.persistence.criteria.*;

import florian_haas.lucas.database.*;
import florian_haas.lucas.model.*;

@JPADAO
public class AccountDAOImpl extends ReadOnlyDAOImpl<Account> implements AccountDAO {

	@Override
	public List<Account> findAccounts(Long id, EnumAccountOwnerType ownerType, BigDecimal bankBalance, Boolean blocked, Boolean useId,
			Boolean useOwnerType, Boolean useBankBalance, Boolean useBlocked, EnumQueryComparator idComparator,
			EnumQueryComparator bankBalanceComparator) {
		return this.readOnlyCriteriaQuery((query, root, builder) -> {
			List<Predicate> predicates = new ArrayList<>();

			Root<AccountOwner> ownerRoot = query.from(AccountOwner.class);

			getSingularRestriction(Account_.id, id, useId, idComparator, predicates, builder, root);
			if (useOwnerType) {
				predicates.add(
						builder.equal(builder.literal(ownerType == EnumAccountOwnerType.COMPANY ? Company.class : User.class), ownerRoot.type()));
			}
			getSingularRestriction(Account_.bankBalance, bankBalance, useBankBalance, bankBalanceComparator, predicates, builder, root);
			getSingularRestriction(Account_.blocked, blocked, useBlocked, null, predicates, builder, root);

			return predicates.toArray(new Predicate[predicates.size()]);
		});
	}
}
