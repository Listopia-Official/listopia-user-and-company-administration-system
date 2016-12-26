package florian_haas.lucas.model;

import java.math.BigDecimal;
import java.util.*;

public class GlobalData extends EntityBase {

	private static final long serialVersionUID = -7426702269184558930L;

	private Map<EnumSalaryClass, BigDecimal> salaries = new HashMap<>();
	private Long minTimePresent = 171_000L;

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

}
