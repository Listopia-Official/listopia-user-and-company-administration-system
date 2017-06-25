package florian_haas.lucas.web;

import java.time.*;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.NotBlank;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

import florian_haas.lucas.business.*;
import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;
import florian_haas.lucas.security.EnumPermission;
import florian_haas.lucas.util.Utils;
import florian_haas.lucas.validation.*;
import florian_haas.lucas.web.converter.*;
import florian_haas.lucas.web.util.WebUtils;

@Named
@ViewScoped
public class CompanyBean extends BaseBean<ReadOnlyCompany> {

	public CompanyBean() {
		super("company", 7);
	}

	private static final long serialVersionUID = 5394240973288053983L;

	@EJB
	private CompanyBeanLocal companyBean;

	@EJB
	private EntityBeanLocal entityBean;

	@EJB
	private UserBeanLocal userBean;

	@EJB
	private RoomBeanLocal roomBean;

	@NotNull
	@Min(0)
	private Long searchCompanyId = 0l;

	@NotNull
	private Boolean useSearchCompanyId = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchCompanyIdComparator = EnumQueryComparator.EQUAL;

	@NotBlank
	private String searchCompanyName = "";

	@NotNull
	private Boolean useSearchCompanyName = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.TEXT)
	private EnumQueryComparator searchCompanyNameComparator = EnumQueryComparator.EQUAL;

	@NotBlankString
	@Size(max = 255)
	private String searchCompanyDescription = null;

	@NotNull
	private Boolean useSearchCompanyDescription = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.TEXT)
	private EnumQueryComparator searchCompanyDescriptionComparator = EnumQueryComparator.EQUAL;

	private ReadOnlyRoomSection searchCompanySection = null;

	@NotNull
	private Boolean useSearchCompanySectionId = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchCompanySectionIdComparator = EnumQueryComparator.EQUAL;

	@NotNull
	private EnumCompanyType searchCompanyCompanyType = EnumCompanyType.CIVIL;

	@NotNull
	private Boolean useSearchCompanyCompanyType = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.LOGIC)
	private EnumQueryComparator searchCompanyCompanyTypeComparator = EnumQueryComparator.EQUAL;

	private ReadOnlyCompany searchCompanyParentCompany;

	@NotNull
	private Boolean useSearchCompanyParentCompanyId = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchCompanyParentCompanyIdComparator = EnumQueryComparator.EQUAL;

	@NotNull
	private ReadOnlyJob searchCompanyJob = null;

	@NotNull
	private Boolean useSearchCompanyJobId = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchCompanyJobIdComparator = EnumQueryComparator.EQUAL;

	@NotNull
	private Boolean searchCompanyAreEmployeesRequired = Boolean.FALSE;

	@NotNull
	private Boolean useSearchCompanyAreEmployeesRequired = Boolean.FALSE;

	@NotNull
	@Min(0)
	private Integer searchCompanyJobsCount = 0;

	@NotNull
	private Boolean useSearchCompanyJobsCount = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchCompanyJobsCountComparator = EnumQueryComparator.EQUAL;

	public Long getSearchCompanyId() {
		return this.searchCompanyId;
	}

	public void setSearchCompanyId(Long searchCompanyId) {
		this.searchCompanyId = searchCompanyId;
	}

	public Boolean getUseSearchCompanyId() {
		return this.useSearchCompanyId;
	}

	public void setUseSearchCompanyId(Boolean useSearchCompanyId) {
		this.useSearchCompanyId = useSearchCompanyId;
	}

	public EnumQueryComparator getSearchCompanyIdComparator() {
		return this.searchCompanyIdComparator;
	}

	public void setSearchCompanyIdComparator(EnumQueryComparator searchCompanyIdComparator) {
		this.searchCompanyIdComparator = searchCompanyIdComparator;
	}

	public String getSearchCompanyName() {
		return this.searchCompanyName;
	}

	public void setSearchCompanyName(String searchCompanyName) {
		this.searchCompanyName = searchCompanyName;
	}

	public Boolean getUseSearchCompanyName() {
		return this.useSearchCompanyName;
	}

	public void setUseSearchCompanyName(Boolean useSearchCompanyName) {
		this.useSearchCompanyName = useSearchCompanyName;
	}

	public EnumQueryComparator getSearchCompanyNameComparator() {
		return this.searchCompanyNameComparator;
	}

	public void setSearchCompanyNameComparator(EnumQueryComparator searchCompanyNameComparator) {
		this.searchCompanyNameComparator = searchCompanyNameComparator;
	}

	public String getSearchCompanyDescription() {
		return this.searchCompanyDescription;
	}

	public void setSearchCompanyDescription(String searchCompanyDescription) {
		this.searchCompanyDescription = searchCompanyDescription;
	}

	public Boolean getUseSearchCompanyDescription() {
		return this.useSearchCompanyDescription;
	}

	public void setUseSearchCompanyDescription(Boolean useSearchCompanyDescription) {
		this.useSearchCompanyDescription = useSearchCompanyDescription;
	}

	public EnumQueryComparator getSearchCompanyDescriptionComparator() {
		return this.searchCompanyDescriptionComparator;
	}

	public void setSearchCompanyDescriptionComparator(EnumQueryComparator searchCompanyDescriptionComparator) {
		this.searchCompanyDescriptionComparator = searchCompanyDescriptionComparator;
	}

	public ReadOnlyRoomSection getSearchCompanySection() {
		return searchCompanySection;
	}

	public void setSearchCompanySection(ReadOnlyRoomSection searchCompanySection) {
		this.searchCompanySection = searchCompanySection;
	}

	public Boolean getUseSearchCompanySectionId() {
		return useSearchCompanySectionId;
	}

	public void setUseSearchCompanySectionId(Boolean useSearchCompanySectionId) {
		this.useSearchCompanySectionId = useSearchCompanySectionId;
	}

	public EnumQueryComparator getSearchCompanySectionIdComparator() {
		return searchCompanySectionIdComparator;
	}

	public void setSearchCompanySectionIdComparator(EnumQueryComparator searchCompanySectionIdComparator) {
		this.searchCompanySectionIdComparator = searchCompanySectionIdComparator;
	}

	public EnumCompanyType getSearchCompanyCompanyType() {
		return this.searchCompanyCompanyType;
	}

	public void setSearchCompanyCompanyType(EnumCompanyType searchCompanyCompanyType) {
		this.searchCompanyCompanyType = searchCompanyCompanyType;
	}

	public Boolean getUseSearchCompanyCompanyType() {
		return this.useSearchCompanyCompanyType;
	}

	public void setUseSearchCompanyCompanyType(Boolean useSearchCompanyCompanyType) {
		this.useSearchCompanyCompanyType = useSearchCompanyCompanyType;
	}

	public EnumQueryComparator getSearchCompanyCompanyTypeComparator() {
		return this.searchCompanyCompanyTypeComparator;
	}

	public void setSearchCompanyCompanyTypeComparator(EnumQueryComparator searchCompanyCompanyTypeComparator) {
		this.searchCompanyCompanyTypeComparator = searchCompanyCompanyTypeComparator;
	}

	public ReadOnlyCompany getSearchCompanyParentCompany() {
		return this.searchCompanyParentCompany;
	}

	public void setSearchCompanyParentCompany(ReadOnlyCompany searchCompanyParentCompany) {
		this.searchCompanyParentCompany = searchCompanyParentCompany;
	}

	public Boolean getUseSearchCompanyParentCompanyId() {
		return this.useSearchCompanyParentCompanyId;
	}

	public void setUseSearchCompanyParentCompanyId(Boolean useSearchCompanyParentCompanyId) {
		this.useSearchCompanyParentCompanyId = useSearchCompanyParentCompanyId;
	}

	public EnumQueryComparator getSearchCompanyParentCompanyIdComparator() {
		return this.searchCompanyParentCompanyIdComparator;
	}

	public void setSearchCompanyParentCompanyIdComparator(EnumQueryComparator searchCompanyParentCompanyIdComparator) {
		this.searchCompanyParentCompanyIdComparator = searchCompanyParentCompanyIdComparator;
	}

	public ReadOnlyJob getSearchCompanyJob() {
		return searchCompanyJob;
	}

	public void setSearchCompanyJob(ReadOnlyJob searchCompanyJob) {
		this.searchCompanyJob = searchCompanyJob;
	}

	public Boolean getUseSearchCompanyJobId() {
		return useSearchCompanyJobId;
	}

	public void setUseSearchCompanyJobId(Boolean useSearchCompanyJobId) {
		this.useSearchCompanyJobId = useSearchCompanyJobId;
	}

	public EnumQueryComparator getSearchCompanyJobIdComparator() {
		return searchCompanyJobIdComparator;
	}

	public void setSearchCompanyJobIdComparator(EnumQueryComparator searchCompanyJobIdComparator) {
		this.searchCompanyJobIdComparator = searchCompanyJobIdComparator;
	}

	public Boolean getSearchCompanyAreEmployeesRequired() {
		return this.searchCompanyAreEmployeesRequired;
	}

	public void setSearchCompanyAreEmployeesRequired(Boolean searchCompanyAreEmployeesRequired) {
		this.searchCompanyAreEmployeesRequired = searchCompanyAreEmployeesRequired;
	}

	public Boolean getUseSearchCompanyAreEmployeesRequired() {
		return this.useSearchCompanyAreEmployeesRequired;
	}

	public void setUseSearchCompanyAreEmployeesRequired(Boolean useSearchCompanyAreEmployeesRequired) {
		this.useSearchCompanyAreEmployeesRequired = useSearchCompanyAreEmployeesRequired;
	}

	public Integer getSearchCompanyJobsCount() {
		return this.searchCompanyJobsCount;
	}

	public void setSearchCompanyJobsCount(Integer searchCompanyJobsCount) {
		this.searchCompanyJobsCount = searchCompanyJobsCount;
	}

	public Boolean getUseSearchCompanyJobsCount() {
		return this.useSearchCompanyJobsCount;
	}

	public void setUseSearchCompanyJobsCount(Boolean useSearchCompanyJobsCount) {
		this.useSearchCompanyJobsCount = useSearchCompanyJobsCount;
	}

	public EnumQueryComparator getSearchCompanyJobsCountComparator() {
		return this.searchCompanyJobsCountComparator;
	}

	public void setSearchCompanyJobsCountComparator(EnumQueryComparator searchCompanyJobsCountComparator) {
		this.searchCompanyJobsCountComparator = searchCompanyJobsCountComparator;
	}

	@Override
	public EnumPermission getFindDynamicPermission() {
		return EnumPermission.COMPANY_FIND_DYNAMIC;
	}

	@Override
	public EnumPermission getPrintPermission() {
		return EnumPermission.COMPANY_PRINT;
	}

	@Override
	public EnumPermission getExportPermission() {
		return EnumPermission.COMPANY_EXPORT;
	}

	@Override
	protected List<? extends ReadOnlyCompany> searchEntities() {
		return companyBean.findCompanies(searchCompanyId, searchCompanyName, searchCompanyDescription,
				searchCompanySection != null ? searchCompanySection.getId() : null, searchCompanyCompanyType,
				searchCompanyParentCompany != null ? searchCompanyParentCompany.getId() : null,
				searchCompanyJob != null ? searchCompanyJob.getId() : null, searchCompanyAreEmployeesRequired, searchCompanyJobsCount,
				useSearchCompanyId, useSearchCompanyName, useSearchCompanyDescription, useSearchCompanySectionId, useSearchCompanyCompanyType,
				useSearchCompanyParentCompanyId, useSearchCompanyJobId, useSearchCompanyAreEmployeesRequired, useSearchCompanyJobsCount,
				searchCompanyIdComparator, searchCompanyNameComparator, searchCompanyDescriptionComparator, searchCompanySectionIdComparator,
				searchCompanyCompanyTypeComparator, searchCompanyParentCompanyIdComparator, searchCompanyJobIdComparator,
				searchCompanyJobsCountComparator);
	}

	@Override
	protected ReadOnlyCompany entityGetter(Long entityId) {
		return companyBean.findById(entityId);
	}

	/*
	 * -------------------- Create Company Dialog Start --------------------
	 */

	@NotBlank
	private String createCompanyDialogName = "";

	@NotBlankString
	@Size(max = 255)
	private String createCompanyDialogDescription = null;

	private ReadOnlyRoomSection createCompanyDialogSection = null;

	@NotNull
	private EnumCompanyType createCompanyDialogCompanyType = EnumCompanyType.CIVIL;

	private ReadOnlyCompany createCompanyDialogParentCompany = null;

	@NotNull
	@Min(0)
	private Integer createCompanyDialogRequiredEmployeesCount = 0;

	@NotNull
	private List<@TypeNotNull ReadOnlyUser> createCompanyDialogManagerUsers = new ArrayList<>();

	@NotNull
	private ReadOnlyUser createCompanyDialogTmpManager = null;

	private ReadOnlyUser createCompanyDialogSelectedManager = null;

	@NotNull
	private Boolean createCompanyDialogIsAdvisorRequired = Boolean.FALSE;

	@NotNull
	private Boolean createCompanyDialogGenerateJobs = Boolean.FALSE;

	public String getCreateCompanyDialogName() {
		return createCompanyDialogName;
	}

	public void setCreateCompanyDialogName(String createCompanyDialogName) {
		this.createCompanyDialogName = createCompanyDialogName;
	}

	public String getCreateCompanyDialogDescription() {
		return createCompanyDialogDescription;
	}

	public void setCreateCompanyDialogDescription(String createCompanyDialogDescription) {
		this.createCompanyDialogDescription = createCompanyDialogDescription;
	}

	public ReadOnlyRoomSection getCreateCompanyDialogSection() {
		return createCompanyDialogSection;
	}

	public void setCreateCompanyDialogSection(ReadOnlyRoomSection createCompanyDialogSection) {
		this.createCompanyDialogSection = createCompanyDialogSection;
	}

	public EnumCompanyType getCreateCompanyDialogCompanyType() {
		return createCompanyDialogCompanyType;
	}

	public void setCreateCompanyDialogCompanyType(EnumCompanyType createCompanyDialogCompanyType) {
		this.createCompanyDialogCompanyType = createCompanyDialogCompanyType;
	}

	public ReadOnlyCompany getCreateCompanyDialogParentCompany() {
		return this.createCompanyDialogParentCompany;
	}

	public void setCreateCompanyDialogParentCompany(ReadOnlyCompany createCompanyDialogParentCompany) {
		this.createCompanyDialogParentCompany = createCompanyDialogParentCompany;
	}

	public Integer getCreateCompanyDialogRequiredEmployeesCount() {
		return createCompanyDialogRequiredEmployeesCount;
	}

	public void setCreateCompanyDialogRequiredEmployeesCount(Integer createCompanyDialogRequiredEmployeesCount) {
		this.createCompanyDialogRequiredEmployeesCount = createCompanyDialogRequiredEmployeesCount;
	}

	public List<ReadOnlyUser> getCreateCompanyDialogManagerUsers() {
		return createCompanyDialogManagerUsers;
	}

	public void setCreateCompanyDialogManagerUsers(List<ReadOnlyUser> createCompanyDialogManagerUsers) {
		this.createCompanyDialogManagerUsers = createCompanyDialogManagerUsers;
	}

	public ReadOnlyUser getCreateCompanyDialogTmpManager() {
		return createCompanyDialogTmpManager;
	}

	public void setCreateCompanyDialogTmpManager(ReadOnlyUser createCompanyDialogTmpManager) {
		this.createCompanyDialogTmpManager = createCompanyDialogTmpManager;
	}

	public void resetCreateCompanyDialogTmpManager() {
		createCompanyDialogTmpManager = null;
	}

	public void createCompanyDialogEditSelectedManager() {
		if (createCompanyDialogSelectedManager != null) {
			this.createCompanyDialogManagerUsers.remove(createCompanyDialogSelectedManager);
			this.createCompanyDialogTmpManager = createCompanyDialogSelectedManager;
			createCompanyDialogSelectedManager = null;
		}
	}

	public void createCompanyDialogRemoveSelectedManager() {
		this.createCompanyDialogManagerUsers.remove(createCompanyDialogSelectedManager);
		this.createCompanyDialogTmpManager = null;
		this.createCompanyDialogSelectedManager = null;
	}

	public ReadOnlyUser getCreateCompanyDialogSelectedManager() {
		return createCompanyDialogSelectedManager;
	}

	public void setCreateCompanyDialogSelectedManager(ReadOnlyUser createCompanyDialogSelectedManager) {
		this.createCompanyDialogSelectedManager = createCompanyDialogSelectedManager;
	}

	public Boolean getCreateCompanyDialogIsAdvisorRequired() {
		return this.createCompanyDialogIsAdvisorRequired;
	}

	public void setCreateCompanyDialogIsAdvisorRequired(Boolean createCompanyDialogIsAdvisorRequired) {
		this.createCompanyDialogIsAdvisorRequired = createCompanyDialogIsAdvisorRequired;
	}

	public Boolean getCreateCompanyDialogGenerateJobs() {
		return createCompanyDialogGenerateJobs;
	}

	public void setCreateCompanyDialogGenerateJobs(Boolean createCompanyDialogGenerateJobs) {
		this.createCompanyDialogGenerateJobs = createCompanyDialogGenerateJobs;
	}

	public void initCreateCompanyDialog() {
		createCompanyDialogName = "";
		createCompanyDialogDescription = null;
		createCompanyDialogSection = null;
		createCompanyDialogCompanyType = EnumCompanyType.CIVIL;
		createCompanyDialogParentCompany = null;
		createCompanyDialogRequiredEmployeesCount = 0;
		createCompanyDialogManagerUsers.clear();
		createCompanyDialogTmpManager = null;
		createCompanyDialogSelectedManager = null;
		createCompanyDialogIsAdvisorRequired = Boolean.FALSE;
		createCompanyDialogGenerateJobs = Boolean.FALSE;
	}

	public void createCompany() {
		WebUtils.executeTask((params) -> {
			List<Long> managerIds = new ArrayList<>();
			createCompanyDialogManagerUsers.forEach(user -> managerIds.add(user.getId()));
			params.add(WebUtils.getAsString(
					companyBean.findById(companyBean.createCompany(createCompanyDialogName, createCompanyDialogDescription,
							createCompanyDialogSection != null ? createCompanyDialogSection.getId() : null, createCompanyDialogCompanyType,
							createCompanyDialogParentCompany == null ? null : createCompanyDialogParentCompany.getId(),
							createCompanyDialogGenerateJobs, managerIds, createCompanyDialogRequiredEmployeesCount,
							createCompanyDialogIsAdvisorRequired,
							WebUtils.getTranslatedMessage("lucas.application.companyScreen.createCompanyDialog.defaultAdvisorJobName"),
							WebUtils.getTranslatedMessage("lucas.application.companyScreen.createCompanyDialog.defaultAdvisorJobDescription"),
							WebUtils.getTranslatedMessage("lucas.application.companyScreen.createCompanyDialog.defaultManagerJobName"),
							WebUtils.getTranslatedMessage("lucas.application.companyScreen.createCompanyDialog.defaultManagerJobDescription"),
							WebUtils.getTranslatedMessage("lucas.application.companyScreen.createCompanyDialog.defaultEmployeeJobName"),
							WebUtils.getTranslatedMessage("lucas.application.companyScreen.createCompanyDialog.defaultEmployeeJobDescription"))),
					CompanyConverter.CONVERTER_ID));
			return true;
		}, "lucas.application.companyScreen.createCompany.message", (exception, params) -> {
			if (exception.getMark().equals(florian_haas.lucas.business.CompanyBeanLocal.NAME_NOT_UNIQUE_EXCEPTION_MARKER)) {
				return WebUtils.getTranslatedMessage("lucas.application.companyScreen.createCompany.message.notUniqueName", createCompanyDialogName);
			} else {
				return WebUtils.getTranslatedMessage("lucas.application.companyScreen.createCompany.message.notUniqueLocation",
						WebUtils.getAsString(roomBean.findRoomSectionById(createCompanyDialogSection.getId()), RoomSectionConverter.CONVERTER_ID));
			}
		});
	}

	/*
	 * -------------------- Create Company Dialog End --------------------
	 */

	/*
	 * -------------------- Edit Company Dialog Start --------------------
	 */

	private ReadOnlyCompany editCompanyDialogSelectedCompany = null;

	@NotBlank
	private String editCompanyDialogName = "";

	@NotBlankString
	@Size(max = 255)
	private String editCompanyDialogDescription = null;

	private ReadOnlyRoomSection editCompanyDialogSection = null;

	@NotNull
	private EnumCompanyType editCompanyDialogCompanyType = EnumCompanyType.CIVIL;

	private ReadOnlyCompany editCompanyDialogParentCompany = null;

	public String getEditCompanyDialogName() {
		return editCompanyDialogName;
	}

	public void setEditCompanyDialogName(String editCompanyDialogName) {
		this.editCompanyDialogName = editCompanyDialogName;
	}

	public String getEditCompanyDialogDescription() {
		return editCompanyDialogDescription;
	}

	public void setEditCompanyDialogDescription(String editCompanyDialogDescription) {
		this.editCompanyDialogDescription = editCompanyDialogDescription;
	}

	public ReadOnlyRoomSection getEditCompanyDialogSection() {
		return editCompanyDialogSection;
	}

	public void setEditCompanyDialogSection(ReadOnlyRoomSection editCompanyDialogSection) {
		this.editCompanyDialogSection = editCompanyDialogSection;
	}

	public EnumCompanyType getEditCompanyDialogCompanyType() {
		return editCompanyDialogCompanyType;
	}

	public void setEditCompanyDialogCompanyType(EnumCompanyType editCompanyDialogCompanyType) {
		this.editCompanyDialogCompanyType = editCompanyDialogCompanyType;
	}

	public ReadOnlyCompany getEditCompanyDialogParentCompany() {
		return this.editCompanyDialogParentCompany;
	}

	public void setEditCompanyDialogParentCompany(ReadOnlyCompany editCompanyDialogParentCompany) {
		this.editCompanyDialogParentCompany = editCompanyDialogParentCompany;
	}

	public void initEditCompanyDialog() {
		if (!selectedEntities.isEmpty()) {
			editCompanyDialogSelectedCompany = selectedEntities.get(0);
			editCompanyDialogName = editCompanyDialogSelectedCompany.getName();
			editCompanyDialogDescription = editCompanyDialogSelectedCompany.getDescription();
			editCompanyDialogSection = (editCompanyDialogSelectedCompany.getSection() != null ? editCompanyDialogSelectedCompany.getSection() : null);
			editCompanyDialogCompanyType = editCompanyDialogSelectedCompany.getCompanyType();
			editCompanyDialogParentCompany = editCompanyDialogSelectedCompany.getParentCompany();
		}
	}

	public void editCompany() {
		WebUtils.executeTask((params) -> {
			Long id = editCompanyDialogSelectedCompany.getId();
			if (WebUtils.isPermitted(EnumPermission.COMPANY_SET_NAME)) {
				companyBean.setName(id, editCompanyDialogName);
			}
			if (WebUtils.isPermitted(EnumPermission.COMPANY_SET_DESCRIPTION)) {
				companyBean.setDescription(id, editCompanyDialogDescription);
			}
			if (WebUtils.isPermitted(EnumPermission.COMPANY_SET_SECTION)) {
				companyBean.setSection(id, editCompanyDialogSection != null ? editCompanyDialogSection.getId() : null);
			}
			if (WebUtils.isPermitted(EnumPermission.COMPANY_SET_COMPANY_TYPE)) {
				companyBean.setCompanyType(id, editCompanyDialogCompanyType);
			}
			if (WebUtils.isPermitted(EnumPermission.COMPANY_SET_PARENT_COMPANY)) {
				companyBean.setParentCompany(id, editCompanyDialogParentCompany != null ? editCompanyDialogParentCompany.getId() : null);
			}
			ReadOnlyCompany tmp2 = companyBean.findById(id);
			params.add(WebUtils.getAsString(tmp2, CompanyConverter.CONVERTER_ID));
			WebUtils.refreshEntities(ReadOnlyCompany.class, searchResults, selectedEntities, tmp2, companyBean::findById, true);
			return true;
		}, "lucas.application.companyScreen.editCompany.message", (exception, params) -> {
			if (exception.getMark().equals(florian_haas.lucas.business.CompanyBeanLocal.NAME_NOT_UNIQUE_EXCEPTION_MARKER)) {
				return WebUtils.getTranslatedMessage("lucas.application.companyScreen.editCompany.message.notUniqueName", editCompanyDialogName);
			} else {
				return WebUtils.getTranslatedMessage("lucas.application.companyScreen.editCompany.message.notUniqueLocation",
						WebUtils.getAsString(roomBean.findRoomSectionById(editCompanyDialogSection.getId()), RoomSectionConverter.CONVERTER_ID));
			}
		});
	}

	/*
	 * -------------------- Edit Company Dialog End --------------------
	 */

	/*
	 * -------------------- View Employees Dialog Start --------------------
	 */

	private String viewEmployeesDialogSelectedCompanyString = null;

	private List<String> viewEmployeesDialogManagers = new ArrayList<>();

	private List<String> viewEmployeesDialogEmployees = new ArrayList<>();

	private List<String> viewEmployeesDialogAdvisors = new ArrayList<>();

	public String getViewEmployeesDialogSelectedCompanyString() {
		return this.viewEmployeesDialogSelectedCompanyString;
	}

	public List<String> getViewEmployeesDialogManagers() {
		return this.viewEmployeesDialogManagers;
	}

	public List<String> getViewEmployeesDialogAdvisors() {
		return this.viewEmployeesDialogAdvisors;
	}

	public List<String> getViewEmployeesDialogEmployees() {
		return this.viewEmployeesDialogEmployees;
	}

	private List<String> getStringFromEmployment(List<? extends ReadOnlyEmployment> employments) {
		List<String> ret = new ArrayList<>();
		employments.forEach(employment -> {
			ret.add(WebUtils.getAsString(employment.getUser(), UserConverter.CONVERTER_ID));
		});
		return ret;
	}

	public void initViewEmployeesDialog() {
		if (!selectedEntities.isEmpty()) {
			ReadOnlyCompany tmp = selectedEntities.get(0);
			viewEmployeesDialogSelectedCompanyString = WebUtils.getAsString(tmp, CompanyConverter.CONVERTER_ID);
			viewEmployeesDialogManagers.clear();
			viewEmployeesDialogEmployees.clear();
			viewEmployeesDialogAdvisors.clear();
			viewEmployeesDialogManagers.addAll(getStringFromEmployment(companyBean.getManagers(tmp.getId())));
			viewEmployeesDialogEmployees.addAll(getStringFromEmployment(companyBean.getEmployees(tmp.getId())));
			viewEmployeesDialogAdvisors.addAll(getStringFromEmployment(companyBean.getAdvisors(tmp.getId())));
		}
	}

	/*
	 * -------------------- View Employees Dialog End --------------------
	 */

	/*
	 * -------------------- Company Card Manager Dialog Start --------------------
	 */

	@EJB
	private IdCardBeanLocal idCardBean;

	private ReadOnlyCompany companyCardManagerDialogSelectedCompany;

	private List<ReadOnlyIdCard> companyCardManagerDialogSelectedCompanyCards = new ArrayList<>();

	private List<ReadOnlyIdCard> companyCardManagerDialogCompanyCards = new ArrayList<>();

	private Date companyCardManagerDialogValidDate = Date.from(Instant.now());

	public static final String COMPANY_CARD_MANAGER_DIALOG_MESSAGES_ID = "companyCardManagerDialogMessages";

	public List<ReadOnlyIdCard> getCompanyCardManagerDialogCompanyCards() {
		return this.companyCardManagerDialogCompanyCards;
	}

	public List<ReadOnlyIdCard> getCompanyCardManagerDialogSelectedCompanyCards() {
		return this.companyCardManagerDialogSelectedCompanyCards;
	}

	public void setCompanyCardManagerDialogSelectedCompanyCards(List<ReadOnlyIdCard> companyCardManagerDialogSelectedCompanyCards) {
		this.companyCardManagerDialogSelectedCompanyCards = companyCardManagerDialogSelectedCompanyCards;
	}

	public ReadOnlyCompany getCompanyCardManagerDialogSelectedCompany() {
		return this.companyCardManagerDialogSelectedCompany;
	}

	public Date getCompanyCardManagerDialogValidDate() {
		return companyCardManagerDialogValidDate;
	}

	public void setCompanyCardManagerDialogValidDate(Date companyCardManagerDialogValidDate) {
		this.companyCardManagerDialogValidDate = companyCardManagerDialogValidDate;
	}

	public void initCompanyCardManagerDialog() {
		if (!selectedEntities.isEmpty()) {
			companyCardManagerDialogSelectedCompany = selectedEntities.get(0);
			companyCardManagerDialogSelectedCompanyCards.clear();
			companyCardManagerDialogCompanyCards.clear();
			companyCardManagerDialogValidDate = Date.from(Instant.now());
			companyCardManagerDialogCompanyCards.addAll(idCardBean.getIdCards(companyCardManagerDialogSelectedCompany.getId()));
		}
	}

	public void companyCardManagerDialogNewCompanyCard() {
		if (companyCardManagerDialogSelectedCompany != null) {
			WebUtils.executeTask(params -> {
				Long id = idCardBean.addIdCard(companyCardManagerDialogSelectedCompany.getId());
				companyCardManagerDialogCompanyCards.add(idCardBean.findIdCardById(id));
				params.add(id);
				return true;
			}, "lucas.application.companyScreen.createCompanyCard", COMPANY_CARD_MANAGER_DIALOG_MESSAGES_ID,
					Utils.asList(WebUtils.getAsString(companyCardManagerDialogSelectedCompany, CompanyConverter.CONVERTER_ID)));
		}
	}

	public void companyCardManagerDialogBlockCompanyCards() {
		for (ReadOnlyIdCard card : companyCardManagerDialogSelectedCompanyCards) {
			Long id = card.getId();
			if (WebUtils.executeTask(params -> {
				params.add(id);
				return idCardBean.blockIdCard(id);
			}, "lucas.application.companyScreen.blockCompanyCard", COMPANY_CARD_MANAGER_DIALOG_MESSAGES_ID,
					Utils.asList(WebUtils.getAsString(companyCardManagerDialogSelectedCompany, CompanyConverter.CONVERTER_ID)))) {
				ReadOnlyIdCard newCard = idCardBean.findIdCardById(id);
				WebUtils.refreshEntities(ReadOnlyIdCard.class, companyCardManagerDialogCompanyCards, companyCardManagerDialogSelectedCompanyCards,
						newCard, idCardBean::findIdCardById, true);
			}
		}
	}

	public void companyCardManagerDialogUnblockCompanyCards() {
		for (ReadOnlyIdCard card : companyCardManagerDialogSelectedCompanyCards) {
			Long id = card.getId();
			if (WebUtils.executeTask(params -> {
				params.add(id);
				return idCardBean.unblockIdCard(id);
			}, "lucas.application.companyScreen.unblockCompanyCard", COMPANY_CARD_MANAGER_DIALOG_MESSAGES_ID,
					Utils.asList(WebUtils.getAsString(companyCardManagerDialogSelectedCompany, CompanyConverter.CONVERTER_ID)))) {
				ReadOnlyIdCard newCard = idCardBean.findIdCardById(id);
				WebUtils.refreshEntities(ReadOnlyIdCard.class, companyCardManagerDialogCompanyCards, companyCardManagerDialogSelectedCompanyCards,
						newCard, idCardBean::findIdCardById, true);
			}
		}
	}

	public void initCompanyCardManagerDialogSetValidDayOverlay() {
		if (!companyCardManagerDialogSelectedCompanyCards.isEmpty()) {
			LocalDate validDay = companyCardManagerDialogSelectedCompanyCards.get(0).getValidDay();
			this.companyCardManagerDialogValidDate = validDay != null ? Utils.asDate(validDay) : Date.from(Instant.now());
		}
	}

	public void companyCardManagerDialogSetValidDay() {
		for (ReadOnlyIdCard card : companyCardManagerDialogSelectedCompanyCards) {
			Long id = card.getId();
			if (WebUtils.executeTask(params -> {
				params.add(card.getId());
				params.add(WebUtils.getAsString(Utils.asLocalDate(companyCardManagerDialogValidDate), LocalDateConverter.CONVERTER_ID));
				params.add(WebUtils.getAsString(card.getValidDay(), LocalDateConverter.CONVERTER_ID));
				return idCardBean.setValidDate(id, Utils.asLocalDate(companyCardManagerDialogValidDate));
			}, "lucas.application.companyScreen.setValidDay", COMPANY_CARD_MANAGER_DIALOG_MESSAGES_ID)) {
				ReadOnlyIdCard newCard = idCardBean.findIdCardById(id);
				WebUtils.refreshEntities(ReadOnlyIdCard.class, companyCardManagerDialogCompanyCards, companyCardManagerDialogSelectedCompanyCards,
						newCard, idCardBean::findIdCardById, true);
			}
		}
	}

	public void companyCardManagerDialogRemoveValidDay() {
		for (ReadOnlyIdCard card : companyCardManagerDialogSelectedCompanyCards) {
			Long id = card.getId();
			if (WebUtils.executeTask(params -> {
				params.add(card.getId());
				return idCardBean.setValidDate(id, null);
			}, "lucas.application.companyScreen.removeValidDay", COMPANY_CARD_MANAGER_DIALOG_MESSAGES_ID)) {
				ReadOnlyIdCard newCard = idCardBean.findIdCardById(id);
				WebUtils.refreshEntities(ReadOnlyIdCard.class, companyCardManagerDialogCompanyCards, companyCardManagerDialogSelectedCompanyCards,
						newCard, idCardBean::findIdCardById, true);
			}
		}
	}

	public void companyCardManagerDialogRemoveCompanyCards() {
		if (!companyCardManagerDialogSelectedCompanyCards.isEmpty()) {
			ListIterator<ReadOnlyIdCard> it = companyCardManagerDialogSelectedCompanyCards.listIterator();
			while (it.hasNext()) {
				WebUtils.executeTask(params -> {
					ReadOnlyIdCard companyCard = it.next();
					Long id = companyCard.getId();
					Boolean removed = idCardBean.removeIdCard(id);
					if (removed) {
						companyCardManagerDialogCompanyCards.remove(companyCard);
						it.remove();
					}
					params.add(id);
					return removed;
				}, "lucas.application.companyScreen.deleteCompanyCard", COMPANY_CARD_MANAGER_DIALOG_MESSAGES_ID,
						Utils.asList(WebUtils.getAsString(companyCardManagerDialogSelectedCompany, CompanyConverter.CONVERTER_ID)));

			}
		}
	}

	public void companyCardManagerDialogRefreshCompanyCards() {
		WebUtils.executeTask((params) -> {
			WebUtils.refreshEntities(ReadOnlyIdCard.class, companyCardManagerDialogCompanyCards, companyCardManagerDialogSelectedCompanyCards,
					idCardBean::findIdCardById, true);
			params.add(companyCardManagerDialogCompanyCards.size());
			return true;
		}, "lucas.application.companyScreen.refreshCompanyCards", COMPANY_CARD_MANAGER_DIALOG_MESSAGES_ID);
	}

	/*
	 * -------------------- Company Card Manager Dialog End --------------------
	 */

	/*
	 * -------------------- Show Purchase Logs Dialog Start --------------------
	 */

	private ReadOnlyCompany showPurchaseLogsDialogSelectedCompany = null;

	private List<ReadOnlyPurchaseLog> showPurchaseLogsDialogLogs = new ArrayList<>();

	private List<Boolean> showPurchaseLogsDialogLogsColumns = Arrays.asList(true, true, true, true, true, true, true, true, true);

	public ReadOnlyCompany getShowPurchaseLogsDialogSelectedCompany() {
		return this.showPurchaseLogsDialogSelectedCompany;
	}

	public List<ReadOnlyPurchaseLog> getShowPurchaseLogsDialogLogs() {
		return this.showPurchaseLogsDialogLogs;
	}

	public List<Boolean> getShowPurchaseLogsDialogLogsColumns() {
		return this.showPurchaseLogsDialogLogsColumns;
	}

	public void initShowPurchaseLogsDialog() {
		if (!selectedEntities.isEmpty()) {
			showPurchaseLogsDialogSelectedCompany = selectedEntities.get(0);
			showPurchaseLogsDialogLogs.clear();
			showPurchaseLogsDialogLogs.addAll(companyBean.getPurchaseLogs(showPurchaseLogsDialogSelectedCompany.getId()));
			Collections.fill(showPurchaseLogsDialogLogsColumns, true);
		}
	}

	public void purchaseLogDialogOnToggle(ToggleEvent e) {
		showPurchaseLogsDialogLogsColumns.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
	}

	/*
	 * -------------------- Show Purchase Logs Dialog End --------------------
	 */

}
