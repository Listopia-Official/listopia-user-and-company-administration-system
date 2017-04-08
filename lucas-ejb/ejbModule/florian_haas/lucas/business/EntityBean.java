package florian_haas.lucas.business;

import static florian_haas.lucas.security.EnumPermission.*;

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

	@Override
	@RequiresPermissions(ENTITY_IS_UNIQUE)
	public <T> Boolean isUnique(String columnName, T value, Class<? extends EntityBase> entityClass) {
		TypedQuery<Long> query = manager.createQuery(
				"SELECT COUNT(e." + columnName + ") FROM " + entityClass.getSimpleName() + " e WHERE e." + columnName + "=?1", Long.class);
		query.setParameter(1, value);
		return query.getSingleResult() == 0;
	}

}
