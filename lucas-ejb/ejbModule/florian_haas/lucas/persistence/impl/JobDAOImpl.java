package florian_haas.lucas.persistence.impl;

import java.util.*;

import javax.persistence.criteria.*;

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;

@JPADAO
public class JobDAOImpl extends DAOImpl<Job> implements JobDAO {

	@Override
	public List<Job> findJobs(Long jobId, String name, String description, Long companyId, EnumSalaryClass salaryClass,
			Integer requiredEmployeesCount, EnumEmployeePosition position, Long employmentId, Integer employmentsCount, Boolean useJobId,
			Boolean useName, Boolean useDescription, Boolean useCompanyId, Boolean useSalaryClass, Boolean useRequiredEmployeesCount,
			Boolean useEmployeePosition, Boolean useEmploymentId, Boolean useEmploymentsCount, EnumQueryComparator jobIdComparator,
			EnumQueryComparator nameComparator, EnumQueryComparator descriptionComparator, EnumQueryComparator companyIdComparator,
			EnumQueryComparator salaryClassComparator, EnumQueryComparator requiredEmployeesCountComparator, EnumQueryComparator positionComparator,
			EnumQueryComparator employmentIdComparator, EnumQueryComparator employmentsCountComparator) {
		return readOnlyCriteriaQuery((query, root, builder) -> {
			List<Predicate> predicates = new ArrayList<>();

			getSingularRestriction(Job_.id, jobId, useJobId, jobIdComparator, predicates, builder, root);
			getSingularRestriction(Job_.name, name, useName, nameComparator, predicates, builder, root);
			getSingularRestriction(Job_.description, description, useDescription, descriptionComparator, predicates, builder, root);
			getSingularRestriction(Company_.id, companyId, useCompanyId, companyIdComparator, predicates, builder, root.join(Job_.company));
			getSingularRestriction(Job_.salaryClass, salaryClass, useSalaryClass, salaryClassComparator, predicates, builder, root);
			getSingularRestriction(Job_.requiredEmploymentsCount, requiredEmployeesCount, useRequiredEmployeesCount, requiredEmployeesCountComparator,
					predicates, builder, root);
			getSingularRestriction(Job_.employeePosition, position, useEmployeePosition, positionComparator, predicates, builder, root);
			getSingularRestriction(Employment_.id, employmentId, useEmploymentId, employmentIdComparator, predicates, builder,
					root.join(Job_.employments, JoinType.LEFT));
			getSingularRestriction(builder.size(root.get(Job_.employments)), employmentsCount, useEmploymentsCount, employmentsCountComparator,
					predicates, builder, root);
			return predicates;
		});
	}

	@Override
	public Boolean isNameUniqueInJobsOfCompany(Long companyId, String name) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Job> jobRoot = query.from(Job.class);
		query.select(builder.count(jobRoot)).where(
				builder.and(builder.equal(jobRoot.get(Job_.name), name), builder.equal(jobRoot.join(Job_.company).get(Company_.id), companyId)));
		return manager.createQuery(query).getSingleResult() <= 0;
	}

	@Override
	public Boolean isReferencedInEmployments(Long jobId) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Job> jobRoot = query.from(Job.class);
		query.select(builder.count(jobRoot))
				.where(builder.and(builder.equal(builder.size(jobRoot.get(Job_.employments)), 0), builder.equal(jobRoot.get(Job_.id), jobId)));
		return manager.createQuery(query).getSingleResult() == 0;
	}

	@Override
	public List<Job> getEmployeeJobsWhereEmploymentsAreRequired(EnumSet<EnumEmployeePosition> validJobs, EnumSet<EnumCompanyType> validCompanyTypes) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Job> query = builder.createQuery(Job.class);
		Root<Job> jobRoot = query.from(Job.class);
		query.select(jobRoot)
				.where(builder.and(jobRoot.join(Job_.company).get(Company_.companyType).in(validCompanyTypes),
						jobRoot.get(Job_.employeePosition).in(validJobs),
						builder.lessThan(builder.size(jobRoot.get(Job_.employments)), jobRoot.get(Job_.requiredEmploymentsCount))));
		return manager.createQuery(query).getResultList();
	}

	@Override
	public List<Job> getJobsFromData(String data, Integer resultsCount) {
		data = data.trim();
		if (!data.isEmpty()) {
			CriteriaBuilder builder = manager.getCriteriaBuilder();
			CriteriaQuery<Job> query = builder.createQuery(Job.class);
			query.distinct(true);
			Root<Job> job = query.from(Job.class);
			List<Predicate> predicates = new ArrayList<>();
			try {
				Long id = Long.parseLong(data);
				predicates.add(builder.equal(job.get(Job_.id), id));
			}
			catch (NumberFormatException e) {}
			data = "%" + data.replaceAll(" ", "%") + "%";
			predicates.add(builder.like(job.get(Job_.name), data));
			predicates.add(builder.like(job.join(Job_.company).get(Company_.name), data));
			query.select(job).where(builder.or(predicates.toArray(new Predicate[predicates.size()])));
			return manager.createQuery(query).setMaxResults(resultsCount).getResultList();
		}
		return new ArrayList<>();
	}

}
