package florian_haas.lucas.web.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;
import java.util.function.*;
import java.util.logging.*;

import javax.ejb.EJBException;
import javax.enterprise.inject.spi.CDI;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.imageio.ImageIO;
import javax.persistence.PersistenceException;
import javax.validation.*;

import org.apache.shiro.*;
import org.primefaces.model.*;

import florian_haas.lucas.business.LucasException;
import florian_haas.lucas.model.EntityBase;
import florian_haas.lucas.security.EnumPermission;
import florian_haas.lucas.util.Utils;
import florian_haas.lucas.web.util.converter.CurrencyConverter;

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

	public static final String SUCCESS_MESSAGE_SUFFIX = ".success";
	public static final String WARN_MESSAGE_SUFFIX = ".warn";
	public static final String FAIL_MESSAGE_SUFFIX = ".fail";

	public static boolean executeTask(FailableTask task, String messagePrefix, List<Object> argParams) {
		return executeTask(task, true, true, messagePrefix, DEFAULT_MESSAGE_COMPONENT_ID, null, argParams);
	}

	public static boolean executeTask(FailableTask task, String messagePrefix,
			BiFunction<LucasException, List<Object>, String> exceptionMessageGetter, List<Object> argParams) {
		return executeTask(task, true, true, messagePrefix, DEFAULT_MESSAGE_COMPONENT_ID, exceptionMessageGetter, argParams);
	}

	public static boolean executeTask(FailableTask task, String messagePrefix) {
		return executeTask(task, true, true, messagePrefix, DEFAULT_MESSAGE_COMPONENT_ID, null, new ArrayList<>());
	}

	public static boolean executeTask(FailableTask task, String messagePrefix,
			BiFunction<LucasException, List<Object>, String> exceptionMessageGetter) {
		return executeTask(task, true, true, messagePrefix, DEFAULT_MESSAGE_COMPONENT_ID, exceptionMessageGetter, new ArrayList<>());
	}

	public static boolean executeTask(FailableTask task, String messagePrefix, String messageComponentId, List<Object> argParams) {
		return executeTask(task, true, true, messagePrefix, messageComponentId, null, argParams);
	}

	public static boolean executeTask(FailableTask task, String messagePrefix, String messageComponentId) {
		return executeTask(task, true, true, messagePrefix, messageComponentId, null, new ArrayList<>());
	}

	public static boolean executeTask(FailableTask task, boolean useSuccessMessage, boolean useWarnMessage, String messagePrefix,
			List<Object> argParams) {
		return executeTask(task, useSuccessMessage, useWarnMessage, messagePrefix, DEFAULT_MESSAGE_COMPONENT_ID, null, argParams);
	}

	public static boolean executeTask(FailableTask task, boolean useSuccessMessage, boolean useWarnMessage, String messagePrefix,
			BiFunction<LucasException, List<Object>, String> exceptionMessageGetter, List<Object> argParams) {
		return executeTask(task, useSuccessMessage, useWarnMessage, messagePrefix, DEFAULT_MESSAGE_COMPONENT_ID, exceptionMessageGetter, argParams);
	}

	public static boolean executeTask(FailableTask task, boolean useSuccessMessage, boolean useWarnMessage, String messagePrefix) {
		return executeTask(task, useSuccessMessage, useWarnMessage, messagePrefix, DEFAULT_MESSAGE_COMPONENT_ID, null, new ArrayList<>());
	}

	public static boolean executeTask(FailableTask task, boolean useSuccessMessage, boolean useWarnMessage, String messagePrefix,
			BiFunction<LucasException, List<Object>, String> exceptionMessageGetter) {
		return executeTask(task, useSuccessMessage, useWarnMessage, messagePrefix, DEFAULT_MESSAGE_COMPONENT_ID, exceptionMessageGetter,
				new ArrayList<>());
	}

	public static boolean executeTask(FailableTask task, boolean useSuccessMessage, boolean useWarnMessage, String messagePrefix,
			String messageComponentId, BiFunction<LucasException, List<Object>, String> exceptionMessageGetter, List<Object> argParams) {
		String successMessageKey = messagePrefix.concat(SUCCESS_MESSAGE_SUFFIX);
		String warnMessageKey = messagePrefix.concat(WARN_MESSAGE_SUFFIX);
		String failMessageKey = messagePrefix.concat(FAIL_MESSAGE_SUFFIX);

		boolean success = false;
		Object[] params = argParams.toArray();
		try {
			success = task.executeTask(argParams);
			params = argParams.toArray();
			if (success && useSuccessMessage) {
				WebUtils.addTranslatedInformationMessage(successMessageKey, messageComponentId, params);
			} else if (useWarnMessage) {
				WebUtils.addTranslatedWarningMessage(warnMessageKey, messageComponentId, params);
			}
		}
		catch (ConstraintViolationException e) {
			handleConstraintViolationException(e, failMessageKey, messageComponentId, params);
		}
		catch (ShiroException e2) {
			WebUtils.addErrorMessage(getTranslatedMessage(failMessageKey, params) + getTranslatedMessage("lucas.application.message.accessDenied")
					+ e2.getLocalizedMessage(), messageComponentId);
		}
		catch (LucasException e45) {
			WebUtils.addErrorMessage(exceptionMessageGetter != null ? exceptionMessageGetter.apply(e45, argParams)
					: getTranslatedMessage(failMessageKey, params) + e45.getLocalizedMessage(), messageComponentId);
		}
		catch (EJBException e25) {
			Throwable cause = e25.getCause();
			boolean log = true;
			if (cause.getCause() instanceof ConstraintViolationException) {
				handleConstraintViolationException((ConstraintViolationException) cause.getCause(), failMessageKey, messageComponentId, params);
				log = false;
			}
			if (log) {
				Logger.getAnonymousLogger().log(Level.SEVERE, e25, e25::getMessage);
				WebUtils.addFatalMessage(getTranslatedMessage(failMessageKey, params) + Utils.getStackTraceAsString(e25), messageComponentId);
			}
		}
		catch (PersistenceException e3) {
			e3.printStackTrace();
			WebUtils.addErrorMessage(getTranslatedMessage(failMessageKey, params) + getTranslatedMessage("lucas.application.message.persistenceError")
					+ e3.getLocalizedMessage(), messageComponentId);
		}
		catch (Exception e3) {
			Logger.getAnonymousLogger().log(Level.SEVERE, e3, e3::getMessage);
			WebUtils.addFatalMessage(getTranslatedMessage(failMessageKey, params) + Utils.getStackTraceAsString(e3), messageComponentId);
		}
		return success;

	}

	private static void handleConstraintViolationException(ConstraintViolationException exception, String failMessageKey, String messageComponentId,
			Object[] params) {
		for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
			WebUtils.addErrorMessage(getTranslatedMessage(failMessageKey, params) + violation.getMessage(), messageComponentId);
		}
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

	public static BufferedImage getBufferedImage(Image image, int type) {
		BufferedImage tmp = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
		Graphics g = tmp.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return tmp;
	}

	public static String getAsString(Object object, Converter converter) {
		FacesContext context = FacesContext.getCurrentInstance();
		return converter.getAsString(context, UIComponent.getCurrentComponent(context), object);
	}

	public static String getAsString(Object object, String converterId) {
		return getAsString(object, getConverterFromId(converterId));
	}

	public static Converter getConverterFromId(String converterId) {
		return FacesContext.getCurrentInstance().getApplication().createConverter(converterId);
	}

	public static <E extends EntityBase> void refreshEntities(Class<E> entityClass, List<E> entityList, List<E> selectedEntities,
			List<E> additionalEntitiesToRefresh, Function<Long, E> entityDao, Boolean onlySelected) {
		refreshEntities(entityClass, entityList, selectedEntities, additionalEntitiesToRefresh, (Map<Long, E>) null, entityDao, onlySelected);
	}

	public static <E extends EntityBase> void refreshEntities(Class<E> entityClass, List<E> entityList, List<E> selectedEntities,
			Function<Long, E> entityDao, Boolean onlySelected) {
		refreshEntities(entityClass, entityList, selectedEntities, null, (Map<Long, E>) null, entityDao, onlySelected);
	}

	public static <E extends EntityBase> void refreshEntities(Class<E> entityClass, List<E> entityList, List<E> selectedEntities, E replacement,
			Function<Long, E> entityDao, Boolean onlySelected) {
		refreshEntities(entityClass, entityList, selectedEntities, null, Utils.asMap(replacement.getId(), replacement), entityDao, onlySelected);
	}

	public static <E extends EntityBase> void refreshEntities(Class<E> entityClass, List<E> entityList, List<E> selectedEntities,
			List<E> additionalEntitiesToRefresh, E replacement, Function<Long, E> entityDao, Boolean onlySelected) {
		refreshEntities(entityClass, entityList, selectedEntities, additionalEntitiesToRefresh, Utils.asMap(replacement.getId(), replacement),
				entityDao, onlySelected);
	}

	public static <E extends EntityBase> void refreshEntities(Class<E> entityClass, List<E> entityList, List<E> selectedEntities,
			List<E> additionalEntitiesToRefresh, Map<Long, E> replacements, Function<Long, E> entityDao, Boolean onlySelected) {
		List<E> allEntities = new ArrayList<>();
		allEntities.addAll(entityList);
		if (additionalEntitiesToRefresh != null) allEntities.addAll(additionalEntitiesToRefresh);
		ListIterator<E> it = allEntities.listIterator();
		while (it.hasNext()) {
			E tmpEntity = it.next();
			Long id = tmpEntity.getId();
			if (getCDIManagerBean(EntityBean.class).exists(id, entityClass)) {
				if (onlySelected ? (selectedEntities.contains(tmpEntity)
						|| (additionalEntitiesToRefresh != null && additionalEntitiesToRefresh.contains(tmpEntity))) : true) {
					E refreshed = replacements != null && replacements.get(id) != null ? replacements.get(id) : entityDao.apply(id);
					if (entityList.contains(tmpEntity)) entityList.set(entityList.indexOf(tmpEntity), refreshed);
					if (selectedEntities.contains(tmpEntity)) selectedEntities.set(selectedEntities.indexOf(tmpEntity), refreshed);
				}
			} else {
				if (entityList.contains(tmpEntity)) entityList.remove(tmpEntity);
				if (selectedEntities.contains(tmpEntity)) selectedEntities.remove(tmpEntity);
			}
		}
	}

	public static String getCurrencyAsString(BigDecimal currency) {
		FacesContext context = FacesContext.getCurrentInstance();
		return getCDIManagerBean(CurrencyConverter.class).getAsString(context, UIComponent.getCurrentComponent(context), currency);
	}

	public static <T> T getCDIManagerBean(Class<T> clazz) {
		return CDI.current().select(clazz).get();
	}

	public static Boolean isPermitted(EnumPermission... permissions) {
		for (EnumPermission permission : permissions) {
			if (!SecurityUtils.getSubject().isPermitted(permission.getPermissionString())) return false;
		}
		return true;
	}

}
