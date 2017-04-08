package florian_haas.lucas.model.validation;

import javax.enterprise.inject.spi.CDI;
import javax.validation.*;

import florian_haas.lucas.business.EntityBeanLocal;
import florian_haas.lucas.model.EntityBase;

public class UniqueValidator implements ConstraintValidator<Unique, Object> {

	private Class<? extends EntityBase> entityClass;
	private String columnName;

	private EntityBeanLocal entityBean = CDI.current().select(EntityBeanLocal.class).get();

	@Override
	public void initialize(Unique annotation) {
		entityClass = annotation.entityClass();
		columnName = annotation.columnName();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		return value != null ? entityBean.isUnique(columnName, value, entityClass) : true;
	}

}
