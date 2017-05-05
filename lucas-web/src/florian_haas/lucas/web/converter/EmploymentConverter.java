package florian_haas.lucas.web.converter;

import javax.faces.convert.FacesConverter;

import florian_haas.lucas.model.Employment;

@FacesConverter(EmploymentConverter.CONVERTER_ID)
public class EmploymentConverter extends DefaultConverter<Employment> {

	public static final String CONVERTER_ID = "lucas:employmentConverter";

	public EmploymentConverter() {
		super(Boolean.FALSE, "lucas.application.employmentConverter");
	}

	@Override
	protected Object[] getParamsFromValue(Employment value) {
		return new Object[] {
				value.getId() };
	}
}
