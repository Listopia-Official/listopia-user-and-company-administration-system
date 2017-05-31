package florian_haas.lucas.web;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.NotBlank;

import florian_haas.lucas.business.*;
import florian_haas.lucas.security.EnumPermission;
import florian_haas.lucas.validation.*;
import florian_haas.lucas.web.util.WebUtils;

@Named
@ViewScoped
public class GlobalDataBean implements Serializable {

	private static final long serialVersionUID = -3081303288228555145L;

	@EJB
	private GlobalDataBeanLocal globalDataBean;

	@EJB
	private AccountBeanLocal accountBean;

	@NotNull
	@Min(1)
	private Long maxIdleTime = 30000l;

	@NotBlank
	private String currencySymbol = " Gd";

	@NotNull
	@DecimalMin(value = "0", inclusive = false)
	private BigDecimal transactionLimit = BigDecimal.TEN;

	@NotNull
	@Min(50)
	private Integer maxUserImageWidth = 300;

	@NotNull
	@Min(50)
	private Integer maxUserImageHeight = 300;

	@NotNull
	@DecimalMin(value = "0", inclusive = false)
	private BigDecimal minimumWage = BigDecimal.ONE;

	@NotNull
	@Min(1)
	private Long minTimePresent = 171_000L;

	@NotNull
	private List<@NotBlankString @TypeNotNull String> uiThemes = Arrays.asList("aristo", "afterwork", "bootatrap", "delta", "omega");

	@ValidUITheme
	private String defaultUiTheme = "omega";

	private List<String> defaultUiThemeThemesList = new ArrayList<>();

	@NotNull
	@Min(1)
	private Integer optimalManagerCount = 2;

	@NotNull
	@ValidSchoolGrade
	private Integer minCivilManagerSchoolGrade = 8;

	@Min(1)
	@NotNull
	private Long maxUserImageUploadSizeBytes = 1000000l;

	@NotBlank
	private String realCurrencySymbol = " €";

	@NotNull
	@DecimalMin(value = "0.0", inclusive = false)
	private BigDecimal rateOfExchange = BigDecimal.ONE;

	@NotNull
	@DecimalMin(value = "0.0", inclusive = false)
	private BigDecimal rateOfBackExchange = BigDecimal.ONE;

	private BigDecimal moneyInCirculation = BigDecimal.ZERO;

	private BigDecimal moneyOnAccounts = BigDecimal.ZERO;

	private BigDecimal allMoney = BigDecimal.ZERO;

	private BigDecimal realMoneyCount = BigDecimal.ONE;

	public Long getMaxIdleTime() {
		return maxIdleTime;
	}

	public void setMaxIdleTime(Long maxIdleTime) {
		this.maxIdleTime = maxIdleTime;
	}

