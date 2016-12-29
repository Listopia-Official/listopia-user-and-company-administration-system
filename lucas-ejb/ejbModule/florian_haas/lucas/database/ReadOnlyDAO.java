package florian_haas.lucas.database;

import java.util.List;
import java.util.function.BiFunction;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;

import florian_haas.lucas.model.EntityBase;

public interface ReadOnlyDAO<E extends EntityBase> {

	public EntityManager getEntityManager();

	public Class<E> getEntityClass();

	public List<E> findAll();

	public E findById(Long id);

	public Boolean exists(Long id);

	public E refresh(E entity);

	public List<E> readOnlyJPQLQuery(String jpql, Object... params);

	public List<E> readOnlyCriteriaQuery(BiFunction<Root<E>, CriteriaBuilder, Predicate[]> restrictions);

}
