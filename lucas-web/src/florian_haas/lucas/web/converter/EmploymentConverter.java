package florian_haas.lucas.web.converter;

import javax.faces.convert.FacesConverter;

import florian_haas.lucas.business.EmploymentBeanLocal;
import florian_haas.lucas.model.ReadOnlyEmployment;

@FacesConverter(value = EmploymentConverter.CONVERTER_ID, managed = true)
public class EmploymentConverter extends DefaultConverter<ReadOnlyEmployment> {

	public static final String CONVERTER_ID = "lucas:employmentConverter";

	// @EJB
	// private EmploymentBeanLocal employmentBean;

	public EmploymentConverter() {
		super(Boolean.FALSE, "lucas.application.employmentConverter", EmploymentBeanLocal.class);
	}

	@Override
	protected Object[] getParamsFromValue(ReadOnlyEmployment value) {
		return new Object[] {
				value.getId() };
	}

	@Override
	protected ReadOnlyEmployment getObjectFromId(Object getter, Long id) {
		return ((EmploymentBeanLocal) getter).findById(id);
	}
}
