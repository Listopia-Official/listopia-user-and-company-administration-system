package florian_haas.lucas.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.*;

@FacesConverter(NullConverterNoText.CONVERTER_ID)
public class NullConverterNoText implements Converter<Object> {

	public static final String CONVERTER_ID = "lucas:nullConverterNoText";

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return (value == null || value.trim().isEmpty()) ? null : value;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String string = value.toString();
		return (string == null || string.trim().isEmpty()) ? null : string;
	}
}
