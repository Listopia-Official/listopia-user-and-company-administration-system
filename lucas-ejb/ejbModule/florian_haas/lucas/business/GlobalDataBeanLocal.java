package florian_haas.lucas.business;

import java.math.BigDecimal;
import java.util.Map;

import javax.ejb.Local;
import javax.validation.constraints.*;

import florian_haas.lucas.model.EnumSalaryClass;
import florian_haas.lucas.model.validation.MinimumWage;

@Local
public interface GlobalDataBeanLocal {

	public Map<EnumSalaryClass, BigDecimal> getSalaries();

	public Long getMinTimePresent();

	public BigDecimal getMinimumWage();

	public void setSalary(@NotNull EnumSalaryClass salaryClass, @MinimumWage BigDecimal salary);

	public void setMinTimePresent(@NotNull @Min(1) Long minTimePresent);

	public void setMinimumWage(@NotNull @DecimalMin(value = "0", inclusive = false) BigDecimal minimumWage);

}
