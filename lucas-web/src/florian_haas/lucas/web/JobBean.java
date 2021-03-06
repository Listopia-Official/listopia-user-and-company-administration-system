package florian_haas.lucas.web;

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
import florian_haas.lucas.web.converter.JobConverter.ShortJobConverter;
import florian_haas.lucas.web.util.WebUtils;

@Named
@ViewScoped
public class JobBean extends BaseBean<ReadOnlyJob> {

	public static final String BASE_NAME = "job";

	private static final long serialVersionUID = 1555704972109670099L;

	@EJB
	private JobBeanLocal jobBean;

	@EJB
	private EntityBeanLocal entityBean;

	@EJB
	private CompanyBeanLocal companyBean;

	public JobBean() {
		super(BASE_NAME, 8);
	}

	@Override
	public EnumPermission getFindDynamicPermission() {
		return EnumPermission.JOB_FIND_DYNAMIC;
	}

	@Override
	public EnumPermission getPrintPermission() {
		return EnumPermission.JOB_PRINT;
	}

	@Override
	public EnumPermission getExportPermission() {
		return EnumPermission.JOB_EXPORT;
	}

	@NotNull
	@Min(0)
	private Long searchJobId = 0l;

	@NotNull
	private Boolean useSearchJobId = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchJobIdComparator = EnumQueryComparator.EQUAL;

	@NotBlank
	private String searchJobName = "";

