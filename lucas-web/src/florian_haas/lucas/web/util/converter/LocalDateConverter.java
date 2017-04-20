package florian_haas.lucas.web.util.converter;

import java.time.LocalDate;
import java.time.format.*;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import florian_haas.lucas.web.util.WebUtils;

@FacesConverter(LocalDateConverter.CONVERTER_ID)
public class LocalDateConverter extends BasicConverter<LocalDate> {

	public static final String CONVERTER_ID = "lucas:localDateConverter";

	@Override
	protected String getString(FacesContext context, UIComponent uiComponent, LocalDate value) {
		return value == null ? WebUtils.getTranslatedMessage("lucas.application.localDateConverter.null")
				: value.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
	}

}
