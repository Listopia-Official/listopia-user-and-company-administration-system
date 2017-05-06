package florian_haas.lucas.web.converter;

import javax.faces.convert.FacesConverter;

import florian_haas.lucas.model.ReadOnlyCompany;
import florian_haas.lucas.web.util.WebUtils;

@FacesConverter(CompaniesConverter.CONVERTER_ID)
public class CompaniesConverter extends CollectionConverter<ReadOnlyCompany> {

	public static final String CONVERTER_ID = "lucas:companiesConverter";

	public CompaniesConverter() {
		super("lucas.application.companiesConverter.empty");
	}

	@Override
	protected String entryToString(ReadOnlyCompany entry) {
		return WebUtils.getAsString(entry, CompanyConverter.CONVERTER_ID);
	}
}
