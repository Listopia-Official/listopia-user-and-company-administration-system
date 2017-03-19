package florian_haas.lucas.web.util.converter;

import javax.faces.convert.FacesConverter;

import florian_haas.lucas.model.EnumAccountActionType;

@FacesConverter(forClass = EnumAccountActionType.class)
public class EnumAccountActionTypeConverter extends EnumConverter<EnumAccountActionType> {

	public EnumAccountActionTypeConverter() throws Exception {
		super();
	}

}
