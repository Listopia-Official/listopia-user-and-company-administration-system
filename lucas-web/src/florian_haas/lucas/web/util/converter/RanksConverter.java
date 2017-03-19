package florian_haas.lucas.web.util.converter;

import javax.faces.convert.FacesConverter;

@FacesConverter(value = "lucas:ranksConverter")
public class RanksConverter extends CollectionConverter<String> {

	public static final String NO_VALUE_KEY = "lucas.application.ranksConverter.noValue";

	public RanksConverter() {
		super(NO_VALUE_KEY);
	}

}
