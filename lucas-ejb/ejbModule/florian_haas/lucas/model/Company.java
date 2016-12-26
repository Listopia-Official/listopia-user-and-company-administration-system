package florian_haas.lucas.model;

import java.util.*;

public class Company extends AccountOwner {

	private static final long serialVersionUID = -8593998936489707393L;

	private String name;
	private String description;
	private String room;
	private String section;
	private EnumCompanyType companyType;
	private Company parentCompany;
	private List<Employment> employees = new ArrayList<>();
	private Set<Company> childCompanies = new HashSet<>();
	private Integer requiredEmployeesCount = 0;
	private List<Taxdata> taxdata = new ArrayList<>();
	private List<PurchaseLog> purchaseLogs = new ArrayList<>();

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

	public String getSection() {
		return this.section;
	}

	public void setSection(String section) {
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
		return childCompanies;
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
		return taxdata;
	}

	public boolean addTaxdata(Taxdata taxdata) {
		return this.taxdata.add(taxdata);
	}

	public boolean removeTaxdata(Taxdata taxdata) {
		return this.taxdata.remove(taxdata);
	}

	public List<PurchaseLog> getPurchaseLogs() {
		return purchaseLogs;
	}

	public boolean addPurchaseLog(PurchaseLog purchaseLog) {
		return this.purchaseLogs.add(purchaseLog);
	}
}