	@NotNull
	private Boolean useSearchJobName = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.TEXT)
	private EnumQueryComparator searchJobNameComparator = EnumQueryComparator.EQUAL;

	@NotBlankString
	@Size(max = 255)
	private String searchJobDescription = null;

	@NotNull
	private Boolean useSearchJobDescription = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.TEXT)
	private EnumQueryComparator searchJobDescriptionComparator = EnumQueryComparator.EQUAL;

	@NotNull
	private ReadOnlyCompany searchJobCompany = null;

	@NotNull
	private Boolean useSearchJobCompanyId = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchJobCompanyIdComparator = EnumQueryComparator.EQUAL;

	private EnumSalaryClass searchJobSalaryClass = null;

	@NotNull
	private Boolean useSearchJobSalaryClass = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.LOGIC)
	private EnumQueryComparator searchJobSalaryClassComparator = EnumQueryComparator.EQUAL;

	@NotNull
	@Min(0)
	private Integer searchJobRequiredEmploymentsCount = 0;

	@NotNull
	private Boolean useSearchJobRequiredEmploymentsCount = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchJobRequiredEmploymentsCountComparator = EnumQueryComparator.EQUAL;

	@NotNull
	private EnumEmployeePosition searchJobPosition = EnumEmployeePosition.EMPLOYEE;

	@NotNull
	private Boolean useSearchJobPosition = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.LOGIC)
	private EnumQueryComparator searchJobPositionComparator = EnumQueryComparator.EQUAL;

	@NotNull
	private ReadOnlyEmployment searchJobEmployment = null;

	@NotNull
	private Boolean useSearchJobEmploymentId = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchJobEmploymentIdComparator = EnumQueryComparator.EQUAL;

	@NotNull
	@Min(0)
	private Integer searchJobEmploymentsCount = 0;

	@NotNull
	private Boolean useSearchJobEmploymentsCount = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchJobEmploymentsCountComparator = EnumQueryComparator.EQUAL;

	@NotNull
	private Boolean searchJobAreEmploymentsRequired = Boolean.FALSE;

	@NotNull
	private Boolean useSearchJobAreEmploymentsRequired = Boolean.FALSE;

	public Long getSearchJobId() {
		return this.searchJobId;
	}

	public void setSearchJobId(Long searchJobId) {
		this.searchJobId = searchJobId;
	}

	public Boolean getUseSearchJobId() {
		return this.useSearchJobId;
	}

	public void setUseSearchJobId(Boolean useSearchJobId) {
		this.useSearchJobId = useSearchJobId;
	}

	public EnumQueryComparator getSearchJobIdComparator() {
		return this.searchJobIdComparator;
	}

	public void setSearchJobIdComparator(EnumQueryComparator searchJobIdComparator) {
		this.searchJobIdComparator = searchJobIdComparator;
	}

	public String getSearchJobName() {
		return this.searchJobName;
	}

	public void setSearchJobName(String searchJobName) {
		this.searchJobName = searchJobName;
	}

	public Boolean getUseSearchJobName() {
		return this.useSearchJobName;
	}

	public void setUseSearchJobName(Boolean useSearchJobName) {
		this.useSearchJobName = useSearchJobName;
	}

	public EnumQueryComparator getSearchJobNameComparator() {
		return this.searchJobNameComparator;
	}

	public void setSearchJobNameComparator(EnumQueryComparator searchJobNameComparator) {
		this.searchJobNameComparator = searchJobNameComparator;
	}

	public String getSearchJobDescription() {
		return this.searchJobDescription;
	}

	public void setSearchJobDescription(String searchJobDescription) {
		this.searchJobDescription = searchJobDescription;
	}

	public Boolean getUseSearchJobDescription() {
		return this.useSearchJobDescription;
	}

	public void setUseSearchJobDescription(Boolean useSearchJobDescription) {
		this.useSearchJobDescription = useSearchJobDescription;
	}

	public EnumQueryComparator getSearchJobDescriptionComparator() {
		return this.searchJobDescriptionComparator;
	}

	public void setSearchJobDescriptionComparator(EnumQueryComparator searchJobDescriptionComparator) {
		this.searchJobDescriptionComparator = searchJobDescriptionComparator;
	}

	public ReadOnlyCompany getSearchJobCompany() {
		return this.searchJobCompany;
	}

	public void setSearchJobCompany(ReadOnlyCompany searchJobCompany) {
		this.searchJobCompany = searchJobCompany;
	}

	public Boolean getUseSearchJobCompanyId() {
		return this.useSearchJobCompanyId;
	}

	public void setUseSearchJobCompanyId(Boolean useSearchJobCompanyId) {
		this.useSearchJobCompanyId = useSearchJobCompanyId;
	}

	public EnumQueryComparator getSearchJobCompanyIdComparator() {
		return this.searchJobCompanyIdComparator;
	}

	public void setSearchJobCompanyIdComparator(EnumQueryComparator searchJobCompanyIdComparator) {
		this.searchJobCompanyIdComparator = searchJobCompanyIdComparator;
	}

	public EnumSalaryClass getSearchJobSalaryClass() {
		return this.searchJobSalaryClass;
	}

	public void setSearchJobSalaryClass(EnumSalaryClass searchJobSalaryClass) {
		this.searchJobSalaryClass = searchJobSalaryClass;
	}

	public Boolean getUseSearchJobSalaryClass() {
		return this.useSearchJobSalaryClass;
	}

	public void setUseSearchJobSalaryClass(Boolean useSearchJobSalaryClass) {
		this.useSearchJobSalaryClass = useSearchJobSalaryClass;
	}

	public EnumQueryComparator getSearchJobSalaryClassComparator() {
		return this.searchJobSalaryClassComparator;
	}

	public void setSearchJobSalaryClassComparator(EnumQueryComparator searchJobSalaryClassComparator) {
		this.searchJobSalaryClassComparator = searchJobSalaryClassComparator;
	}

	public Integer getSearchJobRequiredEmploymentsCount() {
		return this.searchJobRequiredEmploymentsCount;
	}

	public void setSearchJobRequiredEmploymentsCount(Integer searchJobRequiredEmploymentsCount) {
		this.searchJobRequiredEmploymentsCount = searchJobRequiredEmploymentsCount;
	}

	public Boolean getUseSearchJobRequiredEmploymentsCount() {
		return this.useSearchJobRequiredEmploymentsCount;
	}

	public void setUseSearchJobRequiredEmploymentsCount(Boolean useSearchJobRequiredEmploymentsCount) {
		this.useSearchJobRequiredEmploymentsCount = useSearchJobRequiredEmploymentsCount;
	}

	public EnumQueryComparator getSearchJobRequiredEmploymentsCountComparator() {
		return this.searchJobRequiredEmploymentsCountComparator;
	}

	public void setSearchJobRequiredEmploymentsCountComparator(EnumQueryComparator searchJobRequiredEmploymentsCountComparator) {
		this.searchJobRequiredEmploymentsCountComparator = searchJobRequiredEmploymentsCountComparator;
	}

	public EnumEmployeePosition getSearchJobPosition() {
		return this.searchJobPosition;
	}

	public void setSearchJobPosition(EnumEmployeePosition searchJobPosition) {
		this.searchJobPosition = searchJobPosition;
	}

	public Boolean getUseSearchJobPosition() {
		return this.useSearchJobPosition;
	}

	public void setUseSearchJobPosition(Boolean useSearchJobPosition) {
		this.useSearchJobPosition = useSearchJobPosition;
	}

	public EnumQueryComparator getSearchJobPositionComparator() {
		return this.searchJobPositionComparator;
	}

	public void setSearchJobPositionComparator(EnumQueryComparator searchJobPositionComparator) {
		this.searchJobPositionComparator = searchJobPositionComparator;
	}

	public ReadOnlyEmployment getSearchJobEmployment() {
		return this.searchJobEmployment;
	}

	public void setSearchJobEmployment(ReadOnlyEmployment searchJobEmployment) {
		this.searchJobEmployment = searchJobEmployment;
	}

	public Boolean getUseSearchJobEmploymentId() {
		return this.useSearchJobEmploymentId;
	}

	public void setUseSearchJobEmploymentId(Boolean useSearchJobEmploymentId) {
		this.useSearchJobEmploymentId = useSearchJobEmploymentId;
	}

	public EnumQueryComparator getSearchJobEmploymentIdComparator() {
		return this.searchJobEmploymentIdComparator;
	}

	public void setSearchJobEmploymentIdComparator(EnumQueryComparator searchJobEmploymentIdComparator) {
		this.searchJobEmploymentIdComparator = searchJobEmploymentIdComparator;
	}

	public Boolean getSearchJobAreEmploymentsRequired() {
		return searchJobAreEmploymentsRequired;
	}

	public void setSearchJobAreEmploymentsRequired(Boolean searchJobAreEmploymentsRequired) {
		this.searchJobAreEmploymentsRequired = searchJobAreEmploymentsRequired;
	}

	public Boolean getUseSearchJobAreEmploymentsRequired() {
		return useSearchJobAreEmploymentsRequired;
	}

	public void setUseSearchJobAreEmploymentsRequired(Boolean useSearchJobAreEmploymentsRequired) {
		this.useSearchJobAreEmploymentsRequired = useSearchJobAreEmploymentsRequired;
	}

	@Override
	protected List<? extends ReadOnlyJob> searchEntities() {
		return jobBean.findJobs(searchJobId, searchJobName, searchJobDescription, searchJobCompany != null ? searchJobCompany.getId() : null,
				searchJobSalaryClass, searchJobRequiredEmploymentsCount, searchJobPosition,
				searchJobEmployment != null ? searchJobEmployment.getId() : null, searchJobEmploymentsCount, searchJobAreEmploymentsRequired,
				useSearchJobId, useSearchJobName, useSearchJobDescription, useSearchJobCompanyId, useSearchJobSalaryClass,
				useSearchJobRequiredEmploymentsCount, useSearchJobPosition, useSearchJobEmploymentId, useSearchJobEmploymentsCount,
				useSearchJobAreEmploymentsRequired, searchJobIdComparator, searchJobNameComparator, searchJobDescriptionComparator,
				searchJobCompanyIdComparator, searchJobSalaryClassComparator, searchJobRequiredEmploymentsCountComparator,
				searchJobPositionComparator, searchJobEmploymentIdComparator, searchJobEmploymentsCountComparator);
	}

	@Override
	protected ReadOnlyJob entityGetter(Long entityId) {
		return jobBean.findById(entityId);
	}

	/*
	 * -------------------- Create Job Dialog Start --------------------
	 */

	@NotBlank
	private String createJobDialogName;

	@NotBlankString
	@Size(max = 255)
	private String createJobDialogDescription;

	@NotNull
	private ReadOnlyCompany createJobDialogCompany;

	@NotNull
	private EnumEmployeePosition createJobDialogPosition = EnumEmployeePosition.EMPLOYEE;

	@NotNull
	@Min(0)
	private Integer createJobDialogRequiredEmploymentsCount = 1;

	private EnumSalaryClass createJobDialogSalaryClass = null;

	public String getCreateJobDialogName() {
		return createJobDialogName;
	}

	public void setCreateJobDialogName(String createJobDialogName) {
		this.createJobDialogName = createJobDialogName;
	}

	public String getCreateJobDialogDescription() {
		return createJobDialogDescription;
	}

	public void setCreateJobDialogDescription(String createJobDialogDescription) {
		this.createJobDialogDescription = createJobDialogDescription;
	}

	public ReadOnlyCompany getCreateJobDialogCompany() {
		return createJobDialogCompany;
	}

	public void setCreateJobDialogCompany(ReadOnlyCompany createJobDialogCompany) {
		this.createJobDialogCompany = createJobDialogCompany;
	}

	public EnumEmployeePosition getCreateJobDialogPosition() {
		return createJobDialogPosition;
	}

	public void setCreateJobDialogPosition(EnumEmployeePosition createJobDialogPosition) {
		this.createJobDialogPosition = createJobDialogPosition;
	}

	public Integer getCreateJobDialogRequiredEmploymentsCount() {
		return createJobDialogRequiredEmploymentsCount;
	}

	public void setCreateJobDialogRequiredEmploymentsCount(Integer createJobDialogRequiredEmploymentsCount) {
		this.createJobDialogRequiredEmploymentsCount = createJobDialogRequiredEmploymentsCount;
	}

	public EnumSalaryClass getCreateJobDialogSalaryClass() {
		return this.createJobDialogSalaryClass;
	}

	public void setCreateJobDialogSalaryClass(EnumSalaryClass createJobDialogSalaryClass) {
		this.createJobDialogSalaryClass = createJobDialogSalaryClass;
	}

	public Integer getSearchJobEmploymentsCount() {
		return this.searchJobEmploymentsCount;
	}

	public void setSearchJobEmploymentsCount(Integer searchJobEmploymentsCount) {
		this.searchJobEmploymentsCount = searchJobEmploymentsCount;
	}

	public Boolean getUseSearchJobEmploymentsCount() {
		return this.useSearchJobEmploymentsCount;
	}

	public void setUseSearchJobEmploymentsCount(Boolean useSearchJobEmploymentsCount) {
		this.useSearchJobEmploymentsCount = useSearchJobEmploymentsCount;
	}

	public EnumQueryComparator getSearchJobEmploymentsCountComparator() {
		return this.searchJobEmploymentsCountComparator;
	}

	public void setSearchJobEmploymentsCountComparator(EnumQueryComparator searchJobEmploymentsCountComparator) {
		this.searchJobEmploymentsCountComparator = searchJobEmploymentsCountComparator;
	}

	public void initCreateJobDialog() {
		createJobDialogName = null;
		createJobDialogDescription = null;
		createJobDialogCompany = null;
		createJobDialogPosition = EnumEmployeePosition.EMPLOYEE;
		createJobDialogRequiredEmploymentsCount = 1;
		createJobDialogSalaryClass = null;
	}

	public void createJob() {
		WebUtils.executeTask((params) -> {
			params.add(WebUtils.getAsString(
					jobBean.findById(jobBean.createJob(createJobDialogName, createJobDialogDescription, createJobDialogCompany.getId(),
							createJobDialogPosition, createJobDialogRequiredEmploymentsCount, createJobDialogSalaryClass)),
					ShortJobConverter.CONVERTER_ID));
			return true;
		}, "lucas.application.jobScreen.createJob.message", (exception, params) -> {
			return WebUtils.getTranslatedMessage("lucas.application.jobScreen.createJob.message.notUniqueName", createJobDialogName, params.get(0));
		}, Utils.asList(WebUtils.getAsString(createJobDialogCompany, CompanyConverter.CONVERTER_ID)));
	}

	/*
	 * -------------------- Create Job Dialog End --------------------
	 */

	/*
	 * -------------------- Edit Job Dialog Start --------------------
	 */

	private ReadOnlyJob editJobDialogSelectedJob;

	@NotBlank
	private String editJobDialogName;

	@NotBlankString
	@Size(max = 255)
	private String editJobDialogDescription;

	@NotNull
	private EnumEmployeePosition editJobDialogPosition = EnumEmployeePosition.EMPLOYEE;

	@NotNull
	@Min(0)
	private Integer editJobDialogRequiredEmploymentsCount = 1;

	private EnumSalaryClass editJobDialogSalaryClass = EnumSalaryClass.D;

	public String getEditJobDialogName() {
		return editJobDialogName;
	}

	public void setEditJobDialogName(String editJobDialogName) {
		this.editJobDialogName = editJobDialogName;
	}

	public String getEditJobDialogDescription() {
		return editJobDialogDescription;
	}

	public void setEditJobDialogDescription(String editJobDialogDescription) {
		this.editJobDialogDescription = editJobDialogDescription;
	}

	public EnumEmployeePosition getEditJobDialogPosition() {
		return editJobDialogPosition;
	}

	public void setEditJobDialogPosition(EnumEmployeePosition editJobDialogPosition) {
		this.editJobDialogPosition = editJobDialogPosition;
	}

	public Integer getEditJobDialogRequiredEmploymentsCount() {
		return editJobDialogRequiredEmploymentsCount;
	}

	public void setEditJobDialogRequiredEmploymentsCount(Integer editJobDialogRequiredEmploymentsCount) {
		this.editJobDialogRequiredEmploymentsCount = editJobDialogRequiredEmploymentsCount;
	}

	public EnumSalaryClass getEditJobDialogSalaryClass() {
		return this.editJobDialogSalaryClass;
	}

	public void setEditJobDialogSalaryClass(EnumSalaryClass editJobDialogSalaryClass) {
		this.editJobDialogSalaryClass = editJobDialogSalaryClass;
	}

	public void initEditJobDialog() {
		if (!selectedEntities.isEmpty()) {
			editJobDialogSelectedJob = selectedEntities.get(0);
			editJobDialogName = editJobDialogSelectedJob.getName();
			editJobDialogDescription = editJobDialogSelectedJob.getDescription();
			editJobDialogPosition = editJobDialogSelectedJob.getEmployeePosition();
			editJobDialogRequiredEmploymentsCount = editJobDialogSelectedJob.getRequiredEmploymentsCount();
			editJobDialogSalaryClass = editJobDialogSelectedJob.getSalaryClass();
		}
	}

	public void editJob() {
		WebUtils.executeTask((params) -> {
			Long id = editJobDialogSelectedJob.getId();
			if (WebUtils.isPermitted(EnumPermission.JOB_SET_NAME)) {
				jobBean.setName(id, editJobDialogName);
			}
			if (WebUtils.isPermitted(EnumPermission.JOB_SET_DESCRIPTION)) {
				jobBean.setDescription(id, editJobDialogDescription);
			}
			if (WebUtils.isPermitted(EnumPermission.JOB_SET_EMPLOYEE_POSITION)) {
				jobBean.setEmployeePosition(id, editJobDialogPosition);
			}
			if (WebUtils.isPermitted(EnumPermission.JOB_SET_REQUIRED_EMPLOYMENT_COUNT)) {
				jobBean.setRequiredEmploymentsCount(id, editJobDialogRequiredEmploymentsCount);
			}
			if (WebUtils.isPermitted(EnumPermission.JOB_SET_SALARY_CLASS)) {
				jobBean.setSalaryClass(id, editJobDialogSalaryClass);
			}
			ReadOnlyJob tmp2 = jobBean.findById(id);
			params.add(WebUtils.getAsString(tmp2, ShortJobConverter.CONVERTER_ID));
			WebUtils.refreshEntities(ReadOnlyJob.class, searchResults, selectedEntities, tmp2, jobBean::findById, true);
			return true;
		}, "lucas.application.jobScreen.editJob.message", (exception, params) -> {
			return WebUtils.getTranslatedMessage("lucas.application.jobScreen.editJob.message.notUniqueName", editJobDialogName, params.get(0));
		}, Utils.asList(WebUtils.getAsString(editJobDialogSelectedJob.getCompany(), CompanyConverter.CONVERTER_ID)));
	}

	/*
	 * -------------------- Edit Job Dialog End --------------------
	 */

	public void removeJobs() {
		Iterator<ReadOnlyJob> it = selectedEntities.iterator();
		while (it.hasNext()) {
			ReadOnlyJob job = it.next();
			WebUtils.executeTask(params -> {
				params.add(WebUtils.getAsString(job, JobConverter.CONVERTER_ID));
				Boolean ret = jobBean.deleteJob(job.getId());
				if (ret) {
					searchResults.remove(job);
					it.remove();
				}
				return ret;
			}, "lucas.application.jobScreen.removeJobs");
		}
	}

	/*
	 * -------------------- Compute Missing Employments Dialog Start --------------------
	 */

	private List<EnumEmployeePosition> computeMissingEmploymentsDialogValidJobs = new ArrayList<>();

	private List<EnumCompanyType> computeMissingEmploymentsDialogValidCompanyTypes = new ArrayList<>();

	public List<EnumEmployeePosition> getComputeMissingEmploymentsDialogValidJobs() {
		return this.computeMissingEmploymentsDialogValidJobs;
	}

	public void setComputeMissingEmploymentsDialogValidJobs(List<EnumEmployeePosition> computeMissingEmploymentsDialogValidJobs) {
		this.computeMissingEmploymentsDialogValidJobs = computeMissingEmploymentsDialogValidJobs;
	}

	public List<EnumCompanyType> getComputeMissingEmploymentsDialogValidCompanyTypes() {
		return this.computeMissingEmploymentsDialogValidCompanyTypes;
	}

	public void setComputeMissingEmploymentsDialogValidCompanyTypes(List<EnumCompanyType> computeMissingEmploymentsDialogValidCompanyTypes) {
		this.computeMissingEmploymentsDialogValidCompanyTypes = computeMissingEmploymentsDialogValidCompanyTypes;
	}

	public void initComputeMissingEmploymentsDialog() {
		computeMissingEmploymentsDialogValidJobs.clear();
		computeMissingEmploymentsDialogValidJobs.add(EnumEmployeePosition.EMPLOYEE);
		computeMissingEmploymentsDialogValidCompanyTypes.clear();
		computeMissingEmploymentsDialogValidCompanyTypes.add(EnumCompanyType.CIVIL);
	}

	public void computeMissingEmployments() {
		WebUtils.executeTask(params -> {
			params.add(jobBean.computeMissingEmployments(new HashSet<>(computeMissingEmploymentsDialogValidJobs),
					new HashSet<>(computeMissingEmploymentsDialogValidCompanyTypes)));
			return true;
		}, "lucas.application.jobScreen.computeMissingEmployments.message");
	}

	/*
	 * -------------------- Compute Missing Employments Dialog End --------------------
	 */

	public String showReferencedCompanies() {
		navigateToBeanSingle(CompanyBean.BASE_NAME, (job) -> job.getCompany().getId());
		return "/companies?faces-redirect=true";
	}

	public String showReferencedEmployments() {
		navigateToBean(EmploymentBean.BASE_NAME, (job) -> {
			List<Long> ids = new ArrayList<>();
			for (ReadOnlyEmployment employment : jobBean.getEmployments(job.getId())) {
				ids.add(employment.getId());
			}
			return ids;
		});
		return "/employments?faces-redirect=true";
	}
}
