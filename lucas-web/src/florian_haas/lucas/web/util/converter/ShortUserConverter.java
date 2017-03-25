package florian_haas.lucas.web.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import florian_haas.lucas.model.User;
import florian_haas.lucas.web.util.WebUtils;

@FacesConverter(value = "lucas:shortUserStringConverter")
public class ShortUserConverter extends ReadOnlyConverter {

	public static final String PUPIL_KEY = "lucas.application.shortUserConverter.pupil";
	public static final String TEACHER_KEY = "lucas.application.shortUserConverter.teacher";
	public static final String GUEST_KEY = "lucas.application.shortUserConverter.guest";
	public static final String NULL_KEY = "lucas.application.shortUserConverter.none";

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		User user = value != null ? (User) value : null;
		String key = NULL_KEY;
		if (user != null) {
			switch (user.getUserType()) {
				case PUPIL:
					key = PUPIL_KEY;
					break;
				case TEACHER:
					key = TEACHER_KEY;
					break;
				case GUEST:
					key = GUEST_KEY;
					break;
				default:
					key = NULL_KEY;
					break;
			}
		}
		return key != NULL_KEY ? WebUtils.getTranslatedMessage(key, user.getForename(), user.getSurname(), user.getId())
				: WebUtils.getTranslatedMessage(key);
	}

}
