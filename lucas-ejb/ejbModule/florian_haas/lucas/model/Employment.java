package florian_haas.lucas.model;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
public class Employment extends EntityBase {

	private static final long serialVersionUID = 8794256029793389169L;

	@ManyToOne(optional = false)
	@JoinColumn(nullable = false)
	@NotNull
	private User user;

	@ManyToOne(optional = false)
	@JoinColumn(nullable = false)
	@NotNull
	private Company company;

	@Basic(optional = false)
	@Column(nullable = false)
	@NotNull
	private EnumEmployeePosition position;

	@OneToOne(cascade = CascadeType.ALL, optional = true, orphanRemoval = true)
	@Valid
	private SalaryData salaryData;

	Employment() {}

	public Employment(User user, Company company, EnumEmployeePosition position) {
		this(user, company, position, null);
	}

	public Employment(User user, Company company, EnumEmployeePosition position, SalaryData salaryData) {
		this.user = user;
		this.company = company;
		this.position = position;
		this.salaryData = salaryData;
	}

	public User getUser() {
		return this.user;
	}

	public Company getCompany() {
		return this.company;
	}

	public EnumEmployeePosition getPosition() {
		return this.position;
	}

	public void setPosition(EnumEmployeePosition position) {
		this.position = position;
	}

	public SalaryData getSalaryData() {
		return salaryData;
	}

}
