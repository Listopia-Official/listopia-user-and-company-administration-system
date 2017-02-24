package florian_haas.lucas.util.converter;

import javax.faces.convert.FacesConverter;

import florian_haas.lucas.model.EnumSchoolClass;

@FacesConverter(forClass = EnumSchoolClass.class)
public class EnumSchoolClassConverter extends EnumConverter<EnumSchoolClass> {

	public EnumSchoolClassConverter() throws Exception {
		super();
	}

}
