package florian_haas.lucas.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import florian_haas.lucas.model.ReadOnlyEntity;
import florian_haas.lucas.web.util.WebUtils;

public abstract class DefaultConverter<V extends ReadOnlyEntity> extends BasicConverter<V> {

	private final String langKeyBase;
	private final String normalLangKey;
	private final String nullLangKey;
	private final String shortLangKey;
	private Object entityGetter;

	public static final String NULL_KEY_SUFFIX = ".null";
	public static final String SHORT_KEY_SUFFIX = ".short";
	public static final String NORMAL_KEY_SUFFIX = ".normal";
	public static final String ID_ATTRIBUTE_KEY = "lucas:DefaultConverterEntityId";

	protected DefaultConverter(Boolean isShortConverter, String langKeyBase, Class<?> entityGetterClass) {
		super(isShortConverter);
		this.setIsReadonly(Boolean.FALSE);
		this.langKeyBase = langKeyBase;
		this.normalLangKey = this.langKeyBase.concat(NORMAL_KEY_SUFFIX);
		this.nullLangKey = this.langKeyBase.concat(NULL_KEY_SUFFIX);
		this.shortLangKey = this.langKeyBase.concat(SHORT_KEY_SUFFIX);
		entityGetter = WebUtils.getCDIManagedBean(entityGetterClass);
	}

	@Override
	protected String getString(FacesContext context, UIComponent uiComponent, V value) {
		return addStringToAttributes(getStringFromValue(value, normalLangKey, nullLangKey), value, uiComponent);
	}

	@Override
	protected String getShortString(FacesContext context, UIComponent uiComponent, V value) {
		return addStringToAttributes(getStringFromValue(value, shortLangKey, nullLangKey), value, uiComponent);
	}

	private String addStringToAttributes(String string, V value, UIComponent component) {
		if (string != null) {
			component.getAttributes().put(ID_ATTRIBUTE_KEY + string, (value != null ? value.getId() : -1l));
		}
		return string;
	}

	protected final String getStringFromValue(V value, String defaultKey, String nullKey) {
		return value == null ? WebUtils.getTranslatedMessage(nullKey)
				: WebUtils.getTranslatedMessage(getModifiedDefaultKey(value, defaultKey), getParamsFromValue(value));
	}

	protected String getModifiedDefaultKey(V value, String defaultKey) {
		return defaultKey;
	}

	protected Object[] getParamsFromValue(V value) {
		return new Object[] {};
	}

	@Override
	protected V getObject(FacesContext context, UIComponent component, String value) {
		String key = ID_ATTRIBUTE_KEY + value;
		if (component.getAttributes().containsKey(key)) return getObjectFromId(entityGetter, (Long) component.getAttributes().get(key));
		return null;
	}

	protected abstract V getObjectFromId(Object entityGetter, Long id);

	public final String getLangKeyBase() {
		return langKeyBase;
	}

	public final String getNormalLangKey() {
		return normalLangKey;
	}

	public String getNullLangKey() {
		return nullLangKey;
	}

	public String getShortLangKey() {
		return shortLangKey;
	}

}
