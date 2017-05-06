package florian_haas.lucas.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class CompanyCard extends IdCard implements ReadOnlyCompanyCard {

	private static final long serialVersionUID = 6849568385486037437L;

	@ManyToOne
	@JoinColumn(nullable = false)
	@NotNull
	private Company company;

	CompanyCard() {}

	public CompanyCard(Company company) {
		this.company = company;
	}

	public Company getCompany() {
		return this.company;
	}

}
