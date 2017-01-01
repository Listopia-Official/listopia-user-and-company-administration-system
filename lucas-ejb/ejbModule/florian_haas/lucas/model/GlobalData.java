package florian_haas.lucas.model;

import java.math.BigDecimal;
import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.*;

import florian_haas.lucas.model.validation.MinimumWage;

@Entity
public class GlobalData extends EntityBase {

	private static final long serialVersionUID = -7426702269184558930L;

	@ElementCollection(fetch = FetchType.EAGER)
	@NotNull
	private Map<EnumSalaryClass, @MinimumWage BigDecimal> salaries = new HashMap<>();

	@Basic(optional = false)
	@Column(nullable = false)
	@NotNull
	@Min(1)
	private Long minTimePresent = 171_000L;

	@NotNull
	@DecimalMin(value = "0", inclusive = false)
	private BigDecimal minimumWage = new BigDecimal("1.0");

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

}
