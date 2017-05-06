package florian_haas.lucas.business;

import static florian_haas.lucas.security.EnumPermission.*;

import java.util.*;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.*;
import javax.validation.executable.*;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.*;
import org.apache.shiro.subject.Subject;

import florian_haas.lucas.model.*;
import florian_haas.lucas.model.validation.DefaultLoginUserRequired;
import florian_haas.lucas.persistence.*;
import florian_haas.lucas.security.*;

@Stateless
@ValidateOnExecution(type = ExecutableType.IMPLICIT)
@Secured
public class LoginBean implements LoginBeanLocal {

	@Inject
	@JPADAO
	private LoginUserDAO loginUserDao;

	@Inject
	@JPADAO
	private LoginUserRoleDAO loginUserRoleDao;

	@Inject
	@JPADAO
	private UserDAO userDao;

	@Resource
	private Validator validator;

	@Override
	public void login(String username, char[] password) {
		SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password, false));
		Arrays.fill(password, 'c');
	}

	@Override
	public void logout() {
		SecurityUtils.getSubject().logout();
	}

	@Override
	public Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	@Override
	@RequiresPermissions(LOGIN_USER_CREATE_RAW)
	public Long newLoginUser(String username, char[] password, List<Long> userRoleIds) {
		return newLoginUserHelper(username, password, null, userRoleIds);
	}

	@Override
	@RequiresPermissions(LOGIN_USER_CREATE_REGISTERED)
	public Long newLoginUser(Long user, char[] password, List<Long> userRoleIds) {
		return newLoginUserHelper(Long.toString(user), password, userDao.findById(user), userRoleIds);
	}

	private Long newLoginUserHelper(String username, char[] password, User usr, List<Long> userRoleIds) {
		if (usr != null) {
			checkUserIsUnique(usr);
		}
		checkUsernameIsUnique(username);
		PasswordService passwordService = new DefaultPasswordService();
		String encryptedPassword = passwordService.encryptPassword(password);
		LoginUser user = new LoginUser(username, encryptedPassword, usr);
		userRoleIds.forEach(id -> {
			user.addRole(loginUserRoleDao.findById(id));
		});
		Arrays.fill(password, 'c');
		loginUserDao.persist(user);
		return user.getId();
	}

	@Override
	@RequiresPermissions(LOGIN_CHANGE_PASSWORD)
	public void changePassword(char[] oldPassword, char[] newPassword) {
		changePasswordHelper(loginUserDao.findByUsername(SecurityUtils.getSubject().getPrincipal().toString()).getId(), oldPassword, newPassword);
	}

	@Override
	@RequiresPermissions(LOGIN_SET_UI_THEME)
	public Boolean setUITheme(Long id, String uiTheme) {
		LoginUser user = loginUserDao.findById(id);
		if (user.getUiTheme() == null || !user.getUiTheme().equals(uiTheme)) {
			user.setUiTheme(uiTheme);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@Override
	@RequiresPermissions(LOGIN_USER_NEW_PASSWORD)
	public void newPassword(Long loginUserId, char[] newPassword) {
		changePasswordHelper(loginUserId, null, newPassword);
	}

	private void changePasswordHelper(Long loginUserId, char[] oldPassword, char[] newPassword) {
		PasswordService passwordService = new DefaultPasswordService();
		LoginUser user = loginUserDao.findById(loginUserId);
		String newEncryptedPassword = passwordService.encryptPassword(newPassword);
		Arrays.fill(newPassword, 'c');
		if (oldPassword == null || (oldPassword != null && passwordService.passwordsMatch(oldPassword, user.getHashedPassword()))) {
			if (oldPassword != null) {
				Arrays.fill(oldPassword, 'c');
			}
			user.setHashedPassword(newEncryptedPassword);
		} else if ((oldPassword != null && !passwordService.passwordsMatch(oldPassword, user.getHashedPassword())))
			throw new LucasException("The specified old password doesn't match the old password");
	}

	private void checkUsernameIsUnique(String username) {
		if (!loginUserDao.isUsernameUnique(username))
			throw new LucasException("The username is used by another login user", USERNAME_NOT_UNIQUE_EXCEPTION_MARKER);
	}

	private void checkUserIsUnique(User user) {
		if (user != null && !loginUserDao.isReferencedUserUnique(user.getId()))
			throw new LucasException("The user is bound to another login user", USER_NOT_UNIQUE_EXCEPTION_MARKER);
	}

	@Override
	@RequiresPermissions(LOGIN_USER_FIND_BY_ID)
	public LoginUser findLoginUserById(Long id) {
		return loginUserDao.findById(id);
	}

	@Override
	@RequiresPermissions(LOGIN_USER_FIND_BY_USERNAME)
	public LoginUser findLoginUserByUsername(String username) {
		return loginUserDao.findByUsername(username);
	}

	@Override
	@RequiresPermissions(LOGIN_USER_FIND_ALL)
	public List<? extends ReadOnlyLoginUser> findAllLoginUsers() {
		return loginUserDao.findAll();
	}

	@Override
	@RequiresPermissions(LOGIN_USER_GET_ROLES)
	public List<? extends ReadOnlyLoginUserRole> getLoginUserRoles(Long userId) {
		LoginUser user = loginUserDao.findById(userId);
		return new ArrayList<>(user.getRoles());
	}

	@Override
	@RequiresPermissions(LOGIN_USER_FIND_DYNAMIC)
	public List<? extends ReadOnlyLoginUser> findLoginUsers(Long id, String username, Long userId, List<Long> roleIds, Boolean useId,
			Boolean useUsername, Boolean useUserId, Boolean useRoleIds, EnumQueryComparator idComparator, EnumQueryComparator usernameComparator,
			EnumQueryComparator userIdComparator, EnumQueryComparator roleIdsComparator) {
		return loginUserDao.findLoginUsers(id, username, userId, roleIds, useId, useUsername, useUserId, useRoleIds, idComparator, usernameComparator,
				userIdComparator, roleIdsComparator);
	}

	@Override
	@RequiresPermissions(LOGIN_USER_ADD_ROLE)
	public Boolean addLoginUserRoleToUser(Long userId, Long roleId) {
		LoginUser user = loginUserDao.findById(userId);
		LoginUserRole role = loginUserRoleDao.findById(roleId);
		return user.addRole(role);
	}

	@Override
	@RequiresPermissions(LOGIN_USER_REMOVE_ROLE)
	public Boolean removeLoginUserRoleFromUser(Long userId, Long roleId) {
		LoginUser user = loginUserDao.findById(userId);
		LoginUserRole role = loginUserRoleDao.findById(roleId);
		return user.removeRole(role);
	}

	@Override
	@RequiresPermissions(LOGIN_USER_CHANGE_USERNAME)
	public Boolean changeUsername(Long id, String username) {
		LoginUser user = loginUserDao.findById(id);
		Set<ConstraintViolation<LoginUser>> violations = validator.validate(user, DefaultLoginUserRequired.class);
		if (!violations.isEmpty()) throw new ConstraintViolationException(violations);
		if (user.getUsername().equals(username)) {
			return Boolean.FALSE;
		} else {
			checkUsernameIsUnique(username);
			user.setUsername(username);
			return Boolean.TRUE;
		}
	}

}
