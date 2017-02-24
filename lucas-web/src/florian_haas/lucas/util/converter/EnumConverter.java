package florian_haas.lucas.util.converter;

import java.lang.reflect.Field;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import florian_haas.lucas.util.*;

public abstract class EnumConverter<E extends Enum<E>> extends javax.faces.convert.EnumConverter {

	public static final String MESSAGE_PREFIX = "lucas.application.";
	public static final String MESSAGE_NULL_KEY = "NULL";

	protected final Class<E> enumClass;
	protected final String messageName;

	// Better solution?
	protected EnumConverter() throws Exception {
		this.enumClass = Utils.getClassFromArgs(this.getClass());
		try {
			Field targetClass = javax.faces.convert.EnumConverter.class.getDeclaredField("targetClass");
			targetClass.setAccessible(true);
			targetClass.set(this, enumClass);
		}
		catch (Exception e) {
			throw e;
		}
		String enumClassName = this.enumClass.getSimpleName();
		char firstChar = enumClassName.charAt(0);
		this.messageName = this.enumClass.getSimpleName().replace(firstChar, Character.toLowerCase(firstChar));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String enumString = super.getAsString(context, component, value);
		if (enumString.trim().isEmpty()) enumString = MESSAGE_NULL_KEY;
		return getTranslatedName(enumString);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if ((context == null) || (component == null)) throw new NullPointerException();
		value = value.trim();

		if (value == null || value.isEmpty() || value.equals(WebUtils.getTranslatedMessage(MESSAGE_PREFIX + messageName + "." + MESSAGE_NULL_KEY)))
			return null;

		Enum<E> matchingEnum = null;

		for (Enum<E> enumValue : getEnumValues()) {
			String translatedName = getTranslatedName(enumValue.name());
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

	private String getTranslatedName(String enumString) {
		return WebUtils.getTranslatedMessage(MESSAGE_PREFIX + messageName + "." + enumString);
	}

	private Enum<E>[] getEnumValues() {
		return enumClass.getEnumConstants();
	}

}
