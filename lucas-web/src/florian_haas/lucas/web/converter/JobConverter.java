package florian_haas.lucas.web.converter;

import javax.faces.convert.FacesConverter;

import florian_haas.lucas.model.ReadOnlyJob;
import florian_haas.lucas.web.util.WebUtils;

@FacesConverter(JobConverter.CONVERTER_ID)
public class JobConverter extends DefaultConverter<ReadOnlyJob> {

	public static final String CONVERTER_ID = "lucas:jobConverter";

	public JobConverter() {
		this(Boolean.FALSE);
	}

	protected JobConverter(Boolean isShortConverter) {
		super(isShortConverter, "lucas.application.jobConverter");
	}

	@Override
	protected Object[] getParamsFromValue(ReadOnlyJob value) {
		return new Object[] {
				value.getId(), value.getName(), WebUtils.getAsString(value.getCompany(), CompanyConverter.CONVERTER_ID) };
	}

	@FacesConverter(ShortJobConverter.CONVERTER_ID)
	public static class ShortJobConverter extends JobConverter {
		public static final String CONVERTER_ID = "lucas:shortJobConverter";

		public ShortJobConverter() {
			super(Boolean.TRUE);
		}
	}
}
