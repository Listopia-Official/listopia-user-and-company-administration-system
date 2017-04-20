package florian_haas.lucas.web.converter;

import javax.faces.convert.FacesConverter;

import florian_haas.lucas.model.User;

@FacesConverter(UserConverter.CONVERTER_ID)
public class UserConverter extends DefaultConverter<User> {

	public static final String CONVERTER_ID = "lucas:userConverter";

	public UserConverter() {
		this(Boolean.FALSE);
	}

	protected UserConverter(Boolean isShortConverter) {
		super(isShortConverter, "lucas.application.userConverter");
	}

	@Override
	protected Object[] getParamsFromValue(User value) {
		return new Object[] {
				value.getForename(), value.getSurname(), value.getSchoolClass(), value.getId() };
	}

	@Override
	protected String getModifiedDefaultKey(User value, String defaultKey) {
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

	@FacesConverter(ShortUserConverter.CONVERTER_ID)
	public static class ShortUserConverter extends UserConverter {

		public static final String CONVERTER_ID = "lucas:shortUserConverter";

		public ShortUserConverter() {
			super(Boolean.TRUE);
		}

	}
}
