package florian_haas.lucas.business;

import static florian_haas.lucas.security.EnumPermission.*;

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
import florian_haas.lucas.security.*;
import florian_haas.lucas.util.Utils;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@ValidateOnExecution(type = ExecutableType.IMPLICIT)
@Secured
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

	@EJB
	private EmploymentBeanLocal employmentBean;

	@Override
	@RequiresPermissions(COMPANY_CREATE)
	public Long createCompany(String name, String description, String room, Integer section, EnumCompanyType companyType, Long parentCompanyId,
			List<User> managers, Integer requiredEmployeesCount) {
		checkIsNameUnique(name);
		checkIsLocationUnique(room, section);
		Company company = new Company(name, description, room, section, companyType, requiredEmployeesCount);
		if (companyType == EnumCompanyType.STATE) {
			company.getAccount().setIsProtected(Boolean.TRUE);
		}
		companyDao.persist(company);
		companyDao.flush();
		managers.forEach(user -> {
			employmentBean.addDefaultEmployment(user.getId(), company.getId(), EnumEmployeePosition.MANAGER);
		});
		if (parentCompanyId != null) {
			setParentCompany(company.getId(), parentCompanyId);
		}
		return company.getId();
	}

	@Override
	@RequiresPermissions(COMPANY_FIND_ALL)
	public List<Company> findAll() {
		return companyDao.findAll();
	}

	@Override
	@RequiresPermissions(COMPANY_FIND_BY_ID)
	public Company findById(Long companyId) {
		return companyDao.findById(companyId);
	}

	@Override
	@RequiresPermissions(COMPANY_FIND_DYNAMIC)
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
	@RequiresPermissions(COMPANY_SET_NAME)
	public Boolean setName(Long companyId, String name) {
		Company comp = findById(companyId);
		if (comp.getName().equals(name)) return Boolean.FALSE;
		checkIsNameUnique(name);
		comp.setName(name);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(COMPANY_SET_DESCRIPTION)
	public Boolean setDescription(Long companyId, String description) {
		Company comp = companyDao.findById(companyId);
		if (comp.getDescription().equals(description)) return Boolean.FALSE;
		comp.setDescription(description);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(COMPANY_SET_ROOM)
	public Boolean setRoom(Long companyId, String room) {
		Company comp = companyDao.findById(companyId);
		if (comp.getRoom().equals(room)) return Boolean.FALSE;
		checkIsLocationUnique(room, comp.getSection());
		comp.setRoom(room);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(COMPANY_SET_SECTION)
	public Boolean setSection(Long companyId, Integer section) {
		Company comp = companyDao.findById(companyId);
		if (comp.getSection().equals(section)) return Boolean.FALSE;
		checkIsLocationUnique(comp.getRoom(), section);
		comp.setSection(section);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(COMPANY_SET_COMPANY_TYPE)
	public Boolean setCompanyType(Long companyId, EnumCompanyType companyType) {
		Company comp = companyDao.findById(companyId);
		if (comp.getCompanyType().equals(companyType)) return Boolean.FALSE;
		comp.getAccount().setIsProtected(companyType == EnumCompanyType.STATE ? Boolean.TRUE : Boolean.FALSE);
		comp.setCompanyType(companyType);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(COMPANY_SET_PARENT_COMPANY)
	public Boolean setParentCompany(Long companyId, Long parentCompanyId) {
		return setParentCompanyHelper(companyId, parentCompanyId);
	}

	@Override
	@RequiresPermissions(COMPANY_REMOVE_PARENT_COMPANY)
	public Boolean removeParentCompany(Long companyId) {
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
	@RequiresPermissions(COMPANY_SET_REQUIRED_EMPLOYEES_COUNT)
	public Boolean setRequiredEmployeesCount(Long companyId, Integer requiredEmployeesCount) {
		Company comp = companyDao.findById(companyId);
		if (comp.getRequiredEmployeesCount().equals(requiredEmployeesCount)) return Boolean.FALSE;
		comp.setRequiredEmployeesCount(requiredEmployeesCount);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(COMPANY_ADD_TAXDATA)
	public Boolean addTaxdata(Long companyId, LocalDate date, BigDecimal incomings, BigDecimal outgoings) {
		Company comp = companyDao.findById(companyId);
		Taxdata taxdata = new Taxdata(comp, date, incomings, outgoings);
		comp.addTaxdata(taxdata);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(COMPANY_REMOVE_TAXDATA)
	public Boolean removeTaxdata(Long taxdataId) {
		Taxdata taxdata = taxdataDao.findById(taxdataId);
		Company comp = taxdata.getCompany();
		return comp.removeTaxdata(taxdata);
	}

	@Override
	@RequiresPermissions(COMPANY_SET_INCOMINGS)
	public Boolean setIncomings(Long taxdataId, BigDecimal incomings) {
		Taxdata taxdata = taxdataDao.findById(taxdataId);
		if (Utils.isEqual(taxdata.getIncomings(), incomings)) return Boolean.FALSE;
		taxdata.setIncomings(incomings);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(COMPANY_SET_OUTGOINGS)
	public Boolean setOutgoings(Long taxdataId, BigDecimal outgoings) {
		Taxdata taxdata = taxdataDao.findById(taxdataId);
		if (Utils.isEqual(taxdata.getOutgoings(), outgoings)) return Boolean.FALSE;
		taxdata.setOutgoings(outgoings);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(COMPANY_ADD_COMPANY_CARD)
	public Boolean addCompanyCard(Long companyId) {
		Company company = companyDao.findById(companyId);
		CompanyCard companyCard = new CompanyCard(company);
		return company.addCompanyCard(companyCard);
	}

	@Override
	@RequiresPermissions(COMPANY_BLOCK_COMPANY_CARD)
	public Boolean blockCompanyCard(Long companyCardId) {
		CompanyCard companyCard = companyCardDao.findById(companyCardId);
		if (companyCard.getBlocked()) { return Boolean.FALSE; }
		companyCard.setBlocked(Boolean.TRUE);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(COMPANY_UNBLOCK_COMPANY_CARD)
	public Boolean unblockCompanyCard(Long companyCardId) {
		CompanyCard companyCard = companyCardDao.findById(companyCardId);
		if (!companyCard.getBlocked()) { return Boolean.FALSE; }
		companyCard.setBlocked(Boolean.FALSE);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(COMPANY_EXISTS_LOCATION)
	public Boolean existsLocation(String room, Integer section) {
		return companyDao.existsLocation(room, section);
	}

	private void checkIsNameUnique(String name) {
		if (!companyDao.isNameUnique(name)) throw new LucasException("The name is used by another company");
	}

	private void checkIsLocationUnique(String room, Integer section) {
		if (!companyDao.existsLocation(room, section)) throw new LucasException("Another company is assigned to the location");
	}
}
