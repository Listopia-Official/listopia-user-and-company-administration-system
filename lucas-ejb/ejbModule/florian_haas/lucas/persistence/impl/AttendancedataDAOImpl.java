package florian_haas.lucas.persistence.impl;

import java.util.*;

import javax.persistence.criteria.Predicate;

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;

@JPADAO
public class AttendancedataDAOImpl extends ReadOnlyDAOImpl<Attendancedata> implements AttendancedataDAO {

	@Override
	public List<Attendancedata> findAttendancedata(Long id, Boolean isUserInState, Long timePresentDay, Long validTimeMissing, Boolean useId,
			Boolean useIsUserInState, Boolean useTimePresentDay, Boolean useVaidTimeMissing, EnumQueryComparator idComparator,
			EnumQueryComparator timePresentDayComparator, EnumQueryComparator validTimeMissingComparator) {
		return this.readOnlyCriteriaQuery((query, root, builder) -> {
			List<Predicate> predicates = new ArrayList<>();

			getSingularRestriction(Attendancedata_.id, id, useId, idComparator, predicates, builder, root);
			getSingularRestriction(Attendancedata_.isUserInState, isUserInState, useIsUserInState, null, predicates, builder, root);
			getSingularRestriction(Attendancedata_.timePresentDay, timePresentDay, useTimePresentDay, timePresentDayComparator, predicates, builder,
					root);
			getSingularRestriction(Attendancedata_.validTimeMissing, validTimeMissing, useVaidTimeMissing, validTimeMissingComparator, predicates,
					builder, root);

			return predicates;
		});
	}
}