	public String getCurrencySymbol() {
		return currencySymbol;
	}

	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}

	public BigDecimal getTransactionLimit() {
		return transactionLimit;
	}

	public void setTransactionLimit(BigDecimal transactionLimit) {
		this.transactionLimit = transactionLimit;
	}

	public Integer getMaxUserImageWidth() {
		return maxUserImageWidth;
	}

	public void setMaxUserImageWidth(Integer maxUserImageWidth) {
		this.maxUserImageWidth = maxUserImageWidth;
	}

	public Integer getMaxUserImageHeight() {
		return maxUserImageHeight;
	}

	public void setMaxUserImageHeight(Integer maxUserImageHeight) {
		this.maxUserImageHeight = maxUserImageHeight;
	}

	public BigDecimal getMinimumWage() {
		return minimumWage;
	}

	public void setMinimumWage(BigDecimal minimumWage) {
		this.minimumWage = minimumWage;
	}

	public Long getMinTimePresent() {
		return minTimePresent;
	}

	public void setMinTimePresent(Long minTimePresent) {
		this.minTimePresent = minTimePresent;
	}

	public List<@NotBlankString @TypeNotNull String> getUiThemes() {
		return uiThemes;
	}

	public void setUiThemes(List<@NotBlankString @TypeNotNull String> uiThemes) {
		this.uiThemes = uiThemes;
	}

	public String getDefaultUiTheme() {
		return defaultUiTheme;
	}

	public void setDefaultUiTheme(String defaultUiTheme) {
		this.defaultUiTheme = defaultUiTheme;
	}

	public List<String> getDefaultUiThemeThemesList() {
		return this.defaultUiThemeThemesList;
	}

	public Long getMaxUserImageUploadSizeBytes() {
		return maxUserImageUploadSizeBytes;
	}

	public void setMaxUserImageUploadSizeBytes(Long maxUserImageUploadSizeBytes) {
		this.maxUserImageUploadSizeBytes = maxUserImageUploadSizeBytes;
	}

	public Integer getOptimalManagerCount() {
		return optimalManagerCount;
	}

	public void setOptimalManagerCount(Integer optimalManagerCount) {
		this.optimalManagerCount = optimalManagerCount;
	}

	public Integer getMinCivilManagerSchoolGrade() {
		return minCivilManagerSchoolGrade;
	}

	public void setMinCivilManagerSchoolGrade(Integer minCivilManagerSchoolGrade) {
		this.minCivilManagerSchoolGrade = minCivilManagerSchoolGrade;
	}

	public String getRealCurrencySymbol() {
		return this.realCurrencySymbol;
	}

	public void setRealCurrencySymbol(String realCurrencySymbol) {
		this.realCurrencySymbol = realCurrencySymbol;
	}

	public BigDecimal getMoneyInCirculation() {
		return this.moneyInCirculation;
	}

	public BigDecimal getMoneyOnAccounts() {
		return this.moneyOnAccounts;
	}

	public BigDecimal getAllMoney() {
		return this.allMoney;
	}

	public BigDecimal getRealMoneyCount() {
		return this.realMoneyCount;
	}

	public BigDecimal getRateOfExchange() {
		return rateOfExchange;
	}

	public void setRateOfExchange(BigDecimal rateOfExchange) {
		this.rateOfExchange = rateOfExchange;
	}

	public BigDecimal getRateOfBackExchange() {
		return rateOfBackExchange;
	}

	public void setRateOfBackExchange(BigDecimal rateOfBackExchange) {
		this.rateOfBackExchange = rateOfBackExchange;
	}

	@PostConstruct
	public void refresh() {
		if (WebUtils.isPermitted(EnumPermission.GLOBAL_DATA_GET_MAX_IDLE_TIME)) {
			maxIdleTime = globalDataBean.getMaxIdleTime();
		}
		if (WebUtils.isPermitted(EnumPermission.GLOBAL_DATA_GET_CURRENCY_SYMBOL)) {
			currencySymbol = globalDataBean.getCurrencySymbol();
		}
		if (WebUtils.isPermitted(EnumPermission.GLOBAL_DATA_GET_TRANSACTION_LIMIT)) {
			transactionLimit = globalDataBean.getTransactionLimit();
		}
		if (WebUtils.isPermitted(EnumPermission.GLOBAL_DATA_GET_MAX_USER_IMAGE_WIDTH)) {
			maxUserImageWidth = globalDataBean.getMaxUserImageWidth();
		}
		if (WebUtils.isPermitted(EnumPermission.GLOBAL_DATA_GET_MAX_USER_IMAGE_HEIGHT)) {
			maxUserImageHeight = globalDataBean.getMaxUserImageHeight();
		}
		if (WebUtils.isPermitted(EnumPermission.GLOBAL_DATA_GET_MINIMUM_WAGE)) {
			minimumWage = globalDataBean.getMinimumWage();
		}
		if (WebUtils.isPermitted(EnumPermission.GLOBAL_DATA_GET_MIN_TIME_PRESENT)) {
			minTimePresent = globalDataBean.getMinTimePresent();
		}
		if (WebUtils.isPermitted(EnumPermission.GLOBAL_DATA_GET_UI_THEMES)) {
			uiThemes = globalDataBean.getUIThemes();
			defaultUiThemeThemesList.clear();
			defaultUiThemeThemesList.addAll(uiThemes);
		}
		if (WebUtils.isPermitted(EnumPermission.GLOBAL_DATA_GET_DEFAULT_UI_THEME)) {
			defaultUiTheme = globalDataBean.getDefaultUITheme();
		}
		if (WebUtils.isPermitted(EnumPermission.GLOBAL_DATA_GET_MAX_USER_IMAGE_UPLOAD_SIZE_BYTES)) {
			maxUserImageUploadSizeBytes = globalDataBean.getMaxUserImageUploadSizeBytes();
		}
		if (WebUtils.isPermitted(EnumPermission.GLOBAL_DATA_GET_OPTIMAL_CIVIL_MANAGER_COUNT)) {
			optimalManagerCount = globalDataBean.getOptimalCivilManagerCount();
		}
		if (WebUtils.isPermitted(EnumPermission.GLOBAL_DATA_GET_MIN_CIVIL_MANAGER_SCHOOL_GRADE)) {
			minCivilManagerSchoolGrade = globalDataBean.getMinCivilManagerSchoolGrade();
		}
		if (WebUtils.isPermitted(EnumPermission.GLOBAL_DATA_GET_REAL_CURRENCY_SYMBOL)) {
			realCurrencySymbol = globalDataBean.getRealCurrencySymbol();
		}
		if (WebUtils.isPermitted(EnumPermission.GLOBAL_DATA_GET_RATE_OF_EXCHANGE)) {
			rateOfExchange = globalDataBean.getRateOfExchange();
		}
		if (WebUtils.isPermitted(EnumPermission.GLOBAL_DATA_GET_RATE_OF_BACK_EXCHANGE)) {
			rateOfBackExchange = globalDataBean.getRateOfBackExchange();
		}
		if (WebUtils.isPermitted(EnumPermission.GLOBAL_DATA_GET_MONEY_IN_CIRCULATION)) {
			moneyInCirculation = globalDataBean.getMoneyInCirculation();
		}
		if (WebUtils.isPermitted(EnumPermission.ACCOUNT_GET_TOTAL_MONEY_IN_ACCOUNTS)) {
			moneyOnAccounts = accountBean.getTotalMoneyInAccounts();
		}
		if (WebUtils.isPermitted(EnumPermission.GLOBAL_DATA_GET_MONEY_IN_CIRCULATION, EnumPermission.ACCOUNT_GET_TOTAL_MONEY_IN_ACCOUNTS)) {
			allMoney = globalDataBean.getAllFictionalMoney();
		}
		if (WebUtils.isPermitted(EnumPermission.GLOBAL_DATA_GET_REAL_MONEY_COUNT)) {
			realMoneyCount = globalDataBean.getRealMoneyCount();
		}
	}

	public void save() {
		changeValue(maxIdleTime, globalDataBean::setMaxIdleTime, EnumPermission.GLOBAL_DATA_SET_MAX_IDLE_TIME,
				"lucas.application.globalDataScreen.setMaxIdleTime");
		changeValue(currencySymbol, globalDataBean::setCurrencySymbol, EnumPermission.GLOBAL_DATA_SET_CURRENCY_SYMBOL,
				"lucas.application.globalDataScreen.setCurrencySymbol");
		changeValue(transactionLimit, globalDataBean::setTransactionLimit, EnumPermission.GLOBAL_DATA_SET_TRANSACTION_LIMIT,
				"lucas.application.globalDataScreen.setTransactionLimit");
		changeValue(maxUserImageWidth, globalDataBean::setMaxUserImageWidth, EnumPermission.GLOBAL_DATA_SET_MAX_USER_IMAGE_WIDTH,
				"lucas.application.globalDataScreen.setMaxUserImageWidth");
		changeValue(maxUserImageHeight, globalDataBean::setMaxUserImageHeight, EnumPermission.GLOBAL_DATA_SET_MAX_USER_IMAGE_HEIGHT,
				"lucas.application.globalDataScreen.setMaxUserImageHeight");
		changeValue(minimumWage, globalDataBean::setMinimumWage, EnumPermission.GLOBAL_DATA_SET_MINIMUM_WAGE,
				"lucas.application.globalDataScreen.setMinimumWage");
		changeValue(minTimePresent, globalDataBean::setMinTimePresent, EnumPermission.GLOBAL_DATA_SET_MIN_TIME_PRESENT,
				"lucas.application.globalDataScreen.setMinTimePresent");
		if (WebUtils.isPermitted(EnumPermission.GLOBAL_DATA_GET_UI_THEMES)) {
			List<String> allThemes = globalDataBean.getUIThemes();
			uiThemes.forEach(theme -> {
				if (!allThemes.contains(theme)) {
					changeValue(theme, globalDataBean::addUITheme, EnumPermission.GLOBAL_DATA_ADD_UI_THEME,
							"lucas.application.globalDataScreen.addUITheme");
				}
			});
			allThemes.forEach(theme -> {
				if (!uiThemes.contains(theme)) {
					changeValue(theme, globalDataBean::removeUITheme, EnumPermission.GLOBAL_DATA_REMOVE_UI_THEME,
							"lucas.application.globalDataScreen.removeUITheme");
				}
			});
		}
		changeValue(defaultUiTheme, globalDataBean::setDefaultUITheme, EnumPermission.GLOBAL_DATA_SET_DEFAULT_UI_THEME,
				"lucas.application.globalDataScreen.setDefaultUITheme");
		changeValue(maxUserImageUploadSizeBytes, globalDataBean::setMaxUserImageUploadSizeBytes,
				EnumPermission.GLOBAL_DATA_SET_MAX_USER_IMAGE_UPLOAD_SIZE_BYTES, "lucas.application.globalDataScreen.setMaxUserImageUploadSizeBytes");
		changeValue(optimalManagerCount, globalDataBean::setOptimalCivilManagerCount, EnumPermission.GLOBAL_DATA_SET_OPTIMAL_CIVIL_MANAGER_COUNT,
				"lucas.application.globalDataScreen.setOptimalManagerCount");
		changeValue(minCivilManagerSchoolGrade, globalDataBean::setMinCivilManagerSchoolGrade,
				EnumPermission.GLOBAL_DATA_SET_MIN_CIVIL_MANAGER_SCHOOL_GRADE, "lucas.application.globalDataScreen.setMinCivilManagerSchoolGrade");
		changeValue(realCurrencySymbol, globalDataBean::setRealCurrencySymbol, EnumPermission.GLOBAL_DATA_SET_REAL_CURRENCY_SYMBOL,
				"lucas.application.globalDataScreen.setRealCurrencySymbol");
		changeValue(rateOfExchange, globalDataBean::setRateOfExchange, EnumPermission.GLOBAL_DATA_SET_RATE_OF_EXCHANGE,
				"lucas.application.globalDataScreen.setRateOfExchange");
		changeValue(rateOfBackExchange, globalDataBean::setRateOfBackExchange, EnumPermission.GLOBAL_DATA_SET_RATE_OF_BACK_EXCHANGE,
				"lucas.application.globalDataScreen.setRateOfBackExchange");
	}

	private <T> void changeValue(T value, Predicate<T> setter, EnumPermission permission, String messagePrefix) {
		if (WebUtils.isPermitted(permission)) {
			WebUtils.executeTask(params -> {
				return setter.test(value);
			}, true, false, messagePrefix, Arrays.asList(value));
		}
	}

	/*
	 * -------------------- Edit Themes Dialog Start --------------------
	 */

	@NotBlank
	private String editThemesDialogTmpUITheme = null;

	@NotBlank
	private String editThemesDialogSelectedUITheme = null;

	public String getEditThemesDialogTmpUITheme() {
		return this.editThemesDialogTmpUITheme;
	}

	public void setEditThemesDialogTmpUITheme(String editThemesDialogTmpUITheme) {
		this.editThemesDialogTmpUITheme = editThemesDialogTmpUITheme;
	}

	public void editThemesDialogResetTmpUITheme() {
		this.editThemesDialogTmpUITheme = null;
	}

	public String getEditThemesDialogSelectedUITheme() {
		return this.editThemesDialogSelectedUITheme;
	}

	public void setEditThemesDialogSelectedUITheme(String editThemesDialogSelectedUITheme) {
		this.editThemesDialogSelectedUITheme = editThemesDialogSelectedUITheme;
	}

	public void initEditThemesDialog() {
		editThemesDialogTmpUITheme = null;
		editThemesDialogSelectedUITheme = null;
	}

	public void editThemesDialogEditUITheme() {
		if (editThemesDialogSelectedUITheme != null) {
			this.uiThemes.remove(editThemesDialogSelectedUITheme);
			this.editThemesDialogTmpUITheme = editThemesDialogSelectedUITheme;
			editThemesDialogSelectedUITheme = null;
		}
	}

	public void editThemesDialogRemoveUITheme() {
		if (editThemesDialogSelectedUITheme != null) {
			this.uiThemes.remove(editThemesDialogSelectedUITheme);
			this.editThemesDialogTmpUITheme = null;
			this.editThemesDialogSelectedUITheme = null;
		}
	}

	/*
	 * -------------------- Edit Themes Dialog End --------------------
	 */
}
