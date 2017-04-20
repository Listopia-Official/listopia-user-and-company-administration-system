package florian_haas.lucas.web.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import florian_haas.lucas.model.*;
import florian_haas.lucas.web.util.WebUtils;

@FacesConverter(AccountOwnerConverter.CONVERTER_ID)
public class AccountOwnerConverter extends BasicConverter<AccountOwner> {

	public static final String CONVERTER_ID = "lucas:accountOwnerConverter";

	@Override
	protected String getString(FacesContext context, UIComponent uiComponent, AccountOwner value) {
		String ret = WebUtils.getTranslatedMessage("lucas.application.accountOwnerConverter.null");
		if (value != null) {
			switch (value.getOwnerType()) {
				case COMPANY:
					ret = WebUtils.getAsString((Company) value, CompanyConverter.CONVERTER_ID);
					break;
				case USER:
					ret = WebUtils.getAsString((User) value, UserConverter.CONVERTER_ID);
					break;
			}
		}
		return ret;
	}

}
