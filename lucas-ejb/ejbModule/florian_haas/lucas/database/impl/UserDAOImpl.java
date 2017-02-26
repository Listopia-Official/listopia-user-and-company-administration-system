package florian_haas.lucas.database.impl;

import java.util.*;

import javax.persistence.criteria.Predicate;

import florian_haas.lucas.database.*;
import florian_haas.lucas.model.*;

@JPADAO
public class UserDAOImpl extends DAOImpl<User> implements UserDAO {

	@Override
	public List<User> findUsers(Long userId, String forename, String surname, List<EnumSchoolClass> schoolClasses, EnumUserType userType,
			List<String> ranks, Boolean useUserId, Boolean useForename, Boolean useSurname, Boolean useSchoolClass, Boolean useUserType,
			Boolean useRanks, EnumQueryComparator userIdComparator, EnumQueryComparator forenameComparator, EnumQueryComparator surnameComparator,
			EnumQueryComparator searchUserTypeComparator, EnumQueryComparator ranksComparator) {
		return readOnlyCriteriaQuery((query, root, builder) -> {
			List<Predicate> predicates = new ArrayList<>();

			getSingularRestriction(User_.id, userId, useUserId, userIdComparator, predicates, builder, root);
			getSingularRestriction(User_.forename, forename, useForename, forenameComparator, predicates, builder, root);
			getSingularRestriction(User_.surname, surname, useSurname, surnameComparator, predicates, builder, root);
			getSingularRestrictionCollection(User_.schoolClass, schoolClasses, useSchoolClass, null, predicates, builder, root);
			if (useUserType) {
				Predicate pred = null;
				switch (userType) {
					case GUEST:
						pred = builder.or(builder.isNull(root.get(User_.forename)), builder.isNull(root.get(User_.surname)));
						break;
					case PUPIL:
						pred = builder.and(builder.isNotNull(root.get(User_.forename)), builder.isNotNull(root.get(User_.surname)),
								builder.isNotNull(root.get(User_.schoolClass)));
						break;
					case TEACHER:
						pred = builder.and(builder.isNotNull(root.get(User_.forename)), builder.isNotNull(root.get(User_.surname)),
								builder.isNull(root.get(User_.schoolClass)));
						break;
					default:
						break;
				}
				if (pred != null) {
					if (searchUserTypeComparator == EnumQueryComparator.NOT_EQUAL) pred = pred.not();
					predicates.add(pred);
				}
			}
			getPluralRestrictionCollection(User_.ranks, ranks, useRanks, ranksComparator, predicates, builder, root);

			return predicates.toArray(new Predicate[predicates.size()]);
		});
	}

	@Override
	public byte[] getImageFromId(Long userId) {
		List<byte[]> results = readOnlyJPQLQuery("SELECT u.image from User u where u.id=?1", byte[].class, userId);
		return results.isEmpty() || results == null ? null : results.get(0);
	}

}
