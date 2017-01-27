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
import florian_haas.lucas.util.Utils;
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

	@Inject
	@JPADAO
	private CompanyCardDAO companyCardDao;

	@EJB
	private AccountBeanLocal accountBean;

	@Resource
	private Validator validator;

	@Override
	public Long createCompany(@NotBlankString String name, @NotBlankString String description, @NotBlankString String room, Integer section,
			EnumCompanyType companyType, List<@TypeNotNull Employment> managers, Integer requiredEmployeesCount) {
		Company company = new Company(name, description, room, section, companyType, managers, requiredEmployeesCount);
		if (companyType == EnumCompanyType.STATE) {
			company.getAccount().setIsProtected(Boolean.TRUE);
		}
		companyDao.persist(company);
		return company.getId();
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
	public Boolean setName(@ValidEntityId(entityClass = Company.class) Long companyId, @NotBlankString String name) {
		Company comp = findById(companyId);
		if (comp.getName().equals(name)) return Boolean.FALSE;
		comp.setName(name);
		return Boolean.TRUE;
	}

	@Override
	public Boolean setDescription(@ValidEntityId(entityClass = Company.class) Long companyId, @NotBlankString String description) {
		Company comp = companyDao.findById(companyId);
		if (comp.getDescription().equals(description)) return Boolean.FALSE;
		comp.setDescription(description);
		return Boolean.TRUE;
	}

	@Override
	public Boolean setRoom(@ValidEntityId(entityClass = Company.class) Long companyId, @NotBlankString String room) {
		Company comp = companyDao.findById(companyId);
		if (comp.getRoom().equals(room)) return Boolean.FALSE;
		comp.setRoom(room);
		return Boolean.TRUE;
	}

	@Override
	public Boolean setSection(@ValidEntityId(entityClass = Company.class) Long companyId, Integer section) {
		Company comp = companyDao.findById(companyId);
		if (comp.getSection().equals(section)) return Boolean.FALSE;
		comp.setSection(section);
		return Boolean.TRUE;
	}

	@Override
	public Boolean setCompanyType(@ValidEntityId(entityClass = Company.class) Long companyId, EnumCompanyType companyType) {
		Company comp = companyDao.findById(companyId);
		if (comp.getCompanyType().equals(companyType)) return Boolean.FALSE;
		comp.getAccount().setIsProtected(companyType == EnumCompanyType.STATE ? Boolean.TRUE : Boolean.FALSE);
		comp.setCompanyType(companyType);
		return Boolean.TRUE;
	}

	@Override
	public Boolean setParentCompany(@ValidEntityId(entityClass = Company.class) Long companyId,
			@ValidEntityId(entityClass = Company.class) Long parentCompanyId) {
		return setParentCompanyHelper(companyId, parentCompanyId);
	}

	@Override
	public Boolean removeParentCompany(@ValidEntityId(entityClass = Company.class) Long companyId) {
		return setParentCompanyHelper(companyId, null);
	}

	private Boolean setParentCompanyHelper(Long companyId, Long newParentCompany) {
		Company comp = companyDao.findById(companyId);
		Company parent = newParentCompany != null ? findById(newParentCompany) : null;
		if (comp.getParentCompany().equals(parent)) return Boolean.FALSE;
		if (comp.getParentCompany() != null) {
			Company parent2 = comp.getParentCompany();
			parent2.removeChildCompany(comp);
		}
		comp.setParentCompany(parent);
		if (parent != null) parent.addChildCompany(comp);
		return Boolean.TRUE;
	}

	@Override
	public Boolean setRequiredEmployeesCount(@ValidEntityId(entityClass = Company.class) Long companyId, Integer requiredEmployeesCount) {
		Company comp = companyDao.findById(companyId);
		if (comp.getRequiredEmployeesCount().equals(requiredEmployeesCount)) return Boolean.FALSE;
		comp.setRequiredEmployeesCount(requiredEmployeesCount);
		return Boolean.TRUE;
	}

	@Override
	public Boolean addTaxdata(@ValidEntityId(entityClass = Company.class) Long companyId, LocalDate date, BigDecimal incomings,
			BigDecimal outgoings) {
		Company comp = companyDao.findById(companyId);
		Taxdata taxdata = new Taxdata(comp, date, incomings, outgoings);
		comp.addTaxdata(taxdata);
		return Boolean.TRUE;
	}

	@Override
	public Boolean removeTaxdata(@ValidEntityId(entityClass = Taxdata.class) Long taxdataId) {
		Taxdata taxdata = taxdataDao.findById(taxdataId);
		Company comp = taxdata.getCompany();
		return comp.removeTaxdata(taxdata);
	}

	@Override
	public Boolean setIncomings(@ValidEntityId(entityClass = Taxdata.class) Long taxdataId, BigDecimal incomings) {
		Taxdata taxdata = taxdataDao.findById(taxdataId);
		if (Utils.isEqual(taxdata.getIncomings(), incomings)) return Boolean.FALSE;
		taxdata.setIncomings(incomings);
		return Boolean.TRUE;
	}

	@Override
	public Boolean setOutgoings(@ValidEntityId(entityClass = Taxdata.class) Long taxdataId, BigDecimal outgoings) {
		Taxdata taxdata = taxdataDao.findById(taxdataId);
		if (Utils.isEqual(taxdata.getOutgoings(), outgoings)) return Boolean.FALSE;
		taxdata.setOutgoings(outgoings);
		return Boolean.TRUE;
	}

	@Override
	public Boolean addCompanyCard(@ValidEntityId(entityClass = Company.class) Long companyId) {
		Company company = companyDao.findById(companyId);
		CompanyCard companyCard = new CompanyCard(company);
		return company.addCompanyCard(companyCard);
	}

	@Override
	public Boolean blockCompanyCard(@ValidEntityId(entityClass = CompanyCard.class) Long companyCardId) {
		CompanyCard companyCard = companyCardDao.findById(companyCardId);
		if (companyCard.getBlocked()) { return Boolean.FALSE; }
		companyCard.setBlocked(Boolean.TRUE);
		return Boolean.TRUE;
	}

	@Override
	public Boolean unblockCompanyCard(@ValidEntityId(entityClass = CompanyCard.class) Long companyCardId) {
		CompanyCard companyCard = companyCardDao.findById(companyCardId);
		if (!companyCard.getBlocked()) { return Boolean.FALSE; }
		companyCard.setBlocked(Boolean.FALSE);
		return Boolean.TRUE;
	}

	@Override
	public Long transactionToParentCompany(@ValidEntityId(entityClass = Company.class) Long companyId, BigDecimal amount, String comment) {
		Company company = companyDao.findById(companyId);
		if (company.getParentCompany() != null) {
			Company parent = company.getParentCompany();
			accountBean.transaction(company.getId(), parent.getId(), amount, comment);
		}
		return company.getId();
	}

}
