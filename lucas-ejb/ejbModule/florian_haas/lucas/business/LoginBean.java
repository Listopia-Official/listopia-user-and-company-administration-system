package florian_haas.lucas.business;

import static florian_haas.lucas.security.EnumPermission.*;

import java.util.*;

import javax.ejb.*;
import javax.inject.Inject;
import javax.validation.executable.*;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.*;
import org.apache.shiro.subject.Subject;

import florian_haas.lucas.database.*;
import florian_haas.lucas.model.*;
import florian_haas.lucas.model.validation.ValidEntityId;
import florian_haas.lucas.security.*;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@ValidateOnExecution(type = ExecutableType.IMPLICIT)
@Secured
public class LoginBean implements LoginBeanLocal {

	@Inject
	@JPADAO
	private LoginUserDAO loginUserDao;

	@Inject
	@JPADAO
	private LoginUserRoleDAO loginUserRoleDao;

	@EJB
	private UserBeanLocal userBean;

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
	@RequiresPermissions(LOGIN_CREATE_RAW_LOGIN_USER)
	public Long newLoginUser(String username, char[] password) {
		return newLoginUserHelper(username, password, null);
	}

	@Override
	@RequiresPermissions(LOGIN_CREATE_REGISTERED_LOGIN_USER)
	public Long newLoginUser(Long user, char[] password) {
		return newLoginUserHelper(Long.toString(user), password, userBean.findById(user));
	}

	private Long newLoginUserHelper(String username, char[] password, User usr) {
		PasswordService passwordService = new DefaultPasswordService();
		String encryptedPassword = passwordService.encryptPassword(password);
		LoginUser user = new LoginUser(username, encryptedPassword, usr);
		Arrays.fill(password, 'c');
		loginUserDao.persist(user);
		return user.getId();
	}

	@Override
	@RequiresPermissions(LOGIN_CHANGE_PASSWORD)
	public Boolean changePassword(Long loginUserId, char[] oldPassword, char[] newPassword) {
		return changePasswordHelper(loginUserId, oldPassword, newPassword);
	}

	@Override
	@RequiresPermissions(LOGIN_NEW_PASSWORD)
	public Boolean newPassword(Long loginUserId, char[] newPassword) {
		return changePasswordHelper(loginUserId, null, newPassword);
	}

	private Boolean changePasswordHelper(Long loginUserId, char[] oldPassword, char[] newPassword) {
		PasswordService passwordService = new DefaultPasswordService();
		LoginUser user = loginUserDao.findById(loginUserId);
		String newEncryptedPassword = passwordService.encryptPassword(newPassword);
		Arrays.fill(newPassword, 'c');
		if (oldPassword == null || (oldPassword != null && passwordService.passwordsMatch(oldPassword, user.getHashedPassword()))) {
			Arrays.fill(oldPassword, 'c');
			user.setHashedPassword(newEncryptedPassword);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@Override
	public LoginUser findLoginUserById(Long id) {
		return loginUserDao.findById(id);
	}

	@Override
	public Long newLoginUserRole(String name, Set<String> permissions) {
		LoginUserRole role = new LoginUserRole(name);
		if (permissions != null) {
			permissions.forEach(permission -> {
				role.addPermission(permission);
			});
		}
		loginUserRoleDao.persist(role);
		return role.getId();
	}

	@Override
	public Boolean addLoginUserRole(Long userId, Long roleId) {
		LoginUser user = loginUserDao.findById(userId);
		LoginUserRole role = loginUserRoleDao.findById(roleId);
		return user.addRole(role);
	}

	@Override
	public Boolean removeLoginUserRole(Long userId, Long roleId) {
		LoginUser user = loginUserDao.findById(userId);
		LoginUserRole role = loginUserRoleDao.findById(roleId);
		return user.removeRole(role);
	}

	@Override
	public Boolean setRoleName(Long roleId, String name) {
		LoginUserRole role = loginUserRoleDao.findById(roleId);
		if (!role.getName().equals(name)) {
			role.setName(name);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@Override
	public Boolean addPermission(Long roleId, String permission) {
		LoginUserRole role = loginUserRoleDao.findById(roleId);
		return role.addPermission(permission);
	}

	@Override
	public Boolean removePermission(Long roleId, String permission) {
		LoginUserRole role = loginUserRoleDao.findById(roleId);
		return role.removePermission(permission);
	}

	@Override
	public LoginUserRole findLoginUserRoleById(Long roleId) {
		return loginUserRoleDao.findById(roleId);
	}

	@Override
	public List<LoginUserRole> findAllLoginUserRoles() {
		return loginUserRoleDao.findAll();
	}

	@Override
	public List<LoginUser> findAllLoginUsers() {
		return loginUserDao.findAll();
	}

	@Override
	public String getDBUsername(@ValidEntityId(entityClass = LoginUser.class) Long userId) {
		LoginUser user = loginUserDao.findById(userId);
		User user2 = user.getUser();
		return user2 != null && user2.getForename() != null && user2.getSurname() != null ? user2.getForename() + " " + user2.getSurname() : null;
	}

}
