package florian_haas.lucas.util;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.validation.*;

import org.apache.shiro.ShiroException;
import org.primefaces.model.*;

public class WebUtils {

	public static final String DEFAULT_MESSAGE_COMPONENT_ID = "defaultMessage";

	public static final String GROWL_MESSAGE_COMPONENT_ID = "growlMessage";

	public static final String STICKY_GROWL_MESSAGE_COMPONENT_ID = "stickyGrowlMessage";

	public static void addDefaultInformationMessage(String message) {
		addInformationMessage(message, DEFAULT_MESSAGE_COMPONENT_ID);
	}

	public static void addDefaultTranslatedInformationMessage(String message, Object... params) {
		addTranslatedInformationMessage(message, DEFAULT_MESSAGE_COMPONENT_ID, params);
	}

	public static void addDefaultWarningMessage(String message) {
		addWarningMessage(message, DEFAULT_MESSAGE_COMPONENT_ID);
	}

	public static void addDefaultTranslatedWarningMessage(String message, Object... params) {
		addTranslatedWarningMessage(message, DEFAULT_MESSAGE_COMPONENT_ID, params);
	}

	public static void addDefaultErrorMessage(String message) {
		addErrorMessage(message, DEFAULT_MESSAGE_COMPONENT_ID);
	}

	public static void addDefaultTranslatedErrorMessage(String message, Object... params) {
		addTranslatedErrorMessage(message, DEFAULT_MESSAGE_COMPONENT_ID, params);
	}

	public static void addDefaultFatalMessage(String message) {
		addFatalMessage(message, DEFAULT_MESSAGE_COMPONENT_ID);
	}

	public static void addDefaultTranslatedFatalMessage(String message, Object... params) {
		addTranslatedFatalMessage(message, DEFAULT_MESSAGE_COMPONENT_ID, params);
	}

	public static void addGrowlInformationMessage(String message) {
		addInformationMessage(message, GROWL_MESSAGE_COMPONENT_ID);
	}

	public static void addGrowlTranslatedInformationMessage(String message, Object... params) {
		addTranslatedInformationMessage(message, GROWL_MESSAGE_COMPONENT_ID, params);
	}

	public static void addGrowlWarningMessage(String message) {
		addWarningMessage(message, GROWL_MESSAGE_COMPONENT_ID);
	}

	public static void addGrowlTranslatedWarningMessage(String message, Object... params) {
		addTranslatedWarningMessage(message, GROWL_MESSAGE_COMPONENT_ID, params);
	}

	public static void addGrowlErrorMessage(String message) {
		addErrorMessage(message, GROWL_MESSAGE_COMPONENT_ID);
	}

	public static void addGrowlTranslatedErrorMessage(String message, Object... params) {
		addTranslatedErrorMessage(message, GROWL_MESSAGE_COMPONENT_ID, params);
	}

	public static void addGrowlFatalMessage(String message) {
		addFatalMessage(message, GROWL_MESSAGE_COMPONENT_ID);
	}

	public static void addGrowlTranslatedFatalMessage(String message, Object... params) {
		addTranslatedFatalMessage(message, GROWL_MESSAGE_COMPONENT_ID, params);
	}

	public static void addStickyGrowlInformationMessage(String message) {
		addInformationMessage(message, STICKY_GROWL_MESSAGE_COMPONENT_ID);
	}

	public static void addStickyGrowlTranslatedInformationMessage(String message, Object... params) {
		addTranslatedInformationMessage(message, STICKY_GROWL_MESSAGE_COMPONENT_ID, params);
	}

	public static void addStickyGrowlWarningMessage(String message) {
		addWarningMessage(message, STICKY_GROWL_MESSAGE_COMPONENT_ID);
	}

	public static void addStickyGrowlTranslatedWarningMessage(String message, Object... params) {
		addTranslatedWarningMessage(message, STICKY_GROWL_MESSAGE_COMPONENT_ID, params);
	}

	public static void addStickyGrowlErrorMessage(String message) {
		addErrorMessage(message, STICKY_GROWL_MESSAGE_COMPONENT_ID);
	}

	public static void addStickyGrowlTranslatedErrorMessage(String message, Object... params) {
		addTranslatedErrorMessage(message, STICKY_GROWL_MESSAGE_COMPONENT_ID, params);
	}

	public static void addStickyGrowlFatalMessage(String message) {
		addFatalMessage(message, STICKY_GROWL_MESSAGE_COMPONENT_ID);
	}

