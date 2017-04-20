package florian_haas.lucas.web.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import florian_haas.lucas.util.Utils;

public abstract class BasicConverter<V> implements Converter {

	private Boolean isReadonly = Boolean.TRUE;
	private final Boolean isShortConverter;
	private final Class<?> valueClass;

	protected BasicConverter() {
		this(Boolean.FALSE);
	}

	protected BasicConverter(Boolean isShortConverter) {
		this.isShortConverter = isShortConverter == null ? Boolean.FALSE : isShortConverter;
		valueClass = getValueClass();
	}

	protected Class<?> getValueClass() {
		return Utils.getClassFromArgs(isShortConverter ? this.getClass().getSuperclass() : this.getClass());
	}

	@Override
	public final Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (this.isReadonly) throw new UnsupportedOperationException("getAsObject is not supported by " + this.getClass().getSimpleName());
		return getObject(context, component, value);
	}

	@Override
	@SuppressWarnings("unchecked")
	public final String getAsString(FacesContext context, UIComponent uiComponent, Object value) {
		if (value != null && !valueClass.isInstance(value))
			throw new IllegalArgumentException(value.getClass().getName() + " cannot be cast to " + valueClass.getName());
		V castValue = (V) value;
		return isShortConverter ? getShortString(context, uiComponent, castValue) : getString(context, uiComponent, castValue);
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
