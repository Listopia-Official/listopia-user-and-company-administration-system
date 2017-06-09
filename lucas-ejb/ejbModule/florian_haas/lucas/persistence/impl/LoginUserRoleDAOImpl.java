package florian_haas.lucas.persistence.impl;

import java.util.*;

import javax.persistence.criteria.*;

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;

@JPADAO
public class LoginUserRoleDAOImpl extends DAOImpl<LoginUserRole> implements LoginUserRoleDAO {

	@Override
	public LoginUserRole findByName(String name) {
		List<LoginUserRole> results = readOnlyCriteriaQuery((query, root, builder) -> {
			List<Predicate> predicates = new ArrayList<>();
			getSingularRestriction(LoginUserRole_.name, name, Boolean.TRUE, null, predicates, builder, root);
			return predicates;
		});
		return results.size() > 0 ? results.get(0) : null;
	}

	@Override
	public List<LoginUserRole> findLoginUserRoles(Long id, String name, Set<String> permissions, Long loginUserId, Boolean useId, Boolean useName,
			Boolean usePermissions, Boolean useLoginUserId, EnumQueryComparator idComparator, EnumQueryComparator nameComparator,
			EnumQueryComparator permissionsComparator, EnumQueryComparator loginUserIdComparator) {
		return readOnlyCriteriaQuery((query, root, builder) -> {
			List<Predicate> predicates = new ArrayList<>();

			getSingularRestriction(LoginUserRole_.id, id, useId, idComparator, predicates, builder, root);
			getSingularRestriction(LoginUserRole_.name, name, useName, nameComparator, predicates, builder, root);
			if (useLoginUserId) {
				Subquery<Long> sub = query.subquery(Long.class);
				Root<LoginUser> loginUserRoot = sub.from(LoginUser.class);
				Join<LoginUser, LoginUserRole> role = loginUserRoot.join(LoginUser_.roles, JoinType.LEFT);
				sub.distinct(true).select(role.get(LoginUserRole_.id)).where(builder.equal(loginUserRoot.get(LoginUser_.id), loginUserId));
				predicates.add(loginUserIdComparator == EnumQueryComparator.EQUAL ? root.get(LoginUserRole_.id).in(sub)
						: root.get(LoginUserRole_.id).in(sub).not());
			}
			getPluralRestrictionCollection(LoginUserRole_.permissions, permissions, usePermissions, permissionsComparator, predicates, builder, root);

			return predicates;
		});
	}

	@Override
	public Boolean isUsed(Long id) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<LoginUser> user = query.from(LoginUser.class);
		Path<Long> loginUserRoleId = user.join(LoginUser_.roles).get(LoginUserRole_.id);
		query.select(builder.count(loginUserRoleId)).where(builder.equal(loginUserRoleId, id));
		return manager.createQuery(query).getSingleResult() > 0;
	}

	@Override
	public Boolean isNameUnique(String name) {
		return isUnique(name, LoginUserRole_.name);
	}
}
