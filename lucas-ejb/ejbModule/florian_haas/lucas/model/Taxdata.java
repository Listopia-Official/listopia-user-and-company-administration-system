package florian_haas.lucas.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.*;

@Entity
public class Taxdata extends EntityBase {

	private static final long serialVersionUID = -4156175587632314959L;

	@ManyToOne(optional = false)
	@JoinColumn(nullable = false)
	private Company company;

	@Basic(optional = false)
	@Column(nullable = false)
	private LocalDate date;

	@Basic(optional = false)
	@Column(nullable = false, precision = 38, scale = 7)
	private BigDecimal incomings;

	@Basic(optional = false)
	@Column(nullable = false, precision = 38, scale = 7)
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
