package florian_haas.lucas.util.converter;

import java.util.*;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.*;

import florian_haas.lucas.util.WebUtils;

@FacesConverter(value = "lucas:ranksConverter")
public class RanksConverter implements Converter {

	public static final String NO_VALUE_KEY = "lucas.application.ranksConverter.noValue";

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		List<String> ret = new ArrayList<>();
		if (!WebUtils.getTranslatedMessage(NO_VALUE_KEY).equals(value)) {
			StringTokenizer tok = new StringTokenizer(value, "<br />");
			while (tok.hasMoreTokens()) {
				ret.add(tok.nextToken());
			}
		}
		return ret;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		List<?> ranks = (List<?>) value;
		StringBuilder builder = new StringBuilder();
		if (ranks.isEmpty()) {
			builder.append(WebUtils.getTranslatedMessage(NO_VALUE_KEY));
		} else {
			ranks.forEach(rank -> {
				builder.append(rank + "<br />");
			});
		}
		return builder.toString().trim();
	}

}
