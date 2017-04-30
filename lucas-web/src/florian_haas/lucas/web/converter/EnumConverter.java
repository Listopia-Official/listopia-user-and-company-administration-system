package florian_haas.lucas.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.*;

import florian_haas.lucas.web.util.WebUtils;

@FacesConverter(EnumConverter.CONVERTER_ID)
public class EnumConverter implements Converter {

	public static final String CONVERTER_ID = "lucas:enumConverter";

	public static final String ENUM_ATTRIBUTE_KEY = "lucas:enumConverterAttribute";

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (!(value instanceof Enum)) throw new IllegalArgumentException(value.getClass().getName() + " cannot be cast to Enum");
			component.getAttributes().put(ENUM_ATTRIBUTE_KEY, value.getClass());
		}
		return getTranslatedName((Enum<?>) value);
	}

	@Override
	@SuppressWarnings({
			"unchecked", "rawtypes" })
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.trim().isEmpty() || value.equals(getTranslatedName(null))) return null;

		value = value.trim();
		Class<Enum> enumClass = (Class<Enum>) component.getAttributes().get(ENUM_ATTRIBUTE_KEY);

		Enum<?> matchingEnum = null;

		for (Enum<?> enumValue : getEnumValues(enumClass)) {
			String translatedName = getTranslatedName(enumValue);
			if (value.equals(translatedName)) {
				matchingEnum = enumValue;
			}
		}

		if (matchingEnum == null) {
			try {
				matchingEnum = Enum.valueOf(enumClass, value);
			}
			catch (IllegalArgumentException e) {
				throw new ConverterException(
						this.getClass().getName() + ": Could not convert value \"" + value + "\"to enum from class \"" + enumClass.getName() + "\"");
			}
		}
		return matchingEnum;
	}

	private String getTranslatedName(Enum<?> enumValue) {
		return WebUtils.getTranslatedMessage(enumValue == null ? "lucas.application.enumConverter.null"
				: "lucas.application." + getEnumClassName(enumValue) + "." + enumValue.name());
	}

	private String getEnumClassName(Enum<?> enumInstance) {
		String clazzName = enumInstance.getClass().getSimpleName();
		char firstChar = clazzName.charAt(0);
		return clazzName.replaceFirst(Character.toString(firstChar), Character.toString(Character.toLowerCase(firstChar)));
	}

	@SuppressWarnings("rawtypes")
	private Enum<?>[] getEnumValues(Class<Enum> enumClass) {
		return enumClass.getEnumConstants();
	}

}
