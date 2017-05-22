package florian_haas.lucas.persistence.impl;

import java.util.*;

import javax.persistence.criteria.*;

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;

@JPADAO
public class LoginUserDAOImpl extends DAOImpl<LoginUser> implements LoginUserDAO {

	@Override
	public List<LoginUser> findLoginUsers(Long id, String username, Long userId, List<Long> roleIds, Boolean useId, Boolean useUsername,
			Boolean useUserId, Boolean useRoleIds, EnumQueryComparator idComparator, EnumQueryComparator usernameComparator,
			EnumQueryComparator userIdComparator, EnumQueryComparator roleIdsComparator) {
		return this.readOnlyCriteriaQuery((query, root, builder) -> {
			List<Predicate> predicates = new ArrayList<>();

			getSingularRestriction(LoginUser_.id, id, useId, idComparator, predicates, builder, root);
			getSingularRestriction(LoginUser_.username, username, useUsername, usernameComparator, predicates, builder, root);
			getSingularRestriction(root.join(LoginUser_.user, JoinType.LEFT).get(User_.id), userId, useUserId, userIdComparator, predicates, builder,
					root);
			getSingularRestrictionCollection(root.join(LoginUser_.roles, JoinType.LEFT).get(LoginUserRole_.id), roleIds, useRoleIds,
					roleIdsComparator, predicates, builder, root);
			return predicates;
		});
	}

	@Override
	public LoginUser findByUsername(String username) {
		return this.readOnlyCriteriaQuerySingleResult((query, root, builder) -> {
			List<Predicate> predicates = new ArrayList<>();
			getSingularRestriction(LoginUser_.username, username, Boolean.TRUE, EnumQueryComparator.EQUAL, predicates, builder, root);
			return predicates;
		});
	}

	@Override
	public Boolean isUsernameUnique(String username) {
		return isUnique(username, LoginUser_.username);
	}

	@Override
	public Boolean isReferencedUserUnique(Long userId) {
		return isUnique(userId, root -> {
			return root.join(LoginUser_.user).get(User_.id);
		});
	}

	@Override
	public List<LoginUser> getLoginUsersFromData(String data, Integer resultsCount) {
		data = data.trim();
		if (!data.isEmpty()) {
			CriteriaBuilder builder = manager.getCriteriaBuilder();
			CriteriaQuery<LoginUser> query = builder.createQuery(LoginUser.class);
			query.distinct(true);
			Root<LoginUser> loginUser = query.from(LoginUser.class);
			List<Predicate> predicates = new ArrayList<>();
			Join<LoginUser, User> user = loginUser.join(LoginUser_.user, JoinType.LEFT);
			Expression<String> forename = user.get(User_.forename);
			Expression<String> surname = user.get(User_.surname);
			try {
				Long id = Long.parseLong(data);
				predicates.add(builder.equal(loginUser.get(LoginUser_.id), id));
				predicates.add(builder.equal(user.get(User_.id), id));
			}
			catch (NumberFormatException e) {}
			data = "%" + data.replaceAll(" ", "%") + "%";
			predicates.add(builder.like(loginUser.get(LoginUser_.username), data));
			predicates.add(
					builder.or(builder.like(builder.concat(forename, surname), data), builder.like(surname, data), builder.like(forename, data)));
			query.select(loginUser).where(builder.or(predicates.toArray(new Predicate[predicates.size()])));
			return manager.createQuery(query).setMaxResults(resultsCount).getResultList();
		}
		return new ArrayList<>();
	}

	@Override
	public Boolean isBoundLoginUser(Long loginUserId) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<LoginUser> loginUser = query.from(LoginUser.class);
		Path<User> referencedUser = loginUser.get(LoginUser_.user);
		query.select(builder.count(builder.isNotNull(referencedUser))).where(builder.equal(loginUser.get(LoginUser_.id), loginUserId));
		return manager.createQuery(query).getSingleResult() > 0;
	}
}
