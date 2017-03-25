package florian_haas.lucas.web.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import florian_haas.lucas.model.User;
import florian_haas.lucas.web.util.WebUtils;

@FacesConverter(value = "lucas:userStringConverter")
public class UserConverter extends ReadOnlyConverter {

	public static final String PUPIL_KEY = "lucas.application.userConverter.pupil";
	public static final String TEACHER_KEY = "lucas.application.userConverter.teacher";
	public static final String GUEST_KEY = "lucas.application.userConverter.guest";
	public static final String NULL_KEY = "lucas.application.userConverter.none";

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
		return key != NULL_KEY ? WebUtils.getTranslatedMessage(key, user.getForename(), user.getSurname(), user.getSchoolClass(), user.getId())
				: WebUtils.getTranslatedMessage(key);
	}

}
