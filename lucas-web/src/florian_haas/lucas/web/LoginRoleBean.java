package florian_haas.lucas.web;

import java.util.*;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.NotBlank;
import org.primefaces.model.DualListModel;

import florian_haas.lucas.business.LoginUserRoleBeanLocal;
import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;
import florian_haas.lucas.security.EnumPermission;
import florian_haas.lucas.util.Utils;
import florian_haas.lucas.validation.*;
import florian_haas.lucas.web.converter.LoginUserRoleConverter;
import florian_haas.lucas.web.util.WebUtils;

@Named
@ViewScoped
public class LoginRoleBean extends BaseBean<ReadOnlyLoginUserRole> {

	public LoginRoleBean() {
		super(BASE_NAME, 3);
	}

	public static final String BASE_NAME = "loginRole";

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
	private ReadOnlyLoginUser searchLoginUserRoleUser = null;

	@NotNull
	private Boolean useSearchLoginUserRoleUser = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.LOGIC)
	private EnumQueryComparator searchLoginUserRoleUserComparator = EnumQueryComparator.EQUAL;

	@NotNull
	private Boolean useSearchLoginUserRolePermissions = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.ARRAY)
	private EnumQueryComparator searchLoginUserRolePermissionsComparator = EnumQueryComparator.MEMBER_OF;

	@Override
	public EnumPermission getFindDynamicPermission() {
		return EnumPermission.LOGIN_ROLE_FIND_DYNAMIC;
	}

	@Override
	public EnumPermission getPrintPermission() {
		return EnumPermission.LOGIN_ROLE_PRINT;
	}

	@Override
	public EnumPermission getExportPermission() {
		return EnumPermission.LOGIN_ROLE_EXPORT;
	}

	@Override
	protected List<? extends ReadOnlyLoginUserRole> searchEntities() {
		return loginUserRoleBean.findLoginUserRoles(searchLoginUserRoleId, searchLoginUserRoleName,
				searchLoginUserRolePermissions != null ? new HashSet<>(searchLoginUserRolePermissions) : null,
				searchLoginUserRoleUser != null ? searchLoginUserRoleUser.getId() : null, useSearchLoginUserRoleId, useSearchLoginUserRoleName,
				useSearchLoginUserRolePermissions, useSearchLoginUserRoleUser, searchLoginUserRoleIdComparator, searchLoginUserRoleNameComparator,
				searchLoginUserRolePermissionsComparator, searchLoginUserRoleUserComparator);
	}

	@Override
	protected ReadOnlyLoginUserRole entityGetter(Long entityId) {
		return loginUserRoleBean.findById(entityId);
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

	public ReadOnlyLoginUser getSearchLoginUserRoleUser() {
		return this.searchLoginUserRoleUser;
	}

	public void setSearchLoginUserRoleUser(ReadOnlyLoginUser searchLoginUserRoleUser) {
		this.searchLoginUserRoleUser = searchLoginUserRoleUser;
	}

	public Boolean getUseSearchLoginUserRoleUser() {
		return this.useSearchLoginUserRoleUser;
	}

	public void setUseSearchLoginUserRoleUser(Boolean useSearchLoginUserRoleUser) {
		this.useSearchLoginUserRoleUser = useSearchLoginUserRoleUser;
	}

	public EnumQueryComparator getSearchLoginUserRoleUserComparator() {
		return this.searchLoginUserRoleUserComparator;
	}

	public void setSearchLoginUserRoleUserComparator(EnumQueryComparator searchLoginUserRoleUserComparator) {
		this.searchLoginUserRoleUserComparator = searchLoginUserRoleUserComparator;
	}

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
			params.add(WebUtils.getAsString(loginUserRoleBean.findById(loginUserRoleBean.newLoginUserRole(createLoginUserRoleDialogName,
					new HashSet<>(createLoginUserRoleDialogPermissionsListModel.getTarget()))), LoginUserRoleConverter.CONVERTER_ID));
			return true;
		}, "lucas.application.loginRoleScreen.createLoginUserRole", (exception, params) -> {
			return WebUtils.getTranslatedMessage("lucas.application.loginRoleScreen.createLoginUserRole.notUnique",
					params.toArray(new Object[params.size()]));
		}, Utils.asList(createLoginUserRoleDialogName));
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
		if (!selectedEntities.isEmpty()) {
			ReadOnlyLoginUserRole tmp = selectedEntities.get(0);
			permissionsDialogSelectedRoleString = WebUtils.getAsString(tmp, LoginUserRoleConverter.CONVERTER_ID);
			permissionsDialogPermissions.clear();
			permissionsDialogPermissions.addAll(loginUserRoleBean.getPermissions(tmp.getId()));
		}
	}

	/*
	 * -------------------- Permissions Dialog End --------------------
	 */

	public void removeLoginUserRole() {
		Iterator<ReadOnlyLoginUserRole> it = selectedEntities.iterator();
		while (it.hasNext()) {
			ReadOnlyLoginUserRole role = it.next();
			WebUtils.executeTask(params -> {
				params.add(WebUtils.getAsString(role, LoginUserRoleConverter.CONVERTER_ID));
				Boolean ret = loginUserRoleBean.removeLoginUserRole(role.getId());
				if (ret) {
					searchResults.remove(role);
					it.remove();
				}
				return ret;
			}, "lucas.application.loginRoleScreen.removeLoginUserRole");
		}
	}

	/*
	 * -------------------- Edit Login User Role Dialog Start --------------------
	 */

	private ReadOnlyLoginUserRole editLoginUserRole = null;

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
		if (!selectedEntities.isEmpty()) {
			editLoginUserRole = selectedEntities.get(0);
			this.editLoginUserRoleDialogName = editLoginUserRole.getName();
			List<String> actualPermissions = new ArrayList<>();
			if (WebUtils.isPermitted(EnumPermission.LOGIN_ROLE_GET_PERMISSIONS)) {
				actualPermissions.addAll(loginUserRoleBean.getPermissions(editLoginUserRole.getId()));
			}
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
			if (WebUtils.isPermitted(EnumPermission.LOGIN_ROLE_SET_NAME)) {
				loginUserRoleBean.setRoleName(id, editLoginUserRoleDialogName);
			}
			if (WebUtils.isPermitted(EnumPermission.LOGIN_ROLE_ADD_PERMISSION, EnumPermission.LOGIN_ROLE_REMOVE_PERMISSION,
					EnumPermission.LOGIN_ROLE_GET_PERMISSIONS)) {
				List<String> permissions = new ArrayList<>(loginUserRoleBean.getPermissions(id));
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
			}
			ReadOnlyLoginUserRole newRole = loginUserRoleBean.findById(id);
			WebUtils.refreshEntities(ReadOnlyLoginUserRole.class, searchResults, selectedEntities, newRole, loginUserRoleBean::findById, true);
			return true;
		}, "lucas.application.loginRoleScreen.editLoginUserRole", (exception, params) -> {
			return WebUtils.getTranslatedMessage("lucas.application.loginRoleScreen.editLoginUserRole.notUnique",
					params.toArray(new Object[params.size()]));
		}, Utils.asList(WebUtils.getAsString(editLoginUserRole, LoginUserRoleConverter.CONVERTER_ID), editLoginUserRoleDialogName));
	}

	/*
	 * -------------------- Edit Login User Role Dialog End --------------------
	 */

}
