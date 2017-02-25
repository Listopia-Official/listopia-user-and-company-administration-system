package florian_haas.lucas.util;

import java.util.*;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.validation.*;

import org.apache.shiro.ShiroException;

public class WebUtils {

	public static void addInformationMessage(String message) {
		addMessage(FacesMessage.SEVERITY_INFO, message);
	}

	public static void addTranslatedInformationMessage(String message, Object... params) {
		addTranslatedMessage(FacesMessage.SEVERITY_INFO, message, params);
	}

	public static void addWarningMessage(String message) {
		addMessage(FacesMessage.SEVERITY_WARN, message);
	}

	public static void addTranslatedWarningMessage(String message, Object... params) {
		addTranslatedMessage(FacesMessage.SEVERITY_WARN, message, params);
	}

	public static void addErrorMessage(String message) {
		addMessage(FacesMessage.SEVERITY_ERROR, message);
	}

	public static void addTranslatedErrorMessage(String message, Object... params) {
		addTranslatedMessage(FacesMessage.SEVERITY_ERROR, message, params);
	}

	public static void addFatalMessage(String message) {
		addMessage(FacesMessage.SEVERITY_FATAL, message);
	}

	public static void addTranslatedFatalMessage(String message, Object... params) {
		addTranslatedMessage(FacesMessage.SEVERITY_FATAL, message, params);
	}

	private static void addTranslatedMessage(FacesMessage.Severity severity, String key, Object... params) {
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
			if (params[i] != null) message = message.replaceAll("\\?" + i, params[i].toString());
		}
		return message;
	}

	public static String getTranslatedMessage(String key) {
		return FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{msg['" + key + "']}",
				String.class);
	}

	public static boolean executeTask(FailableTask task, String successMessageKey, String warnMessageKey, String failMessageKey,
			Object... argParams) {
		boolean success = false;
		List<Object> paramsList = new ArrayList<>();
		paramsList.addAll(Arrays.asList(argParams));
		Object[] params = paramsList.toArray();
		try {
			success = task.executeTask(paramsList);
			params = paramsList.toArray();
			if (success && successMessageKey != null) {
				WebUtils.addTranslatedInformationMessage(successMessageKey, params);
			} else if (warnMessageKey != null) {
				WebUtils.addTranslatedInformationMessage(warnMessageKey, params);
			}
		}
		catch (ConstraintViolationException e) {
			for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
				WebUtils.addErrorMessage(getTranslatedMessage(failMessageKey, params) + violation.getPropertyPath() + " " + violation.getMessage());
			}
		}
		catch (ShiroException e2) {
			WebUtils.addErrorMessage(getTranslatedMessage(failMessageKey, params) + getTranslatedMessage("lucas.application.message.accessDenied"));
		}
		catch (Exception e3) {
			WebUtils.addFatalMessage(getTranslatedMessage(failMessageKey, params) + e3.getLocalizedMessage());
		}
		return success;
	}

}
