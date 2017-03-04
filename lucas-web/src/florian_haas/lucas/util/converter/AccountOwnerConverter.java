package florian_haas.lucas.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import florian_haas.lucas.model.*;
import florian_haas.lucas.util.WebUtils;

@FacesConverter(value = "lucas:accountOwnerStringConverter")
public class AccountOwnerConverter extends ReadOnlyConverter {

	public static final String NULL_KEY = "lucas.application.accountOwnerConverter.none";

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		AccountOwner owner = value != null ? (AccountOwner) value : null;
		String ret = WebUtils.getTranslatedMessage(NULL_KEY);
		if (owner != null) {
			switch (owner.getOwnerType()) {
				case COMPANY:
					ret = WebUtils.getAsString((Company) owner, "lucas:companyStringConverter");
					break;
				case USER:
					ret = WebUtils.getAsString((User) owner, "lucas:userStringConverter");
					break;
			}
		}
		return ret;
	}

}
