package florian_haas.lucas.model;

import java.util.*;

public interface ReadOnlyCompany extends ReadOnlyAccountOwner {

	public List<? extends ReadOnlyEmployment> getAllEmployees();

	public List<? extends ReadOnlyEmployment> getEmployees();

	public List<? extends ReadOnlyEmployment> getManagers();

	public List<? extends ReadOnlyEmployment> getAdvisors();

	public String getName();

	public String getDescription();

	public ReadOnlyRoomSection getSection();

	public EnumCompanyType getCompanyType();

	public ReadOnlyCompany getParentCompany();

	public Set<? extends ReadOnlyCompany> getChildCompanies();

	public Boolean areEmployeesRequired();

	public List<? extends ReadOnlyTaxdata> getTaxdata();

	public List<? extends ReadOnlyPurchaseLog> getPurchaseLogs();

	public List<? extends ReadOnlyJob> getJobs();

	public Boolean removePurchaseLog(ReadOnlyPurchaseLog log);

}
