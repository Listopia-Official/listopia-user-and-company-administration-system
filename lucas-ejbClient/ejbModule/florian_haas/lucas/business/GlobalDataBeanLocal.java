package florian_haas.lucas.business;

import java.math.BigDecimal;
import java.util.*;

import javax.ejb.Local;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.NotBlank;

import florian_haas.lucas.model.*;
import florian_haas.lucas.validation.*;

@Local
public interface GlobalDataBeanLocal {

	public ReadOnlyGlobalData getInstance();

	public Map<EnumSalaryClass, BigDecimal> getSalaries();

	public Long getMinTimePresent();

	public BigDecimal getMinimumWage();

	public ReadOnlyCompany getWarehouse();

	public BigDecimal getTransactionLimit();

	public String getCurrencySymbol();

	public Long getMaxIdleTime();

	public Integer getMaxUserImageWidth();

	public Integer getMaxUserImageHeight();

	public String getDefaultUITheme();

	public List<String> getUIThemes();

	public Long getMaxUserImageUploadSizeBytes();

	public Integer getOptimalCivilManagerCount();

	public Integer getMinCivilManagerSchoolGrade();

	public BigDecimal getMoneyInCirculation();

	public BigDecimal getAllFictionalMoney();

	public BigDecimal getRateOfExchange();

	public BigDecimal getRateOfBackExchange();

	public BigDecimal getRealMoneyCount();

	public String getRealCurrencySymbol();

	public Boolean setSalary(@NotNull EnumSalaryClass salaryClass, @MinimumWage BigDecimal salary);

	public Boolean setMinTimePresent(@NotNull @Min(1) Long minTimePresent);

	public Boolean setMinimumWage(@NotNull @DecimalMin(value = "0", inclusive = false) BigDecimal minimumWage);

	public Boolean setWarehouse(@ValidEntityId(entityClass = ReadOnlyCompany.class, nullable = true) Long companyId);

	public Boolean setTransactionLimit(@NotNull @DecimalMin(value = "0", inclusive = false) BigDecimal transactionLimit);

	public Boolean setCurrencySymbol(@NotBlank String currencySymbol);

	public Boolean setMaxIdleTime(@NotNull @Min(1) Long maxIdleTime);

	public Boolean setMaxUserImageWidth(@NotNull @Min(50) Integer maxUserImageWidth);

	public Boolean setMaxUserImageHeight(@NotNull @Min(50) Integer maxUserImageHeight);

	public Boolean setDefaultUITheme(@ValidUITheme @NotNull String uiTheme);

	public Boolean addUITheme(@NotBlank String uiTheme);

	public Boolean removeUITheme(@ValidUITheme @NotNull String uiTheme);

	public Boolean setMaxUserImageUploadSizeBytes(@NotNull @Min(1) Long maxUserImageUploadSizeBytes);

	public Boolean setOptimalCivilManagerCount(@NotNull @Min(1) Integer optimalCivilManagerCount);

	public Boolean setMinCivilManagerSchoolGrade(@NotNull @ValidSchoolGrade Integer minCivilManagerSchoolGrade);

	public Boolean setMoneyInCirculation(@NotNull @DecimalMin(value = "0.0", inclusive = true) BigDecimal moneyInCirculation);

	public void addMoneyInCirculation(@NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal moneyToAdd);

	public void subtractMoneyInCirculation(@NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal moneyToSubtract);

	public Boolean setRateOfExchange(@NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal rateofExchange);

	public Boolean setRateOfBackExchange(@NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal rateofExchange);

	public Boolean setRealMoneyCount(@NotNull @DecimalMin(value = "0.0", inclusive = true) BigDecimal realMoneyCount);

	public Boolean addRealMoneyCount(@NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal realMoneyToAdd);

	public Boolean subtractRealMoneyCount(@NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal realMoneyToSubtract);

	public Boolean setRealCurrencySymbol(@NotBlank String realCurrencySymbol);

}
