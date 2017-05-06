package florian_haas.lucas.web.converter;

import javax.faces.convert.FacesConverter;

import florian_haas.lucas.model.ReadOnlyLoginUser;
import florian_haas.lucas.web.converter.UserConverter.ShortUserConverter;
import florian_haas.lucas.web.util.WebUtils;

@FacesConverter(LoginUserConverter.CONVERTER_ID)
public class LoginUserConverter extends DefaultConverter<ReadOnlyLoginUser> {

	public static final String CONVERTER_ID = "lucas:loginUserConverter";

	public LoginUserConverter() {
		this(Boolean.FALSE);
	}

	protected LoginUserConverter(Boolean isShortConverter) {
		super(isShortConverter, "lucas.application.loginUserConverter");
	}

	@Override
	protected Object[] getParamsFromValue(ReadOnlyLoginUser value) {
		return new Object[] {
				value.getId(), (value.getUser() == null ? value.getUsername()
						: WebUtils.getAsString(value.getUser(),
								this.getIsShortConverter() ? ShortUserConverter.CONVERTER_ID : UserConverter.CONVERTER_ID)) };
	}

	@Override
	protected String getModifiedDefaultKey(ReadOnlyLoginUser value, String defaultKey) {
		return defaultKey.concat(value.getUser() == null ? ".default" : ".bound");
	}

	@FacesConverter(ShortLoginUserConverter.CONVERTER_ID)
	public static class ShortLoginUserConverter extends LoginUserConverter {

		public static final String CONVERTER_ID = "lucas:shortLoginUserConverter";

		public ShortLoginUserConverter() {
			super(Boolean.TRUE);
		}

	}

}
