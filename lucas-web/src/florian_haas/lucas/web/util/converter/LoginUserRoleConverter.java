package florian_haas.lucas.web.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import florian_haas.lucas.model.LoginUserRole;
import florian_haas.lucas.web.util.WebUtils;

@FacesConverter(value = "lucas:loginUserRoleStringConverter")
public class LoginUserRoleConverter extends ReadOnlyConverter {

	public static final String KEY = "lucas.application.loginUserRoleConverter.key";
	public static final String NULL_KEY = "lucas.application.loginUserRoleConverter.none";

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		LoginUserRole role = value != null ? (LoginUserRole) value : null;
		return role == null ? WebUtils.getTranslatedMessage(NULL_KEY) : WebUtils.getTranslatedMessage(KEY, role.getId(), role.getName());
	}

}
