package florian_haas.lucas.web.converter;

import java.time.LocalDateTime;
import java.time.format.*;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import florian_haas.lucas.web.util.WebUtils;

@FacesConverter(LocalDateTimeConverter.CONVERTER_ID)
public class LocalDateTimeConverter extends BasicConverter<LocalDateTime> {

	public static final String CONVERTER_ID = "lucas:localDateTimeConverter";

	@Override
	protected String getString(FacesContext context, UIComponent uiComponent, LocalDateTime value) {
		return value == null ? WebUtils.getTranslatedMessage("lucas.application.localDateTimeConverter.null")
				: value.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM));
	}

}
