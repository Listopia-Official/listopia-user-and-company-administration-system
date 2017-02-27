package florian_haas.lucas.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.*;

import florian_haas.lucas.util.WebUtils;

@FacesConverter(value = "lucas:NullStringConverter")
public class NullStringConverter implements Converter {

	public static final String NO_VALUE_KEY = "lucas.application.emptyStringConverter.empty";

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value.trim().equals(WebUtils.getTranslatedMessage(NO_VALUE_KEY))) return null;
		return value;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null) return WebUtils.getTranslatedMessage(NO_VALUE_KEY);
		return value.toString();
	}

}