	public static void addStickyGrowlTranslatedFatalMessage(String message, Object... params) {
		addTranslatedFatalMessage(message, STICKY_GROWL_MESSAGE_COMPONENT_ID, params);
	}

	public static void addInformationMessage(String message, String clientComponent) {
		addMessage(FacesMessage.SEVERITY_INFO, message, clientComponent);
	}

	public static void addTranslatedInformationMessage(String message, String clientComponent, Object... params) {
		addTranslatedMessage(FacesMessage.SEVERITY_INFO, message, clientComponent, params);
	}

	public static void addWarningMessage(String message, String clientComponent) {
		addMessage(FacesMessage.SEVERITY_WARN, message, clientComponent);
	}

	public static void addTranslatedWarningMessage(String message, String clientComponent, Object... params) {
		addTranslatedMessage(FacesMessage.SEVERITY_WARN, message, clientComponent, params);
	}

	public static void addErrorMessage(String message, String clientComponent) {
		addMessage(FacesMessage.SEVERITY_ERROR, message, clientComponent);
	}

	public static void addTranslatedErrorMessage(String message, String clientComponent, Object... params) {
		addTranslatedMessage(FacesMessage.SEVERITY_ERROR, message, clientComponent, params);
	}

	public static void addFatalMessage(String message, String clientComponent) {
		addMessage(FacesMessage.SEVERITY_FATAL, message, clientComponent);
	}

	public static void addTranslatedFatalMessage(String message, String clientComponent, Object... params) {
		addTranslatedMessage(FacesMessage.SEVERITY_FATAL, message, clientComponent, params);
	}

	private static void addTranslatedMessage(FacesMessage.Severity severity, String key, String clientComponent, Object... params) {
		addMessage(severity, getTranslatedMessage(key, params), clientComponent);
	}

	private static void addMessage(FacesMessage.Severity severity, String message, String clientComponent) {
		String titlePrefix = severity == FacesMessage.SEVERITY_WARN ? "warn"
				: severity == FacesMessage.SEVERITY_ERROR ? "error" : severity == FacesMessage.SEVERITY_FATAL ? "fatal" : "info";
		FacesContext.getCurrentInstance().addMessage(clientComponent,
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
				WebUtils.addDefaultTranslatedInformationMessage(successMessageKey, params);
			} else if (warnMessageKey != null) {
				WebUtils.addDefaultTranslatedInformationMessage(warnMessageKey, params);
			}
		}
		catch (ConstraintViolationException e) {
			for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
				WebUtils.addDefaultErrorMessage(
						getTranslatedMessage(failMessageKey, params) + violation.getPropertyPath() + " " + violation.getMessage());
			}
		}
		catch (ShiroException e2) {
			WebUtils.addDefaultErrorMessage(
					getTranslatedMessage(failMessageKey, params) + getTranslatedMessage("lucas.application.message.accessDenied"));
		}
		catch (Exception e3) {
			WebUtils.addDefaultFatalMessage(getTranslatedMessage(failMessageKey, params) + e3.getLocalizedMessage());
		}
		return success;
	}

	public static final String JPEG_MIME = "image/jpeg";

	public static final String JPEG_TYPE = "JPEG";

	public static final String SVG_MIME = "image/svg+xml";

	public static StreamedContent getJPEGImage(InputStream data) {
		return getDataAsStreamedContent(data, JPEG_MIME);
	}

	public static StreamedContent getJPEGImage(byte[] data) {
		return getDataAsStreamedContent(data, JPEG_MIME);
	}

	public static StreamedContent getSVGImage(byte[] data) {
		return getDataAsStreamedContent(data, SVG_MIME);
	}

	public static StreamedContent getSVGImage(InputStream data) {
		return getDataAsStreamedContent(data, SVG_MIME);
	}

	public static StreamedContent getDataAsStreamedContent(byte[] data, String mime) {
		return getDataAsStreamedContent(data != null ? new ByteArrayInputStream(data) : null, mime);
	}

	public static StreamedContent getDataAsStreamedContent(InputStream data, String mime) {
		if (data != null) return new DefaultStreamedContent(data, mime);
		return null;
	}

	public static BufferedImage getBufferedImageFromBytes(byte[] data) throws Exception {
		BufferedImage ret = null;
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		ret = ImageIO.read(new ByteArrayInputStream(data));
		in.close();
		return ret;
	}

	public static byte[] convertBufferedImageToBytes(BufferedImage image, String type) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(image, type, out);
		out.close();
		return out.toByteArray();
	}

}
