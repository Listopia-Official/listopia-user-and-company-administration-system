package florian_haas.lucas.web.converter;

import javax.faces.convert.FacesConverter;

import florian_haas.lucas.business.UserBeanLocal;
import florian_haas.lucas.model.ReadOnlyUser;
import florian_haas.lucas.web.util.WebUtils;

@FacesConverter(value = UserConverter.CONVERTER_ID, managed = true)
public class UserConverter extends DefaultConverter<ReadOnlyUser> {

	public static final String CONVERTER_ID = "lucas:userConverter";

	// @EJB
	// private UserBeanLocal userBean;

	public UserConverter() {
		this(Boolean.FALSE);
	}

	protected UserConverter(Boolean isShortConverter) {
		super(isShortConverter, "lucas.application.userConverter", UserBeanLocal.class);
	}

	@Override
	protected Object[] getParamsFromValue(ReadOnlyUser value) {
		return new Object[] {
				value.getForename(), value.getSurname(), WebUtils.getAsString(value.getSchoolClass(), EnumConverter.CONVERTER_ID), value.getId() };
	}

	@Override
	protected String getModifiedDefaultKey(ReadOnlyUser value, String defaultKey) {
		String keySuffix = ".guest";
		switch (value.getUserType()) {
			case PUPIL:
				keySuffix = ".pupil";
				break;
			case TEACHER:
				keySuffix = ".teacher";
				break;
			case GUEST:
			default:
				keySuffix = ".guest";
				break;
		}
		return defaultKey.concat(keySuffix);
	}

	@Override
	protected ReadOnlyUser getObjectFromId(Object getter, Long id) {
		return ((UserBeanLocal) getter).findById(id);
	}

	@FacesConverter(ShortUserConverter.CONVERTER_ID)
	public static class ShortUserConverter extends UserConverter {

		public static final String CONVERTER_ID = "lucas:shortUserConverter";

		public ShortUserConverter() {
			super(Boolean.TRUE);
		}

	}
}
