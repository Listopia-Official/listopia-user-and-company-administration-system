package florian_haas.lucas.database;

import florian_haas.lucas.model.EntityBase;

public interface DAO<E extends EntityBase> extends ReadOnlyDAO<E> {

	public void persist(E entity);

	public E merge(E entity);

	public void delete(E entity);

}
