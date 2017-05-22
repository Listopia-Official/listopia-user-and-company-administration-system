package florian_haas.lucas.persistence.impl;

import java.util.*;

import javax.persistence.criteria.*;

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

	@Override
	public List<Employment> getEmploymentsFromData(String data, Integer resultsCount) {
		data = data.trim();
		if (!data.isEmpty()) {
			CriteriaBuilder builder = manager.getCriteriaBuilder();
			CriteriaQuery<Employment> query = builder.createQuery(Employment.class);
			query.distinct(true);
			Root<Employment> employment = query.from(Employment.class);
			List<Predicate> predicates = new ArrayList<>();
			Join<Employment, User> user = employment.join(Employment_.user);
			Join<Employment, Job> job = employment.join(Employment_.job);
			Expression<String> forename = user.get(User_.forename);
			Expression<String> surname = user.get(User_.surname);
			try {
				Long id = Long.parseLong(data);
				predicates.add(builder.equal(employment.get(Employment_.id), id));
				predicates.add(builder.equal(user.get(User_.id), id));
				predicates.add(builder.equal(job.get(Job_.id), id));
			}
			catch (NumberFormatException e) {}
			data = "%" + data.replaceAll(" ", "%") + "%";
			predicates.add(builder.like(job.get(Job_.name), data));
			predicates.add(
					builder.or(builder.like(builder.concat(forename, surname), data), builder.like(surname, data), builder.like(forename, data)));
			query.select(employment).where(builder.or(predicates.toArray(new Predicate[predicates.size()])));
			return manager.createQuery(query).setMaxResults(resultsCount).getResultList();
		}
		return new ArrayList<>();
	}
}
