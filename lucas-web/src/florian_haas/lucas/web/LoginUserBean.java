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

import florian_haas.lucas.business.*;
import florian_haas.lucas.database.*;
import florian_haas.lucas.database.validation.QueryComparator;
import florian_haas.lucas.model.*;
import florian_haas.lucas.model.validation.*;
import florian_haas.lucas.security.EnumPermission;
import florian_haas.lucas.util.Utils;
import florian_haas.lucas.util.validation.*;
import florian_haas.lucas.web.converter.*;
import florian_haas.lucas.web.util.WebUtils;

@Named
@ViewScoped
public class LoginUserBean implements Serializable {

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

	private List<LoginUser> searchLoginUserResults = new ArrayList<>();

	private List<LoginUser> selectedLoginUsers = new ArrayList<>();

	private List<Boolean> resultsDatatableColumns = Arrays.asList(true, true, true, true);

	public List<LoginUser> getSearchLoginUserResults() {
		return searchLoginUserResults;
	}

	public void setSearchLoginUserResults(List<LoginUser> searchLoginUserResults) {
		this.searchLoginUserResults = searchLoginUserResults;
	}

	public List<LoginUser> getSelectedLoginUsers() {
		return selectedLoginUsers;
	}

	public void setSelectedLoginUsers(List<LoginUser> selectedLoginUsers) {
		this.selectedLoginUsers = selectedLoginUsers;
	}

	public List<Boolean> getResultsDatatableColumns() {
		return this.resultsDatatableColumns;
	}

	public void onToggle(ToggleEvent e) {
		resultsDatatableColumns.set((Integer) e.getData() - 1, e.getVisibility() == Visibility.VISIBLE);
	}

	public void searchLoginUsers() {
		WebUtils.executeTask((params) -> {
			List<LoginUser> results = loginBean.findLoginUsers(searchLoginUserId, searchLoginUserUsername, searchLoginUserUserId,
					searchLoginUserRoles, useSearchLoginUserId, useSearchLoginUserUsername, useSearchLoginUserUserId, useSearchLoginUserRoles,
					searchLoginUserIdComparator, searchLoginUserUsernameComparator, searchLoginUserUserIdComparator, searchLoginUserRolesComparator);
			searchLoginUserResults.clear();
			selectedLoginUsers.clear();
			searchLoginUserResults.addAll(results);
			params.add(results.size());
			return true;
		}, "lucas.application.loginUserScreen.searchLoginUser.message");
	}

