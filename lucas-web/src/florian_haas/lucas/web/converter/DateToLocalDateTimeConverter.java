package florian_haas.lucas.web.converter;

import java.time.LocalDateTime;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import florian_haas.lucas.util.Utils;
import florian_haas.lucas.web.util.WebUtils;

@FacesConverter(DateToLocalDateTimeConverter.CONVERTER_ID)
public class DateToLocalDateTimeConverter extends BasicConverter<Date> {

	public static final String CONVERTER_ID = "lucas:dateToLocalDateTimeConverter";

	public DateToLocalDateTimeConverter() {
		this.setIsReadonly(Boolean.FALSE);
	}

	@Override
	protected String getString(FacesContext context, UIComponent uiComponent, Date value) {
		return WebUtils.getAsString(Utils.asLocalDateTime(value), LocalDateTimeConverter.CONVERTER_ID);
	}

	@Override
	protected Date getObject(FacesContext context, UIComponent component, String value) {
		LocalDateTime dateTime = (LocalDateTime) WebUtils.getAsObject(value, LocalDateTimeConverter.CONVERTER_ID);
		return dateTime == null ? null : Utils.asDate(dateTime);
	}

}
