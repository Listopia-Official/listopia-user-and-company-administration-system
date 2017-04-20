package florian_haas.lucas.web.util.converter;

import javax.faces.convert.FacesConverter;

@FacesConverter(RanksConverter.CONVERTER_ID)
public class RanksConverter extends CollectionConverter<String> {

	public static final String CONVERTER_ID = "lucas:ranksConverter";

	public RanksConverter() {
		super("lucas.application.ranksConverter.empty");
	}

}
