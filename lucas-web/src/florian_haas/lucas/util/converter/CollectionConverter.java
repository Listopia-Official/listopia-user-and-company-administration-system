package florian_haas.lucas.util.converter;

import java.util.Collection;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import florian_haas.lucas.util.WebUtils;

public abstract class CollectionConverter extends ReadOnlyConverter {

	private final String NO_VALUE_KEY;

	protected CollectionConverter(String noValueKey) {
		NO_VALUE_KEY = noValueKey;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		Collection<?> collection = (Collection<?>) value;
		StringBuilder builder = new StringBuilder();
		if (collection.isEmpty()) {
			builder.append(WebUtils.getTranslatedMessage(NO_VALUE_KEY));
		} else {
			collection.forEach(rank -> {
				builder.append(rank + "<br />");
			});
		}
		return builder.toString().trim();
	}

}
