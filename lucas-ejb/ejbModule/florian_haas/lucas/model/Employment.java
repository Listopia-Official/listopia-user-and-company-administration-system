package florian_haas.lucas.model;

public class Employment extends EntityBase {

	private static final long serialVersionUID = 8794256029793389169L;

	private User user;
	private Company company;
	private EnumEmployeePosition position;
	private SalaryData salaryData;

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
