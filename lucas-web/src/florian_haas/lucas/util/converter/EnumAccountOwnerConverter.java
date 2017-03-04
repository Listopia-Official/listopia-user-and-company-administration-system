package florian_haas.lucas.util.converter;

import javax.faces.convert.FacesConverter;

import florian_haas.lucas.model.EnumAccountOwnerType;

@FacesConverter(forClass = EnumAccountOwnerType.class)
public class EnumAccountOwnerConverter extends EnumConverter<EnumAccountOwnerType> {

	public EnumAccountOwnerConverter() throws Exception {
		super();
	}

}
