package florian_haas.lucas.business;

import javax.ejb.*;
import javax.persistence.*;
import javax.validation.executable.*;

import florian_haas.lucas.model.EntityBase;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@ValidateOnExecution(type = ExecutableType.IMPLICIT)
public class EntityBean implements EntityBeanLocal {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Boolean exists(Long id, Class<? extends EntityBase> entityClass) {
		return manager.createQuery("SELECT COUNT(e.id) FROM " + entityClass.getSimpleName() + " e WHERE e.id=:id", Long.class).getSingleResult() > 0;
	}

}
