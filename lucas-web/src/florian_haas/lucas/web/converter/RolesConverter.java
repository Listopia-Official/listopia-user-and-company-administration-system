package florian_haas.lucas.web.converter;

import javax.faces.convert.FacesConverter;

import florian_haas.lucas.model.ReadOnlyLoginUserRole;
import florian_haas.lucas.web.converter.LoginUserRoleConverter.ShortLoginUserRoleConverter;
import florian_haas.lucas.web.util.WebUtils;

@FacesConverter(RolesConverter.CONVERTER_ID)
public class RolesConverter extends CollectionConverter<ReadOnlyLoginUserRole> {

	public static final String CONVERTER_ID = "lucas:rolesConverter";

	public RolesConverter() {
		super("lucas.application.rolesConverter.empty");
	}

	@Override
	protected String entryToString(ReadOnlyLoginUserRole entry) {
		return WebUtils.getAsString(entry, ShortLoginUserRoleConverter.CONVERTER_ID);
	}
}
