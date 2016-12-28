package florian_haas.lucas.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-12-28T17:03:47.269+0100")
@StaticMetamodel(TransactionLog.class)
public class TransactionLog_ extends EntityBase_ {
	public static volatile SingularAttribute<TransactionLog, Account> account;
	public static volatile SingularAttribute<TransactionLog, LocalDateTime> dateTime;
	public static volatile SingularAttribute<TransactionLog, EnumAccountAction> action;
	public static volatile SingularAttribute<TransactionLog, EnumAccountActionType> actionType;
	public static volatile SingularAttribute<TransactionLog, Account> relatedAccount;
	public static volatile SingularAttribute<TransactionLog, BigDecimal> amount;
	public static volatile SingularAttribute<TransactionLog, BigDecimal> previousBankBalance;
	public static volatile SingularAttribute<TransactionLog, User> bankUser;
	public static volatile SingularAttribute<TransactionLog, String> comment;
}
