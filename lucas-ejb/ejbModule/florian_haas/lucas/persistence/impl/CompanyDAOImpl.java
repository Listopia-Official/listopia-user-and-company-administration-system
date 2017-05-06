package florian_haas.lucas.persistence.impl;

import java.util.*;

import javax.persistence.criteria.*;

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;

@JPADAO
public class CompanyDAOImpl extends DAOImpl<Company> implements CompanyDAO {

	@Override
	public List<Company> findCompanies(Long companyId, String name, String description, Long roomSectionId, EnumCompanyType companyType,
			Long parentCompanyId, Long jobId, Boolean areEmployeesRequired, Boolean useId, Boolean useName, Boolean useDescription,
			Boolean useRoomSectionId, Boolean useCompanyType, Boolean useParentCompanyId, Boolean useJobId, Boolean useAreEmployeesRequired,
			EnumQueryComparator idComparator, EnumQueryComparator nameComparator, EnumQueryComparator descriptionComparator,
			EnumQueryComparator roomSectionIdComparator, EnumQueryComparator companyTypeComparator, EnumQueryComparator parentCompanyIdComparator,
			EnumQueryComparator jobIdComparator) {
		return this.readOnlyCriteriaQuery((query, root, builder) -> {
			List<Predicate> predicates = new ArrayList<>();

			getSingularRestriction(Company_.id, companyId, useId, idComparator, predicates, builder, root);
			getSingularRestriction(Company_.name, name, useName, nameComparator, predicates, builder, root);
			getSingularRestriction(Company_.description, description, useDescription, descriptionComparator, predicates, builder, root);
			getSingularRestriction(RoomSection_.id, roomSectionId, useRoomSectionId, roomSectionIdComparator, predicates, builder,
					root.get(Company_.section));
			getSingularRestriction(Company_.companyType, companyType, useCompanyType, companyTypeComparator, predicates, builder, root);
			getSingularRestriction(Company_.id, parentCompanyId, useParentCompanyId, parentCompanyIdComparator, predicates, builder,
					root.get(Company_.parentCompany));
			getSingularRestriction(Job_.id, jobId, useJobId, jobIdComparator, predicates, builder, root.join(Company_.jobs, JoinType.LEFT));
			if (useAreEmployeesRequired) {
				Subquery<Long> subquery = query.subquery(Long.class);
				Root<Job> jobRoot = subquery.from(Job.class);
				subquery.select(builder.count(jobRoot));
				subquery.where(builder.greaterThanOrEqualTo(builder.size(jobRoot.get(Job_.employments)), jobRoot.get(Job_.requiredEmploymentsCount)));
				Predicate pred = builder.equal(subquery, builder.size(root.get(Company_.jobs)));
				if (areEmployeesRequired) pred = pred.not();
				predicates.add(pred);
			}
			return predicates;
		});
	}

	@Override
	public Boolean isNameUnique(String name) {
		return isUnique(name, Company_.name);
	}

	@Override
	public Boolean isRoomSectionUnique(Long roomSectionId) {
		return isUnique(roomSectionId, root -> {
			return root.join(Company_.section).get(RoomSection_.id);
		});
	}
}
