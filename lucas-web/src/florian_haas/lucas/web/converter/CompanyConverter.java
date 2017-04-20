package florian_haas.lucas.web.converter;

import javax.faces.convert.FacesConverter;

import florian_haas.lucas.model.Company;

@FacesConverter(CompanyConverter.CONVERTER_ID)
public class CompanyConverter extends DefaultConverter<Company> {

	public static final String CONVERTER_ID = "lucas:companyConverter";

	public CompanyConverter() {
		super(Boolean.FALSE, "lucas.application.companyConverter");
	}

	@Override
	protected Object[] getParamsFromValue(Company value) {
		return new Object[] {
				value.getId(), value.getName() };
	}
}
