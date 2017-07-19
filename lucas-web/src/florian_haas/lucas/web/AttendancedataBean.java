package florian_haas.lucas.web;

import java.util.*;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.*;

import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

import florian_haas.lucas.business.AttendanceBeanLocal;
import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;
import florian_haas.lucas.security.EnumPermission;
import florian_haas.lucas.validation.QueryComparator;
import florian_haas.lucas.web.converter.UserConverter;
import florian_haas.lucas.web.util.WebUtils;

@Named
@ViewScoped
public class AttendancedataBean extends BaseBean<ReadOnlyAttendancedata> {

	private static final long serialVersionUID = -4054361446131864008L;

	@EJB
	private AttendanceBeanLocal attendanceBean;

	public AttendancedataBean() {
		super(BASE_NAME, 4);
	}

	public static final String BASE_NAME = "attendancedata";

	@NotNull
	@Min(0)
	private Long searchAttendancedataId = 0l;

	@NotNull
	private Boolean useSearchAttendancedataId = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchAttendancedataIdComparator = EnumQueryComparator.EQUAL;

	@NotNull
	private ReadOnlyUser searchAttendancedataUser = null;

	@NotNull
	private Boolean useSearchAttendancedataUser = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchAttendancedataUserComparator = EnumQueryComparator.EQUAL;

	@NotNull
	private Boolean searchAttendancedataIsPresent = Boolean.FALSE;

	@NotNull
	private Boolean useSearchAttendancedataIsPresent = Boolean.FALSE;

	@NotNull
	@Min(0)
	private Long searchAttendancedataTimePresent = 0l;

	@NotNull
	private Boolean useSearchAttendancedataTimePresent = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchAttendancedataTimePresentComparator = EnumQueryComparator.EQUAL;

	public Long getSearchAttendancedataId() {
		return this.searchAttendancedataId;
	}

	public void setSearchAttendancedataId(Long searchAttendancedataId) {
		this.searchAttendancedataId = searchAttendancedataId;
	}

	public Boolean getUseSearchAttendancedataId() {
		return this.useSearchAttendancedataId;
	}

	public void setUseSearchAttendancedataId(Boolean useSearchAttendancedataId) {
		this.useSearchAttendancedataId = useSearchAttendancedataId;
	}

	public EnumQueryComparator getSearchAttendancedataIdComparator() {
		return this.searchAttendancedataIdComparator;
	}

	public void setSearchAttendancedataIdComparator(EnumQueryComparator searchAttendancedataIdComparator) {
		this.searchAttendancedataIdComparator = searchAttendancedataIdComparator;
	}

	public Boolean getSearchAttendancedataIsPresent() {
		return this.searchAttendancedataIsPresent;
	}

	public void setSearchAttendancedataIsPresent(Boolean searchAttendancedataIsPresent) {
		this.searchAttendancedataIsPresent = searchAttendancedataIsPresent;
	}

	public Boolean getUseSearchAttendancedataIsPresent() {
		return this.useSearchAttendancedataIsPresent;
	}

	public void setUseSearchAttendancedataIsPresent(Boolean useSearchAttendancedataIsPresent) {
		this.useSearchAttendancedataIsPresent = useSearchAttendancedataIsPresent;
	}

	public Long getSearchAttendancedataTimePresent() {
		return this.searchAttendancedataTimePresent;
	}

	public void setSearchAttendancedataTimePresent(Long searchAttendancedataTimePresent) {
		this.searchAttendancedataTimePresent = searchAttendancedataTimePresent;
	}

	public Boolean getUseSearchAttendancedataTimePresent() {
		return this.useSearchAttendancedataTimePresent;
	}

	public void setUseSearchAttendancedataTimePresent(Boolean useSearchAttendancedataTimePresent) {
		this.useSearchAttendancedataTimePresent = useSearchAttendancedataTimePresent;
	}

	public EnumQueryComparator getSearchAttendancedataTimePresentComparator() {
		return this.searchAttendancedataTimePresentComparator;
	}

	public void setSearchAttendancedataTimePresentComparator(EnumQueryComparator searchAttendancedataTimePresentComparator) {
		this.searchAttendancedataTimePresentComparator = searchAttendancedataTimePresentComparator;
	}

	public ReadOnlyUser getSearchAttendancedataUser() {
		return this.searchAttendancedataUser;
	}

	public void setSearchAttendancedataUser(ReadOnlyUser searchAttendancedataUser) {
		this.searchAttendancedataUser = searchAttendancedataUser;
	}

	public Boolean getUseSearchAttendancedataUser() {
		return this.useSearchAttendancedataUser;
	}

	public void setUseSearchAttendancedataUser(Boolean useSearchAttendancedataUser) {
		this.useSearchAttendancedataUser = useSearchAttendancedataUser;
	}

	public EnumQueryComparator getSearchAttendancedataUserComparator() {
		return this.searchAttendancedataUserComparator;
	}

