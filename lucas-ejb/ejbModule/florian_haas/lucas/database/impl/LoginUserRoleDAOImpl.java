package florian_haas.lucas.database.impl;

import java.util.*;

import javax.persistence.criteria.Predicate;

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
}