	public void refreshLoginUsers() {
		WebUtils.executeTask((params) -> {
			WebUtils.refreshEntities(LoginUser.class, searchLoginUserResults, selectedLoginUsers, loginBean::findLoginUserById, false);
			params.add(searchLoginUserResults.size());
			return true;
		}, "lucas.application.loginUserScreen.refreshLoginUsers.message");
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

	private DualListModel<LoginUserRole> createDefaultLoginUserRolesListModel;

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

	public DualListModel<LoginUserRole> getCreateDefaultLoginUserRolesListModel() {
		return createDefaultLoginUserRolesListModel;
	}

	public void setCreateDefaultLoginUserRolesListModel(DualListModel<LoginUserRole> createDefaultLoginUserRolesListModel) {
		this.createDefaultLoginUserRolesListModel = createDefaultLoginUserRolesListModel;
	}

	public void initCreateDefaultLoginUserDialog() {
		createDefaultLoginUserUsername = null;
		createDefaultLoginUserPassword = null;
		createDefaultLoginUserRolesListModel = new DualListModel<>(new ArrayList<>(loginUserRoleBean.findAll()), new ArrayList<LoginUserRole>());
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

	@ValidEntityId(entityClass = User.class)
	private Long createBoundLoginUserBoundUser = null;

	@ValidUnhashedPassword
	private char[] createBoundLoginUserPassword = null;

	private DualListModel<LoginUserRole> createBoundLoginUserRolesListModel;

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

	public DualListModel<LoginUserRole> getCreateBoundLoginUserRolesListModel() {
		return createBoundLoginUserRolesListModel;
	}

	public void setCreateBoundLoginUserRolesListModel(DualListModel<LoginUserRole> createBoundLoginUserRolesListModel) {
		this.createBoundLoginUserRolesListModel = createBoundLoginUserRolesListModel;
	}

	public String getCreateBoundLoginUserBoundUserFromId() {
		User user = createBoundLoginUserBoundUser != null
				? entityBean.exists(createBoundLoginUserBoundUser, User.class) ? userBean.findById(createBoundLoginUserBoundUser) : null : null;
		return WebUtils.getAsString(user, UserConverter.CONVERTER_ID);
	}

	public void initCreateBoundLoginUserDialog() {
		createBoundLoginUserBoundUser = null;
		createBoundLoginUserPassword = null;
		createBoundLoginUserRolesListModel = new DualListModel<>(new ArrayList<>(loginUserRoleBean.findAll()), new ArrayList<LoginUserRole>());
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
					(exception.getMark().equals(florian_haas.lucas.business.LoginBean.USER_NOT_UNIQUE_EXCEPTION_MARKER)
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

	private LoginUser editLoginUserSelectedUser = null;

	@NotBlank
	private String editLoginUserUsername = null;

	private DualListModel<LoginUserRole> editLoginUserRolesListModel;

	public LoginUser getEditLoginUserSelectedUser() {
		return this.editLoginUserSelectedUser;
	}

	public String getEditLoginUserUsername() {
		return this.editLoginUserUsername;
	}

	public void setEditLoginUserUsername(String editLoginUserUsername) {
		this.editLoginUserUsername = editLoginUserUsername;
	}

	public DualListModel<LoginUserRole> getEditLoginUserRolesListModel() {
		return this.editLoginUserRolesListModel;
	}

	public void setEditLoginUserRolesListModel(DualListModel<LoginUserRole> editLoginUserRolesListModel) {
		this.editLoginUserRolesListModel = editLoginUserRolesListModel;
	}

	public Boolean getEditLoginUserIsDefaultLoginUser() {
		return editLoginUserSelectedUser.getUser() == null;
	}

	public void initEditLoginUserDialog() {
		if (!selectedLoginUsers.isEmpty()) {
			editLoginUserSelectedUser = selectedLoginUsers.get(0);
			editLoginUserUsername = editLoginUserSelectedUser.getUsername();
			if (WebUtils.isPermitted(EnumPermission.LOGIN_USER_GET_ROLES, EnumPermission.LOGIN_USER_ADD_ROLE,
					EnumPermission.LOGIN_USER_REMOVE_ROLE)) {
				List<LoginUserRole> rolesOfUser = new ArrayList<>(editLoginUserSelectedUser.getRoles());
				List<LoginUserRole> roles = new ArrayList<>();
				List<LoginUserRole> allRoles = loginUserRoleBean.findAll();
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
				List<LoginUserRole> roles = new ArrayList<>(loginBean.getLoginUserRoles(id));
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
			LoginUser newUser = loginBean.findLoginUserById(id);
			params.add(WebUtils.getAsString(newUser, LoginUserConverter.CONVERTER_ID));
			WebUtils.refreshEntities(LoginUser.class, searchLoginUserResults, selectedLoginUsers, newUser, loginBean::findLoginUserById, true);
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

	private LoginUser changePasswordDialogSelectedUser = null;

	@ValidUnhashedPassword
	private char[] changePasswordDialogPassword = null;

	public char[] getChangePasswordDialogPassword() {
		return this.changePasswordDialogPassword;
	}

	public void setChangePasswordDialogPassword(char[] changePasswordDialogPassword) {
		this.changePasswordDialogPassword = changePasswordDialogPassword;
	}

	public void initChangePasswordDialog() {
		if (!selectedLoginUsers.isEmpty()) {
			changePasswordDialogSelectedUser = selectedLoginUsers.get(0);
		}
	}

	public LoginUser getChangePasswordDialogSelectedUser() {
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
