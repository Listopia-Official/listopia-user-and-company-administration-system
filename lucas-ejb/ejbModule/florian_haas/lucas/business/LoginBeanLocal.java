package florian_haas.lucas.business;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

import org.apache.shiro.subject.Subject;

import florian_haas.lucas.model.User;
import florian_haas.lucas.model.validation.ValidEntityId;

@Local
public interface LoginBeanLocal {

	public void login(@ValidEntityId(entityClass = User.class) Long userId, char[] password, @NotNull Boolean rememberMe);

	public void logout();

	public Subject getSubject();

}
