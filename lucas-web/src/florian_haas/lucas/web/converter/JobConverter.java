package florian_haas.lucas.web.converter;

import javax.faces.convert.FacesConverter;

import florian_haas.lucas.business.JobBeanLocal;
import florian_haas.lucas.model.ReadOnlyJob;
import florian_haas.lucas.web.util.WebUtils;

@FacesConverter(value = JobConverter.CONVERTER_ID, managed = true)
public class JobConverter extends DefaultConverter<ReadOnlyJob> {

	public static final String CONVERTER_ID = "lucas:jobConverter";

	// @EJB
	// private JobBeanLocal jobBean;

	public JobConverter() {
		this(Boolean.FALSE);
	}

	protected JobConverter(Boolean isShortConverter) {
		super(isShortConverter, "lucas.application.jobConverter", JobBeanLocal.class);
	}

	@Override
	protected Object[] getParamsFromValue(ReadOnlyJob value) {
		return new Object[] {
				value.getId(), value.getName(), WebUtils.getAsString(value.getCompany(), CompanyConverter.CONVERTER_ID) };
	}

	@Override
	protected ReadOnlyJob getObjectFromId(Object getter, Long id) {
		return ((JobBeanLocal) getter).findById(id);
	}

	@FacesConverter(ShortJobConverter.CONVERTER_ID)
	public static class ShortJobConverter extends JobConverter {
		public static final String CONVERTER_ID = "lucas:shortJobConverter";

		public ShortJobConverter() {
			super(Boolean.TRUE);
		}
	}
}
