package florian_haas.lucas.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class Taxdata extends EntityBase implements ReadOnlyTaxdata {

	private static final long serialVersionUID = -4156175587632314959L;

	@ManyToOne(optional = false)
	@JoinColumn(nullable = false)
	@NotNull
	private Company company;

	@Basic(optional = false)
	@Column(nullable = false)
	@NotNull
	private LocalDate date;

	@Basic(optional = false)
	@Column(nullable = false, precision = 38, scale = 7)
	@NotNull
	@DecimalMin(value = "0", inclusive = true)
	private BigDecimal incomings;

	@Basic(optional = false)
	@Column(nullable = false, precision = 38, scale = 7)
	@DecimalMin(value = "0", inclusive = true)
	private BigDecimal outgoings;

	Taxdata() {}

	public Taxdata(Company company, LocalDate date, BigDecimal incomings, BigDecimal outgoings) {
		this.company = company;
		this.date = date;
		this.incomings = incomings;
		this.outgoings = outgoings;
	}

	public Company getCompany() {
		return company;
	}

	public LocalDate getDate() {
		return this.date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public BigDecimal getIncomings() {
		return this.incomings;
	}

	public void setIncomings(BigDecimal incomings) {
		this.incomings = incomings;
	}

	public BigDecimal getOutgoings() {
		return this.outgoings;
	}

	public void setOutgoings(BigDecimal outgoings) {
		this.outgoings = outgoings;
	}

	public BigDecimal getBalance() {
		return incomings.subtract(outgoings);
	}

}
