package florian_haas.lucas.web.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public abstract class ReadOnlyConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		throw new UnsupportedOperationException("getAsObject is not supported by" + this.getClass().getSimpleName());
	}

}
