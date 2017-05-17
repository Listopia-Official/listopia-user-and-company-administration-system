package florian_haas.lucas.web.converter;

import javax.faces.convert.FacesConverter;

import florian_haas.lucas.business.LoginBeanLocal;
import florian_haas.lucas.model.ReadOnlyLoginUser;
import florian_haas.lucas.web.converter.UserConverter.ShortUserConverter;
import florian_haas.lucas.web.util.WebUtils;

@FacesConverter(value = LoginUserConverter.CONVERTER_ID, managed = true)
public class LoginUserConverter extends DefaultConverter<ReadOnlyLoginUser> {

	public static final String CONVERTER_ID = "lucas:loginUserConverter";

	// @EJB
	// private LoginBeanLocal loginBean;

	public LoginUserConverter() {
		this(Boolean.FALSE);
	}

	protected LoginUserConverter(Boolean isShortConverter) {
		super(isShortConverter, "lucas.application.loginUserConverter", LoginBeanLocal.class);
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

	@Override
	protected ReadOnlyLoginUser getObjectFromId(Object getter, Long id) {
		return ((LoginBeanLocal) getter).findLoginUserById(id);
	}

	@FacesConverter(ShortLoginUserConverter.CONVERTER_ID)
	public static class ShortLoginUserConverter extends LoginUserConverter {

		public static final String CONVERTER_ID = "lucas:shortLoginUserConverter";

		public ShortLoginUserConverter() {
			super(Boolean.TRUE);
		}

	}

}
