package florian_haas.lucas.persistence.impl;

import java.math.BigDecimal;
import java.util.*;

import javax.persistence.criteria.*;

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;

@JPADAO
public class AccountDAOImpl extends ReadOnlyDAOImpl<Account> implements AccountDAO {

	@Override
	public List<Account> findAccounts(Long id, Long ownerId, EnumAccountOwnerType ownerType, BigDecimal bankBalance, Boolean blocked,
			Boolean isProtected, Boolean useId, Boolean useOwnerId, Boolean useOwnerType, Boolean useBankBalance, Boolean useBlocked,
			Boolean useProtected, EnumQueryComparator idComparator, EnumQueryComparator ownerIdComparator, EnumQueryComparator ownerTypeComparator,
			EnumQueryComparator bankBalanceComparator) {
		return this.readOnlyCriteriaQuery((query, root, builder) -> {
			List<Predicate> predicates = new ArrayList<>();

			Path<AccountOwner> ownerRoot = root.get(Account_.owner);
			getSingularRestriction(Account_.id, id, useId, idComparator, predicates, builder, root);
			getSingularRestriction(root.join(Account_.owner).get(AccountOwner_.id), ownerId, useOwnerId, ownerIdComparator, predicates, builder,
					root);
			if (useOwnerType) {
				Expression<Class<? extends AccountOwner>> expr = builder
						.literal(ownerType == EnumAccountOwnerType.COMPANY ? Company.class : User.class);
				predicates.add((ownerTypeComparator == null || ownerTypeComparator == EnumQueryComparator.EQUAL)
						? builder.equal(expr, ownerRoot.type()) : builder.notEqual(expr, ownerRoot.type()));
			}
			getSingularRestriction(Account_.bankBalance, bankBalance, useBankBalance, bankBalanceComparator, predicates, builder, root);
			getSingularRestriction(Account_.blocked, blocked, useBlocked, null, predicates, builder, root);
			getSingularRestriction(Account_.isProtected, isProtected, useProtected, null, predicates, builder, root);

			return predicates;
		});
	}

	@Override
	public List<Account> getAccountsFromData(String data, Integer resultsCount) {
		data = data.trim();
		if (!data.isEmpty()) {
			CriteriaBuilder builder = manager.getCriteriaBuilder();
			CriteriaQuery<Account> query = builder.createQuery(Account.class);
			query.distinct(true);
			Root<Account> account = query.from(Account.class);
			List<Predicate> predicates = new ArrayList<>();
			Join<Account, AccountOwner> owner = account.join(Account_.owner);
			Join<Account, User> user = builder.treat(owner, User.class);
			Expression<String> forename = user.get(User_.forename);
			Expression<String> surname = user.get(User_.surname);
			Expression<? extends Class<?>> ownerType = owner.type();
			try {
				Long id = Long.parseLong(data);
				predicates.add(builder.equal(account.get(Account_.id), id));
				predicates.add(builder.equal(owner.get(AccountOwner_.id), id));
				predicates.add(builder.equal(owner.join(AccountOwner_.idCards, JoinType.LEFT).get(IdCard_.id), id));
			}
			catch (NumberFormatException e) {}
			data = "%" + data.replaceAll(" ", "%") + "%";
			predicates.add(builder.and(builder.equal(ownerType, builder.literal(User.class)),
					builder.or(builder.like(builder.concat(forename, surname), data), builder.like(surname, data), builder.like(forename, data))));
			predicates.add(builder.and(builder.equal(ownerType, builder.literal(Company.class)),
					builder.like(builder.treat(owner, Company.class).get(Company_.name), data)));
			query.select(account).where(builder.or(predicates.toArray(new Predicate[predicates.size()])));
			return manager.createQuery(query).setMaxResults(resultsCount).getResultList();
		}
		return new ArrayList<>();
	}

}
