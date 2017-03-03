package florian_haas.lucas.database.impl;

import java.math.BigDecimal;
import java.util.*;

import javax.persistence.criteria.*;

import florian_haas.lucas.database.*;
import florian_haas.lucas.model.*;

@JPADAO
public class AccountDAOImpl extends ReadOnlyDAOImpl<Account> implements AccountDAO {

	@Override
	public List<Account> findAccounts(Long id, Long ownerId, EnumAccountOwnerType ownerType, BigDecimal bankBalance, Boolean blocked,
			Boolean isProtected, Boolean useId, Boolean useOwnerId, Boolean useOwnerType, Boolean useBankBalance, Boolean useBlocked,
			Boolean useProtected, EnumQueryComparator idComparator, EnumQueryComparator ownerIdComparator, EnumQueryComparator ownerTypeComparator,
			EnumQueryComparator bankBalanceComparator) {
		return this.readOnlyCriteriaQuery((query, root, builder) -> {
			List<Predicate> predicates = new ArrayList<>();

			Root<AccountOwner> ownerRoot = query.from(AccountOwner.class);
			getSingularRestriction(Account_.id, id, useId, idComparator, predicates, builder, root);
			getSingularRestriction(root.join(Account_.owner).get(AccountOwner_.id), ownerId, useOwnerId, ownerIdComparator, predicates, builder,
					root);
			if (useOwnerType) {
				Expression<Class<? extends AccountOwner>> expr = builder
						.literal(ownerType == EnumAccountOwnerType.COMPANY ? Company.class : User.class);
				predicates.add(ownerTypeComparator == null || ownerTypeComparator == EnumQueryComparator.EQUAL ? builder.equal(expr, ownerRoot.type())
						: builder.notEqual(expr, ownerRoot.type()));
			}
			getSingularRestriction(Account_.bankBalance, bankBalance, useBankBalance, bankBalanceComparator, predicates, builder, root);
			getSingularRestriction(Account_.blocked, blocked, useBlocked, null, predicates, builder, root);
			getSingularRestriction(Account_.isProtected, isProtected, useProtected, null, predicates, builder, root);

			return predicates.toArray(new Predicate[predicates.size()]);
		});
	}
}
