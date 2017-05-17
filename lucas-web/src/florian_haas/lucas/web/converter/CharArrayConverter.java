package florian_haas.lucas.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.*;

@FacesConverter(CharArrayConverter.CONVERTER_ID)
public class CharArrayConverter implements Converter<char[]> {

	public static final String CONVERTER_ID = "lucas:charArrayConverter";

	@Override
	public char[] getAsObject(FacesContext context, UIComponent component, String value) {
		return value != null ? value.toCharArray() : null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, char[] value) {
		return value != null ? new String((char[]) value) : null;
	}

}
