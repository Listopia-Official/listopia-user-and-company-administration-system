package florian_haas.lucas.web.util.converter;

import java.time.LocalDateTime;
import java.time.format.*;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import florian_haas.lucas.web.util.WebUtils;

@FacesConverter(value = "lucas:localDateTimeStringConverter")
public class LocalDateTimeConverter extends ReadOnlyConverter {

	public static final String NULL_KEY = "lucas.application.localDateTimeConverter.null";

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		LocalDateTime dateTime = arg2 != null ? (LocalDateTime) arg2 : null;
		return dateTime != null ? dateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM))
				: WebUtils.getTranslatedMessage(NULL_KEY);
	}

}
