package florian_haas.lucas.database.impl;

import java.util.*;

import javax.persistence.criteria.*;

import florian_haas.lucas.database.*;
import florian_haas.lucas.model.*;

@JPADAO
public class LoginUserRoleDAOImpl extends DAOImpl<LoginUserRole> implements LoginUserRoleDAO {

	@Override
	public LoginUserRole findByName(String name) {
		List<LoginUserRole> results = readOnlyCriteriaQuery((query, root, builder) -> {
			List<Predicate> predicates = new ArrayList<>();
			getSingularRestriction(LoginUserRole_.name, name, Boolean.TRUE, null, predicates, builder, root);
			return predicates.toArray(new Predicate[predicates.size()]);
		});
		return results.size() > 0 ? results.get(0) : null;
	}

	@Override
	public List<LoginUserRole> findLoginUserRoles(Long id, String name, Set<String> permissions, Boolean useId, Boolean useName,
			Boolean usePermissions, EnumQueryComparator idComparator, EnumQueryComparator nameComparator, EnumQueryComparator permissionsComparator) {
		return readOnlyCriteriaQuery((query, root, builder) -> {
			List<Predicate> predicates = new ArrayList<>();

			getSingularRestriction(LoginUserRole_.id, id, useName, idComparator, predicates, builder, root);
			getSingularRestriction(LoginUserRole_.name, name, useName, nameComparator, predicates, builder, root);
			getPluralRestrictionCollection(LoginUserRole_.permissions, permissions, usePermissions, permissionsComparator, predicates, builder, root);

			return predicates.toArray(new Predicate[predicates.size()]);
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
}
