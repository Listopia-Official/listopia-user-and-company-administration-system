package florian_haas.lucas.business;

import florian_haas.lucas.model.EntityBase;

public interface EntityBeanLocal {
	public Boolean exists(Long id, Class<? extends EntityBase> entityClass);
}
