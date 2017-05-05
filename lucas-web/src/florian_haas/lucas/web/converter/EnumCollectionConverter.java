package florian_haas.lucas.web.converter;

import javax.faces.convert.FacesConverter;

import florian_haas.lucas.web.util.WebUtils;

@FacesConverter(EnumCollectionConverter.CONVERTER_ID)
public class EnumCollectionConverter extends CollectionConverter<Enum<?>> {

	public static final String CONVERTER_ID = "lucas:enumsConverter";

	public EnumCollectionConverter() {
		super("lucas.application.enumsConverter.empty");
	}

	@Override
	protected String entryToString(Enum<?> entry) {
		return WebUtils.getAsString(entry, EnumConverter.CONVERTER_ID);
	}

}
