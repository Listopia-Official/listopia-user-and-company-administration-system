package florian_haas.lucas.web;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import florian_haas.lucas.util.Utils;
import florian_haas.lucas.web.util.WebUtils;

@Named
@ApplicationScoped
public class ConverterBean implements Serializable {

	private static final long serialVersionUID = 5695265421704720573L;

	public static final String NULL_KEY = "lucas.application.converterBean.nullValue";

	public <E extends Enum<E>> String convertEnum(E enumValue) {
		String ret;
		if (enumValue != null) {
			FacesContext context = FacesContext.getCurrentInstance();
			ret = context.getApplication().createConverter(enumValue.getClass()).getAsString(context, UIComponent.getCurrentComponent(context),
					enumValue);
		} else {
			ret = WebUtils.getTranslatedMessage(NULL_KEY);
		}
		return ret;
	}

	public String getAsString(Object object, String converterId) {
		return WebUtils.getAsString(object, converterId);
	}

	public String getStackTrace(Exception e) {
		return Utils.getStackTraceAsString(e);
	}
}
