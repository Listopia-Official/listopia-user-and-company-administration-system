package florian_haas.lucas.web.converter;

import javax.faces.convert.FacesConverter;

import florian_haas.lucas.model.ReadOnlyAccount;
import florian_haas.lucas.web.util.WebUtils;

@FacesConverter(AccountConverter.CONVERTER_ID)
public class AccountConverter extends DefaultConverter<ReadOnlyAccount> {

	public static final String CONVERTER_ID = "lucas:accountConverter";

	public AccountConverter() {
		super(Boolean.FALSE, "lucas.application.accountConverter");
	}

	@Override
	protected Object[] getParamsFromValue(ReadOnlyAccount value) {
		return new Object[] {
				value.getId(), WebUtils.getAsString(value.getOwner(), AccountOwnerConverter.CONVERTER_ID) };
	}

}
