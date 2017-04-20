package florian_haas.lucas.web.util.converter;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

import florian_haas.lucas.business.LoginUserRoleBeanLocal;
import florian_haas.lucas.model.LoginUserRole;
import florian_haas.lucas.web.util.*;

@FacesConverter(LoginUserRoleConverter.CONVERTER_ID)
public class LoginUserRoleConverter extends DefaultConverter<LoginUserRole> {

	public static final String CONVERTER_ID = "lucas:loginUserRoleConverter";

	public LoginUserRoleConverter() {
		this(Boolean.FALSE);
	}

	protected LoginUserRoleConverter(Boolean isShortConverter) {
		super(isShortConverter, "lucas.application.loginUserRoleConverter");
	}

	@Override
	protected Object[] getParamsFromValue(LoginUserRole value) {
		return new Object[] {
				value.getId(), value.getName() };
	}

	@FacesConverter(ShortLoginUserRoleConverter.CONVERTER_ID)
	public static class ShortLoginUserRoleConverter extends LoginUserRoleConverter {

		public static final String CONVERTER_ID = "lucas:shortLoginUserRoleConverter";

		public ShortLoginUserRoleConverter() {
			super(Boolean.TRUE);
			this.setIsReadonly(Boolean.FALSE);
		}

		@Override
		protected LoginUserRole getObject(FacesContext context, UIComponent component, String value) {
			if (value == null || value.trim().isEmpty() || value.equals(WebUtils.getTranslatedMessage(this.getNullLangKey()))) return null;

			Long id = null;
			try {
				id = Long.valueOf(value.replaceAll("\\D+", ""));
			}
			catch (NumberFormatException e) {}

			return id != null
					? WebUtils.getCDIManagerBean(EntityBean.class).exists(id, LoginUserRole.class)
							? WebUtils.getCDIManagerBean(ShortLoginUserRoleConverterEJBHolder.class).getLoginUserRoleBean().findById(id) : null
					: null;
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

}
