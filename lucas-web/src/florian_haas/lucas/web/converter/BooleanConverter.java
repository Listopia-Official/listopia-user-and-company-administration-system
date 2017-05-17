package florian_haas.lucas.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.*;

import florian_haas.lucas.web.util.WebUtils;

@FacesConverter(BooleanConverter.CONVERTER_ID)
public class BooleanConverter implements Converter<Boolean> {

	public static final String CONVERTER_ID = "lucas:booleanConverter";

	public static final String NULL_KEY = "lucas.application.booleanConverter.null";
	public static final String TRUE_KEY = "lucas.application.booleanConverter.normal.true";
	public static final String FALSE_KEY = "lucas.application.booleanConverter.normal.false";

	@Override
	public Boolean getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		return arg2 == null || arg2.equals(WebUtils.getTranslatedMessage(NULL_KEY)) ? null
				: arg2.equals(WebUtils.getTranslatedMessage(TRUE_KEY)) ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Boolean value) {
		String key = value == null ? NULL_KEY : value == Boolean.TRUE ? TRUE_KEY : FALSE_KEY;
		return WebUtils.getTranslatedMessage(key);
	}

}
