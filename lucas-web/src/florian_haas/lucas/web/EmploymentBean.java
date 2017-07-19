package florian_haas.lucas.web;

import java.util.*;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.*;

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
public class EmploymentBean extends BaseBean<ReadOnlyEmployment> {

	public EmploymentBean() {
		super(BASE_NAME, 4);
	}

	public static final String BASE_NAME = "employment";

	private static final long serialVersionUID = 6550522987925022505L;

	@EJB
	private EmploymentBeanLocal employmentBean;

	@EJB
	private UserBeanLocal userBean;

	@EJB
	private JobBeanLocal jobBean;

	@EJB
	private EntityBeanLocal entityBean;

	@NotNull
	@Min(0)
	private Long searchEmploymentId = 0l;

	@NotNull
	private Boolean useSearchEmploymentId = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchEmploymentIdComparator = EnumQueryComparator.EQUAL;

	@NotNull
	private ReadOnlyUser searchEmploymentUser = null;

	@NotNull
	private Boolean useSearchEmploymentUserId = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchEmploymentUserIdComparator = EnumQueryComparator.EQUAL;

	@NotNull
	private ReadOnlyJob searchEmploymentJob = null;

	@NotNull
	private Boolean useSearchEmploymentJobId = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchEmploymentJobIdComparator = EnumQueryComparator.EQUAL;

	@NotNull
	private List<@TypeNotNull EnumWorkShift> searchEmploymentWorkShifts = new ArrayList<>();

