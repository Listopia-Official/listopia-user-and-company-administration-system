package florian_haas.lucas.database;

import java.util.*;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;

import florian_haas.lucas.model.EntityBase;
import florian_haas.lucas.util.TriFunction;

public interface ReadOnlyDAO<E extends EntityBase> {

	public EntityManager getEntityManager();

	public Class<E> getEntityClass();

	public List<Long> findAllIds();

	public List<E> findAll();

	public E findById(Long id);

	public Boolean exists(Long id);

	public <T> Boolean isUnique(T value, SingularAttribute<? super E, T> attribute);

	public <T> Boolean isUnique(T value, Function<Root<E>, Path<T>> attribute);

	public E refresh(E entity);

	public List<E> readOnlyJPQLQuery(String jpql, Object... params);

	public List<E> readOnlyCriteriaQuery(TriFunction<CriteriaQuery<E>, Root<E>, CriteriaBuilder, Collection<Predicate>> restrictions);

	public void flush();
}
