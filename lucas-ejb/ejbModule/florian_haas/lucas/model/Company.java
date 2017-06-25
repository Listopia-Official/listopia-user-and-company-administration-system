package florian_haas.lucas.model;

import java.util.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.NotBlank;

import florian_haas.lucas.validation.*;

@Entity
@DiscriminatorValue(value = "company")
@ValidCompanyHierarchy
public class Company extends AccountOwner implements ReadOnlyCompany {

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

	@OneToOne(cascade = {
			CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE }, optional = true)
	@JoinColumn(nullable = true, unique = true)
	private RoomSection section;

	@Basic(optional = false)
	@Column(nullable = false)
	@NotNull
	private EnumCompanyType companyType;

	@ManyToOne(optional = true, cascade = {
			CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE })
	@JoinColumn(nullable = true)
	private Company parentCompany;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parentCompany", cascade = {
			CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE })
	@NotNull
	private Set<@TypeNotNull Company> childCompanies = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "company")
	@NotNull
	@Valid
	private List<@TypeNotNull Job> jobs = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "company")
	@NotNull
	@UniqueValue(fieldName = "date")
	@Valid
	private List<@TypeNotNull Taxdata> taxdata = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "company")
	@NotNull
	@Valid
	private List<@TypeNotNull PurchaseLog> purchaseLogs = new ArrayList<>();

	Company() {}

	public Company(String name, String description, RoomSection section, EnumCompanyType companyType) {
		this.name = name;
		this.description = description;
		this.section = section;
		this.companyType = companyType;
	}

	public List<Employment> getAllEmployees() {
		return getEmployeesFromPosition(null);
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
		jobs.forEach(job -> {
			if (job.getEmployeePosition() == position || position == null) {
				ret.addAll(job.getEmployments());
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

	public RoomSection getSection() {
		return this.section;
	}

	public void setSection(RoomSection section) {
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

	public Boolean areEmployeesRequired() {
		for (Job job : jobs) {
			if (job.areEmployeesRequiredForJob()) return Boolean.TRUE;
		}
		return Boolean.FALSE;
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

	public List<Job> getJobs() {
		return Collections.unmodifiableList(this.jobs);
	}

	public Boolean addJob(Job job) {
		return this.jobs.add(job);
	}

	public Boolean removeJob(Job job) {
		return this.jobs.remove(job);
	}

	@Override
	public Boolean removePurchaseLog(ReadOnlyPurchaseLog log) {
		return purchaseLogs.remove(log);
	}
}
