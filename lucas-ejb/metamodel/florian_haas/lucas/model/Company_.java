package florian_haas.lucas.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-29T12:13:50.770+0100")
@StaticMetamodel(Company.class)
public class Company_ extends AccountOwner_ {
	public static volatile SingularAttribute<Company, String> name;
	public static volatile SingularAttribute<Company, String> description;
	public static volatile SingularAttribute<Company, String> room;
	public static volatile SingularAttribute<Company, Integer> section;
	public static volatile SingularAttribute<Company, EnumCompanyType> companyType;
	public static volatile SingularAttribute<Company, Company> parentCompany;
	public static volatile SetAttribute<Company, Company> childCompanies;
	public static volatile ListAttribute<Company, Employment> employees;
	public static volatile SingularAttribute<Company, Integer> requiredEmployeesCount;
	public static volatile ListAttribute<Company, Taxdata> taxdata;
	public static volatile ListAttribute<Company, PurchaseLog> purchaseLogs;
}
