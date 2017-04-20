package florian_haas.lucas.web;

import java.io.Serializable;
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
import florian_haas.lucas.database.*;
import florian_haas.lucas.database.validation.QueryComparator;
import florian_haas.lucas.model.*;
import florian_haas.lucas.model.validation.ValidEntityId;
import florian_haas.lucas.security.EnumPermission;
import florian_haas.lucas.util.Utils;
import florian_haas.lucas.util.validation.NotBlankString;
import florian_haas.lucas.web.util.WebUtils;
import florian_haas.lucas.web.util.converter.*;

@Named
@ViewScoped
public class CompanyBean implements Serializable {

	private static final long serialVersionUID = 5394240973288053983L;

	private List<Company> searchCompanyResults = new ArrayList<>();

	private List<Company> selectedCompanies = new ArrayList<>();

	@EJB
	private CompanyBeanLocal companyBean;

	@EJB
	private EntityBeanLocal entityBean;

	@EJB
	private UserBeanLocal userBean;

	public List<Company> getSearchCompanyResults() {
		return searchCompanyResults;
	}

	public void setSearchCompanyResults(List<Company> searchCompanyResults) {
		this.searchCompanyResults = searchCompanyResults;
	}

	public List<Company> getSelectedCompanies() {
		return selectedCompanies;
	}

	public void setSelectedCompanies(List<Company> selectedCompanies) {
		this.selectedCompanies = selectedCompanies;
	}

	private List<Boolean> resultsDatatableColumns = Arrays.asList(true, true, true, true, true, true, true, true, true);

	public void onToggle(ToggleEvent e) {
		resultsDatatableColumns.set((Integer) e.getData() - 1, e.getVisibility() == Visibility.VISIBLE);
	}

	public List<Boolean> getResultsDatatableColumns() {
		return this.resultsDatatableColumns;
	}

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

	@NotBlank
	private String searchCompanyRoom = "";

