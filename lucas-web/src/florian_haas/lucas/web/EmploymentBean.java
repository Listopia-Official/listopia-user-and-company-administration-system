package florian_haas.lucas.web;

import java.util.*;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.*;

import florian_haas.lucas.business.*;
import florian_haas.lucas.database.*;
import florian_haas.lucas.model.*;
import florian_haas.lucas.security.EnumPermission;
import florian_haas.lucas.util.Utils;
import florian_haas.lucas.validation.*;
import florian_haas.lucas.web.converter.*;
import florian_haas.lucas.web.util.WebUtils;

@Named
@ViewScoped
public class EmploymentBean extends BaseBean<ReadOnlyEmployment> {

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
	@Min(0)
	private Long searchEmploymentUserId = 0l;

	@NotNull
	private Boolean useSearchEmploymentUserId = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchEmploymentUserIdComparator = EnumQueryComparator.EQUAL;

	@NotNull
	@Min(0)
	private Long searchEmploymentJobId = 0l;

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

	public EmploymentBean() {
		super("employment", 4);
	}

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

	public Long getSearchEmploymentUserId() {
		return searchEmploymentUserId;
	}

	public void setSearchEmploymentUserId(Long searchEmploymentUserId) {
		this.searchEmploymentUserId = searchEmploymentUserId;
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

	public Long getSearchEmploymentJobId() {
		return searchEmploymentJobId;
	}

	public void setSearchEmploymentJobId(Long searchEmploymentJobId) {
		this.searchEmploymentJobId = searchEmploymentJobId;
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
		return employmentBean.findEmployments(searchEmploymentId, searchEmploymentUserId, searchEmploymentJobId,
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

	@ValidEntityId(entityClass = ReadOnlyUser.class)
	private Long createEmploymentDialogUserId;

	@ValidEntityId(entityClass = ReadOnlyJob.class)
	private Long createEmploymentDialogJobId;

	@NotNull
	private List<@TypeNotNull EnumWorkShift> createEmploymentDialogWorkShifts = new ArrayList<>();

	public Long getCreateEmploymentDialogUserId() {
		return this.createEmploymentDialogUserId;
	}

	public void setCreateEmploymentDialogUserId(Long createEmploymentDialogUserId) {
		this.createEmploymentDialogUserId = createEmploymentDialogUserId;
	}

	public Long getCreateEmploymentDialogJobId() {
		return this.createEmploymentDialogJobId;
	}

	public void setCreateEmploymentDialogJobId(Long createEmploymentDialogJobId) {
		this.createEmploymentDialogJobId = createEmploymentDialogJobId;
	}

	public List<EnumWorkShift> getCreateEmploymentDialogWorkShifts() {
		return this.createEmploymentDialogWorkShifts;
	}

	public void setCreateEmploymentDialogWorkShifts(List<EnumWorkShift> createEmploymentDialogWorkShifts) {
		this.createEmploymentDialogWorkShifts = createEmploymentDialogWorkShifts;
	}

	public String getCreateEmploymentDialogUserFromId() {
		ReadOnlyUser user = createEmploymentDialogUserId != null
				? entityBean.exists(createEmploymentDialogUserId, ReadOnlyUser.class) ? userBean.findById(createEmploymentDialogUserId) : null : null;
		return WebUtils.getAsString(user, UserConverter.CONVERTER_ID);
	}

	public String getCreateEmploymentDialogJobFromId() {
		ReadOnlyJob job = createEmploymentDialogJobId != null
				? entityBean.exists(createEmploymentDialogJobId, ReadOnlyJob.class) ? jobBean.findById(createEmploymentDialogJobId) : null : null;
		return WebUtils.getAsString(job, JobConverter.CONVERTER_ID);
	}

	public void initCreateEmploymentDialog() {
		createEmploymentDialogUserId = null;
		createEmploymentDialogJobId = null;
		createEmploymentDialogWorkShifts.clear();
	}

	public void createEmployment() {
		WebUtils.executeTask((params) -> {
			params.add(WebUtils.getAsString(
					employmentBean.findById(employmentBean.createEmployment(createEmploymentDialogUserId, createEmploymentDialogJobId,
							createEmploymentDialogWorkShifts.isEmpty() ? null : new HashSet<>(createEmploymentDialogWorkShifts))),
					EmploymentConverter.CONVERTER_ID));
			return true;
		}, "lucas.application.employmentScreen.createEmployment.message", Utils.asList(getCreateEmploymentDialogUserFromId()));
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
}
