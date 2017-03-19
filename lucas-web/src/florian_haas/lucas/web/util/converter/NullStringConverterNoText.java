package florian_haas.lucas.web.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.*;

@FacesConverter(value = "lucas:nullStringConverterNoText")
public class NullStringConverterNoText implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if (arg2 == null || arg2.trim().isEmpty()) return null;
		return arg2;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		String string = (String) arg2;
		if (string == null || string.trim().isEmpty()) return null;
		return string;
	}

}
