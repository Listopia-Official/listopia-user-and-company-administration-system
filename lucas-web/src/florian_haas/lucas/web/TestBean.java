package florian_haas.lucas.web;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.*;

import florian_haas.lucas.business.*;
import florian_haas.lucas.model.LoginUserRole;
import florian_haas.lucas.model.validation.ValidUnhashedPassword;

@Named
@ViewScoped
public class TestBean implements Serializable {

	private static final long serialVersionUID = -8272863330457069304L;

	@ValidUnhashedPassword
	private char[] password = null;

	@EJB
	private UserBeanLocal userBean;

	@EJB
	private LoginBeanLocal loginBean;

	@EJB
	private EntityBeanLocal entityBean;

	public void newUser() {
		try {
			Long id = userBean.createGuest();
			Long userId = loginBean.newLoginUser(id, password);
			List<LoginUserRole> roles = loginBean.findAllLoginUserRoles();
			if (roles.isEmpty()) {
				Set<String> permissions = new HashSet<>();
				permissions.add("*");
				LoginUserRole role = loginBean.findLoginUserRoleById(loginBean.newLoginUserRole("Testrole", permissions));
				roles.add(role);
			}
			loginBean.addLoginUserRole(userId, roles.get(0).getId());
		}
		catch (ConstraintViolationException e) {
			Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
			violations.forEach(violation -> {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", violation.getMessage()));
			});
		}
		catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", e.getMessage()));
		}
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

	public char[] getPassword() {
		return this.password;
	}
}
