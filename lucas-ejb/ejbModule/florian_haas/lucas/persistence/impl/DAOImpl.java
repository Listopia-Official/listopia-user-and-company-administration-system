package florian_haas.lucas.persistence.impl;

import florian_haas.lucas.model.EntityBase;
import florian_haas.lucas.persistence.DAO;

public abstract class DAOImpl<E extends EntityBase> extends ReadOnlyDAOImpl<E> implements DAO<E> {

	@Override
	public void persist(E entity) {
		manager.persist(entity);
	}

	@Override
	public E merge(E entity) {
		return manager.merge(entity);
	}

	@Override
	public void delete(E entity) {
		manager.remove(entity);
	}

}
