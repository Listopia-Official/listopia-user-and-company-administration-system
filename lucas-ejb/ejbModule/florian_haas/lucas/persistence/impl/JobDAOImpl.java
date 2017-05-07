package florian_haas.lucas.persistence.impl;

import java.util.*;

import javax.persistence.criteria.*;

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;

@JPADAO
public class JobDAOImpl extends DAOImpl<Job> implements JobDAO {

	@Override
	public List<Job> findJobs(Long jobId, String name, String description, Long companyId, EnumSalaryClass salaryClass,
			Integer requiredEmployeesCount, EnumEmployeePosition position, Long employmentId, Boolean useJobId, Boolean useName,
			Boolean useDescription, Boolean useCompanyId, Boolean useSalaryClass, Boolean useRequiredEmployeesCount, Boolean useEmployeePosition,
			Boolean useEmploymentId, EnumQueryComparator jobIdComparator, EnumQueryComparator nameComparator,
			EnumQueryComparator descriptionComparator, EnumQueryComparator companyIdComparator, EnumQueryComparator salaryClassComparator,
			EnumQueryComparator requiredEmployeesCountComparator, EnumQueryComparator positionComparator,
			EnumQueryComparator employmentIdComparator) {
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

}
