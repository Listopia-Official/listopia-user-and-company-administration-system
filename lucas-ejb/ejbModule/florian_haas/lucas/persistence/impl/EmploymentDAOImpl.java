package florian_haas.lucas.persistence.impl;

import java.util.*;

import javax.persistence.criteria.Predicate;

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;

@JPADAO
public class EmploymentDAOImpl extends ReadOnlyDAOImpl<Employment> implements EmploymentDAO {

	@Override
	public List<Employment> findEmployments(Long employmentId, Long userId, Long jobId, Set<EnumWorkShift> workShifts, Boolean useEmploymentId,
			Boolean useUserId, Boolean useJobId, Boolean useWorkShifts, EnumQueryComparator employmentIdComparator,
			EnumQueryComparator userIdComparator, EnumQueryComparator jobIdComparator, EnumQueryComparator workShiftsComparator) {
		return this.readOnlyCriteriaQuery((query, root, builder) -> {
			List<Predicate> predicates = new ArrayList<>();

			getSingularRestriction(Employment_.id, employmentId, useEmploymentId, employmentIdComparator, predicates, builder, root);
			getSingularRestriction(User_.id, userId, useUserId, userIdComparator, predicates, builder, root.join(Employment_.user));
			getSingularRestriction(Job_.id, jobId, useJobId, jobIdComparator, predicates, builder, root.join(Employment_.job));
			getPluralRestrictionCollection(Employment_.workShifts, workShifts, useWorkShifts, workShiftsComparator, predicates, builder, root);

			return predicates;
		});
	}
}
