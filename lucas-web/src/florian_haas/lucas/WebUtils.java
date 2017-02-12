package florian_haas.lucas;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class WebUtils {

	public static void addInformationMessage(String message) {
		addMessage(FacesMessage.SEVERITY_INFO, message);
	}

	public static void addInformationMessage(String message, Object... params) {
		addMessage(FacesMessage.SEVERITY_INFO, message, params);
	}

	public static void addWarningMessage(String message) {
		addMessage(FacesMessage.SEVERITY_WARN, message);
	}

	public static void addWarningMessage(String message, Object... params) {
		addMessage(FacesMessage.SEVERITY_WARN, message, params);
	}

	public static void addErrorMessage(String message) {
		addMessage(FacesMessage.SEVERITY_ERROR, message);
	}

	public static void addErrorMessage(String message, Object... params) {
		addMessage(FacesMessage.SEVERITY_ERROR, message, params);
	}

	public static void addFatalMessage(String message) {
		addMessage(FacesMessage.SEVERITY_FATAL, message);
	}

	public static void addFatalMessage(String message, Object... params) {
		addMessage(FacesMessage.SEVERITY_FATAL, message, params);
	}

	private static void addMessage(FacesMessage.Severity severity, String key, Object... params) {
		addMessage(severity, getTranslatedMessage(key, params));
	}

	private static void addMessage(FacesMessage.Severity severity, String message) {
		String titlePrefix = severity == FacesMessage.SEVERITY_WARN ? "warn"
				: severity == FacesMessage.SEVERITY_ERROR ? "error" : severity == FacesMessage.SEVERITY_FATAL ? "fatal" : "info";
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(severity, getTranslatedMessage("lucas.application.message." + titlePrefix), message));
	}

	public static String getTranslatedMessage(String key, Object... params) {
		String message = getTranslatedMessage(key);
		for (int i = 0; i < params.length; i++) {
			message = message.replaceAll("\\?" + i, params[i].toString());
		}
		return message;
	}

	public static String getTranslatedMessage(String key) {
		return FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{msg['" + key + "']}",
				String.class);
	}

}
