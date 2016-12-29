package florian_haas.lucas.database;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;

import florian_haas.lucas.model.EntityBase;
import florian_haas.lucas.util.QuadFunction;

public interface ReadOnlyDAO<E extends EntityBase> {

	public EntityManager getEntityManager();

	public Class<E> getEntityClass();

	public List<E> findAll();

	public E findById(Long id);

	public Boolean exists(Long id);

	public E refresh(E entity);

	public List<E> readOnlyJPQLQuery(String jpql, Object... params);

	public List<E> readOnlyCriteriaQuery(
			QuadFunction<CriteriaQuery<E>, Root<EntityBase>, Root<E>, CriteriaBuilder, Predicate[]> restrictions);

}
