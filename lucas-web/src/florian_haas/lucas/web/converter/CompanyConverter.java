package florian_haas.lucas.web.converter;

import javax.faces.convert.FacesConverter;

import florian_haas.lucas.business.CompanyBeanLocal;
import florian_haas.lucas.model.ReadOnlyCompany;

@FacesConverter(value = CompanyConverter.CONVERTER_ID, managed = true)
public class CompanyConverter extends DefaultConverter<ReadOnlyCompany> {

	public static final String CONVERTER_ID = "lucas:companyConverter";

	// @EJB
	// private CompanyBeanLocal companyBean;

	public CompanyConverter() {
		super(Boolean.FALSE, "lucas.application.companyConverter", CompanyBeanLocal.class);
	}

	@Override
	protected Object[] getParamsFromValue(ReadOnlyCompany value) {
		return new Object[] {
				value.getId(), value.getName() };
	}

	@Override
	protected ReadOnlyCompany getObjectFromId(Object getter, Long id) {
		return ((CompanyBeanLocal) getter).findById(id);
	}
}
