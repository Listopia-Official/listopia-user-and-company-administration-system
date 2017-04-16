package florian_haas.lucas.web.util.converter;

import javax.faces.convert.FacesConverter;

import florian_haas.lucas.model.EnumCompanyType;

@FacesConverter(forClass = EnumCompanyType.class)
public class EnumCompanyTypeConverter extends EnumConverter<EnumCompanyType> {

	public EnumCompanyTypeConverter() throws Exception {
		super();
	}

}
