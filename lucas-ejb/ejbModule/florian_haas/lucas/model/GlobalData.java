package florian_haas.lucas.model;

import java.math.BigDecimal;
import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.NotBlank;

import florian_haas.lucas.validation.*;

@Entity
public class GlobalData extends EntityBase implements ReadOnlyGlobalData {

	private static final long serialVersionUID = -7426702269184558930L;

	@ElementCollection(fetch = FetchType.EAGER)
	@NotNull
	private Map<EnumSalaryClass, @MinimumWage BigDecimal> salaries = new HashMap<>();

	@Basic(optional = false)
	@Column(nullable = false)
	@NotNull
	@Min(1)
	private Long minTimePresent = 171_000L;

	@Basic(optional = false)
	@Column(nullable = false, scale = 7, precision = 38)
	@NotNull
	@DecimalMin(value = "0", inclusive = false)
	private BigDecimal minimumWage = new BigDecimal("1.0");

	@NotNull(groups = NotNullWarehouseRequired.class)
	@OneToOne(fetch = FetchType.LAZY, optional = true, cascade = {
			CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE })
	@JoinColumn(nullable = true)
	private Company warehouse;

	@Basic(optional = false)
	@Column(nullable = false, scale = 7, precision = 38)
	@NotNull
	@DecimalMin(value = "0", inclusive = false)
	private BigDecimal transactionLimit = new BigDecimal("20.0");

	@Basic(optional = false)
	@Column(nullable = false)
	@NotBlank
	private String currencySymbol = "Gd";

	@Basic(optional = false)
	@Column(nullable = false)
	@Min(1)
	@NotNull
	private Long maxIdleTime = 30000l;

	@Basic(optional = false)
	@Column(nullable = false)
	@NotNull
	@Min(50)
	private Integer maxUserImageWidth = 300;

	@Basic(optional = false)
	@Column(nullable = false)
	@NotNull
	@Min(50)
	private Integer maxUserImageHeight = 300;

	@Basic(optional = false)
	@Column(nullable = false)
	@NotBlank
	private String defaultUITheme = "omega";

	@ElementCollection
	@NotNull
	private List<@NotBlankString @TypeNotNull String> uiThemes = Arrays.asList("afterdark", "afternoon", "afterwork", "aristo", "black-tie",
			"blitzer", "bluesky", "bootstrap", "casablanca", "cupertino", "cruze", "dark-hive", "delta", "dot-luv", "eggplant", "excite-bike",
			"flick", "glass-x", "home", "hot-sneaks", "humanity", "le-frog", "midnight", "mint-choc", "overcast", "omega", "pepper-grinder",
			"redmond", "rocket", "sam", "smoothness", "south-street", "start", "sunny", "swanky-purse", "trontastic", "ui-darkness", "ui-lightness",
			"vader");

	@Basic(optional = false)
	@Column(nullable = false)
	@Min(1)
	@NotNull
	private Long maxUserImageUploadSizeByte = 1000000l;

	@Basic(optional = false)
	@Column(nullable = false)
	@NotNull
	@Min(1)
	private Integer civilCompanyOptimalManagerCount = 2;

	@Basic(optional = false)
	@Column(nullable = false)
	@ValidSchoolGrade
	@NotNull
	private Integer minCivilManagerSchoolGrade = 8;

	@Basic(optional = false)
	@Column(nullable = false, scale = 7, precision = 38)
	@NotNull
	@DecimalMin(value = "0.0", inclusive = true)
	private BigDecimal moneyInCirculation = BigDecimal.ZERO;

	@Basic(optional = false)
	@Column(nullable = false, scale = 7, precision = 38)
	@NotNull
	@DecimalMin(value = "0.0", inclusive = false)
	private BigDecimal rateOfExchange = BigDecimal.ONE;

	@Basic(optional = false)
	@Column(nullable = false, scale = 7, precision = 38)
	@NotNull
	@DecimalMin(value = "0.0", inclusive = false)
	private BigDecimal rateOfBackExchange = BigDecimal.ONE;

