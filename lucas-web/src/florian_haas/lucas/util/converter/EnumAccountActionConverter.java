package florian_haas.lucas.util.converter;

import javax.faces.convert.FacesConverter;

import florian_haas.lucas.model.EnumAccountAction;

@FacesConverter(forClass = EnumAccountAction.class)
public class EnumAccountActionConverter extends EnumConverter<EnumAccountAction> {

	public EnumAccountActionConverter() throws Exception {
		super();
	}

}
