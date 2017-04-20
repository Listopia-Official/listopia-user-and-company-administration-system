package florian_haas.lucas.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import florian_haas.lucas.web.util.WebUtils;

@FacesConverter(ThemeConverter.CONVERTER_ID)
public class ThemeConverter extends BasicConverter<String> {

	public static final String CONVERTER_ID = "lucas:themeConverter";

	@Override
	public String getString(FacesContext context, UIComponent component, String value) {
		return WebUtils.getTranslatedMessage(value == null ? "lucas.application.theme.null" : "lucas.application.theme." + value + ".name");
	}

}
