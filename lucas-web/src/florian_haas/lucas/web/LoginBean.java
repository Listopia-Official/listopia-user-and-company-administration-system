package florian_haas.lucas.web;

import java.io.IOException;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.*;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.apache.shiro.SecurityUtils;
import org.primefaces.model.StreamedContent;

import florian_haas.lucas.business.*;
import florian_haas.lucas.model.LoginUser;
import florian_haas.lucas.model.validation.*;
import florian_haas.lucas.security.EnumPermission;
import florian_haas.lucas.util.Utils;
import florian_haas.lucas.web.util.WebUtils;
import florian_haas.lucas.web.util.converter.LoginUserConverter.ShortLoginUserConverter;
import florian_haas.lucas.web.util.converter.ThemeConverter;

@Named
@RequestScoped
public class LoginBean {

	@EJB
	private EntityBeanLocal entityBean;

	@EJB
	private LoginBeanLocal loginBean;

	@EJB
	private UserBeanLocal userBean;

	@EJB
	private GlobalDataBeanLocal globalDataBean;

	public Boolean getIsAnonymous() {
		return SecurityUtils.getSubject() == null || SecurityUtils.getSubject().getPrincipals() == null
				|| SecurityUtils.getSubject().getPrincipals().isEmpty();
	}

	private Object getPrincipal() {
		return getIsAnonymous() ? null : SecurityUtils.getSubject().getPrincipal();
	}

	public String getSimpleUsername() {
		return getIsAnonymous() ? "Anonymous" : getPrincipal().toString();
	}

	private Long getUserId(String principal) {
		String simpleUsername = principal;
		Long id = null;
		if (simpleUsername != null) {
			try {
				id = Long.valueOf(simpleUsername);
			}
			catch (Exception e) {}
		}
		return id;
	}

	public String getAdvancedUsername() {
		String ret = getSimpleUsername();
		if (ret != null) {
			LoginUser user = loginBean.findLoginUserByUsername(ret);
			if (user != null) {
				ret = WebUtils.getAsString(user, ShortLoginUserConverter.CONVERTER_ID);
			}
		}
		return ret;
	}

	public Boolean getIsUserInDatabase() {
		String username = getSimpleUsername();
		return !getIsAnonymous() && username != null && loginBean.findLoginUserByUsername(username) != null;
	}

	public StreamedContent getImage() {
		StreamedContent ret = null;
		Long id = getUserId(getSimpleUsername());
		if (id != null && SecurityUtils.getSubject().isPermitted(EnumPermission.USER_GET_IMAGE_FROM_ID.getPermissionString())) {
			try {
				ret = WebUtils.getJPEGImage(userBean.getImage(id));
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ret == null ? WebUtils.getSVGImage(
				FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/resources/images/user_male.svg")) : ret;
	}

	public void logout() throws IOException {
		String username = getAdvancedUsername();
		loginBean.logout();
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(WebUtils.getTranslatedMessage("lucas.application.logout.message", username)));
		ExternalContext externalContext = context.getExternalContext();
		externalContext.getFlash().setKeepMessages(true);
		externalContext.redirect("/lucas-web/login.jsf");
	}

	public Long getIdleTimeout() {
		return globalDataBean.getMaxIdleTime();
	}

	public Boolean hasPermission(EnumPermission permission) {
		return WebUtils.isPermitted(permission);
	}

	public Boolean hasPermissionsOr(List<EnumPermission> permissions) {
		for (EnumPermission permission : permissions) {
			if (hasPermission(permission)) return true;
		}
		return false;
	}

	public Boolean hasPermissionsAnd(List<EnumPermission> permissions) {
		for (EnumPermission permission : permissions) {
			if (!hasPermission(permission)) return false;
		}
		return true;
	}

	public String getUiTheme() {
		String username = getSimpleUsername();
		String theme = null;
		if (username != null && !username.trim().isEmpty() && WebUtils.isPermitted(EnumPermission.LOGIN_USER_FIND_BY_USERNAME)) {
			LoginUser user = loginBean.findLoginUserByUsername(username);
			theme = user != null ? user.getUiTheme() : null;
		}
		return theme != null ? theme : globalDataBean.getDefaultUITheme();
	}

	@NotNull
	@ValidUITheme
	private String preferencesScreenSelectedUITheme = null;

	public void preferencesScreenSetUITheme() {
		WebUtils.executeTask(params -> {
			String username = getSimpleUsername();
			if (username != null && !username.trim().isEmpty()) {
				LoginUser user = loginBean.findLoginUserByUsername(username);
				params.add(username);
				params.add(WebUtils.getAsString(preferencesScreenSelectedUITheme, ThemeConverter.CONVERTER_ID));
				if (user != null) return loginBean.setUITheme(user.getId(), preferencesScreenSelectedUITheme);
			}
			return true;
		}, "lucas.application.preferencesScreen.changeTheme");

	}

	public List<String> getUIThemes() {
		return globalDataBean.getUIThemes();
	}

	public String getPreferencesScreenSelectedUITheme() {
		return preferencesScreenSelectedUITheme;
	}

	public void setPreferencesScreenSelectedUITheme(String preferencesScreenSelectedUITheme) {
		this.preferencesScreenSelectedUITheme = preferencesScreenSelectedUITheme;
	}

	@ValidUnhashedPassword
	private char[] changePasswordDialogOldPassword = null;

	@ValidUnhashedPassword
	private char[] changePasswordDialogPassword = null;

	@PostConstruct
	public void onPostConstruct() {
		preferencesScreenSelectedUITheme = getUiTheme();
	}

	public char[] getChangePasswordDialogOldPassword() {
		return changePasswordDialogOldPassword;
	}

	public void setChangePasswordDialogOldPassword(char[] changePasswordDialogOldPassword) {
		this.changePasswordDialogOldPassword = changePasswordDialogOldPassword;
	}

	public char[] getChangePasswordDialogPassword() {
		return changePasswordDialogPassword;
	}

	public void setChangePasswordDialogPassword(char[] changePasswordDialogPassword) {
		this.changePasswordDialogPassword = changePasswordDialogPassword;
	}

	public void initChangePasswordDialog() {
		changePasswordDialogOldPassword = null;
		changePasswordDialogPassword = null;
	}

	public void changePassword() {
		WebUtils.executeTask(params -> {
			loginBean.changePassword(changePasswordDialogOldPassword, changePasswordDialogPassword);
			return true;
		}, "lucas.application.preferencesScreen.changePassword", (exception, params) -> {
			return WebUtils.getTranslatedMessage("lucas.application.preferencesScreen.changePassword.wrongOldPassword");
		}, Utils.asList(getAdvancedUsername()));
		Arrays.fill(changePasswordDialogPassword, 'c');
		Arrays.fill(changePasswordDialogOldPassword, 'c');
	}
}
