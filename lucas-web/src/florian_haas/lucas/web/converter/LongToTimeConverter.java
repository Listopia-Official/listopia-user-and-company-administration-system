package florian_haas.lucas.web.converter;

import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import florian_haas.lucas.web.util.WebUtils;

@FacesConverter(LongToTimeConverter.CONVERTER_ID)
public class LongToTimeConverter extends BasicConverter<Long> {

	public static final String CONVERTER_ID = "lucas:longToTimeConverter";

	public LongToTimeConverter() {
		this.setIsReadonly(Boolean.FALSE);
	}

	@Override
	protected String getString(FacesContext context, UIComponent uiComponent, Long value) {
		// long mls = value % 1000;
		String separator = WebUtils.getTranslatedMessage("lucas.application.longToTimeConverter.separator");
		return value != null
				? formattLong(TimeUnit.MILLISECONDS.toHours(value) % 24) + separator + formattLong(TimeUnit.MILLISECONDS.toMinutes(value) % 60)
						+ separator + formattLong(TimeUnit.MILLISECONDS.toSeconds(value) % 60)
				: WebUtils.getTranslatedMessage("lucas.application.longToTimeConverter.null");
	}

	private String formattLong(Long data) {
		return String.format("%02d", data);
	}

	@Override
	protected Long getObject(FacesContext context, UIComponent component, String value) {
		if (value.trim().equals(WebUtils.getTranslatedMessage("lucas.application.longToTimeConverter.null"))) return null;
		StringTokenizer tok = new StringTokenizer(value, WebUtils.getTranslatedMessage("lucas.application.longToTimeConverter.separator"));
		Long hours = Long.parseLong(tok.nextToken());
		Long minutes = Long.parseLong(tok.nextToken());
		Long seconds = Long.parseLong(tok.nextToken());
		return TimeUnit.HOURS.toMillis(hours) + TimeUnit.MINUTES.toMillis(minutes) + TimeUnit.SECONDS.toMillis(seconds);
	}

}
