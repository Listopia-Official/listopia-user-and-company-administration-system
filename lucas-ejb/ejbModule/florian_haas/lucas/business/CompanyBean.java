package florian_haas.lucas.business;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.validation.Validator;
import javax.validation.executable.*;

import florian_haas.lucas.database.*;
import florian_haas.lucas.model.*;
import florian_haas.lucas.model.validation.ValidEntityId;
import florian_haas.lucas.util.validation.*;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@ValidateOnExecution(type = ExecutableType.IMPLICIT)
public class CompanyBean implements CompanyBeanLocal {

	@Inject
	@JPADAO
	private CompanyDAO companyDao;

	@Inject
	@JPADAO
	private TaxdataDAO taxdataDao;

	@Resource
	private Validator validator;

	@Override
	public Company createCompany(@NotBlankString String name, @NotBlankString String description, @NotBlankString String room, Integer section,
			EnumCompanyType companyType, List<@TypeNotNull Employment> managers, Integer requiredEmployeesCount) {
		Company company = new Company(name, description, room, section, companyType, managers, requiredEmployeesCount);
		companyDao.persist(company);
		return company;
	}

	@Override
	public List<Company> findAll() {
		return companyDao.findAll();
	}

	@Override
	public Company findById(@ValidEntityId(entityClass = Company.class) Long companyId) {
		return companyDao.findById(companyId);
	}

	@Override
	public List<Company> findCompanies(Long companyId, String name, String description, String room, Integer section, EnumCompanyType companyType,
			Long parentCompanyId, Integer requiredEmployeesCount, Boolean areEmployeesRequired, Boolean useId, Boolean useName,
			Boolean useDescription, Boolean useRoom, Boolean useSection, Boolean useCompanyType, Boolean useParentCompanyId,
			Boolean useRequiredEmployeesCount, Boolean useAreEmployeesRequired, EnumQueryComparator idComparator, EnumQueryComparator nameComparator,
			EnumQueryComparator descriptionComparator, EnumQueryComparator roomComparator, EnumQueryComparator sectionComparator,
			EnumQueryComparator companyTypeComparator, EnumQueryComparator parentCompanyIdComparator,
			EnumQueryComparator requiredEmployeesCountComparator) {
		return companyDao.findCompanies(companyId, name, description, room, section, companyType, parentCompanyId, requiredEmployeesCount,
				areEmployeesRequired, useId, useName, useDescription, useRoom, useSection, useCompanyType, useParentCompanyId,
				useRequiredEmployeesCount, useAreEmployeesRequired, idComparator, nameComparator, descriptionComparator, roomComparator,
				sectionComparator, companyTypeComparator, parentCompanyIdComparator, requiredEmployeesCountComparator);
	}

	@Override
	public Company setName(@ValidEntityId(entityClass = Company.class) Long companyId, @NotBlankString String name) {
		Company comp = findById(companyId);
		comp.setName(name);
		return comp;
	}

	@Override
	public Company setDescription(@ValidEntityId(entityClass = Company.class) Long companyId, @NotBlankString String description) {
		Company comp = companyDao.findById(companyId);
		comp.setDescription(description);
		return comp;
	}

	@Override
	public Company setRoom(@ValidEntityId(entityClass = Company.class) Long companyId, @NotBlankString String room) {
		Company comp = companyDao.findById(companyId);
		comp.setRoom(room);
		return comp;
	}

	@Override
	public Company setSection(@ValidEntityId(entityClass = Company.class) Long companyId, Integer section) {
		Company comp = companyDao.findById(companyId);
		comp.setSection(section);
		return comp;
	}

	@Override
	public Company setCompanyType(@ValidEntityId(entityClass = Company.class) Long companyId, EnumCompanyType companyType) {
		Company comp = companyDao.findById(companyId);
		comp.setCompanyType(companyType);
		return comp;
	}

	@Override
	public Company setParentCompany(@ValidEntityId(entityClass = Company.class) Long companyId,
			@ValidEntityId(entityClass = Company.class) Long parentCompanyId) {
		return setParentCompanyHelper(companyId, parentCompanyId);
	}

	@Override
	public Company removeParentCompany(@ValidEntityId(entityClass = Company.class) Long companyId) {
		return setParentCompanyHelper(companyId, null);
	}

	private Company setParentCompanyHelper(Long companyId, Long newParentCompany) {
		Company comp = companyDao.findById(companyId);
		Company parent = newParentCompany != null ? findById(newParentCompany) : null;
		if (comp.getParentCompany() != null) {
			Company parent2 = comp.getParentCompany();
			parent2.removeChildCompany(comp);
		}
		comp.setParentCompany(parent);
		if (parent != null) parent.addChildCompany(comp);
		return comp;
	}

	@Override
	public Company setRequiredEmployeesCount(@ValidEntityId(entityClass = Company.class) Long companyId, Integer requiredEmployeesCount) {
		Company comp = companyDao.findById(companyId);
		comp.setRequiredEmployeesCount(requiredEmployeesCount);
		return comp;
	}

	@Override
	public Company addTaxdata(@ValidEntityId(entityClass = Company.class) Long companyId, LocalDate date, BigDecimal incomings,
			BigDecimal outgoings) {
		Company comp = companyDao.findById(companyId);
		Taxdata taxdata = new Taxdata(comp, date, incomings, outgoings);
		comp.addTaxdata(taxdata);
		return comp;
	}

	@Override
	public void removeTaxdata(@ValidEntityId(entityClass = Taxdata.class) Long taxdataId) {
		Taxdata taxdata = taxdataDao.findById(taxdataId);
		Company comp = taxdata.getCompany();
		comp.removeTaxdata(taxdata);
	}

	@Override
	public Taxdata setIncomings(@ValidEntityId(entityClass = Taxdata.class) Long taxdataId, BigDecimal incomings) {
		Taxdata taxdata = taxdataDao.findById(taxdataId);
		taxdata.setIncomings(incomings);
		return taxdata;
	}

	@Override
	public Taxdata setOutgoings(@ValidEntityId(entityClass = Taxdata.class) Long taxdataId, BigDecimal outgoings) {
		Taxdata taxdata = taxdataDao.findById(taxdataId);
		taxdata.setOutgoings(outgoings);
		return taxdata;
	}

}
