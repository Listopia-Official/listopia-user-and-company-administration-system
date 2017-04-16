package florian_haas.lucas.web.util.converter;

import javax.faces.convert.FacesConverter;

import florian_haas.lucas.model.Company;
import florian_haas.lucas.web.util.WebUtils;

@FacesConverter(value = "lucas:companiesConverter")
public class CompaniesConverter extends CollectionConverter<Company> {

	public static final String NO_VALUE_KEY = "lucas.application.companiesConverter.noValue";

	public CompaniesConverter() {
		super(NO_VALUE_KEY);
	}

	@Override
	protected String entryToString(Company entry) {
		return WebUtils.getAsString(entry, "lucas:companyStringConverter");
	}
}