	@Basic(optional = false)
	@Column(nullable = false, scale = 7, precision = 38)
	@NotNull
	@DecimalMin(value = "0.0", inclusive = true)
	private BigDecimal realMoneyCount = BigDecimal.ZERO;

	@Basic(optional = false)
	@Column(nullable = false)
	@NotBlank
	private String realCurrencySymbol = "€";

	public Map<EnumSalaryClass, BigDecimal> getSalaries() {
		return Collections.unmodifiableMap(salaries);
	}

	public void setSalary(EnumSalaryClass salaryClass, BigDecimal salary) {
		this.salaries.replace(salaryClass, salary);
	}

	public Long getMinTimePresent() {
		return minTimePresent;
	}

	public void setMinTimePresent(Long minTimePresent) {
		this.minTimePresent = minTimePresent;
	}

	public BigDecimal getMinimumWage() {
		return minimumWage;
	}

	public void setMinimumWage(BigDecimal minimumWage) {
		this.minimumWage = minimumWage;
	}

	public Company getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Company warehouse) {
		this.warehouse = warehouse;
	}

	public BigDecimal getTransactionLimit() {
		return transactionLimit;
	}

	public void setTransactionLimit(BigDecimal transactionLimit) {
		this.transactionLimit = transactionLimit;
	}

	public String getCurrencySymbol() {
		return currencySymbol;
	}

	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}

	public Long getMaxIdleTime() {
		return maxIdleTime;
	}

	public void setMaxIdleTime(Long maxIdleTime) {
		this.maxIdleTime = maxIdleTime;
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

	public String getDefaultUITheme() {
		return defaultUITheme;
	}

	public void setDefaultUITheme(String defaultUITheme) {
		this.defaultUITheme = defaultUITheme;
	}

	public List<String> getUiThemes() {
		return this.uiThemes;
	}

	public Boolean addUITheme(String uiTheme) {
		return uiThemes.add(uiTheme);
	}

	public Boolean removeUITheme(String uiTheme) {
		return uiThemes.remove(uiTheme);
	}

	public Long getMaxUserImageUploadSizeByte() {
		return maxUserImageUploadSizeByte;
	}

	public void setMaxUserImageUploadSizeByte(Long maxUserImageUploadSizeByte) {
		this.maxUserImageUploadSizeByte = maxUserImageUploadSizeByte;
	}

	public Integer getCivilCompanyOptimalManagerCount() {
		return civilCompanyOptimalManagerCount;
	}

	public void setCivilCompanyOptimalManagerCount(Integer civilCompanyOptimalManagerCount) {
		this.civilCompanyOptimalManagerCount = civilCompanyOptimalManagerCount;
	}

	public Integer getMinCivilManagerSchoolGrade() {
		return minCivilManagerSchoolGrade;
	}

	public void setMinCivilManagerSchoolGrade(Integer minCivilManagerSchoolGrade) {
		this.minCivilManagerSchoolGrade = minCivilManagerSchoolGrade;
	}

	@Override
	public BigDecimal getMoneyInCirculation() {
		return moneyInCirculation;
	}

	public void setMoneyInCirculation(BigDecimal moneyInCirculation) {
		this.moneyInCirculation = moneyInCirculation;
	}

	@Override
	public BigDecimal getRateOfExchange() {
		return this.rateOfExchange;
	}

	public void setRateOfExchange(BigDecimal rateOfExchange) {
		this.rateOfExchange = rateOfExchange;
	}

	@Override
	public BigDecimal getRateOfBackExchange() {
		return this.rateOfBackExchange;
	}

	public void setRateOfBackExchange(BigDecimal rateOfBackExchange) {
		this.rateOfBackExchange = rateOfBackExchange;
	}

	@Override
	public BigDecimal getRealMoneyCount() {
		return this.realMoneyCount;
	}

	public void setRealMoneyCount(BigDecimal realMoneyCount) {
		this.realMoneyCount = realMoneyCount;
	}

	@Override
	public String getRealCurrencySymbol() {
		return this.realCurrencySymbol;
	}

	public void setRealCurrencySymbol(String realCurrencySymbol) {
		this.realCurrencySymbol = realCurrencySymbol;
	}

}
