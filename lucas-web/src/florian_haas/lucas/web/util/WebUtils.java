package florian_haas.lucas.web.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
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
import florian_haas.lucas.model.ReadOnlyEntity;
import florian_haas.lucas.security.EnumPermission;
import florian_haas.lucas.util.Utils;
import florian_haas.lucas.web.converter.*;

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
			if (params[i] != null) message = message.replace("?" + i, params[i].toString());
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
		catch (ConstraintViolationException constraintViolationException) {
			handleConstraintViolations(Utils.extractConstraintViolations(constraintViolationException), failMessageKey, messageComponentId, params);
		}
		catch (ShiroException shiroException) {
			Set<ConstraintViolation<?>> violations = Utils.extractConstraintViolations(shiroException);
			if (violations.isEmpty()) {
				WebUtils.addErrorMessage(getTranslatedMessage(failMessageKey, params) + getTranslatedMessage("lucas.application.message.accessDenied")
						+ shiroException.getLocalizedMessage(), messageComponentId);
			} else {
				handleConstraintViolations(Utils.extractConstraintViolations(shiroException), failMessageKey, messageComponentId, params);
			}
		}
		catch (LucasException lucasException) {
			Set<ConstraintViolation<?>> violations = Utils.extractConstraintViolations(lucasException);
			if (violations.isEmpty()) {
				String message = exceptionMessageGetter != null ? exceptionMessageGetter.apply(lucasException, argParams) : null;
				WebUtils.addErrorMessage(
						message != null ? message : getTranslatedMessage(failMessageKey, params) + lucasException.getLocalizedMessage(),
						messageComponentId);
			} else {
				handleConstraintViolations(Utils.extractConstraintViolations(lucasException), failMessageKey, messageComponentId, params);
			}
		}
		catch (EJBException ejbException) {
			Set<ConstraintViolation<?>> violations = Utils.extractConstraintViolations(ejbException);
			if (violations.isEmpty()) {
				Logger.getAnonymousLogger().log(Level.SEVERE, ejbException, ejbException::getMessage);
				WebUtils.addFatalMessage(getTranslatedMessage(failMessageKey, params) + "\n" + Utils.getStackTraceAsString(ejbException),
						messageComponentId);
			} else {
				handleConstraintViolations(violations, failMessageKey, messageComponentId, params);
			}
		}
		catch (PersistenceException persistenceException) {
			Set<ConstraintViolation<?>> violations = Utils.extractConstraintViolations(persistenceException);
			persistenceException.printStackTrace();
			if (violations.isEmpty()) {
				WebUtils.addErrorMessage(getTranslatedMessage(failMessageKey, params)
						+ getTranslatedMessage("lucas.application.message.persistenceError") + persistenceException.getLocalizedMessage(),
						messageComponentId);
			} else {
				handleConstraintViolations(Utils.extractConstraintViolations(persistenceException), failMessageKey, messageComponentId, params);
			}
		}
		catch (Exception exception) {
			Set<ConstraintViolation<?>> violations = Utils.extractConstraintViolations(exception);
			if (violations.isEmpty()) {
				Logger.getAnonymousLogger().log(Level.SEVERE, exception, exception::getMessage);
				WebUtils.addFatalMessage(getTranslatedMessage(failMessageKey, params) + "\n" + Utils.getStackTraceAsString(exception),
						messageComponentId);
			} else {
				handleConstraintViolations(Utils.extractConstraintViolations(exception), failMessageKey, messageComponentId, params);
			}
		}
		return success;

	}

	private static void handleConstraintViolations(Set<ConstraintViolation<?>> violations, String failMessageKey, String messageComponentId,
			Object[] params) {
		for (ConstraintViolation<?> violation : violations) {
			WebUtils.addErrorMessage(getTranslatedMessage(failMessageKey, params) + violation.getMessage(), messageComponentId);
		}
	}

	public static final String JPEG_MIME = "image/jpeg";

	public static final String PNG_MIME = "image/png";

	public static final String JPEG_TYPE = "JPEG";

	public static final String PNG_TYPE = "PNG";

	public static final String SVG_MIME = "image/svg+xml";

	public static final String CSV_MIME = "text/csv";

	public static final String TEXT_MIME = "text";

	public static final String CSV_TYPE = "CSV";

	public static final String PDF_MIME = "application/pdf";

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

	public static BufferedImage deepCopy(BufferedImage img) {
		return new BufferedImage(img.getColorModel(), img.copyData(null), img.isAlphaPremultiplied(), null);
	}

	public static <T> String getAsString(T object, Converter<T> converter) {
		FacesContext context = FacesContext.getCurrentInstance();
		return converter.getAsString(context, UIComponent.getCurrentComponent(context), object);
	}

	public static String getAsString(Object object, String converterId) {
		return getAsString(object, getConverterFromId(converterId));
	}

	public static <T> T getAsObject(String value, Converter<T> converter) {
		FacesContext context = FacesContext.getCurrentInstance();
		return converter.getAsObject(context, UIComponent.getCurrentComponent(context), value);
	}

	public static Object getAsObject(String string, String converterId) {
		return getAsObject(string, getConverterFromId(converterId));
	}

	@SuppressWarnings("unchecked")
	public static Converter<Object> getConverterFromId(String converterId) {
		return (Converter<Object>) (Converter<?>) FacesContext.getCurrentInstance().getApplication().createConverter(converterId);
	}

	public static <E extends ReadOnlyEntity> void refreshEntities(Class<E> entityClass, List<E> entityList, List<E> selectedEntities,
			List<E> additionalEntitiesToRefresh, Function<Long, E> entityDao, Boolean onlySelected) {
		refreshEntities(entityClass, entityList, selectedEntities, additionalEntitiesToRefresh, (Map<Long, E>) null, entityDao, onlySelected);
	}

	public static <E extends ReadOnlyEntity> void refreshEntities(Class<E> entityClass, List<E> entityList, List<E> selectedEntities,
			Function<Long, E> entityDao, Boolean onlySelected) {
		refreshEntities(entityClass, entityList, selectedEntities, null, (Map<Long, E>) null, entityDao, onlySelected);
	}

	public static <E extends ReadOnlyEntity> void refreshEntities(Class<E> entityClass, List<E> entityList, List<E> selectedEntities, E replacement,
			Function<Long, E> entityDao, Boolean onlySelected) {
		refreshEntities(entityClass, entityList, selectedEntities, null, Utils.asMap(replacement.getId(), replacement), entityDao, onlySelected);
	}

	public static <E extends ReadOnlyEntity> void refreshEntities(Class<E> entityClass, List<E> entityList, List<E> selectedEntities,
			List<E> additionalEntitiesToRefresh, E replacement, Function<Long, E> entityDao, Boolean onlySelected) {
		refreshEntities(entityClass, entityList, selectedEntities, additionalEntitiesToRefresh, Utils.asMap(replacement.getId(), replacement),
				entityDao, onlySelected);
	}

	public static <E extends ReadOnlyEntity> void refreshEntities(Class<E> entityClass, List<E> entityList, List<E> selectedEntities,
			List<E> additionalEntitiesToRefresh, Map<Long, E> replacements, Function<Long, E> entityDao, Boolean onlySelected) {
		List<E> allEntities = new ArrayList<>();
		allEntities.addAll(entityList);
		if (additionalEntitiesToRefresh != null) allEntities.addAll(additionalEntitiesToRefresh);
		ListIterator<E> it = allEntities.listIterator();
		while (it.hasNext()) {
			E tmpEntity = it.next();
			Long id = tmpEntity.getId();
			if (getCDIManagedBean(EntityBean.class).exists(id, entityClass)) {
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
		return getCDIManagedBean(CurrencyConverter.class).getAsString(context, UIComponent.getCurrentComponent(context), currency);
	}

	public static String getRealCurrencyAsString(BigDecimal currency) {
		FacesContext context = FacesContext.getCurrentInstance();
		return getCDIManagedBean(RealCurrencyConverter.class).getAsString(context, UIComponent.getCurrentComponent(context), currency);
	}

	public static <T> T getCDIManagedBean(Class<T> clazz) {
		return CDI.current().select(clazz).get();
	}

	public static Boolean isPermitted(EnumPermission... permissions) {
		for (EnumPermission permission : permissions) {
			if (!SecurityUtils.getSubject().isPermitted(permission.getPermissionString())) return false;
		}
		return true;
	}

	public static List<List<String>> parseCSV(String content, String delim) throws Exception {
		List<List<String>> ret = new ArrayList<>();
		try (StringReader tmpReader = new StringReader(content); BufferedReader reader = new BufferedReader(tmpReader)) {
			String read = null;
			while ((read = reader.readLine()) != null) {
				read = read.replace("\uFEFF", "");
				List<String> line = new ArrayList<>();
				StringTokenizer tokenizer = new StringTokenizer(read, delim);
				while (tokenizer.hasMoreTokens()) {
					line.add(tokenizer.nextToken().trim());
				}
				ret.add(line);
			}
		}
		catch (Exception e) {
			throw e;
		}
		return ret;
	}

	public static final List<String> CHARSETS = new ArrayList<>();

	static {
		CHARSETS.add(StandardCharsets.UTF_8.name());
		CHARSETS.add(StandardCharsets.UTF_16.name());
		CHARSETS.add(StandardCharsets.US_ASCII.name());
		CHARSETS.add(StandardCharsets.ISO_8859_1.name());
		CHARSETS.add(StandardCharsets.UTF_16BE.name());
		CHARSETS.add(StandardCharsets.UTF_16LE.name());
	}

}
