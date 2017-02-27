package florian_haas.lucas.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.*;

import florian_haas.lucas.model.User;
import florian_haas.lucas.util.WebUtils;

@FacesConverter(value = "lucas:userStringConverter")
public class UserConverter implements Converter {

	public static final String PUPIL_KEY = "lucas.application.shortUserConverter.pupil";
	public static final String TEACHER_KEY = "lucas.application.shortUserConverter.teacher";
	public static final String GUEST_KEY = "lucas.application.shortUserConverter.guest";
	public static final String NULL_KEY = "lucas.application.shortUserConverter.none";

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		throw new UnsupportedOperationException("getAsObject is not supported by" + this.getClass().getSimpleName());
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		User user = value != null ? (User) value : null;
		String key = NULL_KEY;
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
		return key != NULL_KEY ? WebUtils.getTranslatedMessage(key, user.getForename(), user.getSurname(), user.getSchoolClass(), user.getId())
				: WebUtils.getTranslatedMessage(key);
	}

}
