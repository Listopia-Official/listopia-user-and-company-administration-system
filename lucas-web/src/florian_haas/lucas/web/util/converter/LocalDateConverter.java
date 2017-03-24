package florian_haas.lucas.web.util.converter;

import java.time.LocalDate;
import java.time.format.*;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import florian_haas.lucas.web.util.WebUtils;

@FacesConverter(value = "lucas:localDateStringConverter")
public class LocalDateConverter extends ReadOnlyConverter {

	public static final String NULL_KEY = "lucas.application.localDateConverter.null";

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		LocalDate date = arg2 != null ? (LocalDate) arg2 : null;
		return date != null ? date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)) : WebUtils.getTranslatedMessage(NULL_KEY);
	}

}
