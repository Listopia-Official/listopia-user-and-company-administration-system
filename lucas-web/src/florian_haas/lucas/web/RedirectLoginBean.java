package florian_haas.lucas.web;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.apache.shiro.SecurityUtils;

@Named
@RequestScoped
public class RedirectLoginBean {

	public String redirect() {
		return SecurityUtils.getSubject().isAuthenticated() ? "/main.xhtml" : null;
	}

}
