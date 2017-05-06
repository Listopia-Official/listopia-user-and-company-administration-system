package florian_haas.lucas.validation;

import javax.ejb.EJB;
import javax.validation.*;

import florian_haas.lucas.business.EntityBeanLocal;
import florian_haas.lucas.model.ReadOnlyEntity;

public class ValidEntityIdValidator implements ConstraintValidator<ValidEntityId, Long> {

	@EJB
	private EntityBeanLocal bean;

	private Class<? extends ReadOnlyEntity> entityClass;

	private boolean nullable;

	@Override
	public void initialize(ValidEntityId constraintAnnotation) {
		entityClass = constraintAnnotation.entityClass();
		nullable = constraintAnnotation.nullable();
	}

	@Override
	public boolean isValid(Long value, ConstraintValidatorContext context) {
		if (value == null) return nullable;
		return bean.exists(value, entityClass);
	}

}
