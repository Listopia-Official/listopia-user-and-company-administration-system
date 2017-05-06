package florian_haas.lucas.validation;

import javax.validation.*;

import florian_haas.lucas.model.*;
import florian_haas.lucas.util.Utils;

public class ValidTransactionLogValidator implements ConstraintValidator<ValidTransactionLog, TransactionLog> {

	@Override
	public void initialize(ValidTransactionLog constraintAnnotation) {}

	@Override
	public boolean isValid(TransactionLog value, ConstraintValidatorContext context) {
		if (value == null) return true;
		return (value.getAction() == EnumAccountAction.DEBIT ? Utils.isGreatherEqualZero(value.getPreviousBankBalance().subtract(value.getAmount()))
				: true) && (value.getActionType() == EnumAccountActionType.TRANSACTION ? value.getRelatedAccount() != null : true)
				&& (value.getActionType() == EnumAccountActionType.BANK ? value.getRelatedAccount() == null : true);
	}

}
