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

	private final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM);

	public LocalDateTimeConverter() {
		this.setIsReadonly(Boolean.FALSE);
	}

	@Override
	protected String getString(FacesContext context, UIComponent uiComponent, LocalDateTime value) {
		return value == null ? WebUtils.getTranslatedMessage("lucas.application.localDateTimeConverter.null") : value.format(formatter);
	}

	@Override
	protected LocalDateTime getObject(FacesContext context, UIComponent component, String value) {
		return value == null || value.trim().isEmpty() || value.equals(WebUtils.getTranslatedMessage("lucas.application.localDateTimeConverter.null"))
				? null : LocalDateTime.from(formatter.parse(value));
	}

}
