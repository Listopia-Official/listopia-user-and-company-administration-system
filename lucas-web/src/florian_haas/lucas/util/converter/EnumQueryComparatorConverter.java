package florian_haas.lucas.util.converter;

import javax.faces.convert.FacesConverter;

import florian_haas.lucas.database.EnumQueryComparator;

@FacesConverter(forClass = EnumQueryComparator.class)
public class EnumQueryComparatorConverter extends EnumConverter<EnumQueryComparator> {

	public EnumQueryComparatorConverter() throws Exception {
		super();
	}
}
