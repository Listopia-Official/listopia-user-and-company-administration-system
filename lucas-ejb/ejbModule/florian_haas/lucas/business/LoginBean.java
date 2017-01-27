package florian_haas.lucas.business;

import javax.ejb.*;
import javax.inject.Inject;
import javax.validation.executable.*;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;

import florian_haas.lucas.util.*;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@ValidateOnExecution(type = ExecutableType.IMPLICIT)
@Secured
public class LoginBean implements LoginBeanLocal {

	@Inject
	private SecurityProducer producer;

	@Override
	public void login(Long userId, char[] password, Boolean rememberMe) {
		producer.getSubject().login(new UsernamePasswordToken(userId.toString(), password, rememberMe));
	}

	@Override
	public void logout() {
		producer.getSubject().logout();
	}

	@Override
	@RequiresPermissions({
			"login:getSubject" })
	public Subject getSubject() {
		return producer.getSubject();
	}

}
