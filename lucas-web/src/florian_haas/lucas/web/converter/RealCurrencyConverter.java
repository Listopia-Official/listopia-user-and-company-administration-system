package florian_haas.lucas.web.converter;

import java.math.BigDecimal;
import java.text.*;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import org.primefaces.component.inputnumber.InputNumber;

import florian_haas.lucas.business.GlobalDataBeanLocal;
import florian_haas.lucas.web.util.WebUtils;

@Named(RealCurrencyConverter.CONVERTER_ID)
@RequestScoped
public class RealCurrencyConverter implements Converter<BigDecimal> {

	public static final String CONVERTER_ID = "realCurrencyConverter";

	public static final String NULL_KEY = "lucas.application.realCurrencyConverter.null";

	@EJB
	private GlobalDataBeanLocal globalData;

	@Override
	public BigDecimal getAsObject(FacesContext context, UIComponent component, String value) {
		return value.equals(WebUtils.getTranslatedMessage(RealCurrencyConverter.NULL_KEY)) ? null : new BigDecimal(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, BigDecimal value) {
		return value != null
				? ((DecimalFormat) NumberFormat.getNumberInstance(context.getViewRoot().getLocale())).format(value)
						+ (!(component != null && component instanceof InputNumber) ? " " + this.globalData.getRealCurrencySymbol() : "")
				: WebUtils.getTranslatedMessage(RealCurrencyConverter.NULL_KEY);
	}
}
