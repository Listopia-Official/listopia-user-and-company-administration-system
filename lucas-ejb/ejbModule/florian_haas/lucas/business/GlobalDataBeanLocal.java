package florian_haas.lucas.business;

import java.math.BigDecimal;
import java.util.Map;

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

	public BigDecimal getTransactionLimit();

	public String getCurrencySymbol();

	public Integer getMinGrade();

	public Integer getMaxGrade();

	public Boolean setSalary(@NotNull EnumSalaryClass salaryClass, @MinimumWage BigDecimal salary);

	public Boolean setMinTimePresent(@NotNull @Min(1) Long minTimePresent);

	public Boolean setMinimumWage(@NotNull @DecimalMin(value = "0", inclusive = false) BigDecimal minimumWage);

	public Company getWarehouse();

	public Boolean setWarehouse(@ValidEntityId(entityClass = Company.class) Long companyId);

	public Boolean setTransactionLimit(@NotNull @DecimalMin(value = "0", inclusive = false) BigDecimal transactionLimit);

	public Boolean setCurrencySymbol(@NotBlank String currencySymbol);

	public Boolean setMinGrade(@NotNull @Min(1) Integer minGrade);

	public Boolean setMaxGrade(@NotNull Integer maxGrade);

}