	@NotNull
	private Boolean useSearchEmploymentWorkShifts = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.ARRAY)
	private EnumQueryComparator searchEmploymentWorkShiftsComparator = EnumQueryComparator.MEMBER_OF;

	public Long getSearchEmploymentId() {
		return this.searchEmploymentId;
	}

	public void setSearchEmploymentId(Long searchEmploymentId) {
		this.searchEmploymentId = searchEmploymentId;
	}

	public Boolean getUseSearchEmploymentId() {
		return useSearchEmploymentId;
	}

	public void setUseSearchEmploymentId(Boolean useSearchEmploymentId) {
		this.useSearchEmploymentId = useSearchEmploymentId;
	}

	public EnumQueryComparator getSearchEmploymentIdComparator() {
		return searchEmploymentIdComparator;
	}

	public void setSearchEmploymentIdComparator(EnumQueryComparator searchEmploymentIdComparator) {
		this.searchEmploymentIdComparator = searchEmploymentIdComparator;
	}

	public ReadOnlyUser getSearchEmploymentUser() {
		return searchEmploymentUser;
	}

	public void setSearchEmploymentUser(ReadOnlyUser searchEmploymentUser) {
		this.searchEmploymentUser = searchEmploymentUser;
	}

	public Boolean getUseSearchEmploymentUserId() {
		return useSearchEmploymentUserId;
	}

	public void setUseSearchEmploymentUserId(Boolean useSearchEmploymentUserId) {
		this.useSearchEmploymentUserId = useSearchEmploymentUserId;
	}

	public EnumQueryComparator getSearchEmploymentUserIdComparator() {
		return searchEmploymentUserIdComparator;
	}

	public void setSearchEmploymentUserIdComparator(EnumQueryComparator searchEmploymentUserIdComparator) {
		this.searchEmploymentUserIdComparator = searchEmploymentUserIdComparator;
	}

	public ReadOnlyJob getSearchEmploymentJob() {
		return searchEmploymentJob;
	}

	public void setSearchEmploymentJob(ReadOnlyJob searchEmploymentJob) {
		this.searchEmploymentJob = searchEmploymentJob;
	}

	public Boolean getUseSearchEmploymentJobId() {
		return useSearchEmploymentJobId;
	}

	public void setUseSearchEmploymentJobId(Boolean useSearchEmploymentJobId) {
		this.useSearchEmploymentJobId = useSearchEmploymentJobId;
	}

	public EnumQueryComparator getSearchEmploymentJobIdComparator() {
		return searchEmploymentJobIdComparator;
	}

	public void setSearchEmploymentJobIdComparator(EnumQueryComparator searchEmploymentJobIdComparator) {
		this.searchEmploymentJobIdComparator = searchEmploymentJobIdComparator;
	}

	public List<EnumWorkShift> getSearchEmploymentWorkShifts() {
		return this.searchEmploymentWorkShifts;
	}

	public void setSearchEmploymentWorkShifts(List<EnumWorkShift> searchEmploymentWorkShifts) {
		this.searchEmploymentWorkShifts = searchEmploymentWorkShifts;
	}

	public Boolean getUseSearchEmploymentWorkShifts() {
		return this.useSearchEmploymentWorkShifts;
	}

	public void setUseSearchEmploymentWorkShifts(Boolean useSearchEmploymentWorkShifts) {
		this.useSearchEmploymentWorkShifts = useSearchEmploymentWorkShifts;
	}

	public EnumQueryComparator getSearchEmploymentWorkShiftsComparator() {
		return this.searchEmploymentWorkShiftsComparator;
	}

	public void setSearchEmploymentWorkShiftsComparator(EnumQueryComparator searchEmploymentWorkShiftsComparator) {
		this.searchEmploymentWorkShiftsComparator = searchEmploymentWorkShiftsComparator;
	}

	@Override
	public EnumPermission getFindDynamicPermission() {
		return EnumPermission.EMPLOYMENT_FIND_DYNAMIC;
	}

	@Override
	public EnumPermission getPrintPermission() {
		return EnumPermission.EMPLOYMENT_PRINT;
	}

	@Override
	public EnumPermission getExportPermission() {
		return EnumPermission.EMPLOYMENT_EXPORT;
	}

	@Override
	protected List<? extends ReadOnlyEmployment> searchEntities() {
		return employmentBean.findEmployments(searchEmploymentId, searchEmploymentUser != null ? searchEmploymentUser.getId() : null,
				searchEmploymentJob != null ? searchEmploymentJob.getId() : null,
				!searchEmploymentWorkShifts.isEmpty() ? new HashSet<>(searchEmploymentWorkShifts) : null, useSearchEmploymentId,
				useSearchEmploymentUserId, useSearchEmploymentJobId, useSearchEmploymentWorkShifts, searchEmploymentIdComparator,
				searchEmploymentUserIdComparator, searchEmploymentJobIdComparator, searchEmploymentWorkShiftsComparator);
	}

	@Override
	protected ReadOnlyEmployment entityGetter(Long entityId) {
		return employmentBean.findById(entityId);
	}

	/*
	 * -------------------- Create Employment Dialog Start --------------------
	 */

	@NotNull
	private ReadOnlyUser createEmploymentDialogUser;

	@NotNull
	private ReadOnlyJob createEmploymentDialogJob;

	@NotNull
	private List<@TypeNotNull EnumWorkShift> createEmploymentDialogWorkShifts = new ArrayList<>();

	public ReadOnlyUser getCreateEmploymentDialogUser() {
		return this.createEmploymentDialogUser;
	}

	public void setCreateEmploymentDialogUser(ReadOnlyUser createEmploymentDialogUser) {
		this.createEmploymentDialogUser = createEmploymentDialogUser;
	}

	public ReadOnlyJob getCreateEmploymentDialogJob() {
		return this.createEmploymentDialogJob;
	}

	public void setCreateEmploymentDialogJob(ReadOnlyJob createEmploymentDialogJob) {
		this.createEmploymentDialogJob = createEmploymentDialogJob;
	}

	public List<EnumWorkShift> getCreateEmploymentDialogWorkShifts() {
		return this.createEmploymentDialogWorkShifts;
	}

	public void setCreateEmploymentDialogWorkShifts(List<EnumWorkShift> createEmploymentDialogWorkShifts) {
		this.createEmploymentDialogWorkShifts = createEmploymentDialogWorkShifts;
	}

	public void initCreateEmploymentDialog() {
		createEmploymentDialogUser = null;
		createEmploymentDialogJob = null;
		createEmploymentDialogWorkShifts.clear();
	}

	public void createEmployment() {
		WebUtils.executeTask((params) -> {
			params.add(WebUtils.getAsString(
					employmentBean.findById(employmentBean.createEmployment(createEmploymentDialogUser.getId(), createEmploymentDialogJob.getId(),
							createEmploymentDialogWorkShifts.isEmpty() ? null : new HashSet<>(createEmploymentDialogWorkShifts))),
					EmploymentConverter.CONVERTER_ID));
			return true;
		}, "lucas.application.employmentScreen.createEmployment.message",
				Utils.asList(WebUtils.getAsString(createEmploymentDialogUser, UserConverter.CONVERTER_ID)));
	}

	/*
	 * -------------------- Create Employment Dialog End --------------------
	 */

	/*
	 * -------------------- Edit Employment Dialog Start --------------------
	 */

	private ReadOnlyEmployment editEmploymentDialogSelectedEmployment;

	@NotNull
	private List<@TypeNotNull EnumWorkShift> editEmploymentDialogWorkShifts = new ArrayList<>();

	public List<EnumWorkShift> getEditEmploymentDialogWorkShifts() {
		return this.editEmploymentDialogWorkShifts;
	}

	public void setEditEmploymentDialogWorkShifts(List<EnumWorkShift> editEmploymentDialogWorkShifts) {
		this.editEmploymentDialogWorkShifts = editEmploymentDialogWorkShifts;
	}

	public void initEditEmploymentDialog() {
		if (!selectedEntities.isEmpty()) {
			editEmploymentDialogSelectedEmployment = selectedEntities.get(0);
			editEmploymentDialogWorkShifts.clear();
			editEmploymentDialogWorkShifts.addAll(editEmploymentDialogSelectedEmployment.getWorkShifts());
		}
	}

	public void editEmployment() {
		WebUtils.executeTask((params) -> {
			Long id = editEmploymentDialogSelectedEmployment.getId();
			if (WebUtils.isPermitted(EnumPermission.EMPLOYMENT_ADD_WORK_SHIFT, EnumPermission.EMPLOYMENT_REMOVE_WORK_SHIFT)) {
				Set<EnumWorkShift> shifts = employmentBean.getWorkShifts(id);
				editEmploymentDialogWorkShifts.forEach(shift -> {
					if (!shifts.contains(shift)) {
						employmentBean.addWorkShift(id, shift);
					}
				});
				shifts.forEach(shift -> {
					if (!editEmploymentDialogWorkShifts.contains(shift)) {
						employmentBean.removeWorkShift(id, shift);
					}
				});
			}
			ReadOnlyEmployment tmp2 = employmentBean.findById(id);
			params.add(WebUtils.getAsString(tmp2, EmploymentConverter.CONVERTER_ID));
			WebUtils.refreshEntities(ReadOnlyEmployment.class, searchResults, selectedEntities, tmp2, employmentBean::findById, true);
			return true;
		}, "lucas.application.employmentScreen.editEmployment.message");
	}

	/*
	 * -------------------- Edit Employment Dialog End --------------------
	 */

	public void removeEmployments() {
		Iterator<ReadOnlyEmployment> it = selectedEntities.iterator();
		while (it.hasNext()) {
			ReadOnlyEmployment employment = it.next();
			WebUtils.executeTask(params -> {
				params.add(WebUtils.getAsString(employment, EmploymentConverter.CONVERTER_ID));
				Boolean ret = employmentBean.removeEmployment(employment.getId());
				if (ret) {
					searchResults.remove(employment);
					it.remove();
				}
				return ret;
			}, "lucas.application.employmentScreen.removeEmployments");
		}
	}

	/*
	 * -------------------- Distribute Jobs Dialog Start --------------------
	 */

	@Size(min = 1)
	private List<EnumUserType> distributeJobsDialogValidUsers = new ArrayList<>();

	@Size(min = 1)
	private List<EnumEmployeePosition> distributeJobsDialogValidJobs = new ArrayList<>();

	@Size(min = 1)
	private List<EnumCompanyType> distributeJobsDialogValidCompanyTypes = new ArrayList<>();

	public List<EnumUserType> getDistributeJobsDialogValidUsers() {
		return this.distributeJobsDialogValidUsers;
	}

	public void setDistributeJobsDialogValidUsers(List<EnumUserType> distributeJobsDialogValidUsers) {
		this.distributeJobsDialogValidUsers = distributeJobsDialogValidUsers;
	}

	public List<EnumEmployeePosition> getDistributeJobsDialogValidJobs() {
		return this.distributeJobsDialogValidJobs;
	}

	public void setDistributeJobsDialogValidJobs(List<EnumEmployeePosition> distributeJobsDialogValidJobs) {
		this.distributeJobsDialogValidJobs = distributeJobsDialogValidJobs;
	}

	public List<EnumCompanyType> getDistributeJobsDialogValidCompanyTypes() {
		return this.distributeJobsDialogValidCompanyTypes;
	}

	public void setDistributeJobsDialogValidCompanyTypes(List<EnumCompanyType> distributeJobsDialogValidCompanyTypes) {
		this.distributeJobsDialogValidCompanyTypes = distributeJobsDialogValidCompanyTypes;
	}

	public void initDistributeJobsDialog() {
		distributeJobsDialogValidUsers.clear();
		distributeJobsDialogValidUsers.add(EnumUserType.PUPIL);
		distributeJobsDialogValidJobs.clear();
		distributeJobsDialogValidJobs.add(EnumEmployeePosition.EMPLOYEE);
		distributeJobsDialogValidCompanyTypes.clear();
		distributeJobsDialogValidCompanyTypes.add(EnumCompanyType.CIVIL);
	}

	public void distributeJobs() {
		WebUtils.executeTask(params -> {
			params.addAll(employmentBean.distributeJobs(EnumSet.copyOf(distributeJobsDialogValidUsers), EnumSet.copyOf(distributeJobsDialogValidJobs),
					EnumSet.copyOf(distributeJobsDialogValidCompanyTypes)));
			return true;
		}, "lucas.application.employmentScreen.distributeJobs.message");
	}

	/*
	 * -------------------- Distribute Jobs Dialog End --------------------
	 */

	public String showReferencedUsers() {
		navigateToBeanSingle(UserBean.BASE_NAME, (employment) -> employment.getUser().getId());
		return "/users?faces-redirect=true";
	}

	public String showReferencedJobs() {
		navigateToBeanSingle(JobBean.BASE_NAME, (employment) -> employment.getJob().getId());
		return "/jobs?faces-redirect=true";
	}
}
