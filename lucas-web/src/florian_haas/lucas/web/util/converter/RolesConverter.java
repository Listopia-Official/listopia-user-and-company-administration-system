package florian_haas.lucas.web.util.converter;

import javax.faces.convert.FacesConverter;

import florian_haas.lucas.model.LoginUserRole;
import florian_haas.lucas.web.util.WebUtils;
import florian_haas.lucas.web.util.converter.LoginUserRoleConverter.ShortLoginUserRoleConverter;

@FacesConverter(RolesConverter.CONVERTER_ID)
public class RolesConverter extends CollectionConverter<LoginUserRole> {

	public static final String CONVERTER_ID = "lucas:rolesConverter";

	public RolesConverter() {
		super("lucas.application.rolesConverter.empty");
	}

	@Override
	protected String entryToString(LoginUserRole entry) {
		return WebUtils.getAsString(entry, ShortLoginUserRoleConverter.CONVERTER_ID);
	}
}
