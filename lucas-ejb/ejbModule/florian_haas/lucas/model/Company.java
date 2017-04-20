package florian_haas.lucas.model;

import java.util.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.NotBlank;

import florian_haas.lucas.model.validation.*;
import florian_haas.lucas.util.validation.*;

@Entity
@DiscriminatorValue(value = "company")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {
		"room", "section" }))
@ValidCompanyHierarchy
public class Company extends AccountOwner {

	private static final long serialVersionUID = -8593998936489707393L;

	@Basic(optional = false)
	@Column(nullable = false, unique = true)
	@NotBlank
	private String name;

	@Basic(optional = true)
	@Column(nullable = true)
	@NotBlankString
	@Size(max = 255)
	private String description;

	@Basic(optional = false)
	@Column(nullable = false)
	@NotBlank
	private String room;

	@Basic(optional = false)
	@Column(nullable = false)
	@NotNull
	@Min(value = 0)
	private Integer section;

	@Basic(optional = false)
	@Column(nullable = false)
	@NotNull
	private EnumCompanyType companyType;

	@ManyToOne(optional = true)
	@JoinColumn(nullable = true)
	private Company parentCompany;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parentCompany")
	@NotNull
	private Set<@TypeNotNull Company> childCompanies = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "company")
	@NotNull
	@Valid
	private List<@TypeNotNull Employment> employees = new ArrayList<>();

	@Basic(optional = false)
	@Column(nullable = false)
	@NotNull
	@Min(value = 0)
	private Integer requiredEmployeesCount = 0;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "company")
	@NotNull
	@UniqueValue(fieldName = "date")
	@Valid
	private List<@TypeNotNull Taxdata> taxdata = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "company")
	@NotNull
	@Valid
	private List<@TypeNotNull PurchaseLog> purchaseLogs = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "company")
	@Valid
	@NotNull
	private Set<@TypeNotNull CompanyCard> companyCards = new HashSet<>();

	Company() {}

	public Company(String name, String description, String room, Integer section, EnumCompanyType companyType, Integer requiredEmployeesCount) {
		this.name = name;
		this.description = description;
		this.room = room;
		this.section = section;
		this.companyType = companyType;
		this.requiredEmployeesCount = requiredEmployeesCount;
	}

	public List<Employment> getAllEmployees() {
		return Collections.unmodifiableList(employees);
	}

	public Boolean addEmployee(Employment employment) {
		return employees.add(employment);
	}

	public Boolean removeEmployee(Employment employment) {
		return employees.remove(employment);
	}

	public List<Employment> getEmployees() {
		return getEmployeesFromPosition(EnumEmployeePosition.EMPLOYEE);
	}

	public List<Employment> getManagers() {
		return getEmployeesFromPosition(EnumEmployeePosition.MANAGER);
	}

	public List<Employment> getAdvisors() {
		return getEmployeesFromPosition(EnumEmployeePosition.ADVISOR);
	}

	private List<Employment> getEmployeesFromPosition(EnumEmployeePosition position) {
		List<Employment> ret = new ArrayList<>();
		employees.forEach(employment -> {
			if (employment.getPosition() == position) {
				ret.add(employment);
			}
		});
		return Collections.unmodifiableList(ret);
	}

	@Override
	public EnumAccountOwnerType getOwnerType() {
		return EnumAccountOwnerType.COMPANY;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRoom() {
		return this.room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public Integer getSection() {
		return this.section;
	}

	public void setSection(Integer section) {
		this.section = section;
	}

	public EnumCompanyType getCompanyType() {
		return this.companyType;
	}

	public void setCompanyType(EnumCompanyType companyType) {
		this.companyType = companyType;
	}

	public Company getParentCompany() {
		return this.parentCompany;
	}

	public void setParentCompany(Company parentCompany) {
		this.parentCompany = parentCompany;
	}

	public Set<Company> getChildCompanies() {
		return Collections.unmodifiableSet(childCompanies);
	}

	public Boolean addChildCompany(Company childCompany) {
		return this.childCompanies.add(childCompany);
	}

	public Boolean removeChildCompany(Company childCompany) {
		return this.childCompanies.remove(childCompany);
	}

	public Integer getRequiredEmployeesCount() {
		return requiredEmployeesCount;
	}

	public void setRequiredEmployeesCount(Integer requiredEmployeesCount) {
		this.requiredEmployeesCount = requiredEmployeesCount;
	}

	public Boolean areEmployeesRequired() {
		return requiredEmployeesCount > 0;
	}

	public List<Taxdata> getTaxdata() {
		return Collections.unmodifiableList(taxdata);
	}

	public Boolean addTaxdata(Taxdata taxdata) {
		return this.taxdata.add(taxdata);
	}

	public Boolean removeTaxdata(Taxdata taxdata) {
		return this.taxdata.remove(taxdata);
	}

	public List<PurchaseLog> getPurchaseLogs() {
		return Collections.unmodifiableList(purchaseLogs);
	}

	public Boolean addPurchaseLog(PurchaseLog purchaseLog) {
		return this.purchaseLogs.add(purchaseLog);
	}

	public Set<CompanyCard> getCompanyCards() {
		return Collections.unmodifiableSet(companyCards);
	}

	public Boolean addCompanyCard(CompanyCard companyCard) {
		return this.companyCards.add(companyCard);
	}

	public Boolean removeCompanyCard(CompanyCard companyCard) {
		return this.companyCards.remove(companyCard);
	}
}
