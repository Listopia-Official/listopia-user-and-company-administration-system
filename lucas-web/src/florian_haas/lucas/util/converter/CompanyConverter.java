package florian_haas.lucas.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import florian_haas.lucas.model.Company;
import florian_haas.lucas.util.WebUtils;

@FacesConverter(value = "lucas:companyStringConverter")
public class CompanyConverter extends ReadOnlyConverter {

	public static final String NOT_NULL_KEY = "lucas.application.companyConverter.message";
	public static final String NULL_KEY = "lucas.application.companyConverter.none";

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		Company company = value != null ? (Company) value : null;
		return company != null ? WebUtils.getTranslatedMessage(NOT_NULL_KEY, company.getId(), company.getName())
				: WebUtils.getTranslatedMessage(NULL_KEY);
	}

}
