package florian_haas.lucas.business;

import static florian_haas.lucas.security.EnumPermission.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.validation.Validator;
import javax.validation.executable.*;

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;
import florian_haas.lucas.security.*;
import florian_haas.lucas.util.Utils;

@Stateless
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
	private RoomSectionDAO roomSectionDao;

	@EJB
	private AccountBeanLocal accountBean;

	@Resource
	private Validator validator;

	@EJB
	private EmploymentBeanLocal employmentBean;

	@EJB
	private JobBeanLocal jobBean;

	@EJB
	private LoginBeanLocal loginBean;

	@EJB
	private UserBeanLocal userBean;

	@EJB
	private GlobalDataBeanLocal globalData;

	@Override
	@RequiresPermissions(COMPANY_CREATE)
	public Long createCompany(String name, String description, Long roomSectionId, EnumCompanyType companyType, Long parentCompanyId,
			Boolean generateJobs, List<Long> managerUserIds, Integer requiredEmployeesCount, Boolean isAdvisorRequired, String advisorJobName,
			String advisorJobDescription, String managerJobName, String managerJobDescription, String employeeJobName,
			String employeeJobDescription) {
		checkIsNameUnique(name);
		if (roomSectionId != null) checkIsSectionUnique(roomSectionId);
		RoomSection section = roomSectionId != null ? roomSectionDao.findById(roomSectionId) : null;
		Company company = new Company(name, description, section, companyType);
		if (companyType == EnumCompanyType.STATE) {
			company.getAccount().setIsProtected(Boolean.TRUE);
		}

		companyDao.persist(company);
		companyDao.flush();

		if (parentCompanyId != null) {
			setParentCompany(company.getId(), parentCompanyId);
		}
		if (section != null) {
			section.setCompany(company);
		}

		if (generateJobs) {
			Integer optimalCivilManagerCount = globalData.getOptimalCivilManagerCount();

			// Create Job for advisor if one is definitely needed
			if (companyType == EnumCompanyType.CIVIL) {
				Integer minSchoolGrade = globalData.getMinCivilManagerSchoolGrade();
				int optimalUsersCounter = 0;
				for (Long id : managerUserIds) {
					ReadOnlyUser user = userBean.findById(id);
					if ((user.getSchoolClass() != null ? user.getSchoolClass().getGrade() : Integer.MAX_VALUE) >= minSchoolGrade)
						optimalUsersCounter++;
				}
				if ((!managerUserIds.isEmpty() && optimalUsersCounter < 1) || isAdvisorRequired) {
					jobBean.createJob(advisorJobName, advisorJobDescription, company.getId(), EnumEmployeePosition.ADVISOR, 1, null);
				}
			}
			// Create Job for managers
			Long managerJobId = jobBean.createJob(managerJobName, managerJobDescription, company.getId(), EnumEmployeePosition.MANAGER,
					optimalCivilManagerCount, null);
			managerUserIds.forEach(userId -> {
				employmentBean.createEmployment(userId, managerJobId, null);
			});

			// Create Job for employees
			jobBean.createJob(employeeJobName, employeeJobDescription, company.getId(), EnumEmployeePosition.EMPLOYEE, requiredEmployeesCount, null);
		}

		return company.getId();
	}

	@Override
	@RequiresPermissions(COMPANY_FIND_ALL)
	public List<? extends ReadOnlyCompany> findAll() {
		return companyDao.findAll();
	}

	@Override
	@RequiresPermissions(COMPANY_FIND_BY_ID)
	public Company findById(Long companyId) {
		return companyDao.findById(companyId);
	}

	@Override
	@RequiresPermissions(COMPANY_FIND_DYNAMIC)
	public List<Company> findCompanies(Long companyId, String name, String description, Long roomSectionId, EnumCompanyType companyType,
			Long parentCompanyId, Long jobId, Boolean areEmployeesRequired, Integer jobCount, Boolean useId, Boolean useName, Boolean useDescription,
			Boolean useRoomSectionId, Boolean useCompanyType, Boolean useParentCompanyId, Boolean useJobId, Boolean useAreEmployeesRequired,
			Boolean useJobCount, EnumQueryComparator idComparator, EnumQueryComparator nameComparator, EnumQueryComparator descriptionComparator,
			EnumQueryComparator roomSectionIdComparator, EnumQueryComparator companyTypeComparator, EnumQueryComparator parentCompanyIdComparator,
			EnumQueryComparator jobIdComparator, EnumQueryComparator jobCountComparator) {
		return (!useId && !useName && !useDescription && !useRoomSectionId && !useCompanyType && !useParentCompanyId && !useJobId
				&& !useAreEmployeesRequired && !useJobCount
				&& !loginBean.getSubject().isPermitted(EnumPermission.COMPANY_FIND_ALL.getPermissionString()))
						? new ArrayList<>()
						: companyDao.findCompanies(companyId, name, description, roomSectionId, companyType, parentCompanyId, jobId,
								areEmployeesRequired, jobCount, useId, useName, useDescription, useRoomSectionId, useCompanyType, useParentCompanyId,
								useJobId, useAreEmployeesRequired, useJobCount, idComparator, nameComparator, descriptionComparator,
								roomSectionIdComparator, companyTypeComparator, parentCompanyIdComparator, jobIdComparator, jobCountComparator);
	}

	@Override
	@RequiresPermissions(COMPANY_SET_NAME)
	public Boolean setName(Long companyId, String name) {
		Company comp = companyDao.findById(companyId);
		if (comp.getName().equals(name)) return Boolean.FALSE;
		checkIsNameUnique(name);
		comp.setName(name);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(COMPANY_SET_DESCRIPTION)
	public Boolean setDescription(Long companyId, String description) {
		Company comp = companyDao.findById(companyId);
		if ((comp.getDescription() == null && description == null) || (comp.getDescription() != null && comp.getDescription().equals(description)))
			return Boolean.FALSE;
		comp.setDescription(description);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(COMPANY_SET_SECTION)
	public Boolean setSection(Long companyId, Long sectionId) {
		Company comp = companyDao.findById(companyId);
		RoomSection oldSection = comp.getSection();
		RoomSection section = sectionId != null ? roomSectionDao.findById(sectionId) : null;
		if ((oldSection == null && section == null) || oldSection != null && oldSection.equals(section)) return Boolean.FALSE;
		if (sectionId != null) checkIsSectionUnique(sectionId);
		comp.setSection(section);
		if (section != null) section.setCompany(comp);
		if (oldSection != null) oldSection.setCompany(null);
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
		Company oldParentCompany = comp.getParentCompany();
		if ((oldParentCompany == null && parent == null) || (oldParentCompany != null && oldParentCompany.equals(parent))) return Boolean.FALSE;
		if (comp.getParentCompany() != null) {
			oldParentCompany.removeChildCompany(comp);
		}
		comp.setParentCompany(parent);
		if (parent != null) parent.addChildCompany(comp);
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

	private void checkIsNameUnique(String name) {
		if (!companyDao.isNameUnique(name)) throw new LucasException("The name is used by another company", NAME_NOT_UNIQUE_EXCEPTION_MARKER);
	}

	private void checkIsSectionUnique(Long sectionId) {
		if (!companyDao.isRoomSectionUnique(sectionId))
			throw new LucasException("Another company is assigned to the section", SECTION_NOT_UNIQUE_EXCEPTION_MARKER);
	}

	@Override
	@RequiresPermissions(COMPANY_GET_MANAGERS)
	public List<Employment> getManagers(Long companyId) {
		return companyDao.findById(companyId).getManagers();
	}

	@Override
	@RequiresPermissions(COMPANY_GET_ADVISORS)
	public List<Employment> getAdvisors(Long companyId) {
		return companyDao.findById(companyId).getAdvisors();
	}

	@Override
	@RequiresPermissions(COMPANY_GET_EMPLOYEES)
	public List<Employment> getEmployees(Long companyId) {
		return companyDao.findById(companyId).getEmployees();
	}

	@Override
	@RequiresPermissions(COMPANY_GET_EMPLOYMENTS)
	public List<Employment> getEmployments(Long companyId) {
		return companyDao.findById(companyId).getAllEmployees();
	}

	@Override
	@RequiresPermissions(COMPANY_GET_COMPANY_TYPE_FROM_ID)
	public EnumCompanyType getCompanyTypeFromId(Long companyId) {
		return companyDao.getCompanyTypeFromId(companyId);
	}

	@Override
	@RequiresPermissions(COMPANY_FIND_BY_DATA)
	public List<? extends ReadOnlyCompany> getCompaniesByData(String data, Integer resultsCount) {
		return companyDao.getCompaniesFromData(data, resultsCount);
	}

	@Override
	@RequiresPermissions(COMPANY_GET_PURCHASE_LOGS)
	public List<? extends ReadOnlyPurchaseLog> getPurchaseLogs(Long companyId) {
		return companyDao.findById(companyId).getPurchaseLogs();
	}

	@Override
	@RequiresPermissions(COMPANY_GET_JOBS)
	public List<? extends ReadOnlyJob> getJobs(Long companyId) {
		return companyDao.findById(companyId).getJobs();
	}

}
