package florian_haas.lucas.web.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import florian_haas.lucas.web.util.WebUtils;

@FacesConverter(value = "lucas:themeConverter")
public class ThemeConverter extends ReadOnlyConverter {

	public static final String NULL_KEY = "lucas.application.theme.none";
	public static final String BASE_KEY = "lucas.application.theme.#.name";

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		String themeName = arg2 != null ? arg2.toString() : null;
		return WebUtils.getTranslatedMessage(themeName == null ? NULL_KEY : BASE_KEY.replaceAll("#", themeName));
	}

}
