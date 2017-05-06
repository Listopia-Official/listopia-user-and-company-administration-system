package florian_haas.lucas.model;

import java.util.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.NotBlank;

import florian_haas.lucas.validation.*;

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = {
				"COMPANY_ID", "NAME" }) })
public class Job extends EntityBase implements ReadOnlyJob {

	private static final long serialVersionUID = -4555758067528633376L;

	@Basic(optional = false)
	@Column(nullable = false)
	@NotBlank
	private String name;

	@Basic(optional = true)
	@Column(nullable = true)
	@NotBlankString
	@Size(max = 255)
	private String description;

	@ManyToOne
	@JoinColumn(nullable = false)
	@NotNull
	private Company company;

	@Basic(optional = true)
	@Column(nullable = true)
	@ValidSchoolGrade
	private Integer optimalSchoolGrade = null;

	@Basic(optional = false)
	@Column(nullable = false)
	@NotNull
	@Min(0)
	private Integer requiredEmploymentsCount = 0;

	@Basic(optional = false)
	@Column(nullable = false)
	@NotNull
	private EnumEmployeePosition employeePosition = EnumEmployeePosition.EMPLOYEE;

	@Basic(optional = true)
	@Column(nullable = true)
	private EnumSalaryClass salaryClass;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "job")
	@NotNull
	@Valid
	private List<@TypeNotNull Employment> employments = new ArrayList<>();

	Job() {}

	public Job(String name, String description, Company company, EnumEmployeePosition position, Integer optimalSchoolGrade,
			Integer requiredEmploymentsCount, EnumSalaryClass salaryClass) {
		this.name = name;
		this.description = description;
		this.company = company;
		this.employeePosition = position;
		this.optimalSchoolGrade = optimalSchoolGrade;
		this.requiredEmploymentsCount = requiredEmploymentsCount;
		this.salaryClass = salaryClass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Company getCompany() {
		return company;
	}

	public EnumSalaryClass getSalaryClass() {
		return salaryClass;
	}

	public void setSalaryClass(EnumSalaryClass salaryClass) {
		this.salaryClass = salaryClass;
	}

	public Integer getOptimalSchoolGrade() {
		return optimalSchoolGrade;
	}

	public void setOptimalSchoolGrade(Integer optimalSchoolGrade) {
		this.optimalSchoolGrade = optimalSchoolGrade;
	}

	public Integer getRequiredEmploymentsCount() {
		return requiredEmploymentsCount;
	}

	public void setRequiredEmploymentsCount(Integer requiredEmploymentsCount) {
		this.requiredEmploymentsCount = requiredEmploymentsCount;
	}

	public EnumEmployeePosition getEmployeePosition() {
		return employeePosition;
	}

	public void setEmployeePosition(EnumEmployeePosition employeePosition) {
		this.employeePosition = employeePosition;
	}

	public List<Employment> getEmployments() {
		return Collections.unmodifiableList(this.employments);
	}

	public Boolean addEmployment(Employment employment) {
		return employments.add(employment);
	}

	public Boolean removeEmployment(Employment employment) {
		return employments.remove(employment);
	}

	public Boolean areEmployeesRequiredForJob() {
		return employments.size() < requiredEmploymentsCount;
	}
}
