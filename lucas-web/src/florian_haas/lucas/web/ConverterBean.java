package florian_haas.lucas.web;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.*;
import javax.faces.convert.Converter;
import javax.inject.Named;

import florian_haas.lucas.persistence.EnumQueryComparator;
import florian_haas.lucas.util.Utils;
import florian_haas.lucas.web.converter.EnumConverter;
import florian_haas.lucas.web.util.WebUtils;

@Named
@ApplicationScoped
public class ConverterBean implements Serializable {

	private static final long serialVersionUID = 5695265421704720573L;

	public static final String NULL_KEY = "lucas.application.converterBean.nullValue";

	public Converter<?> getConverter(String converterId) {
		return WebUtils.getConverterFromId(converterId);
	}

	@SuppressWarnings({
			"unchecked", "rawtypes" })
	public Converter getManagedConverter(String converterId) {
		BeanManager manager = CDI.current().getBeanManager();
		Bean bean = manager.getBeans(converterId).iterator().next();
		return (Converter) manager.getReference(bean, bean.getBeanClass(), manager.createCreationalContext(bean));
	}

	public <E extends Enum<E>> String convertEnum(E enumValue) {
		return WebUtils.getAsString(enumValue, EnumConverter.CONVERTER_ID);
	}

	public String getAsString(Object object, String converterId) {
		return WebUtils.getAsString(object, converterId);
	}

	public String getStackTrace(Exception e) {
		return Utils.getStackTraceAsString(e);
	}

	public Boolean isDisabled(EnumQueryComparator comparator) {
		return comparator == EnumQueryComparator.EMPTY || comparator == EnumQueryComparator.NOT_EMPTY || comparator == EnumQueryComparator.NULL
				|| comparator == EnumQueryComparator.NOT_NULL;
	}
}
