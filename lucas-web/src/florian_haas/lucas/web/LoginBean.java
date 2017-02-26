package florian_haas.lucas.web;

import java.io.IOException;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.primefaces.model.StreamedContent;

import florian_haas.lucas.business.*;
import florian_haas.lucas.security.EnumPermission;
import florian_haas.lucas.util.WebUtils;

@Named
@RequestScoped
public class LoginBean {

	@EJB
	private EntityBeanLocal entityBean;

	@EJB
	private LoginBeanLocal loginBean;

	@EJB
	private UserBeanLocal userBean;

	@EJB
	private GlobalDataBeanLocal globalDataBean;

	public Boolean getIsUserInDatabase() {
		return !getIsAnonymous() && getDBUsername() != null;
	}

	public Object getPrincipal() {
		return getIsAnonymous() ? null : SecurityUtils.getSubject().getPrincipal();
	}

	public Long getUserId() {
		String username = getUsername();
		Long id = null;
		if (username != null) {
			try {
				id = Long.valueOf(username);
			}
			catch (Exception e) {}
		}
		return id;
	}

	public String getUsername() {
		Object principal = getPrincipal();
		return principal != null ? principal.toString() : null;
	}

	public String getDBUsername() {
		Long id = getUserId();
		return id != null ? loginBean.getDBUsername(id) : null;
	}

	public Boolean getIsAnonymous() {
		return SecurityUtils.getSubject().getPrincipals().isEmpty();
	}

	public String getLoggedInUsername() {
		Subject subject = SecurityUtils.getSubject();
		if (getIsUserInDatabase()) {

		}
		if (!CollectionUtils.isEmpty(subject.getPrincipals())) return subject.getPrincipal().toString();
		return "Anonymous";
	}

	public StreamedContent getImage() {
		StreamedContent ret = null;
		Long id = getUserId();
		if (id != null && SecurityUtils.getSubject().isPermitted(EnumPermission.USER_GET_IMAGE_FROM_ID.getPermissionString())) {
			try {
				ret = WebUtils.getJPEGImage(userBean.getImage(id));
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (ret == null) {
			ret = WebUtils.getSVGImage(
					FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/resources/images/user_male.svg"));
		}
		return ret;
	}

	public void logout() {
		loginBean.logout();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/lucas-web/login.jsf");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Long getIdleTimeout() {
		return globalDataBean.getMaxIdleTime();
	}

}
