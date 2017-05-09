package florian_haas.lucas.web;

import java.time.*;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.NotBlank;

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

	@ValidEntityId(entityClass = ReadOnlyRoomSection.class, nullable = true)
	private Long searchCompanySectionId = null;

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

	@ValidEntityId(entityClass = ReadOnlyCompany.class, nullable = true)
	private Long searchCompanyParentCompanyId;

	@NotNull
	private Boolean useSearchCompanyParentCompanyId = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchCompanyParentCompanyIdComparator = EnumQueryComparator.EQUAL;

	@NotNull
	@Min(0)
	private Long searchCompanyJobId = 0l;

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

	public Long getSearchCompanySectionId() {
		return searchCompanySectionId;
	}

	public void setSearchCompanySectionId(Long searchCompanySectionId) {
		this.searchCompanySectionId = searchCompanySectionId;
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

	public Long getSearchCompanyParentCompanyId() {
		return this.searchCompanyParentCompanyId;
	}

	public void setSearchCompanyParentCompanyId(Long searchCompanyParentCompanyId) {
		this.searchCompanyParentCompanyId = searchCompanyParentCompanyId;
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

	public Long getSearchCompanyJobId() {
		return searchCompanyJobId;
	}

	public void setSearchCompanyJobId(Long searchCompanyJobId) {
		this.searchCompanyJobId = searchCompanyJobId;
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
		return companyBean.findCompanies(searchCompanyId, searchCompanyName, searchCompanyDescription, searchCompanySectionId,
				searchCompanyCompanyType, searchCompanyParentCompanyId, searchCompanyJobId, searchCompanyAreEmployeesRequired, searchCompanyJobsCount,
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

	@ValidEntityId(entityClass = ReadOnlyRoomSection.class, nullable = true)
	private Long createCompanyDialogSectionId = null;

	@NotNull
	private EnumCompanyType createCompanyDialogCompanyType = EnumCompanyType.CIVIL;

	@ValidEntityId(entityClass = ReadOnlyCompany.class, nullable = true)
	private Long createCompanyDialogParentCompanyId = null;

	@NotNull
	@Min(0)
	private Integer createCompanyDialogRequiredEmployeesCount = 0;

	private List<@ValidEntityId(entityClass = ReadOnlyUser.class) Long> createCompanyDialogManagerUserIds = new ArrayList<>();

	@ValidEntityId(entityClass = ReadOnlyUser.class, nullable = false)
	private Long createCompanyDialogTmpManagerId = null;

	private Long createCompanyDialogSelectedManagerId = null;

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

	public Long getCreateCompanyDialogSectionId() {
		return createCompanyDialogSectionId;
	}

	public void setCreateCompanyDialogSectionId(Long createCompanyDialogSectionId) {
		this.createCompanyDialogSectionId = createCompanyDialogSectionId;
	}

	public EnumCompanyType getCreateCompanyDialogCompanyType() {
		return createCompanyDialogCompanyType;
	}

	public void setCreateCompanyDialogCompanyType(EnumCompanyType createCompanyDialogCompanyType) {
		this.createCompanyDialogCompanyType = createCompanyDialogCompanyType;
	}

	public Long getCreateCompanyDialogParentCompanyId() {
		return this.createCompanyDialogParentCompanyId;
	}

	public void setCreateCompanyDialogParentCompanyId(Long createCompanyDialogParentCompanyId) {
		this.createCompanyDialogParentCompanyId = createCompanyDialogParentCompanyId;
	}

	public String getCreateCompanyDialogParentCompanyFromId() {
		ReadOnlyCompany company = createCompanyDialogParentCompanyId != null
				? entityBean.exists(createCompanyDialogParentCompanyId, ReadOnlyCompany.class)
						? companyBean.findById(createCompanyDialogParentCompanyId) : null
				: null;
		return WebUtils.getAsString(company, CompanyConverter.CONVERTER_ID);
	}

	public Integer getCreateCompanyDialogRequiredEmployeesCount() {
		return createCompanyDialogRequiredEmployeesCount;
	}

	public void setCreateCompanyDialogRequiredEmployeesCount(Integer createCompanyDialogRequiredEmployeesCount) {
		this.createCompanyDialogRequiredEmployeesCount = createCompanyDialogRequiredEmployeesCount;
	}

	public List<Long> getCreateCompanyDialogManagerUserIds() {
		return createCompanyDialogManagerUserIds;
	}

	public void setCreateCompanyDialogManagerUserIds(List<@ValidEntityId(entityClass = ReadOnlyUser.class) Long> createCompanyDialogManagerUserIds) {
		this.createCompanyDialogManagerUserIds = createCompanyDialogManagerUserIds;
	}

	public String getUserAsString(Long id) {
		return WebUtils.getAsString(id != null ? userBean.findById(id) : null, UserConverter.CONVERTER_ID);
	}

	public Long getCreateCompanyDialogTmpManagerId() {
		return createCompanyDialogTmpManagerId;
	}

	public void setCreateCompanyDialogTmpManagerId(Long createCompanyDialogTmpManagerId) {
		this.createCompanyDialogTmpManagerId = createCompanyDialogTmpManagerId;
	}

	public String getCreateCompanyDialogTmpManagerFromId() {
		return getUserAsString(createCompanyDialogTmpManagerId);
	}

	public void resetCreateCompanyDialogTmpManagerId() {
		createCompanyDialogTmpManagerId = null;
	}

	public void createCompanyDialogEditSelectedRank() {
		if (createCompanyDialogSelectedManagerId != null) {
			this.createCompanyDialogManagerUserIds.remove(createCompanyDialogSelectedManagerId);
			this.createCompanyDialogTmpManagerId = createCompanyDialogSelectedManagerId;
			createCompanyDialogSelectedManagerId = null;
		}
	}

	public void createCompanyDialogRemoveSelectedRank() {
		this.createCompanyDialogManagerUserIds.remove(createCompanyDialogSelectedManagerId);
		this.createCompanyDialogTmpManagerId = null;
		this.createCompanyDialogSelectedManagerId = null;
	}

	public Long getCreateCompanyDialogSelectedManagerId() {
		return createCompanyDialogSelectedManagerId;
	}

	public void setCreateCompanyDialogSelectedManagerId(Long createCompanyDialogSelectedManagerId) {
		this.createCompanyDialogSelectedManagerId = createCompanyDialogSelectedManagerId;
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
		createCompanyDialogSectionId = null;
		createCompanyDialogCompanyType = EnumCompanyType.CIVIL;
		createCompanyDialogParentCompanyId = null;
		createCompanyDialogRequiredEmployeesCount = 0;
		createCompanyDialogManagerUserIds.clear();
		createCompanyDialogTmpManagerId = null;
		createCompanyDialogSelectedManagerId = null;
		createCompanyDialogIsAdvisorRequired = Boolean.FALSE;
		createCompanyDialogGenerateJobs = Boolean.FALSE;
	}

	public void createCompany() {
		WebUtils.executeTask((params) -> {
			params.add(WebUtils
					.getAsString(
							companyBean.findById(companyBean.createCompany(createCompanyDialogName, createCompanyDialogDescription,
									createCompanyDialogSectionId, createCompanyDialogCompanyType, createCompanyDialogParentCompanyId,
									createCompanyDialogGenerateJobs, createCompanyDialogManagerUserIds, createCompanyDialogRequiredEmployeesCount,
									createCompanyDialogIsAdvisorRequired,
									WebUtils.getTranslatedMessage("lucas.application.companyScreen.createCompanyDialog.defaultAdvisorJobName"),
									WebUtils.getTranslatedMessage("lucas.application.companyScreen.createCompanyDialog.defaultAdvisorJobDescription"),
									WebUtils.getTranslatedMessage("lucas.application.companyScreen.createCompanyDialog.defaultManagerJobName"),
									WebUtils.getTranslatedMessage("lucas.application.companyScreen.createCompanyDialog.defaultManagerJobDescription"),
									WebUtils.getTranslatedMessage("lucas.application.companyScreen.createCompanyDialog.defaultEmployeeJobName"),
									WebUtils.getTranslatedMessage(
											"lucas.application.companyScreen.createCompanyDialog.defaultEmployeeJobDescription"))),
							CompanyConverter.CONVERTER_ID));
			return true;
		}, "lucas.application.companyScreen.createCompany.message", (exception, params) -> {
			if (exception.getMark().equals(florian_haas.lucas.business.CompanyBeanLocal.NAME_NOT_UNIQUE_EXCEPTION_MARKER)) {
				return WebUtils.getTranslatedMessage("lucas.application.companyScreen.createCompany.message.notUniqueName", createCompanyDialogName);
			} else {
				return WebUtils.getTranslatedMessage("lucas.application.companyScreen.createCompany.message.notUniqueLocation",
						WebUtils.getAsString(roomBean.findRoomSectionById(createCompanyDialogSectionId), RoomSectionConverter.CONVERTER_ID));
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

	@ValidEntityId(entityClass = ReadOnlyRoomSection.class, nullable = true)
	private Long editCompanyDialogSectionId = null;

	@NotNull
	private EnumCompanyType editCompanyDialogCompanyType = EnumCompanyType.CIVIL;

	@ValidEntityId(entityClass = ReadOnlyCompany.class, nullable = true)
	private Long editCompanyDialogParentCompanyId = null;

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

	public Long getEditCompanyDialogSectionId() {
		return editCompanyDialogSectionId;
	}

	public void setEditCompanyDialogSectionId(Long editCompanyDialogSectionId) {
		this.editCompanyDialogSectionId = editCompanyDialogSectionId;
	}

	public EnumCompanyType getEditCompanyDialogCompanyType() {
		return editCompanyDialogCompanyType;
	}

	public void setEditCompanyDialogCompanyType(EnumCompanyType editCompanyDialogCompanyType) {
		this.editCompanyDialogCompanyType = editCompanyDialogCompanyType;
	}

	public Long getEditCompanyDialogParentCompanyId() {
		return this.editCompanyDialogParentCompanyId;
	}

	public void setEditCompanyDialogParentCompanyId(Long editCompanyDialogParentCompanyId) {
		this.editCompanyDialogParentCompanyId = editCompanyDialogParentCompanyId;
	}

	public String getEditCompanyDialogParentCompanyFromId() {
		ReadOnlyCompany company = editCompanyDialogParentCompanyId != null
				? entityBean.exists(editCompanyDialogParentCompanyId, ReadOnlyCompany.class) ? companyBean.findById(editCompanyDialogParentCompanyId)
						: null
				: null;
		return WebUtils.getAsString(company, CompanyConverter.CONVERTER_ID);
	}

	public void initEditCompanyDialog() {
		if (!selectedEntities.isEmpty()) {
			editCompanyDialogSelectedCompany = selectedEntities.get(0);
			editCompanyDialogName = editCompanyDialogSelectedCompany.getName();
			editCompanyDialogDescription = editCompanyDialogSelectedCompany.getDescription();
			editCompanyDialogSectionId = (editCompanyDialogSelectedCompany.getSection() != null
					? editCompanyDialogSelectedCompany.getSection().getId() : null);
			editCompanyDialogCompanyType = editCompanyDialogSelectedCompany.getCompanyType();
			ReadOnlyCompany parent = editCompanyDialogSelectedCompany.getParentCompany();
			editCompanyDialogParentCompanyId = parent == null ? null : parent.getId();
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
				companyBean.setSection(id, editCompanyDialogSectionId);
			}
			if (WebUtils.isPermitted(EnumPermission.COMPANY_SET_COMPANY_TYPE)) {
				companyBean.setCompanyType(id, editCompanyDialogCompanyType);
			}
			if (WebUtils.isPermitted(EnumPermission.COMPANY_SET_PARENT_COMPANY)) {
				companyBean.setParentCompany(id, editCompanyDialogParentCompanyId);
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
						WebUtils.getAsString(roomBean.findRoomSectionById(editCompanyDialogSectionId), RoomSectionConverter.CONVERTER_ID));
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

	private ReadOnlyCompany companyCardManagerDialogSelectedCompany;

	private List<ReadOnlyCompanyCard> companyCardManagerDialogSelectedCompanyCards = new ArrayList<>();

	private List<ReadOnlyCompanyCard> companyCardManagerDialogCompanyCards = new ArrayList<>();

	private Date companyCardManagerDialogValidDate = Date.from(Instant.now());

	public static final String COMPANY_CARD_MANAGER_DIALOG_MESSAGES_ID = "companyCardManagerDialogMessages";

	public List<ReadOnlyCompanyCard> getCompanyCardManagerDialogCompanyCards() {
		return this.companyCardManagerDialogCompanyCards;
	}

	public List<ReadOnlyCompanyCard> getCompanyCardManagerDialogSelectedCompanyCards() {
		return this.companyCardManagerDialogSelectedCompanyCards;
	}

	public void setCompanyCardManagerDialogSelectedCompanyCards(List<ReadOnlyCompanyCard> companyCardManagerDialogSelectedCompanyCards) {
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
			companyCardManagerDialogCompanyCards.addAll(companyBean.getCompanyCards(companyCardManagerDialogSelectedCompany.getId()));
		}
	}

	public void companyCardManagerDialogNewCompanyCard() {
		if (companyCardManagerDialogSelectedCompany != null) {
			WebUtils.executeTask(params -> {
				Long id = companyBean.addCompanyCard(companyCardManagerDialogSelectedCompany.getId());
				companyCardManagerDialogCompanyCards.add(companyBean.findCompanyCardById(id));
				params.add(id);
				return true;
			}, "lucas.application.companyScreen.createCompanyCard", COMPANY_CARD_MANAGER_DIALOG_MESSAGES_ID,
					Utils.asList(WebUtils.getAsString(companyCardManagerDialogSelectedCompany, CompanyConverter.CONVERTER_ID)));
		}
	}

	public void companyCardManagerDialogBlockCompanyCards() {
		for (ReadOnlyCompanyCard card : companyCardManagerDialogSelectedCompanyCards) {
			Long id = card.getId();
			if (WebUtils.executeTask(params -> {
				params.add(id);
				return companyBean.blockCompanyCard(id);
			}, "lucas.application.companyScreen.blockCompanyCard", COMPANY_CARD_MANAGER_DIALOG_MESSAGES_ID,
					Utils.asList(WebUtils.getAsString(companyCardManagerDialogSelectedCompany, CompanyConverter.CONVERTER_ID)))) {
				ReadOnlyCompanyCard newCard = companyBean.findCompanyCardById(id);
				WebUtils.refreshEntities(ReadOnlyCompanyCard.class, companyCardManagerDialogCompanyCards,
						companyCardManagerDialogSelectedCompanyCards, newCard, companyBean::findCompanyCardById, true);
			}
		}
	}

	public void companyCardManagerDialogUnblockCompanyCards() {
		for (ReadOnlyCompanyCard card : companyCardManagerDialogSelectedCompanyCards) {
			Long id = card.getId();
			if (WebUtils.executeTask(params -> {
				params.add(id);
				return companyBean.unblockCompanyCard(id);
			}, "lucas.application.companyScreen.unblockCompanyCard", COMPANY_CARD_MANAGER_DIALOG_MESSAGES_ID,
					Utils.asList(WebUtils.getAsString(companyCardManagerDialogSelectedCompany, CompanyConverter.CONVERTER_ID)))) {
				ReadOnlyCompanyCard newCard = companyBean.findCompanyCardById(id);
				WebUtils.refreshEntities(ReadOnlyCompanyCard.class, companyCardManagerDialogCompanyCards,
						companyCardManagerDialogSelectedCompanyCards, newCard, companyBean::findCompanyCardById, true);
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
		for (ReadOnlyCompanyCard card : companyCardManagerDialogSelectedCompanyCards) {
			Long id = card.getId();
			if (WebUtils.executeTask(params -> {
				params.add(card.getId());
				params.add(WebUtils.getAsString(Utils.asLocalDate(companyCardManagerDialogValidDate), LocalDateConverter.CONVERTER_ID));
				params.add(WebUtils.getAsString(card.getValidDay(), LocalDateConverter.CONVERTER_ID));
				return companyBean.setValidDate(id, Utils.asLocalDate(companyCardManagerDialogValidDate));
			}, "lucas.application.companyScreen.setValidDay", COMPANY_CARD_MANAGER_DIALOG_MESSAGES_ID)) {
				ReadOnlyCompanyCard newCard = companyBean.findCompanyCardById(id);
				WebUtils.refreshEntities(ReadOnlyCompanyCard.class, companyCardManagerDialogCompanyCards,
						companyCardManagerDialogSelectedCompanyCards, newCard, companyBean::findCompanyCardById, true);
			}
		}
	}

	public void companyCardManagerDialogRemoveValidDay() {
		for (ReadOnlyCompanyCard card : companyCardManagerDialogSelectedCompanyCards) {
			Long id = card.getId();
			if (WebUtils.executeTask(params -> {
				params.add(card.getId());
				return companyBean.setValidDate(id, null);
			}, "lucas.application.companyScreen.removeValidDay", COMPANY_CARD_MANAGER_DIALOG_MESSAGES_ID)) {
				ReadOnlyCompanyCard newCard = companyBean.findCompanyCardById(id);
				WebUtils.refreshEntities(ReadOnlyCompanyCard.class, companyCardManagerDialogCompanyCards,
						companyCardManagerDialogSelectedCompanyCards, newCard, companyBean::findCompanyCardById, true);
			}
		}
	}

	public void companyCardManagerDialogRemoveCompanyCards() {
		if (!companyCardManagerDialogSelectedCompanyCards.isEmpty()) {
			ListIterator<ReadOnlyCompanyCard> it = companyCardManagerDialogSelectedCompanyCards.listIterator();
			while (it.hasNext()) {
				WebUtils.executeTask(params -> {
					ReadOnlyCompanyCard companyCard = it.next();
					Long id = companyCard.getId();
					Boolean removed = companyBean.removeCompanyCard(id);
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
			WebUtils.refreshEntities(ReadOnlyCompanyCard.class, companyCardManagerDialogCompanyCards, companyCardManagerDialogSelectedCompanyCards,
					companyBean::findCompanyCardById, true);
			params.add(companyCardManagerDialogCompanyCards.size());
			return true;
		}, "lucas.application.companyScreen.refreshCompanyCards", COMPANY_CARD_MANAGER_DIALOG_MESSAGES_ID);
	}

	/*
	 * -------------------- Company Card Manager Dialog End --------------------
	 */

}
