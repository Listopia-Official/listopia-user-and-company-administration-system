package florian_haas.lucas.web;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.NotBlank;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.*;

import florian_haas.lucas.business.LoginUserRoleBeanLocal;
import florian_haas.lucas.database.*;
import florian_haas.lucas.database.validation.QueryComparator;
import florian_haas.lucas.model.LoginUserRole;
import florian_haas.lucas.security.EnumPermission;
import florian_haas.lucas.util.WebUtils;
import florian_haas.lucas.util.validation.*;

@Named
@ViewScoped
public class LoginRoleBean implements Serializable {

	private static final long serialVersionUID = 5788543415266420741L;

	@EJB
	private LoginUserRoleBeanLocal loginUserRoleBean;

	@NotNull
	@Min(0)
	private Long searchLoginUserRoleId = 0l;

	@NotNull
	private Boolean useSearchLoginUserRoleId = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchLoginUserRoleIdComparator = EnumQueryComparator.EQUAL;

	@NotNull
	private String searchLoginUserRoleName = "";

	@NotNull
	private Boolean useSearchLoginUserRoleName = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.TEXT)
	private EnumQueryComparator searchLoginUserRoleNameComparator = EnumQueryComparator.EQUAL;

	private List<@TypeNotNull @NotBlankString String> searchLoginUserRolePermissions = new ArrayList<>();

	@NotNull
	private Boolean useSearchLoginUserRolePermissions = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.ARRAY)
	private EnumQueryComparator searchLoginUserRolePermissionsComparator = EnumQueryComparator.MEMBER_OF;

	private List<LoginUserRole> searchLoginRoleResults = new ArrayList<>();

	private List<LoginUserRole> selectedLoginRoles = new ArrayList<>();

	private List<Boolean> resultsDatatableColumns = Arrays.asList(true, true, true);

	public List<LoginUserRole> getSearchLoginRoleResults() {
		return searchLoginRoleResults;
	}

	public void setSearchLoginRoleResults(List<LoginUserRole> searchLoginRoleResults) {
		this.searchLoginRoleResults = searchLoginRoleResults;
	}

	public List<LoginUserRole> getSelectedLoginRoles() {
		return selectedLoginRoles;
	}

	public void setSelectedLoginRoles(List<LoginUserRole> selectedLoginRoles) {
		this.selectedLoginRoles = selectedLoginRoles;
	}

	public List<Boolean> getResultsDatatableColumns() {
		return this.resultsDatatableColumns;
	}

	public void onToggle(ToggleEvent e) {
		resultsDatatableColumns.set((Integer) e.getData() - 1, e.getVisibility() == Visibility.VISIBLE);
	}

	public void searchLoginRoles() {
		WebUtils.executeTask((params) -> {
			List<LoginUserRole> results = loginUserRoleBean.findLoginUserRoles(searchLoginUserRoleId, searchLoginUserRoleName,
					searchLoginUserRolePermissions != null ? new HashSet<>(searchLoginUserRolePermissions) : null, useSearchLoginUserRoleId,
					useSearchLoginUserRoleName, useSearchLoginUserRolePermissions, searchLoginUserRoleIdComparator, searchLoginUserRoleNameComparator,
					searchLoginUserRolePermissionsComparator);
			searchLoginRoleResults.clear();
			selectedLoginRoles.clear();
			searchLoginRoleResults.addAll(results);
			params.add(results.size());
			return true;
		}, "lucas.application.loginRoleScreen.searchLoginUser.message.success", null,
				"lucas.application.loginRoleScreen.searchLoginUser.message.fail");
	}

	public void refreshLoginRoles() {
		WebUtils.executeTask((params) -> {
			WebUtils.refreshEntities(LoginUserRole.class, searchLoginRoleResults, selectedLoginRoles, loginUserRoleBean::findLoginUserRoleById);
			params.add(searchLoginRoleResults.size());
			return true;
		}, "lucas.application.loginRoleScreen.refreshLoginUsers.message.success", null,
				"lucas.application.loginRoleScreen.refreshLoginUsers.message.fail");
	}

	public Long getSearchLoginUserRoleId() {
		return searchLoginUserRoleId;
	}

	public void setSearchLoginUserRoleId(Long searchLoginUserRoleId) {
		this.searchLoginUserRoleId = searchLoginUserRoleId;
	}

	public String getSearchLoginUserRoleName() {
		return searchLoginUserRoleName;
	}

	public void setSearchLoginUserRoleName(String searchLoginUserRoleName) {
		this.searchLoginUserRoleName = searchLoginUserRoleName;
	}

	public List<String> getSearchLoginUserRolePermissions() {
		return this.searchLoginUserRolePermissions;
	}

	public void setSearchLoginUserRolePermissions(List<String> searchLoginUserRolePermissions) {
		this.searchLoginUserRolePermissions = searchLoginUserRolePermissions;
	}

	public Boolean getUseSearchLoginUserRoleId() {
		return this.useSearchLoginUserRoleId;
	}

	public void setUseSearchLoginUserRoleId(Boolean useSearchLoginUserRoleId) {
		this.useSearchLoginUserRoleId = useSearchLoginUserRoleId;
	}

	public Boolean getUseSearchLoginUserRoleName() {
		return this.useSearchLoginUserRoleName;
	}

	public void setUseSearchLoginUserRoleName(Boolean useSearchLoginUserRoleName) {
		this.useSearchLoginUserRoleName = useSearchLoginUserRoleName;
	}

	public Boolean getUseSearchLoginUserRolePermissions() {
		return this.useSearchLoginUserRolePermissions;
	}

	public void setUseSearchLoginUserRolePermissions(Boolean useSearchLoginUserRolePermissions) {
		this.useSearchLoginUserRolePermissions = useSearchLoginUserRolePermissions;
	}

	public EnumQueryComparator getSearchLoginUserRoleIdComparator() {
		return this.searchLoginUserRoleIdComparator;
	}

	public void setSearchLoginUserRoleIdComparator(EnumQueryComparator searchLoginUserRoleIdComparator) {
		this.searchLoginUserRoleIdComparator = searchLoginUserRoleIdComparator;
	}

	public EnumQueryComparator getSearchLoginUserRoleNameComparator() {
		return this.searchLoginUserRoleNameComparator;
	}

	public void setSearchLoginUserRoleNameComparator(EnumQueryComparator searchLoginUserRoleNameComparator) {
		this.searchLoginUserRoleNameComparator = searchLoginUserRoleNameComparator;
	}

	public EnumQueryComparator getSearchLoginUserRolePermissionsComparator() {
		return this.searchLoginUserRolePermissionsComparator;
	}

	public void setSearchLoginUserRolePermissionsComparator(EnumQueryComparator searchLoginUserRolePermissionsComparator) {
		this.searchLoginUserRolePermissionsComparator = searchLoginUserRolePermissionsComparator;
	}

	/*
	 * -------------------- Create Login User Role Dialog Start --------------------
	 */

	@NotBlank
	private String createLoginUserRoleDialogName = null;

	private DualListModel<String> createLoginUserRoleDialogPermissionsListModel = new DualListModel<>();

	public String getCreateLoginUserRoleDialogName() {
		return this.createLoginUserRoleDialogName;
	}

	public void setCreateLoginUserRoleDialogName(String createLoginUserRoleDialogName) {
		this.createLoginUserRoleDialogName = createLoginUserRoleDialogName;
	}

	public DualListModel<String> getCreateLoginUserRoleDialogPermissionsListModel() {
		return this.createLoginUserRoleDialogPermissionsListModel;
	}

	public void setCreateLoginUserRoleDialogPermissionsListModel(DualListModel<String> createLoginUserRoleDialogPermissionsListModel) {
		this.createLoginUserRoleDialogPermissionsListModel = createLoginUserRoleDialogPermissionsListModel;
	}

	public void initCreateLoginUserRoleDialog() {
		this.createLoginUserRoleDialogName = null;
		List<String> permissions = new ArrayList<>();
		for (EnumPermission value : EnumPermission.values()) {
			permissions.add(value.getPermissionString());
		}
		this.createLoginUserRoleDialogPermissionsListModel = new DualListModel<>(permissions, new ArrayList<String>());
	}

	public void createLoginRole() {
		WebUtils.executeTask(params -> {
			params.add(
					WebUtils.getAsString(
							loginUserRoleBean.findLoginUserRoleById(loginUserRoleBean.newLoginUserRole(createLoginUserRoleDialogName,
									new HashSet<>(createLoginUserRoleDialogPermissionsListModel.getTarget()))),
							"lucas:loginUserRoleStringConverter"));
			return true;
		}, "lucas.application.loginRoleScreen.createLoginUserRole.success", null, "lucas.application.loginRoleScreen.createLoginUserRole.fail");
	}

	/*
	 * -------------------- Create Login User Role Dialog End --------------------
	 */

	/*
	 * -------------------- Permissions Dialog Start --------------------
	 */

	private String permissionsDialogSelectedRoleString = null;

	private List<String> permissionsDialogPermissions = new ArrayList<>();

	public String getPermissionsDialogSelectedRoleString() {
		return this.permissionsDialogSelectedRoleString;
	}

	public List<String> getPermissionsDialogPermissions() {
		return this.permissionsDialogPermissions;
	}

	public void initPermissionsDialog() {
		if (!selectedLoginRoles.isEmpty()) {
			LoginUserRole tmp = selectedLoginRoles.get(0);
			permissionsDialogSelectedRoleString = WebUtils.getAsString(tmp, "lucas:loginUserRoleStringConverter");
			permissionsDialogPermissions.clear();
			permissionsDialogPermissions.addAll(loginUserRoleBean.getPermissions(tmp.getId()));
		}
	}

	/*
	 * -------------------- Permissions Dialog End --------------------
	 */

	public void removeLoginUserRole() {
		Iterator<LoginUserRole> it = selectedLoginRoles.iterator();
		while (it.hasNext()) {
			LoginUserRole role = it.next();
			WebUtils.executeTask(params -> {
				params.add(WebUtils.getAsString(role, "lucas:loginUserRoleStringConverter"));
				Boolean ret = loginUserRoleBean.removeLoginUserRole(role.getId());
				if (ret) {
					searchLoginRoleResults.remove(role);
					it.remove();
				}
				return ret;
			}, "lucas.application.loginRoleScreen.removeLoginUserRole.success", "lucas.application.loginRoleScreen.removeLoginUserRole.warn",
					"lucas.application.loginRoleScreen.removeLoginUserRole.fail");
		}
	}

	/*
	 * -------------------- Edit Login User Role Dialog Start --------------------
	 */

	private LoginUserRole editLoginUserRole = null;

	@NotBlank
	private String editLoginUserRoleDialogName = null;

	private DualListModel<String> editLoginUserRoleDialogPermissionsListModel = new DualListModel<>();

	public String getEditLoginUserRoleDialogName() {
		return this.editLoginUserRoleDialogName;
	}

	public void setEditLoginUserRoleDialogName(String editLoginUserRoleDialogName) {
		this.editLoginUserRoleDialogName = editLoginUserRoleDialogName;
	}

	public DualListModel<String> getEditLoginUserRoleDialogPermissionsListModel() {
		return this.editLoginUserRoleDialogPermissionsListModel;
	}

	public void setEditLoginUserRoleDialogPermissionsListModel(DualListModel<String> editLoginUserRoleDialogPermissionsListModel) {
		this.editLoginUserRoleDialogPermissionsListModel = editLoginUserRoleDialogPermissionsListModel;
	}

	public void initEditLoginUserRoleDialog() {
		if (!selectedLoginRoles.isEmpty()) {
			editLoginUserRole = selectedLoginRoles.get(0);
			this.editLoginUserRoleDialogName = editLoginUserRole.getName();
			List<String> actualPermissions = new ArrayList<>();
			actualPermissions.addAll(loginUserRoleBean.getPermissions(editLoginUserRole.getId()));
			List<String> permissions = new ArrayList<>();
			for (EnumPermission value : EnumPermission.values()) {
				if (!actualPermissions.contains(value.getPermissionString())) {
					permissions.add(value.getPermissionString());
				}
			}
			this.editLoginUserRoleDialogPermissionsListModel = new DualListModel<>(permissions, actualPermissions);
		}
	}

	public void editLoginRole() {
		WebUtils.executeTask(params -> {
			Long id = editLoginUserRole.getId();
			List<String> permissions = new ArrayList<>(loginUserRoleBean.getPermissions(id));
			loginUserRoleBean.setRoleName(id, editLoginUserRoleDialogName);
			editLoginUserRoleDialogPermissionsListModel.getTarget().forEach(permission -> {
				if (!permissions.contains(permission)) {
					loginUserRoleBean.addPermission(id, permission);
				}
			});
			permissions.forEach(permission -> {
				if (!editLoginUserRoleDialogPermissionsListModel.getTarget().contains(permission)) {
					loginUserRoleBean.removePermission(id, permission);
				}
			});
			LoginUserRole newRole = loginUserRoleBean.findLoginUserRoleById(id);
			params.add(WebUtils.getAsString(editLoginUserRole, "lucas:loginUserRoleStringConverter"));
			Collections.replaceAll(searchLoginRoleResults, editLoginUserRole, newRole);
			Collections.replaceAll(selectedLoginRoles, editLoginUserRole, newRole);
			return true;
		}, "lucas.application.loginRoleScreen.editLoginUserRole.success", null, "lucas.application.loginRoleScreen.editLoginUserRole.fail");
	}

	/*
	 * -------------------- Edit Login User Role Dialog End --------------------
	 */

}
