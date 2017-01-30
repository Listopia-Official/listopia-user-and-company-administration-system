package florian_haas.lucas.business;

import java.util.Arrays;

import javax.ejb.*;
import javax.inject.Inject;
import javax.validation.executable.*;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;

import florian_haas.lucas.database.*;
import florian_haas.lucas.model.*;
import florian_haas.lucas.security.Secured;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@ValidateOnExecution(type = ExecutableType.IMPLICIT)
@Secured
public class LoginBean implements LoginBeanLocal {

	@Inject
	@JPADAO
	private LoginUserDAO loginUserDao;

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
	@RequiresPermissions({
			"login:getSubject" })
	public Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	@Override
	@RequiresPermissions({
			"login:createRawLoginUser" })
	public Long newLoginUser(String username, char[] password) {
		return newLoginUserHelper(username, password, null);
	}

	@Override
	@RequiresPermissions({
			"login:createRegisteredLoginUser" })
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
	@RequiresPermissions({
			"login:changePassword" })
	public Boolean changePassword(Long loginUserId, char[] oldPassword, char[] newPassword) {
		return changePasswordHelper(loginUserId, oldPassword, newPassword);
	}

	@Override
	@RequiresPermissions({
			"login:newPassword" })
	public Boolean newPassword(Long loginUserId, char[] newPassword) {
		return changePasswordHelper(loginUserId, null, newPassword);
	}

	private Boolean changePasswordHelper(Long loginUserId, char[] oldPassword, char[] newPassword) {
		// PasswordService passwordService = new DefaultPasswordService();
		// LoginUser user = loginUserDao.findById(loginUserId);
		// String oldHashedPassword = null;
		// if (oldPassword != null) {
		// oldHashedPassword = passwordService.enc
		// Arrays.fill(oldPassword, 'c');
		// }
		// String salt = randomSalt();
		// String newHashedPassword = new Sha512Hash(newPassword, salt).toHex();
		// Arrays.fill(newPassword, 'c');
		//
		// if (!user.getHashedPassword().equals(newHashedPassword)) {
		// if (oldPassword == null || (oldPassword != null &&
		// user.getHashedPassword().equals(oldHashedPassword))) {
		// user.setHashedPassword(newHashedPassword, salt);
		// return Boolean.TRUE;
		// }
		// }
		return Boolean.FALSE;
	}

}
