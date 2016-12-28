package florian_haas.lucas.model;

import javax.persistence.*;

@Entity
public class Employment extends EntityBase {

	private static final long serialVersionUID = 8794256029793389169L;

	@ManyToOne(optional = false)
	@JoinColumn(nullable = false)
	private User user;

	@ManyToOne(optional = false)
	@JoinColumn(nullable = false)
	private Company company;

	@Basic(optional = false)
	@Column(nullable = false)
	private EnumEmployeePosition position;

	@OneToOne(cascade = CascadeType.ALL, optional = true, orphanRemoval = true)
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