	@NotNull
	private Boolean useSearchCompanyRoom = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.TEXT)
	private EnumQueryComparator searchCompanyRoomComparator = EnumQueryComparator.EQUAL;

	@NotNull
	@Min(0)
	private Integer searchCompanySection = 0;

	@NotNull
	private Boolean useSearchCompanySection = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchCompanySectionComparator = EnumQueryComparator.EQUAL;

	@NotNull
	private EnumCompanyType searchCompanyCompanyType = EnumCompanyType.CIVIL;

	@NotNull
	private Boolean useSearchCompanyCompanyType = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.LOGIC)
	private EnumQueryComparator searchCompanyCompanyTypeComparator = EnumQueryComparator.EQUAL;

	@ValidEntityId(entityClass = Company.class, nullable = true)
	private Long searchCompanyParentCompanyId;

	@NotNull
	private Boolean useSearchCompanyParentCompanyId = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchCompanyParentCompanyIdComparator = EnumQueryComparator.EQUAL;

	@NotNull
	@Min(0)
	private Integer searchCompanyRequiredEmployeesCount = 0;

	@NotNull
	private Boolean useSearchCompanyRequiredEmployeesCount = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchCompanyRequiredEmployeesCountComparator = EnumQueryComparator.EQUAL;

	@NotNull
	private Boolean searchCompanyAreEmployeesRequired = Boolean.FALSE;

	@NotNull
	private Boolean useSearchCompanyAreEmployeesRequired = Boolean.FALSE;

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

	public String getSearchCompanyRoom() {
		return this.searchCompanyRoom;
	}

	public void setSearchCompanyRoom(String searchCompanyRoom) {
		this.searchCompanyRoom = searchCompanyRoom;
	}

	public Boolean getUseSearchCompanyRoom() {
		return this.useSearchCompanyRoom;
	}

	public void setUseSearchCompanyRoom(Boolean useSearchCompanyRoom) {
		this.useSearchCompanyRoom = useSearchCompanyRoom;
	}

	public EnumQueryComparator getSearchCompanyRoomComparator() {
		return this.searchCompanyRoomComparator;
	}

	public void setSearchCompanyRoomComparator(EnumQueryComparator searchCompanyRoomComparator) {
		this.searchCompanyRoomComparator = searchCompanyRoomComparator;
	}

	public Integer getSearchCompanySection() {
		return this.searchCompanySection;
	}

	public void setSearchCompanySection(Integer searchCompanySection) {
		this.searchCompanySection = searchCompanySection;
	}

	public Boolean getUseSearchCompanySection() {
		return this.useSearchCompanySection;
	}

	public void setUseSearchCompanySection(Boolean useSearchCompanySection) {
		this.useSearchCompanySection = useSearchCompanySection;
	}

	public EnumQueryComparator getSearchCompanySectionComparator() {
		return this.searchCompanySectionComparator;
	}

	public void setSearchCompanySectionComparator(EnumQueryComparator searchCompanySectionComparator) {
		this.searchCompanySectionComparator = searchCompanySectionComparator;
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

	public Integer getSearchCompanyRequiredEmployeesCount() {
		return this.searchCompanyRequiredEmployeesCount;
	}

	public void setSearchCompanyRequiredEmployeesCount(Integer searchCompanyRequiredEmployeesCount) {
		this.searchCompanyRequiredEmployeesCount = searchCompanyRequiredEmployeesCount;
	}

	public Boolean getUseSearchCompanyRequiredEmployeesCount() {
		return this.useSearchCompanyRequiredEmployeesCount;
	}

	public void setUseSearchCompanyRequiredEmployeesCount(Boolean useSearchCompanyRequiredEmployeesCount) {
		this.useSearchCompanyRequiredEmployeesCount = useSearchCompanyRequiredEmployeesCount;
	}

	public EnumQueryComparator getSearchCompanyRequiredEmployeesCountComparator() {
		return this.searchCompanyRequiredEmployeesCountComparator;
	}

	public void setSearchCompanyRequiredEmployeesCountComparator(EnumQueryComparator searchCompanyRequiredEmployeesCountComparator) {
		this.searchCompanyRequiredEmployeesCountComparator = searchCompanyRequiredEmployeesCountComparator;
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

	public void searchCompanies() {
		WebUtils.executeTask((params) -> {
			List<Company> results = companyBean.findCompanies(searchCompanyId, searchCompanyName, searchCompanyDescription, searchCompanyRoom,
					searchCompanySection, searchCompanyCompanyType, searchCompanyParentCompanyId, searchCompanyRequiredEmployeesCount,
					searchCompanyAreEmployeesRequired, useSearchCompanyId, useSearchCompanyName, useSearchCompanyDescription, useSearchCompanyRoom,
					useSearchCompanySection, useSearchCompanyCompanyType, useSearchCompanyParentCompanyId, useSearchCompanyRequiredEmployeesCount,
					useSearchCompanyAreEmployeesRequired, searchCompanyIdComparator, searchCompanyNameComparator, searchCompanyDescriptionComparator,
					searchCompanyRoomComparator, searchCompanySectionComparator, searchCompanyCompanyTypeComparator,
					searchCompanyParentCompanyIdComparator, searchCompanyRequiredEmployeesCountComparator);
			searchCompanyResults.clear();
			selectedCompanies.clear();
			searchCompanyResults.addAll(results);
			params.add(results.size());
			return true;
		}, "lucas.application.companyScreen.searchCompany.message");
	}

	public void refreshCompanies() {
		WebUtils.executeTask((params) -> {
			WebUtils.refreshEntities(Company.class, searchCompanyResults, selectedCompanies, companyBean::findById, false);
			params.add(searchCompanyResults.size());
			return true;
		}, "lucas.application.companyScreen.refreshCompanies.message");
	}

	/*
	 * -------------------- Create Company Dialog Start --------------------
	 */

	@NotBlank
	private String createCompanyDialogName = "";

	@NotBlankString
	@Size(max = 255)
	private String createCompanyDialogDescription = null;

	@NotBlank
	private String createCompanyDialogRoom = "";

	@NotNull
	@Min(0)
	private Integer createCompanyDialogSection = null;

	@NotNull
	private EnumCompanyType createCompanyDialogCompanyType = EnumCompanyType.CIVIL;

	@ValidEntityId(entityClass = Company.class, nullable = true)
	private Long createCompanyDialogParentCompanyId = null;

	@NotNull
	@Min(0)
	private Integer createCompanyDialogRequiredEmployeesCount = 0;

	private List<@ValidEntityId(entityClass = User.class) Long> createCompanyDialogManagerUserIds = new ArrayList<>();

	@ValidEntityId(entityClass = User.class, nullable = false)
	private Long createCompanyDialogTmpManagerId = null;

	private Long createCompanyDialogSelectedManagerId = null;

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

	public String getCreateCompanyDialogRoom() {
		return createCompanyDialogRoom;
	}

	public void setCreateCompanyDialogRoom(String createCompanyDialogRoom) {
		this.createCompanyDialogRoom = createCompanyDialogRoom;
	}

	public Integer getCreateCompanyDialogSection() {
		return createCompanyDialogSection;
	}

	public void setCreateCompanyDialogSection(Integer createCompanyDialogSection) {
		this.createCompanyDialogSection = createCompanyDialogSection;
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
		Company company = createCompanyDialogParentCompanyId != null ? entityBean.exists(createCompanyDialogParentCompanyId, Company.class)
				? companyBean.findById(createCompanyDialogParentCompanyId) : null : null;
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

	public void setCreateCompanyDialogManagerUserIds(List<@ValidEntityId(entityClass = User.class) Long> createCompanyDialogManagerUserIds) {
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

	public Long getCreateCompanyDialogSelectedManagerId() {
		return createCompanyDialogSelectedManagerId;
	}

	public void setCreateCompanyDialogSelectedManagerId(Long createCompanyDialogSelectedManagerId) {
		this.createCompanyDialogSelectedManagerId = createCompanyDialogSelectedManagerId;
	}

	public void initCreateCompanyDialog() {
		createCompanyDialogName = "";
		createCompanyDialogDescription = null;
		createCompanyDialogRoom = "";
		createCompanyDialogSection = null;
		createCompanyDialogCompanyType = EnumCompanyType.CIVIL;
		createCompanyDialogParentCompanyId = null;
		createCompanyDialogRequiredEmployeesCount = 0;
		createCompanyDialogManagerUserIds.clear();
		createCompanyDialogTmpManagerId = null;
		createCompanyDialogSelectedManagerId = null;
	}

	public void createCompany() {
		WebUtils.executeTask((params) -> {
			params.add(WebUtils.getAsString(
					companyBean.findById(companyBean.createCompany(createCompanyDialogName, createCompanyDialogDescription, createCompanyDialogRoom,
							createCompanyDialogSection, createCompanyDialogCompanyType, createCompanyDialogParentCompanyId,
							createCompanyDialogManagerUserIds, createCompanyDialogRequiredEmployeesCount)),
					CompanyConverter.CONVERTER_ID));
			return true;
		}, "lucas.application.companyScreen.createCompany.message", (exception, params) -> {
			if (exception.getMark().equals(florian_haas.lucas.business.CompanyBean.NAME_NOT_UNIQUE_EXCEPTION_MARKER)) {
				return WebUtils.getTranslatedMessage("lucas.application.companyScreen.createCompany.message.notUniqueName", createCompanyDialogName);
			} else {
				return WebUtils.getTranslatedMessage("lucas.application.companyScreen.createCompany.message.notUniqueLocation",
						createCompanyDialogRoom, createCompanyDialogSection);
			}
		});
	}

	/*
	 * -------------------- Create Company Dialog End --------------------
	 */

	/*
	 * -------------------- Edit Company Dialog Start --------------------
	 */

	private Company editCompanyDialogSelectedCompany = null;

	@NotBlank
	private String editCompanyDialogName = "";

	@NotBlankString
	@Size(max = 255)
	private String editCompanyDialogDescription = null;

	@NotBlank
	private String editCompanyDialogRoom = "";

	@NotNull
	@Min(0)
	private Integer editCompanyDialogSection = null;

	@NotNull
	private EnumCompanyType editCompanyDialogCompanyType = EnumCompanyType.CIVIL;

	@ValidEntityId(entityClass = Company.class, nullable = true)
	private Long editCompanyDialogParentCompanyId = null;

	@NotNull
	@Min(0)
	private Integer editCompanyDialogRequiredEmployeesCount = 0;

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

	public String getEditCompanyDialogRoom() {
		return editCompanyDialogRoom;
	}

	public void setEditCompanyDialogRoom(String editCompanyDialogRoom) {
		this.editCompanyDialogRoom = editCompanyDialogRoom;
	}

	public Integer getEditCompanyDialogSection() {
		return editCompanyDialogSection;
	}

	public void setEditCompanyDialogSection(Integer editCompanyDialogSection) {
		this.editCompanyDialogSection = editCompanyDialogSection;
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
		Company company = editCompanyDialogParentCompanyId != null
				? entityBean.exists(editCompanyDialogParentCompanyId, Company.class) ? companyBean.findById(editCompanyDialogParentCompanyId) : null
				: null;
		return WebUtils.getAsString(company, CompanyConverter.CONVERTER_ID);
	}

	public Integer getEditCompanyDialogRequiredEmployeesCount() {
		return editCompanyDialogRequiredEmployeesCount;
	}

	public void setEditCompanyDialogRequiredEmployeesCount(Integer editCompanyDialogRequiredEmployeesCount) {
		this.editCompanyDialogRequiredEmployeesCount = editCompanyDialogRequiredEmployeesCount;
	}

	public void initEditCompanyDialog() {
		if (!selectedCompanies.isEmpty()) {
			editCompanyDialogSelectedCompany = selectedCompanies.get(0);
			editCompanyDialogName = editCompanyDialogSelectedCompany.getName();
			editCompanyDialogDescription = editCompanyDialogSelectedCompany.getDescription();
			editCompanyDialogRoom = editCompanyDialogSelectedCompany.getRoom();
			editCompanyDialogSection = editCompanyDialogSelectedCompany.getSection();
			editCompanyDialogCompanyType = editCompanyDialogSelectedCompany.getCompanyType();
			Company parent = editCompanyDialogSelectedCompany.getParentCompany();
			editCompanyDialogParentCompanyId = parent == null ? null : parent.getId();
			editCompanyDialogRequiredEmployeesCount = editCompanyDialogSelectedCompany.getRequiredEmployeesCount();
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
			if (WebUtils.isPermitted(EnumPermission.COMPANY_SET_ROOM)) {
				companyBean.setRoom(id, editCompanyDialogRoom);
			}
			if (WebUtils.isPermitted(EnumPermission.COMPANY_SET_SECTION)) {
				companyBean.setSection(id, editCompanyDialogSection);
			}
			if (WebUtils.isPermitted(EnumPermission.COMPANY_SET_COMPANY_TYPE)) {
				companyBean.setCompanyType(id, editCompanyDialogCompanyType);
			}
			if (WebUtils.isPermitted(EnumPermission.COMPANY_SET_PARENT_COMPANY)) {
				companyBean.setParentCompany(id, editCompanyDialogParentCompanyId);
			}
			if (WebUtils.isPermitted(EnumPermission.COMPANY_SET_REQUIRED_EMPLOYEES_COUNT)) {
				companyBean.setRequiredEmployeesCount(id, editCompanyDialogRequiredEmployeesCount);
			}
			Company tmp2 = companyBean.findById(id);
			params.add(WebUtils.getAsString(tmp2, CompanyConverter.CONVERTER_ID));
			WebUtils.refreshEntities(Company.class, searchCompanyResults, selectedCompanies, tmp2, companyBean::findById, true);
			return true;
		}, "lucas.application.companyScreen.editCompany.message", (exception, params) -> {
			if (exception.getMark().equals(florian_haas.lucas.business.CompanyBean.NAME_NOT_UNIQUE_EXCEPTION_MARKER)) {
				return WebUtils.getTranslatedMessage("lucas.application.companyScreen.editCompany.message.notUniqueName", editCompanyDialogName);
			} else {
				return WebUtils.getTranslatedMessage("lucas.application.companyScreen.editCompany.message.notUniqueLocation", editCompanyDialogRoom,
						editCompanyDialogSection);
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

	private List<String> getStringFromEmployment(List<Employment> employments) {
		List<String> ret = new ArrayList<>();
		employments.forEach(employment -> {
			ret.add(WebUtils.getAsString(employment.getUser(), UserConverter.CONVERTER_ID));
		});
		return ret;
	}

	public void initViewEmployeesDialog() {
		if (!selectedCompanies.isEmpty()) {
			Company tmp = selectedCompanies.get(0);
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

	private Company companyCardManagerDialogSelectedCompany;

	private List<CompanyCard> companyCardManagerDialogSelectedCompanyCards = new ArrayList<>();

	private List<CompanyCard> companyCardManagerDialogCompanyCards = new ArrayList<>();

	private Date companyCardManagerDialogValidDate = Date.from(Instant.now());

	public static final String COMPANY_CARD_MANAGER_DIALOG_MESSAGES_ID = "companyCardManagerDialogMessages";

	public List<CompanyCard> getCompanyCardManagerDialogCompanyCards() {
		return this.companyCardManagerDialogCompanyCards;
	}

	public List<CompanyCard> getCompanyCardManagerDialogSelectedCompanyCards() {
		return this.companyCardManagerDialogSelectedCompanyCards;
	}

	public void setCompanyCardManagerDialogSelectedCompanyCards(List<CompanyCard> companyCardManagerDialogSelectedCompanyCards) {
		this.companyCardManagerDialogSelectedCompanyCards = companyCardManagerDialogSelectedCompanyCards;
	}

	public Company getCompanyCardManagerDialogSelectedCompany() {
		return this.companyCardManagerDialogSelectedCompany;
	}

	public Date getCompanyCardManagerDialogValidDate() {
		return companyCardManagerDialogValidDate;
	}

	public void setCompanyCardManagerDialogValidDate(Date companyCardManagerDialogValidDate) {
		this.companyCardManagerDialogValidDate = companyCardManagerDialogValidDate;
	}

	public void initCompanyCardManagerDialog() {
		if (!selectedCompanies.isEmpty()) {
			companyCardManagerDialogSelectedCompany = selectedCompanies.get(0);
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
		for (CompanyCard card : companyCardManagerDialogSelectedCompanyCards) {
			Long id = card.getId();
			if (WebUtils.executeTask(params -> {
				params.add(id);
				return companyBean.blockCompanyCard(id);
			}, "lucas.application.companyScreen.blockCompanyCard", COMPANY_CARD_MANAGER_DIALOG_MESSAGES_ID,
					Utils.asList(WebUtils.getAsString(companyCardManagerDialogSelectedCompany, CompanyConverter.CONVERTER_ID)))) {
				CompanyCard newCard = companyBean.findCompanyCardById(id);
				WebUtils.refreshEntities(CompanyCard.class, companyCardManagerDialogCompanyCards, companyCardManagerDialogSelectedCompanyCards,
						newCard, companyBean::findCompanyCardById, true);
			}
		}
	}

	public void companyCardManagerDialogUnblockCompanyCards() {
		for (CompanyCard card : companyCardManagerDialogSelectedCompanyCards) {
			Long id = card.getId();
			if (WebUtils.executeTask(params -> {
				params.add(id);
				return companyBean.unblockCompanyCard(id);
			}, "lucas.application.companyScreen.unblockCompanyCard", COMPANY_CARD_MANAGER_DIALOG_MESSAGES_ID,
					Utils.asList(WebUtils.getAsString(companyCardManagerDialogSelectedCompany, CompanyConverter.CONVERTER_ID)))) {
				CompanyCard newCard = companyBean.findCompanyCardById(id);
				WebUtils.refreshEntities(CompanyCard.class, companyCardManagerDialogCompanyCards, companyCardManagerDialogSelectedCompanyCards,
						newCard, companyBean::findCompanyCardById, true);
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
		for (CompanyCard card : companyCardManagerDialogSelectedCompanyCards) {
			Long id = card.getId();
			if (WebUtils.executeTask(params -> {
				params.add(card.getId());
				params.add(WebUtils.getAsString(Utils.asLocalDate(companyCardManagerDialogValidDate), LocalDateConverter.CONVERTER_ID));
				params.add(WebUtils.getAsString(card.getValidDay(), LocalDateConverter.CONVERTER_ID));
				return companyBean.setValidDate(id, Utils.asLocalDate(companyCardManagerDialogValidDate));
			}, "lucas.application.companyScreen.setValidDay", COMPANY_CARD_MANAGER_DIALOG_MESSAGES_ID)) {
				CompanyCard newCard = companyBean.findCompanyCardById(id);
				WebUtils.refreshEntities(CompanyCard.class, companyCardManagerDialogCompanyCards, companyCardManagerDialogSelectedCompanyCards,
						newCard, companyBean::findCompanyCardById, true);
			}
		}
	}

	public void companyCardManagerDialogRemoveValidDay() {
		for (CompanyCard card : companyCardManagerDialogSelectedCompanyCards) {
			Long id = card.getId();
			if (WebUtils.executeTask(params -> {
				params.add(card.getId());
				return companyBean.setValidDate(id, null);
			}, "lucas.application.companyScreen.removeValidDay", COMPANY_CARD_MANAGER_DIALOG_MESSAGES_ID)) {
				CompanyCard newCard = companyBean.findCompanyCardById(id);
				WebUtils.refreshEntities(CompanyCard.class, companyCardManagerDialogCompanyCards, companyCardManagerDialogSelectedCompanyCards,
						newCard, companyBean::findCompanyCardById, true);
			}
		}
	}

	public void companyCardManagerDialogRemoveCompanyCards() {
		if (!companyCardManagerDialogSelectedCompanyCards.isEmpty()) {
			ListIterator<CompanyCard> it = companyCardManagerDialogSelectedCompanyCards.listIterator();
			while (it.hasNext()) {
				WebUtils.executeTask(params -> {
					CompanyCard companyCard = it.next();
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
			WebUtils.refreshEntities(CompanyCard.class, companyCardManagerDialogCompanyCards, companyCardManagerDialogSelectedCompanyCards,
					companyBean::findCompanyCardById, true);
			params.add(companyCardManagerDialogCompanyCards.size());
			return true;
		}, "lucas.application.companyScreen.refreshCompanyCards", COMPANY_CARD_MANAGER_DIALOG_MESSAGES_ID);
	}

	/*
	 * -------------------- Company Card Manager Dialog End --------------------
	 */

}
