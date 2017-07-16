package florian_haas.lucas.persistence.impl;

import java.util.*;

import javax.persistence.criteria.Predicate;

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;

@JPADAO
public class AttendancedataDAOImpl extends ReadOnlyDAOImpl<Attendancedata> implements AttendancedataDAO {

	@Override
	public List<Attendancedata> findAttendancedata(Long id, Long userId, Boolean isUserInState, Long timePresentDay, Boolean useId, Boolean useUserId,
			Boolean useIsUserInState, Boolean useTimePresentDay, EnumQueryComparator idComparator, EnumQueryComparator userIdComparator,
			EnumQueryComparator timePresentDayComparator) {
		return this.readOnlyCriteriaQuery((query, root, builder) -> {
			List<Predicate> predicates = new ArrayList<>();

			getSingularRestriction(Attendancedata_.id, id, useId, idComparator, predicates, builder, root);
			getSingularRestriction(root.join(Attendancedata_.user).get(User_.id), userId, useUserId, userIdComparator, predicates, builder, root);
			getSingularRestriction(root.get(Attendancedata_.isUserInState), isUserInState, useIsUserInState, null, predicates, builder, root);
			getSingularRestriction(
					builder.sum(root.get(Attendancedata_.timePresentDay), root.join(Attendancedata_.timeIn).get(Stopwatch_.tmpDuration)),
					timePresentDay, useTimePresentDay, timePresentDayComparator, predicates, builder, root);
			return predicates;
		});
	}
}
