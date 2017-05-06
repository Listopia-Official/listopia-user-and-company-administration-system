package florian_haas.lucas.validation;

import java.util.Map;

import javax.ejb.EJB;
import javax.validation.*;

import florian_haas.lucas.business.EntityBeanLocal;
import florian_haas.lucas.model.ReadOnlyEntity;

public class ValidEntityIdMapKeyValidator implements ConstraintValidator<ValidEntityId, Map<Long, ?>> {

	@EJB
	private EntityBeanLocal bean;

	private Class<? extends ReadOnlyEntity> entityClass;

	@Override
	public void initialize(ValidEntityId constraintAnnotation) {
		entityClass = constraintAnnotation.entityClass();
	}

	@Override
	public boolean isValid(Map<Long, ?> value, ConstraintValidatorContext context) {
		if (value == null) return true;
		for (Long key : value.keySet()) {
			if (key == null || !bean.exists(key, entityClass)) return false;
		}
		return true;
	}

}
