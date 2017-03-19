package florian_haas.lucas.web.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.*;

import florian_haas.lucas.web.util.WebUtils;

@FacesConverter(value = "lucas:BooleanStringConverter")
public class BooleanConverter implements Converter {

	public static final String NULL_KEY = "lucas.application.booleanConverter.null";
	public static final String TRUE_KEY = "lucas.application.booleanConverter.true";
	public static final String FALSE_KEY = "lucas.application.booleanConverter.false";

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		return arg2 == null || arg2.equals(WebUtils.getTranslatedMessage(NULL_KEY)) ? null
				: arg2.equals(WebUtils.getTranslatedMessage(TRUE_KEY)) ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		Boolean bool = (Boolean) arg2;
		String key = bool == null ? NULL_KEY : bool == Boolean.TRUE ? TRUE_KEY : FALSE_KEY;
		return WebUtils.getTranslatedMessage(key);
	}

}
