package florian_haas.lucas.web.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import florian_haas.lucas.model.LoginUser;
import florian_haas.lucas.web.util.WebUtils;

@FacesConverter(value = "lucas:loginUserStringConverter")
public class LoginUserConverter extends ReadOnlyConverter {

	public static final String NORMAL_KEY = "lucas.application.loginUserConverter.normal";
	public static final String USER_KEY = "lucas.application.loginUserConverter.user";
	public static final String NULL_KEY = "lucas.application.loginUserConverter.none";

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		LoginUser user = value != null ? (LoginUser) value : null;
		return user == null ? WebUtils.getTranslatedMessage(NULL_KEY)
				: user.getUser() != null
						? WebUtils.getTranslatedMessage(USER_KEY, user.getId(), WebUtils.getAsString(user.getUser(), "lucas:userStringConverter"))
						: WebUtils.getTranslatedMessage(NORMAL_KEY, user.getId(), user.getUsername());
	}

}
