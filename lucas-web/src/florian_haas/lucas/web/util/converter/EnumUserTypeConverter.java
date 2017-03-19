package florian_haas.lucas.web.util.converter;

import javax.faces.convert.FacesConverter;

import florian_haas.lucas.model.EnumUserType;

@FacesConverter(forClass = EnumUserType.class)
public class EnumUserTypeConverter extends EnumConverter<EnumUserType> {

	public EnumUserTypeConverter() throws Exception {
		super();
	}
}
