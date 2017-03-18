package florian_haas.lucas.util.converter;

import javax.faces.convert.FacesConverter;

@FacesConverter(value = "lucas:ranksConverter")
public class RanksConverter extends CollectionConverter {

	public static final String NO_VALUE_KEY = "lucas.application.ranksConverter.noValue";

	public RanksConverter() {
		super(NO_VALUE_KEY);
	}

}
