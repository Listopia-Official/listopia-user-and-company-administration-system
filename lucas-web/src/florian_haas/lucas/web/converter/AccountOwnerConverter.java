package florian_haas.lucas.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import florian_haas.lucas.business.AccountBeanLocal;
import florian_haas.lucas.model.*;
import florian_haas.lucas.web.util.WebUtils;

@FacesConverter(AccountOwnerConverter.CONVERTER_ID)
public class AccountOwnerConverter extends BasicConverter<ReadOnlyAccountOwner> {

	public static final String CONVERTER_ID = "lucas:accountOwnerConverter";

	public AccountOwnerConverter() {
		this.setIsReadonly(Boolean.FALSE);
	}

	@Override
	protected String getString(FacesContext context, UIComponent uiComponent, ReadOnlyAccountOwner value) {
		String ret = WebUtils.getTranslatedMessage("lucas.application.accountOwnerConverter.null");
		if (value != null) {
			switch (value.getOwnerType()) {
				case COMPANY:
					ret = WebUtils.getAsString((ReadOnlyCompany) value, CompanyConverter.CONVERTER_ID);
					break;
				case USER:
					ret = WebUtils.getAsString((ReadOnlyUser) value, UserConverter.CONVERTER_ID);
					break;
			}
			uiComponent.getAttributes().put(CONVERTER_ID + ret, value.getId());
		}
		return ret;
	}

	@Override
	protected ReadOnlyAccountOwner getObject(FacesContext context, UIComponent component, String value) {
		Object key = component.getAttributes().get(CONVERTER_ID + value);
		return key != null ? WebUtils.getCDIManagedBean(AccountBeanLocal.class).findAccountOwnerById(Long.parseLong(key.toString().trim())) : null;
	}

}
