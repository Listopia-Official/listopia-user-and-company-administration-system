package florian_haas.lucas.model;

import java.math.BigDecimal;
import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.NotBlank;

import florian_haas.lucas.model.validation.*;

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

	@Basic(optional = false)
	@Column(nullable = false, scale = 7, precision = 38)
	@NotNull
	@DecimalMin(value = "0", inclusive = false)
	private BigDecimal minimumWage = new BigDecimal("1.0");

	@NotNull(groups = NotNullWarehouseRequired.class)
	private Company warehouse;

	@Basic(optional = false)
	@Column(nullable = false, scale = 7, precision = 38)
	@NotNull
	@DecimalMin(value = "0", inclusive = false)
	private BigDecimal transactionLimit = new BigDecimal("20.0");

	@Basic(optional = false)
	@NotBlank
	private String currencySymbol = "L";

	@Basic(optional = false)
	@NotNull
	@Min(1)
	private Integer minGrade = 5;

	@Basic(optional = false)
	@NotNull
	private Integer maxGrade = 12;

	@Basic(optional = false)
	@Min(1)
	@NotNull
	private Long maxIdleTime = 300000l;

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

	public Integer getMinGrade() {
		return minGrade;
	}

	public void setMinGrade(Integer minGrade) {
		this.minGrade = minGrade;
	}

	public Integer getMaxGrade() {
		return maxGrade;
	}

	public void setMaxGrade(Integer maxGrade) {
		this.maxGrade = maxGrade;
	}

	public Long getMaxIdleTime() {
		return maxIdleTime;
	}

	public void setMaxIdleTime(Long maxIdleTime) {
		this.maxIdleTime = maxIdleTime;
	}

}
