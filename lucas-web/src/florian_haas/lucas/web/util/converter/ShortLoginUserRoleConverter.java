package florian_haas.lucas.web.util.converter;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.*;
import javax.inject.Named;

import florian_haas.lucas.business.LoginUserRoleBeanLocal;
import florian_haas.lucas.model.LoginUserRole;
import florian_haas.lucas.web.util.*;

@FacesConverter(value = "lucas:shortLoginUserRoleConverter")
public class ShortLoginUserRoleConverter implements Converter {

	public static final String KEY = "lucas.application.shortLoginUserRoleConverter.key";
	public static final String NULL_KEY = "lucas.application.shortLoginUserRoleConverter.none";

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.trim().isEmpty() || value.equals(WebUtils.getTranslatedMessage(NULL_KEY))) return null;
		Long id = null;
		try {
			id = Long.valueOf(value.replaceAll("\\D+", ""));
		}
		catch (NumberFormatException e) {}

		return id != null ? WebUtils.getCDIManagerBean(EntityBean.class).exists(id, LoginUserRole.class)
				? WebUtils.getCDIManagerBean(ShortLoginUserRoleConverterEJBHolder.class).getLoginUserRoleBean().findById(id) : null : null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		LoginUserRole role = value != null ? (LoginUserRole) value : null;
		String ret = role == null ? WebUtils.getTranslatedMessage(NULL_KEY) : WebUtils.getTranslatedMessage(KEY, role.getId(), role.getName());
		return ret;
	}

	@Named
	@RequestScoped
	public static class ShortLoginUserRoleConverterEJBHolder {

		@EJB
		private LoginUserRoleBeanLocal loginUserRoleBean;

		public LoginUserRoleBeanLocal getLoginUserRoleBean() {
			return loginUserRoleBean;
		}
	}
}
