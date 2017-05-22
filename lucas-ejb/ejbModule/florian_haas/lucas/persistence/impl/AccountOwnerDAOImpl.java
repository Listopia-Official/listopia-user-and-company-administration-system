package florian_haas.lucas.persistence.impl;

import java.util.*;

import javax.persistence.criteria.*;

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;

@JPADAO
public class AccountOwnerDAOImpl extends ReadOnlyDAOImpl<AccountOwner> implements AccountOwnerDAO {

	@Override
	public List<AccountOwner> getAccountOwnersFromData(String data, Integer resultsCount) {
		data = data.trim();
		if (!data.isEmpty()) {
			CriteriaBuilder builder = manager.getCriteriaBuilder();
			CriteriaQuery<AccountOwner> query = builder.createQuery(AccountOwner.class);
			query.distinct(true);
			Root<AccountOwner> accountOwner = query.from(AccountOwner.class);
			List<Predicate> predicates = new ArrayList<>();
			Root<User> user = builder.treat(accountOwner, User.class);
			Expression<String> forename = user.get(User_.forename);
			Expression<String> surname = user.get(User_.surname);
			Expression<? extends Class<?>> ownerType = accountOwner.type();
			try {
				Long id = Long.parseLong(data);
				predicates.add(builder.equal(accountOwner.get(AccountOwner_.id), id));
				predicates.add(builder.equal(accountOwner.join(AccountOwner_.idCards, JoinType.LEFT).get(IdCard_.id), id));
			}
			catch (NumberFormatException e) {}
			data = "%" + data.replaceAll(" ", "%") + "%";
			predicates.add(builder.and(builder.equal(ownerType, builder.literal(User.class)),
					builder.or(builder.like(builder.concat(forename, surname), data), builder.like(surname, data), builder.like(forename, data))));
			predicates.add(builder.and(builder.equal(ownerType, builder.literal(Company.class)),
					builder.like(builder.treat(accountOwner, Company.class).get(Company_.name), data)));
			query.select(accountOwner).where(builder.or(predicates.toArray(new Predicate[predicates.size()])));
			return manager.createQuery(query).setMaxResults(resultsCount).getResultList();
		}
		return new ArrayList<>();
	}
}