	public void setSearchAttendancedataUserComparator(EnumQueryComparator searchAttendancedataUserComparator) {
		this.searchAttendancedataUserComparator = searchAttendancedataUserComparator;
	}

	@Override
	public EnumPermission getFindDynamicPermission() {
		return EnumPermission.ATTENDANCE_FIND_DYNAMIC;
	}

	@Override
	public EnumPermission getPrintPermission() {
		return EnumPermission.ATTENDANCE_PRINT;
	}

	@Override
	public EnumPermission getExportPermission() {
		return EnumPermission.ATTENDANCE_EXPORT;
	}

	@Override
	protected List<? extends ReadOnlyAttendancedata> searchEntities() {
		return attendanceBean.findAttendancedata(searchAttendancedataId, searchAttendancedataUser != null ? searchAttendancedataUser.getId() : 0l,
				searchAttendancedataIsPresent, searchAttendancedataTimePresent, useSearchAttendancedataId, useSearchAttendancedataUser,
				useSearchAttendancedataIsPresent, useSearchAttendancedataTimePresent, searchAttendancedataIdComparator,
				searchAttendancedataUserComparator, searchAttendancedataTimePresentComparator);
	}

	@Override
	protected ReadOnlyAttendancedata entityGetter(Long entityId) {
		return attendanceBean.findById(entityId);
	}

	/*
	 * -------------------- Show Activity Logs Dialog Start --------------------
	 */

	private ReadOnlyAttendancedata showAttendanceActivityLogsDialogSelectedAttendancedata = null;

	private ReadOnlyUser showAttendanceActivityLogsDialogSelectedUser = null;

	private List<ReadOnlyAttendanceActivityLog> showAttendanceActivityLogsDialogLogs = new ArrayList<>();

	private List<Boolean> showAttendanceActivityLogsDialogLogsColumns = Arrays.asList(true, true, true, true, true, true, true, true, true);

	public ReadOnlyUser getShowAttendanceActivityLogsDialogSelectedUser() {
		return this.showAttendanceActivityLogsDialogSelectedUser;
	}

	public List<ReadOnlyAttendanceActivityLog> getShowAttendanceActivityLogsDialogLogs() {
		return this.showAttendanceActivityLogsDialogLogs;
	}

	public List<Boolean> getShowAttendanceActivityLogsDialogLogsColumns() {
		return this.showAttendanceActivityLogsDialogLogsColumns;
	}

	public void initShowAttendanceActivityLogsDialog() {
		if (!selectedEntities.isEmpty()) {
			showAttendanceActivityLogsDialogSelectedAttendancedata = selectedEntities.get(0);
			showAttendanceActivityLogsDialogSelectedUser = showAttendanceActivityLogsDialogSelectedAttendancedata.getUser();
			showAttendanceActivityLogsDialogLogs.clear();
			showAttendanceActivityLogsDialogLogs
					.addAll(attendanceBean.getAttendanceActivityLogs(showAttendanceActivityLogsDialogSelectedAttendancedata.getId()));
			Collections.fill(showAttendanceActivityLogsDialogLogsColumns, true);
		}
	}

	public void attendanceActivityLogDialogOnToggle(ToggleEvent e) {
		showAttendanceActivityLogsDialogLogsColumns.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
	}

	/*
	 * -------------------- Show Activity Logs Dialog End --------------------
	 */

	public void togglePresence() {
		selectedEntities.forEach(attendancedata -> {
			WebUtils.executeTask((params) -> {
				params.add(WebUtils.getAsString(attendancedata.getUser(), UserConverter.CONVERTER_ID));
				Boolean entered = attendanceBean.scanByAttendancedata(attendancedata.getId());
				params.add(WebUtils.getTranslatedMessage("lucas.application.attendancedataScreen.scan.message.success" + (entered ? "1" : "2")));
				return true;
			}, "lucas.application.attendancedataScreen.scan.message");
		});
		WebUtils.refreshEntities(ReadOnlyAttendancedata.class, searchResults, selectedEntities, attendanceBean::findById, Boolean.TRUE);
	}

	public void reset() {
		selectedEntities.forEach(attendancedata -> {
			WebUtils.executeTask((params) -> {
				params.add(WebUtils.getAsString(attendancedata.getUser(), UserConverter.CONVERTER_ID));
				attendanceBean.reset(attendancedata.getId());
				return true;
			}, "lucas.application.attendancedataScreen.reset.message");
		});
		WebUtils.refreshEntities(ReadOnlyAttendancedata.class, searchResults, selectedEntities, attendanceBean::findById, Boolean.TRUE);
	}

	public String showReferencedUsers() {
		navigateToBeanSingle(UserBean.BASE_NAME, (attendancedata) -> attendancedata.getUser().getId());
		return "/users?faces-redirect=true";
	}

}
