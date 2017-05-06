package florian_haas.lucas.web;

import java.util.*;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.NotBlank;
import org.primefaces.model.DualListModel;

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
public class LoginUserBean extends BaseBean<ReadOnlyLoginUser> {

	public LoginUserBean() {
		super("loginUser", 4);
	}

	private static final long serialVersionUID = -3674051794597830546L;

	@EJB
	private LoginBeanLocal loginBean;

	@EJB
	private LoginUserRoleBeanLocal loginUserRoleBean;

	@EJB
	private EntityBeanLocal entityBean;

	@EJB
	private UserBeanLocal userBean;

	@NotNull
	@Min(0)
	private Long searchLoginUserId = 0l;

	@NotNull
	private Boolean useSearchLoginUserId = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchLoginUserIdComparator = EnumQueryComparator.EQUAL;

	@NotNull
	private String searchLoginUserUsername = "";

	@NotNull
	private Boolean useSearchLoginUserUsername = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.TEXT)
	private EnumQueryComparator searchLoginUserUsernameComparator = EnumQueryComparator.EQUAL;

	@Min(0)
	private Long searchLoginUserUserId = null;

	@NotNull
	private Boolean useSearchLoginUserUserId = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchLoginUserUserIdComparator = EnumQueryComparator.EQUAL;

	private List<@TypeNotNull @TypeMin(0) Long> searchLoginUserRoles = new ArrayList<>();

	@NotNull
	private Boolean useSearchLoginUserRoles = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.LOGIC)
	private EnumQueryComparator searchLoginUserRolesComparator = EnumQueryComparator.EQUAL;

	@Override
	public EnumPermission getFindDynamicPermission() {
		return EnumPermission.LOGIN_USER_FIND_DYNAMIC;
	}

	@Override
	public EnumPermission getPrintPermission() {
		return EnumPermission.LOGIN_USER_PRINT;
	}

	@Override
	public EnumPermission getExportPermission() {
		return EnumPermission.LOGIN_USER_EXPORT;
	}

	@Override
	protected List<? extends ReadOnlyLoginUser> searchEntities() {
		return loginBean.findLoginUsers(searchLoginUserId, searchLoginUserUsername, searchLoginUserUserId, searchLoginUserRoles, useSearchLoginUserId,
				useSearchLoginUserUsername, useSearchLoginUserUserId, useSearchLoginUserRoles, searchLoginUserIdComparator,
				searchLoginUserUsernameComparator, searchLoginUserUserIdComparator, searchLoginUserRolesComparator);
	}

	@Override
	protected ReadOnlyLoginUser entityGetter(Long entityId) {
		return loginBean.findLoginUserById(entityId);
	}

	public Long getSearchLoginUserId() {
		return searchLoginUserId;
	}

	public void setSearchLoginUserId(Long searchLoginUserId) {
		this.searchLoginUserId = searchLoginUserId;
	}

	public Boolean getUseSearchLoginUserId() {
		return useSearchLoginUserId;
	}

	public void setUseSearchLoginUserId(Boolean useSearchLoginUserId) {
		this.useSearchLoginUserId = useSearchLoginUserId;
	}

	public EnumQueryComparator getSearchLoginUserIdComparator() {
		return searchLoginUserIdComparator;
	}

	public void setSearchLoginUserIdComparator(EnumQueryComparator searchLoginUserIdComparator) {
		this.searchLoginUserIdComparator = searchLoginUserIdComparator;
	}

	public String getSearchLoginUserUsername() {
		return searchLoginUserUsername;
	}

	public void setSearchLoginUserUsername(String searchLoginUserUsername) {
		this.searchLoginUserUsername = searchLoginUserUsername;
	}

	public Boolean getUseSearchLoginUserUsername() {
		return useSearchLoginUserUsername;
	}

	public void setUseSearchLoginUserUsername(Boolean useSearchLoginUserUsername) {
		this.useSearchLoginUserUsername = useSearchLoginUserUsername;
	}

	public EnumQueryComparator getSearchLoginUserUsernameComparator() {
		return searchLoginUserUsernameComparator;
	}

	public void setSearchLoginUserUsernameComparator(EnumQueryComparator searchLoginUserUsernameComparator) {
		this.searchLoginUserUsernameComparator = searchLoginUserUsernameComparator;
	}

	public Long getSearchLoginUserUserId() {
		return searchLoginUserUserId;
	}

	public void setSearchLoginUserUserId(Long searchLoginUserUserId) {
		this.searchLoginUserUserId = searchLoginUserUserId;
	}

	public Boolean getUseSearchLoginUserUserId() {
		return useSearchLoginUserUserId;
	}

	public void setUseSearchLoginUserUserId(Boolean useSearchLoginUserUserId) {
		this.useSearchLoginUserUserId = useSearchLoginUserUserId;
	}

	public EnumQueryComparator getSearchLoginUserUserIdComparator() {
		return searchLoginUserUserIdComparator;
	}

	public void setSearchLoginUserUserIdComparator(EnumQueryComparator searchLoginUserUserIdComparator) {
		this.searchLoginUserUserIdComparator = searchLoginUserUserIdComparator;
	}

	public List<@TypeNotNull @TypeMin(0) Long> getSearchLoginUserRoles() {
		return searchLoginUserRoles;
	}

	public void setSearchLoginUserRoles(List<@TypeNotNull @TypeMin(0) Long> searchLoginUserRoles) {
		this.searchLoginUserRoles = searchLoginUserRoles;
	}

	public Boolean getUseSearchLoginUserRoles() {
		return useSearchLoginUserRoles;
	}

	public void setUseSearchLoginUserRoles(Boolean useSearchLoginUserRoles) {
		this.useSearchLoginUserRoles = useSearchLoginUserRoles;
	}

	public EnumQueryComparator getSearchLoginUserRolesComparator() {
		return searchLoginUserRolesComparator;
	}

	public void setSearchLoginUserRolesComparator(EnumQueryComparator searchLoginUserRolesComparator) {
		this.searchLoginUserRolesComparator = searchLoginUserRolesComparator;
	}

	/*
	 * -------------------- Create Default Login User Dialog Start --------------------
	 */

	@NotBlank
	private String createDefaultLoginUserUsername = null;

	@ValidUnhashedPassword
	private char[] createDefaultLoginUserPassword = null;

	private DualListModel<ReadOnlyLoginUserRole> createDefaultLoginUserRolesListModel;

	public String getCreateDefaultLoginUserUsername() {
		return createDefaultLoginUserUsername;
	}

	public void setCreateDefaultLoginUserUsername(String createDefaultLoginUserUsername) {
		this.createDefaultLoginUserUsername = createDefaultLoginUserUsername;
	}

	public char[] getCreateDefaultLoginUserPassword() {
		return createDefaultLoginUserPassword;
	}

	public void setCreateDefaultLoginUserPassword(char[] createDefaultLoginUserPassword) {
		this.createDefaultLoginUserPassword = createDefaultLoginUserPassword;
	}

	public DualListModel<ReadOnlyLoginUserRole> getCreateDefaultLoginUserRolesListModel() {
		return createDefaultLoginUserRolesListModel;
	}

	public void setCreateDefaultLoginUserRolesListModel(DualListModel<ReadOnlyLoginUserRole> createDefaultLoginUserRolesListModel) {
		this.createDefaultLoginUserRolesListModel = createDefaultLoginUserRolesListModel;
	}

	public void initCreateDefaultLoginUserDialog() {
		createDefaultLoginUserUsername = null;
		createDefaultLoginUserPassword = null;
		createDefaultLoginUserRolesListModel = new DualListModel<>(new ArrayList<>(loginUserRoleBean.findAll()),
				new ArrayList<ReadOnlyLoginUserRole>());
	}

	public void createDefaultLoginUser() {
		WebUtils.executeTask(params -> {
			List<Long> ids = new ArrayList<>();
			createDefaultLoginUserRolesListModel.getTarget().forEach(role -> ids.add(role.getId()));
			params.add(WebUtils.getAsString(
					loginBean.findLoginUserById(loginBean.newLoginUser(createDefaultLoginUserUsername, createDefaultLoginUserPassword, ids)),
					LoginUserConverter.CONVERTER_ID));
			Arrays.fill(createDefaultLoginUserPassword, 'c');
			return true;
		}, "lucas.application.loginUserScreen.createDefaultLoginUser", (exception, params) -> {
			return WebUtils.getTranslatedMessage("lucas.application.loginUserScreen.createDefaultLoginUser.notUniqueUsername",
					params.toArray(new Object[params.size()]));
		}, Utils.asList(createDefaultLoginUserUsername));
	}

	/*
	 * -------------------- Create Default Login User Dialog End --------------------
	 */

	/*
	 * -------------------- Create Bound Login User Dialog Start --------------------
	 */

	@ValidEntityId(entityClass = ReadOnlyUser.class)
	private Long createBoundLoginUserBoundUser = null;

	@ValidUnhashedPassword
	private char[] createBoundLoginUserPassword = null;

	private DualListModel<ReadOnlyLoginUserRole> createBoundLoginUserRolesListModel;

	public Long getCreateBoundLoginUserBoundUser() {
		return createBoundLoginUserBoundUser;
	}

	public void setCreateBoundLoginUserBoundUser(Long createBoundLoginUserBoundUser) {
		this.createBoundLoginUserBoundUser = createBoundLoginUserBoundUser;
	}

	public char[] getCreateBoundLoginUserPassword() {
		return createBoundLoginUserPassword;
	}

	public void setCreateBoundLoginUserPassword(char[] createBoundLoginUserPassword) {
		this.createBoundLoginUserPassword = createBoundLoginUserPassword;
	}

	public DualListModel<ReadOnlyLoginUserRole> getCreateBoundLoginUserRolesListModel() {
		return createBoundLoginUserRolesListModel;
	}

	public void setCreateBoundLoginUserRolesListModel(DualListModel<ReadOnlyLoginUserRole> createBoundLoginUserRolesListModel) {
		this.createBoundLoginUserRolesListModel = createBoundLoginUserRolesListModel;
	}

	public String getCreateBoundLoginUserBoundUserFromId() {
		ReadOnlyUser user = createBoundLoginUserBoundUser != null
				? entityBean.exists(createBoundLoginUserBoundUser, ReadOnlyUser.class) ? userBean.findById(createBoundLoginUserBoundUser) : null
				: null;
		return WebUtils.getAsString(user, UserConverter.CONVERTER_ID);
	}

	public void initCreateBoundLoginUserDialog() {
		createBoundLoginUserBoundUser = null;
		createBoundLoginUserPassword = null;
		createBoundLoginUserRolesListModel = new DualListModel<>(new ArrayList<>(loginUserRoleBean.findAll()),
				new ArrayList<ReadOnlyLoginUserRole>());
	}

	public void createBoundLoginUser() {
		WebUtils.executeTask(params -> {
			List<Long> ids = new ArrayList<>();
			createBoundLoginUserRolesListModel.getTarget().forEach(role -> ids.add(role.getId()));
			params.add(WebUtils.getAsString(
					loginBean.findLoginUserById(loginBean.newLoginUser(createBoundLoginUserBoundUser, createBoundLoginUserPassword, ids)),
					LoginUserConverter.CONVERTER_ID));
			Arrays.fill(createBoundLoginUserPassword, 'c');
			return true;
		}, "lucas.application.loginUserScreen.createBoundLoginUser", (exception, params) -> {
			return WebUtils.getTranslatedMessage(
					(exception.getMark().equals(florian_haas.lucas.business.LoginBeanLocal.USER_NOT_UNIQUE_EXCEPTION_MARKER)
							? "lucas.application.loginUserScreen.createBoundLoginUser.notUniqueUser"
							: "lucas.application.loginUserScreen.createBoundLoginUser.notUniqueUsername"),
					params.toArray(new Object[params.size()]));
		}, Utils.asList(WebUtils.getAsString(userBean.findById(createBoundLoginUserBoundUser), UserConverter.CONVERTER_ID)));
	}

	/*
	 * -------------------- Create Bound Login User Dialog End --------------------
	 */

	/*
	 * -------------------- Edit Login User Dialog Start --------------------
	 */

	private ReadOnlyLoginUser editLoginUserSelectedUser = null;

	@NotBlank
	private String editLoginUserUsername = null;

	private DualListModel<ReadOnlyLoginUserRole> editLoginUserRolesListModel;

	public ReadOnlyLoginUser getEditLoginUserSelectedUser() {
		return this.editLoginUserSelectedUser;
	}

	public String getEditLoginUserUsername() {
		return this.editLoginUserUsername;
	}

	public void setEditLoginUserUsername(String editLoginUserUsername) {
		this.editLoginUserUsername = editLoginUserUsername;
	}

	public DualListModel<ReadOnlyLoginUserRole> getEditLoginUserRolesListModel() {
		return this.editLoginUserRolesListModel;
	}

	public void setEditLoginUserRolesListModel(DualListModel<ReadOnlyLoginUserRole> editLoginUserRolesListModel) {
		this.editLoginUserRolesListModel = editLoginUserRolesListModel;
	}

	public Boolean getEditLoginUserIsDefaultLoginUser() {
		return editLoginUserSelectedUser.getUser() == null;
	}

	public void initEditLoginUserDialog() {
		if (!selectedEntities.isEmpty()) {
			editLoginUserSelectedUser = selectedEntities.get(0);
			editLoginUserUsername = editLoginUserSelectedUser.getUsername();
			if (WebUtils.isPermitted(EnumPermission.LOGIN_USER_GET_ROLES, EnumPermission.LOGIN_USER_ADD_ROLE,
					EnumPermission.LOGIN_USER_REMOVE_ROLE)) {
				List<ReadOnlyLoginUserRole> rolesOfUser = new ArrayList<>(editLoginUserSelectedUser.getRoles());
				List<ReadOnlyLoginUserRole> roles = new ArrayList<>();
				List<? extends ReadOnlyLoginUserRole> allRoles = loginUserRoleBean.findAll();
				allRoles.forEach(role -> {
					if (!rolesOfUser.contains(role)) {
						roles.add(role);
					}
				});
				editLoginUserRolesListModel = new DualListModel<>(roles, rolesOfUser);
			} else {
				editLoginUserRolesListModel = new DualListModel<>();
			}
		}
	}

	public void editLoginUser() {
		WebUtils.executeTask(params -> {
			Long id = editLoginUserSelectedUser.getId();
			if (getEditLoginUserIsDefaultLoginUser() && WebUtils.isPermitted(EnumPermission.LOGIN_USER_CHANGE_USERNAME)) {
				loginBean.changeUsername(id, editLoginUserUsername);
			}
			if (WebUtils.isPermitted(EnumPermission.LOGIN_USER_GET_ROLES, EnumPermission.LOGIN_USER_ADD_ROLE,
					EnumPermission.LOGIN_USER_REMOVE_ROLE)) {
				List<ReadOnlyLoginUserRole> roles = new ArrayList<>(loginBean.getLoginUserRoles(id));
				editLoginUserRolesListModel.getTarget().forEach(role -> {
					if (!roles.contains(role)) {
						loginBean.addLoginUserRoleToUser(id, role.getId());
					}
				});
				roles.forEach(role -> {
					if (!editLoginUserRolesListModel.getTarget().contains(role)) {
						loginBean.removeLoginUserRoleFromUser(id, role.getId());
					}
				});
			}
			ReadOnlyLoginUser newUser = loginBean.findLoginUserById(id);
			params.add(WebUtils.getAsString(newUser, LoginUserConverter.CONVERTER_ID));
			WebUtils.refreshEntities(ReadOnlyLoginUser.class, searchResults, selectedEntities, newUser, loginBean::findLoginUserById, true);
			return true;
		}, "lucas.application.loginUserScreen.editLoginUser", (exception, params) -> {
			return WebUtils.getTranslatedMessage("lucas.application.loginUserScreen.createBoundLoginUser.notUniqueUsername",
					params.toArray(new Object[params.size()]));
		}, Utils.asList(editLoginUserUsername));
	}

	/*
	 * -------------------- Edit Login User Dialog End --------------------
	 */

	/*
	 * -------------------- New Password Dialog Start --------------------
	 */

	private ReadOnlyLoginUser changePasswordDialogSelectedUser = null;

	@ValidUnhashedPassword
	private char[] changePasswordDialogPassword = null;

	public char[] getChangePasswordDialogPassword() {
		return this.changePasswordDialogPassword;
	}

	public void setChangePasswordDialogPassword(char[] changePasswordDialogPassword) {
		this.changePasswordDialogPassword = changePasswordDialogPassword;
	}

	public void initChangePasswordDialog() {
		if (!selectedEntities.isEmpty()) {
			changePasswordDialogSelectedUser = selectedEntities.get(0);
		}
	}

	public ReadOnlyLoginUser getChangePasswordDialogSelectedUser() {
		return this.changePasswordDialogSelectedUser;
	}

	public void changePassword() {
		WebUtils.executeTask(params -> {
			loginBean.newPassword(changePasswordDialogSelectedUser.getId(), changePasswordDialogPassword);
			Arrays.fill(changePasswordDialogPassword, 'c');
			return true;
		}, "lucas.application.loginUserScreen.changePassword",
				Utils.asList(WebUtils.getAsString(changePasswordDialogSelectedUser, LoginUserConverter.CONVERTER_ID)));
	}

	/*
	 * -------------------- New Password Dialog End --------------------
	 */
}
