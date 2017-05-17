package florian_haas.lucas.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.*;

import florian_haas.lucas.web.util.WebUtils;

@FacesConverter(IntegerNullConverter.CONVERTER_ID)
public class IntegerNullConverter implements Converter<Integer> {

	public static final String CONVERTER_ID = "lucas:integerNullConverter";
	public static final String NO_VALUE_KEY = "lucas.application.integerNullConverter.null";

	@Override
	public Integer getAsObject(FacesContext context, UIComponent component, String value) {
		return value.trim().equals(WebUtils.getTranslatedMessage(NO_VALUE_KEY)) ? null : Integer.valueOf(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Integer value) {
		return value == null ? WebUtils.getTranslatedMessage(NO_VALUE_KEY) : value.toString();
	}

}
