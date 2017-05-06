package florian_haas.lucas.model;

import java.util.*;

public interface ReadOnlyJob extends ReadOnlyEntity {
	public String getName();

	public String getDescription();

	public ReadOnlyCompany getCompany();

	public EnumSalaryClass getSalaryClass();

	public Integer getOptimalSchoolGrade();

	public Integer getRequiredEmploymentsCount();

	public EnumEmployeePosition getEmployeePosition();

	public List<? extends ReadOnlyEmployment> getEmployments();

	public Boolean areEmployeesRequiredForJob();

	public Set<? extends ReadOnlyUser> getEmployeeSuggestions();
}
