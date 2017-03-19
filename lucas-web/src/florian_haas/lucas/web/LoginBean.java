package florian_haas.lucas.web;

import java.io.IOException;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.shiro.SecurityUtils;
import org.primefaces.model.StreamedContent;

import florian_haas.lucas.business.*;
import florian_haas.lucas.model.User;
import florian_haas.lucas.security.EnumPermission;
import florian_haas.lucas.web.util.WebUtils;

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

	public Boolean getIsAnonymous() {
		return SecurityUtils.getSubject().getPrincipals().isEmpty();
	}

	private Object getPrincipal() {
		return getIsAnonymous() ? null : SecurityUtils.getSubject().getPrincipal();
	}

	public String getSimpleUsername() {
		return getIsAnonymous() ? "Anonymous" : getPrincipal().toString();
	}

	private Long getUserId(String principal) {
		String simpleUsername = principal;
		Long id = null;
		if (simpleUsername != null) {
			try {
				id = Long.valueOf(simpleUsername);
			}
			catch (Exception e) {}
		}
		return id;
	}

	public String getAdvancedUsername() {
		String ret = getSimpleUsername();
		Long id = getUserId(ret);
		if (id != null && entityBean.exists(id, User.class)) {
			ret = WebUtils.getAsString(userBean.findById(id), "lucas:userStringConverter");
		}
		return ret;
	}

	public Boolean getIsUserInDatabase() {
		Long id = getUserId(getSimpleUsername());
		return !getIsAnonymous() && id != null && loginBean.findLoginUserByUsername(id.toString()) != null;
	}

	public StreamedContent getImage() {
		StreamedContent ret = null;
		Long id = getUserId(getSimpleUsername());
		if (id != null && SecurityUtils.getSubject().isPermitted(EnumPermission.USER_GET_IMAGE_FROM_ID.getPermissionString())) {
			try {
				ret = WebUtils.getJPEGImage(userBean.getImage(id));
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ret == null ? WebUtils.getSVGImage(
				FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/resources/images/user_male.svg")) : ret;
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
