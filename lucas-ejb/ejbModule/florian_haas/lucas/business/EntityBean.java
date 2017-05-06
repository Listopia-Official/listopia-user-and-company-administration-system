package florian_haas.lucas.business;

import static florian_haas.lucas.security.EnumPermission.ENTITY_EXISTS;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.validation.executable.*;

import florian_haas.lucas.model.ReadOnlyEntity;
import florian_haas.lucas.security.*;

@Stateless
@ValidateOnExecution(type = ExecutableType.IMPLICIT)
@Secured
public class EntityBean implements EntityBeanLocal {

	@PersistenceContext
	private EntityManager manager;

	@Override
	@RequiresPermissions(ENTITY_EXISTS)
	public Boolean exists(Long id, Class<? extends ReadOnlyEntity> entityClass) {
		return manager
				.createQuery("SELECT COUNT(e.id) FROM " + entityClass.getSimpleName().replaceAll("ReadOnly", "") + " e WHERE e.id=" + id, Long.class)
				.getSingleResult() > 0;
	}
}
