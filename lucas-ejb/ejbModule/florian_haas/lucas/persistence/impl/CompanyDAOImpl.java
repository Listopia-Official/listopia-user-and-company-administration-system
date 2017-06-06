package florian_haas.lucas.persistence.impl;

import java.util.*;

import javax.persistence.criteria.*;

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;

@JPADAO
public class CompanyDAOImpl extends DAOImpl<Company> implements CompanyDAO {

	@Override
	public List<Company> findCompanies(Long companyId, String name, String description, Long roomSectionId, EnumCompanyType companyType,
			Long parentCompanyId, Long jobId, Boolean areEmployeesRequired, Integer jobCount, Boolean useId, Boolean useName, Boolean useDescription,
			Boolean useRoomSectionId, Boolean useCompanyType, Boolean useParentCompanyId, Boolean useJobId, Boolean useAreEmployeesRequired,
			Boolean useJobCount, EnumQueryComparator idComparator, EnumQueryComparator nameComparator, EnumQueryComparator descriptionComparator,
			EnumQueryComparator roomSectionIdComparator, EnumQueryComparator companyTypeComparator, EnumQueryComparator parentCompanyIdComparator,
			EnumQueryComparator jobIdComparator, EnumQueryComparator jobCountComparator) {
		return this.readOnlyCriteriaQuery((query, root, builder) -> {
			List<Predicate> predicates = new ArrayList<>();

			getSingularRestriction(Company_.id, companyId, useId, idComparator, predicates, builder, root);
			getSingularRestriction(Company_.name, name, useName, nameComparator, predicates, builder, root);
			getSingularRestriction(Company_.description, description, useDescription, descriptionComparator, predicates, builder, root);
			getSingularRestriction(RoomSection_.id, roomSectionId, useRoomSectionId, roomSectionIdComparator, predicates, builder,
					root.join(Company_.section, JoinType.LEFT));
			getSingularRestriction(Company_.companyType, companyType, useCompanyType, companyTypeComparator, predicates, builder, root);
			getSingularRestriction(Company_.id, parentCompanyId, useParentCompanyId, parentCompanyIdComparator, predicates, builder,
					root.join(Company_.parentCompany, JoinType.LEFT));
			Join<Company, Job> jobJoin = root.join(Company_.jobs, JoinType.LEFT);
			getSingularRestriction(Job_.id, jobId, useJobId, jobIdComparator, predicates, builder, jobJoin);
			if (useAreEmployeesRequired) {
				Predicate pred = builder.lessThan(builder.size(jobJoin.get(Job_.employments)), jobJoin.get(Job_.requiredEmploymentsCount));
				predicates.add(areEmployeesRequired ? pred : pred.not());
			}
			getSingularRestriction(builder.size(root.get(Company_.jobs)), jobCount, useJobCount, jobCountComparator, predicates, builder, root);
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

	@Override
	public EnumCompanyType getCompanyTypeFromId(Long companyId) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<EnumCompanyType> query = builder.createQuery(EnumCompanyType.class);
		Root<Company> root = query.from(Company.class);
		query.select(root.get(Company_.companyType)).where(builder.equal(root.get(Company_.id), companyId));
		return manager.createQuery(query).getSingleResult();
	}

	@Override
	public List<Company> getCompaniesFromData(String data, Integer resultsCount) {
		data = data.trim();
		if (!data.isEmpty()) {
			CriteriaBuilder builder = manager.getCriteriaBuilder();
			CriteriaQuery<Company> query = builder.createQuery(Company.class);
			query.distinct(true);
			Root<Company> company = query.from(Company.class);
			List<Predicate> predicates = new ArrayList<>();
			try {
				Long id = Long.parseLong(data);
				predicates.add(builder.equal(company.get(Company_.id), id));
				predicates.add(builder.equal(company.join(Company_.idCards, JoinType.LEFT).get(IdCard_.id), id));
			}
			catch (NumberFormatException e) {}
			data = "%" + data.replaceAll(" ", "%") + "%";
			predicates.add(builder.like(company.get(Company_.name), data));
			query.select(company).where(builder.or(predicates.toArray(new Predicate[predicates.size()])));
			return manager.createQuery(query).setMaxResults(resultsCount).getResultList();
		}
		return new ArrayList<>();
	}
}
