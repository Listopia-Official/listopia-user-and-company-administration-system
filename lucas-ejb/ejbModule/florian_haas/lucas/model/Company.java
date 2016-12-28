package florian_haas.lucas.model;

import java.util.*;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = "company")
public class Company extends AccountOwner {

	private static final long serialVersionUID = -8593998936489707393L;

	@Basic(optional = false)
	@Column(nullable = false, unique = true)
	private String name;

	@Basic(optional = false)
	@Column(nullable = false)
	private String description;

	@Basic(optional = false)
	@Column(nullable = false)
	private String room;

	@Basic(optional = false)
	@Column(nullable = false)
	private Integer section;

	@Basic(optional = false)
	@Column(nullable = false)
	private EnumCompanyType companyType;

	@ManyToOne(optional = true)
	@JoinColumn(nullable = true)
	private Company parentCompany;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parentCompany")
	private Set<Company> childCompanies = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "company")
	private List<Employment> employees = new ArrayList<>();

	@Basic(optional = false)
	@Column(nullable = false)
	private Integer requiredEmployeesCount = 0;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "company")
	private List<Taxdata> taxdata = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "company")
	private List<PurchaseLog> purchaseLogs = new ArrayList<>();

	Company() {}

	public Company(String name, String description, String room, Integer section, EnumCompanyType companyType,
			List<Employment> managers, Integer requiredEmployeesCount) {
		this.name = name;
		this.description = description;
		this.room = room;
		this.section = section;
		this.companyType = companyType;
		this.employees.addAll(managers);
		this.requiredEmployeesCount = requiredEmployeesCount;
	}

	public List<Employment> getAllEmployees() {
		return Collections.unmodifiableList(employees);
	}

	public boolean addEmployee(Employment employment) {
		return employees.add(employment);
	}

	public boolean removeEmployee(Employment employment) {
		return employees.remove(employment);
	}

	public List<Employment> getEmployees() {
		return getEmployeesFromPosition(EnumEmployeePosition.EMPLOYEE);
	}

	public List<Employment> getManagers() {
		return getEmployeesFromPosition(EnumEmployeePosition.MANAGER);
	}

	public List<Employment> getKeepers() {
		return getEmployeesFromPosition(EnumEmployeePosition.KEEPER);
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

	public boolean addChildCompany(Company childCompany) {
		return this.childCompanies.add(childCompany);
	}

	public boolean removeChildCompany(Company childCompany) {
		return this.childCompanies.remove(childCompany);
	}

	public Integer getRequiredEmployeesCount() {
		return requiredEmployeesCount;
	}

	public void setRequiredEmployeesCount(Integer requiredEmployeesCount) {
		this.requiredEmployeesCount = requiredEmployeesCount;
	}

	public boolean areEmployeesRequired() {
		return requiredEmployeesCount > 0;
	}

	public List<Taxdata> getTaxdata() {
		return Collections.unmodifiableList(taxdata);
	}

	public boolean addTaxdata(Taxdata taxdata) {
		return this.taxdata.add(taxdata);
	}

	public boolean removeTaxdata(Taxdata taxdata) {
		return this.taxdata.remove(taxdata);
	}

	public List<PurchaseLog> getPurchaseLogs() {
		return Collections.unmodifiableList(purchaseLogs);
	}

	public boolean addPurchaseLog(PurchaseLog purchaseLog) {
		return this.purchaseLogs.add(purchaseLog);
	}
}
