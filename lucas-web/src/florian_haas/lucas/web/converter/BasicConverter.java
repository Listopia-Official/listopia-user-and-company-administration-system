package florian_haas.lucas.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public abstract class BasicConverter<V> implements Converter<V> {

	private Boolean isReadonly = Boolean.TRUE;
	private final Boolean isShortConverter;

	protected BasicConverter() {
		this(Boolean.FALSE);
	}

	protected BasicConverter(Boolean isShortConverter) {
		this.isShortConverter = isShortConverter == null ? Boolean.FALSE : isShortConverter;
	}

	@Override
	public final V getAsObject(FacesContext context, UIComponent component, String value) {
		if (this.isReadonly) throw new UnsupportedOperationException("getAsObject is not supported by " + this.getClass().getSimpleName());
		return getObject(context, component, value);
	}

	@Override
	public final String getAsString(FacesContext context, UIComponent uiComponent, V value) {
		return isShortConverter ? getShortString(context, uiComponent, value) : getString(context, uiComponent, value);
	}

	protected abstract String getString(FacesContext context, UIComponent uiComponent, V value);

	protected String getShortString(FacesContext context, UIComponent uiComponent, V value) {
		return getString(context, uiComponent, value);
	}

	protected V getObject(FacesContext context, UIComponent component, String value) {
		return null;
	}

	public final Boolean getIsShortConverter() {
		return this.isShortConverter;
	}

	public Boolean getIsReadonly() {
		return isReadonly;
	}

	public void setIsReadonly(Boolean isReadonly) {
		this.isReadonly = isReadonly == null ? Boolean.TRUE : isReadonly;
	}
}
