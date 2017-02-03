package florian_haas.lucas.web;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;

import florian_haas.lucas.business.LoginBeanLocal;

@Named
@RequestScoped
public class LoginBean {

	@EJB
	private LoginBeanLocal loginBean;

	public boolean isAnonymous() {
		return getLoggedInUsername().equals("Anonymous");
	}

	public String getLoggedInUsername() {
		Subject subject = SecurityUtils.getSubject();
		if (!CollectionUtils.isEmpty(subject.getPrincipals())) return subject.getPrincipal().toString();
		return "Anonymous";
	}

	public String logout() {
		loginBean.logout();
		return "/login.jsf";
	}

}
