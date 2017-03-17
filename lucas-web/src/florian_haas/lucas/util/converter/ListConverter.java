package florian_haas.lucas.util.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import florian_haas.lucas.util.WebUtils;

public abstract class ListConverter extends ReadOnlyConverter {

	private final String NO_VALUE_KEY;

	protected ListConverter(String noValueKey) {
		NO_VALUE_KEY = noValueKey;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		List<?> list = (List<?>) value;
		StringBuilder builder = new StringBuilder();
		if (list.isEmpty()) {
			builder.append(WebUtils.getTranslatedMessage(NO_VALUE_KEY));
		} else {
			list.forEach(rank -> {
				builder.append(rank + "<br />");
			});
		}
		return builder.toString().trim();
	}

}
