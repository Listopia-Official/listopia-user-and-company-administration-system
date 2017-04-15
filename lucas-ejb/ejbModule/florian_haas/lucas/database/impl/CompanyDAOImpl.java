package florian_haas.lucas.database.impl;

import java.util.*;

import javax.persistence.criteria.*;

import florian_haas.lucas.database.*;
import florian_haas.lucas.model.*;

@JPADAO
public class CompanyDAOImpl extends DAOImpl<Company> implements CompanyDAO {

	@Override
	public List<Company> findCompanies(Long companyId, String name, String description, String room, Integer section, EnumCompanyType companyType,
			Long parentCompanyId, Integer requiredEmployeesCount, Boolean areEmployeesRequired, Boolean useId, Boolean useName,
			Boolean useDescription, Boolean useRoom, Boolean useSection, Boolean useCompanyType, Boolean useParentCompanyId,
			Boolean useRequiredEmployeesCount, Boolean useAreEmployeesRequired, EnumQueryComparator idComparator, EnumQueryComparator nameComparator,
			EnumQueryComparator descriptionComparator, EnumQueryComparator roomComparator, EnumQueryComparator sectionComparator,
			EnumQueryComparator companyTypeComparator, EnumQueryComparator parentCompanyIdComparator,
			EnumQueryComparator requiredEmployeesCountComparator) {
		return this.readOnlyCriteriaQuery((query, root, builder) -> {
			List<Predicate> predicates = new ArrayList<>();

			getSingularRestriction(Company_.id, companyId, useId, idComparator, predicates, builder, root);
			getSingularRestriction(Company_.name, name, useName, nameComparator, predicates, builder, root);
			getSingularRestriction(Company_.description, description, useDescription, descriptionComparator, predicates, builder, root);
			getSingularRestriction(Company_.room, room, useRoom, roomComparator, predicates, builder, root);
			getSingularRestriction(Company_.section, section, useSection, sectionComparator, predicates, builder, root);
			getSingularRestriction(Company_.companyType, companyType, useCompanyType, companyTypeComparator, predicates, builder, root);
			getSingularRestriction(Company_.id, parentCompanyId, useParentCompanyId, parentCompanyIdComparator, predicates, builder,
					root.get(Company_.parentCompany));
			getSingularRestriction(Company_.requiredEmployeesCount, requiredEmployeesCount, useRequiredEmployeesCount,
					requiredEmployeesCountComparator, predicates, builder, root);
			if (useAreEmployeesRequired) {
				Subquery<Long> subquery = query.subquery(Long.class);
				Root<Employment> employmentRoot = subquery.from(Employment.class);
				subquery.select(builder.count(employmentRoot));
				subquery.where(builder.equal(employmentRoot.get(Employment_.position), EnumEmployeePosition.EMPLOYEE));
				Predicate pred = builder.lessThan(subquery, builder.toLong(root.get(Company_.requiredEmployeesCount)));
				if (!areEmployeesRequired) pred = pred.not();
				predicates.add(pred);
			}
			return predicates;
		});
	}

	@Override
	public Boolean existsLocation(String room, Integer section) {
		return !this.readOnlyCriteriaQuery((query, root, builder) -> {
			return Arrays.asList(builder.and(getSingularRestriction(Company_.room, room, EnumQueryComparator.EQUAL, builder, root),
					getSingularRestriction(Company_.section, section, EnumQueryComparator.EQUAL, builder, root)));
		}).isEmpty();
	}

	@Override
	public Boolean isNameUnique(String name) {
		return isUnique(name, Company_.name);
	}

}
