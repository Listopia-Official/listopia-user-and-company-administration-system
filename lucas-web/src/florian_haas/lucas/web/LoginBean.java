package florian_haas.lucas.web;

import java.io.*;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.primefaces.model.*;

import florian_haas.lucas.business.*;

@Named
@RequestScoped
public class LoginBean {

	@EJB
	private EntityBeanLocal entityBean;

	@EJB
	private LoginBeanLocal loginBean;

	@EJB
	private UserBeanLocal userBean;

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
		InputStream in = null;
		String mime = null;
		Long id = getUserId();
		if (id != null) {
			byte[] img = userBean.getImage(id);
			if (img != null) {
				in = new ByteArrayInputStream(img);
				mime = "image/png";
			}
		}
		if (in == null && mime == null) {
			in = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/resources/images/user_male.svg");
			if (in != null) {
				mime = "image/svg+xml";
			}
		}
		return in != null && mime != null ? new DefaultStreamedContent(in, mime) : null;
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

	public String getIdleTimeout() {
		return "300000";
	}

}
