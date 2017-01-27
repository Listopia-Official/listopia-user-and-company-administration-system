package florian_haas.lucas.business;

import javax.ejb.Local;

import org.apache.shiro.subject.Subject;

@Local
public interface LoginBeanLocal {

	public void login();

	public void logout();

	public Subject getSubject();

}
