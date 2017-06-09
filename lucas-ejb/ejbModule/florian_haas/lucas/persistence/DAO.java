package florian_haas.lucas.persistence;

import florian_haas.lucas.model.EntityBase;

public interface DAO<E extends EntityBase> extends ReadOnlyDAO<E> {

	public void persist(E entity);

	public void delete(E entity);

}
