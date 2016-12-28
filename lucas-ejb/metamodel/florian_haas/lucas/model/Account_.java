package florian_haas.lucas.model;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-28T17:03:01.468+0100")
@StaticMetamodel(Account.class)
public class Account_ extends EntityBase_ {
	public static volatile SingularAttribute<Account, AccountOwner> owner;
	public static volatile SingularAttribute<Account, BigDecimal> bankBalance;
	public static volatile SingularAttribute<Account, Boolean> blocked;
	public static volatile ListAttribute<Account, TransactionLog> transactionLogs;
}
