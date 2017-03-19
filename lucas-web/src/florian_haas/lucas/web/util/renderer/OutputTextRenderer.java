package florian_haas.lucas.web.util.renderer;

import javax.faces.component.*;
import javax.faces.context.FacesContext;

import com.sun.faces.renderkit.html_basic.TextRenderer;

public class OutputTextRenderer extends TextRenderer {

	@Override
	protected String getCurrentValue(FacesContext context, UIComponent component) {
		if ((component instanceof UIInput)) {
			Object submittedValue = ((UIInput) component).getSubmittedValue();
			if (submittedValue != null) return submittedValue.toString();
		}
		return getFormattedValue(context, component, getValue(component));
	}

}
