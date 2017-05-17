package florian_haas.lucas.web.converter;

import javax.faces.convert.FacesConverter;

import florian_haas.lucas.business.AccountBeanLocal;
import florian_haas.lucas.model.ReadOnlyAccount;
import florian_haas.lucas.web.util.WebUtils;

@FacesConverter(value = AccountConverter.CONVERTER_ID, managed = true)
public class AccountConverter extends DefaultConverter<ReadOnlyAccount> {

	public static final String CONVERTER_ID = "lucas:accountConverter";

	// @EJB
	// private AccountBeanLocal accountBean;

	public AccountConverter() {
		super(Boolean.FALSE, "lucas.application.accountConverter", AccountBeanLocal.class);
	}

	@Override
	protected Object[] getParamsFromValue(ReadOnlyAccount value) {
		return new Object[] {
				value.getId(), WebUtils.getAsString(value.getOwner(), AccountOwnerConverter.CONVERTER_ID) };
	}

	@Override
	protected ReadOnlyAccount getObjectFromId(Object getter, Long id) {
		return ((AccountBeanLocal) getter).findById(id);
	}

}
