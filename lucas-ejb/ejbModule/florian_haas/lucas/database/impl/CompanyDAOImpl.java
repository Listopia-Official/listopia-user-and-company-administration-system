package florian_haas.lucas.database.impl;

import java.util.*;

import javax.persistence.criteria.Predicate;

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

			return predicates.toArray(new Predicate[predicates.size()]);
		});
	}

}
