package florian_haas.lucas.util.converter;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import org.primefaces.component.inputnumber.InputNumber;

import florian_haas.lucas.business.GlobalDataBeanLocal;
import florian_haas.lucas.util.WebUtils;

@Named(value = "currencyConverter")
@RequestScoped
public class CurrencyConverter implements Converter {

	public static final String NULL_KEY = "lucas.application.currencyConverter.none";

	@EJB
	private GlobalDataBeanLocal globalData;

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		return arg2.equals(WebUtils.getTranslatedMessage(NULL_KEY)) ? null : new BigDecimal(arg2);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return arg2 != null
				? ((DecimalFormat) DecimalFormat.getNumberInstance(arg0.getViewRoot().getLocale())).format((BigDecimal) arg2)
						+ (!(arg1 != null && arg1 instanceof InputNumber) ? globalData.getCurrencySymbol() : "")
				: WebUtils.getTranslatedMessage(NULL_KEY);
	}
}
