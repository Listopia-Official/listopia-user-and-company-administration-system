package florian_haas.lucas.web.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import florian_haas.lucas.model.LoginUser;
import florian_haas.lucas.web.util.WebUtils;

@FacesConverter(value = "lucas:shortLoginUserStringConverter")
public class ShortLoginUserConverter extends ReadOnlyConverter {

	public static final String NORMAL_KEY = "lucas.application.shortLoginUserConverter.normal";
	public static final String USER_KEY = "lucas.application.shortLoginUserConverter.user";
	public static final String NULL_KEY = "lucas.application.shortLoginUserConverter.none";

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		LoginUser user = value != null ? (LoginUser) value : null;
		return user == null ? WebUtils.getTranslatedMessage(NULL_KEY)
				: user.getUser() != null
						? WebUtils.getTranslatedMessage(USER_KEY, user.getId(),
								WebUtils.getAsString(user.getUser(), "lucas:shortUserStringConverter"))
						: WebUtils.getTranslatedMessage(NORMAL_KEY, user.getId(), user.getUsername());
	}

}
