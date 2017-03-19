package florian_haas.lucas.web.util.converter;

import java.util.Collection;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import florian_haas.lucas.web.util.WebUtils;

public abstract class CollectionConverter<T> extends ReadOnlyConverter {

	private final String NO_VALUE_KEY;

	protected CollectionConverter(String noValueKey) {
		NO_VALUE_KEY = noValueKey;
	}

	@Override
	@SuppressWarnings("unchecked")
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		Collection<T> collection = (Collection<T>) value;
		StringBuilder builder = new StringBuilder();
		if (collection.isEmpty()) {
			builder.append(WebUtils.getTranslatedMessage(NO_VALUE_KEY));
		} else {
			collection.forEach(entry -> {
				builder.append(entryToString(entry) + "<br />");
			});
		}
		return builder.toString().trim();
	}

	protected String entryToString(T entry) {
		return entry.toString();
	}

}
