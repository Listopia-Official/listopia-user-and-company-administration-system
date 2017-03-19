package florian_haas.lucas.util.converter;

import javax.faces.convert.FacesConverter;

import florian_haas.lucas.model.LoginUserRole;
import florian_haas.lucas.util.WebUtils;

@FacesConverter(value = "lucas:rolesConverter")
public class RolesConverter extends CollectionConverter<LoginUserRole> {

	public static final String NO_VALUE_KEY = "lucas.application.rolesConverter.noValue";

	public RolesConverter() {
		super(NO_VALUE_KEY);
	}

	@Override
	protected String entryToString(LoginUserRole entry) {
		return WebUtils.getAsString(entry, "lucas:shortLoginUserRoleConverter");
	}
}
