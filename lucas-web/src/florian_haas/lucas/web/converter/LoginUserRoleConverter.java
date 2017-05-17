package florian_haas.lucas.web.converter;

import javax.faces.convert.FacesConverter;

import florian_haas.lucas.business.LoginUserRoleBeanLocal;
import florian_haas.lucas.model.ReadOnlyLoginUserRole;

@FacesConverter(value = LoginUserRoleConverter.CONVERTER_ID, managed = true)
public class LoginUserRoleConverter extends DefaultConverter<ReadOnlyLoginUserRole> {

	public static final String CONVERTER_ID = "lucas:loginUserRoleConverter";

	// @EJB
	// private LoginUserRoleBeanLocal loginUserRoleBean;

	public LoginUserRoleConverter() {
		this(Boolean.FALSE);
	}

	protected LoginUserRoleConverter(Boolean isShortConverter) {
		super(isShortConverter, "lucas.application.loginUserRoleConverter", LoginUserRoleBeanLocal.class);
	}

	@Override
	protected Object[] getParamsFromValue(ReadOnlyLoginUserRole value) {
		return new Object[] {
				value.getId(), value.getName() };
	}

	@Override
	protected ReadOnlyLoginUserRole getObjectFromId(Object getter, Long id) {
		return ((LoginUserRoleBeanLocal) getter).findById(id);
	}

	@FacesConverter(value = ShortLoginUserRoleConverter.CONVERTER_ID, managed = true)
	public static class ShortLoginUserRoleConverter extends LoginUserRoleConverter {

		public static final String CONVERTER_ID = "lucas:shortLoginUserRoleConverter";

		public ShortLoginUserRoleConverter() {
			super(Boolean.TRUE);
		}

	}

}
