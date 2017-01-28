package florian_haas.lucas.business;

import javax.ejb.*;
import javax.validation.executable.*;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;

import florian_haas.lucas.util.Secured;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@ValidateOnExecution(type = ExecutableType.IMPLICIT)
@Secured
public class LoginBean implements LoginBeanLocal {

	@Override
	public void login(Long userId, char[] password, Boolean rememberMe) {
		SecurityUtils.getSubject().login(new UsernamePasswordToken(userId.toString(), password, rememberMe));
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

}
