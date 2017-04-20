package florian_haas.lucas.web.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.*;

import florian_haas.lucas.web.util.WebUtils;

@FacesConverter(NullConverter.CONVERTER_ID)
public class NullConverter implements Converter {

	public static final String CONVERTER_ID = "lucas:nullConverter";
	public static final String NO_VALUE_KEY = "lucas.application.nullConverter.null";

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return value.trim().equals(WebUtils.getTranslatedMessage(NO_VALUE_KEY)) ? null : value;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return value == null ? WebUtils.getTranslatedMessage(NO_VALUE_KEY) : value.toString();
	}

}
