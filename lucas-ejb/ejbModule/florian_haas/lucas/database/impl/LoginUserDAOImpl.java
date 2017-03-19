package florian_haas.lucas.database.impl;

import java.util.*;

import javax.persistence.criteria.*;

import florian_haas.lucas.database.*;
import florian_haas.lucas.model.*;

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
			return predicates.toArray(new Predicate[predicates.size()]);
		});
	}

	@Override
	public LoginUser findByUsername(String username) {
		return this.readOnlyCriteriaQuerySingleResult((query, root, builder) -> {
			List<Predicate> predicates = new ArrayList<>();
			getSingularRestriction(LoginUser_.username, username, Boolean.TRUE, EnumQueryComparator.EQUAL, predicates, builder, root);
			return predicates.toArray(new Predicate[predicates.size()]);
		});
	}
}
