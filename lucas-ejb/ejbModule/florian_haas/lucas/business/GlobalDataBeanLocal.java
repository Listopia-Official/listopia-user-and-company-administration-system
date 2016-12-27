package florian_haas.lucas.business;

import java.math.BigDecimal;
import java.util.Map;

import javax.ejb.Local;

import florian_haas.lucas.model.EnumSalaryClass;

@Local
public interface GlobalDataBeanLocal {

	public Map<EnumSalaryClass, BigDecimal> getSalaries();

	public Long getMinTimePresent();

	public void setSalary(EnumSalaryClass salaryClass, BigDecimal salary);

	public void setMinTimePresent(Long minTimePresent);

}
