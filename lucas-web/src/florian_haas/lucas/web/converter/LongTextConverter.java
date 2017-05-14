package florian_haas.lucas.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import florian_haas.lucas.util.Utils;
import florian_haas.lucas.web.util.WebUtils;

@FacesConverter(LongTextConverter.CONVERTER_ID)
public class LongTextConverter extends BasicConverter<String> {

	public static final String CONVERTER_ID = "lucas:longTextConverter";

	private Integer maxSizeChars = 45;

	@Override
	protected String getString(FacesContext context, UIComponent uiComponent, String value) {
		String converted = WebUtils.getAsString(value, NullConverter.CONVERTER_ID);
		if (converted.length() > maxSizeChars) {
			converted = converted.substring(0, maxSizeChars - 1)
					.concat(WebUtils.getTranslatedMessage("lucas.application.longTextConverter.moreChars"));
		}
		return converted;

	}

	public Integer getMaxSizeChars() {
		return maxSizeChars;
	}

	public void setMaxSizeChars(Integer maxSizeChars) {
		this.maxSizeChars = maxSizeChars;
	}

	@FacesConverter(LongTextConverter2.CONVERTER_ID)
	public static class LongTextConverter2 extends LongTextConverter {

		public static final String CONVERTER_ID = "lucas:longTextConverter2";

		public LongTextConverter2() {
			this.setMaxSizeChars(20);
		}

		@Override
		protected Class<?> getValueClass() {
			return Utils.getClassFromArgs(this.getClass().getSuperclass());
		}

	}

}
