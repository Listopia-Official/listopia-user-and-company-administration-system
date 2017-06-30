package florian_haas.lucas.web.converter;

import java.util.concurrent.TimeUnit;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import florian_haas.lucas.web.util.WebUtils;

@FacesConverter(LongToTimeConverter.CONVERTER_ID)
public class LongToTimeConverter extends BasicConverter<Long> {

	public static final String CONVERTER_ID = "lucas:longToTimeConverter";

	@Override
	protected String getString(FacesContext context, UIComponent uiComponent, Long value) {
		// long mls = value % 1000;
		return value != null
				? WebUtils.getTranslatedMessage("lucas.application.longToTimeConverter.normal",
						formattLong(TimeUnit.MILLISECONDS.toHours(value) % 24), formattLong(TimeUnit.MILLISECONDS.toMinutes(value) % 60),
						formattLong(TimeUnit.MILLISECONDS.toSeconds(value) % 60))
				: WebUtils.getTranslatedMessage("lucas.application.longToTimeConverter.normal");
	}

	private String formattLong(Long data) {
		return String.format("%02d", data);
	}

}
