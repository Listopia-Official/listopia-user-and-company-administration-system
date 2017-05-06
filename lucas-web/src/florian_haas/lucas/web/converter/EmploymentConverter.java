package florian_haas.lucas.web.converter;

import javax.faces.convert.FacesConverter;

import florian_haas.lucas.model.ReadOnlyEmployment;

@FacesConverter(EmploymentConverter.CONVERTER_ID)
public class EmploymentConverter extends DefaultConverter<ReadOnlyEmployment> {

	public static final String CONVERTER_ID = "lucas:employmentConverter";

	public EmploymentConverter() {
		super(Boolean.FALSE, "lucas.application.employmentConverter");
	}

	@Override
	protected Object[] getParamsFromValue(ReadOnlyEmployment value) {
		return new Object[] {
				value.getId() };
	}
}
