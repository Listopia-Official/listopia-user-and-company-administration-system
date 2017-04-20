package florian_haas.lucas.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import florian_haas.lucas.web.util.WebUtils;

public abstract class DefaultConverter<V> extends BasicConverter<V> {

	private final String langKeyBase;
	private final String normalLangKey;
	private final String nullLangKey;
	private final String shortLangKey;

	public static final String NULL_KEY_SUFFIX = ".null";
	public static final String SHORT_KEY_SUFFIX = ".short";
	public static final String NORMAL_KEY_SUFFIX = ".normal";

	protected DefaultConverter(Boolean isShortConverter, String langKeyBase) {
		super(isShortConverter);
		this.langKeyBase = langKeyBase;
		this.normalLangKey = this.langKeyBase.concat(NORMAL_KEY_SUFFIX);
		this.nullLangKey = this.langKeyBase.concat(NULL_KEY_SUFFIX);
		this.shortLangKey = this.langKeyBase.concat(SHORT_KEY_SUFFIX);
	}

	@Override
	protected String getString(FacesContext context, UIComponent uiComponent, V value) {
		return getStringFromValue(value, normalLangKey, nullLangKey);
	}

	@Override
	protected String getShortString(FacesContext context, UIComponent uiComponent, V value) {
		return getStringFromValue(value, shortLangKey, nullLangKey);
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
