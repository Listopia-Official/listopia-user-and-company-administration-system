package florian_haas.lucas.database.impl;

import florian_haas.lucas.database.DAO;
import florian_haas.lucas.model.EntityBase;

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
