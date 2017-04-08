package florian_haas.lucas.business;

import static florian_haas.lucas.security.EnumPermission.ENTITY_EXISTS;

import javax.ejb.*;
import javax.persistence.*;
import javax.validation.executable.*;

import florian_haas.lucas.model.EntityBase;
import florian_haas.lucas.security.*;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@ValidateOnExecution(type = ExecutableType.IMPLICIT)
@Secured
public class EntityBean implements EntityBeanLocal {

	@PersistenceContext
	private EntityManager manager;

	@Override
	@RequiresPermissions(ENTITY_EXISTS)
	public Boolean exists(Long id, Class<? extends EntityBase> entityClass) {
		return manager.createQuery("SELECT COUNT(e.id) FROM " + entityClass.getSimpleName() + " e WHERE e.id=" + id, Long.class)
				.getSingleResult() > 0;
	}
}
