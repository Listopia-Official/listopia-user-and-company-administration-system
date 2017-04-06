package florian_haas.lucas.business;

import java.math.BigDecimal;
import java.util.*;

import javax.ejb.Local;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.NotBlank;

import florian_haas.lucas.model.*;
import florian_haas.lucas.model.validation.*;

@Local
public interface GlobalDataBeanLocal {

	public GlobalData getInstance();

	public Map<EnumSalaryClass, BigDecimal> getSalaries();

	public Long getMinTimePresent();

	public BigDecimal getMinimumWage();

	public Company getWarehouse();

	public BigDecimal getTransactionLimit();

	public String getCurrencySymbol();

	public Long getMaxIdleTime();

	public Integer getMaxUserImageWidth();

	public Integer getMaxUserImageHeight();

	public String getDefaultUITheme();

	public List<String> getUIThemes();

	public Long getMaxUserImageUploadSizeBytes();

	public Boolean setSalary(@NotNull EnumSalaryClass salaryClass, @MinimumWage BigDecimal salary);

	public Boolean setMinTimePresent(@NotNull @Min(1) Long minTimePresent);

	public Boolean setMinimumWage(@NotNull @DecimalMin(value = "0", inclusive = false) BigDecimal minimumWage);

	public Boolean setWarehouse(@ValidEntityId(entityClass = Company.class) Long companyId);

	public Boolean setTransactionLimit(@NotNull @DecimalMin(value = "0", inclusive = false) BigDecimal transactionLimit);

	public Boolean setCurrencySymbol(@NotBlank String currencySymbol);

	public Boolean setMaxIdleTime(@NotNull @Min(1) Long maxIdleTime);

	public Boolean setMaxUserImageWidth(@NotNull @Min(50) Integer maxUserImageWidth);

	public Boolean setMaxUserImageHeight(@NotNull @Min(50) Integer maxUserImageHeight);

	public Boolean setDefaultUITheme(@ValidUITheme @NotNull String uiTheme);

	public Boolean addUITheme(@NotBlank String uiTheme);

	public Boolean removeUITheme(@ValidUITheme @NotNull String uiTheme);

	public Boolean setMaxUserImageUploadSizeBytes(@NotNull @Min(1) Long maxUserImageUploadSizeBytes);

}
