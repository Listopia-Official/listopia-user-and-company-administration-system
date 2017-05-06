package florian_haas.lucas.persistence.impl;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;

import javax.inject.Named;
import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.*;

import florian_haas.lucas.model.EntityBase;
import florian_haas.lucas.persistence.*;
import florian_haas.lucas.util.*;

@Named
public abstract class ReadOnlyDAOImpl<E extends EntityBase> implements ReadOnlyDAO<E> {

	@PersistenceContext
	protected EntityManager manager;

	protected final Class<E> entityClass;

	protected ReadOnlyDAOImpl() {
		entityClass = Utils.getClassFromArgs(this.getClass());
	}

	@Override
	public EntityManager getEntityManager() {
		return manager;
	}

	@Override
	public Class<E> getEntityClass() {
		return entityClass;
	}

	@Override
	public List<Long> findAllIds() {
		return manager.createQuery("SELECT e.id FROM " + entityClass.getSimpleName() + " e", Long.class).getResultList();
	}

	@Override
	public List<E> findAll() {
		return readOnlyJPQLQuery("SELECT e FROM " + entityClass.getSimpleName() + " e");
	}

	@Override
	public E findById(Long id) {
		return manager.find(getEntityClass(), id);
	}

	@Override
	public Boolean exists(Long id) {
		return manager.createQuery("SELECT COUNT(e.id) FROM " + entityClass.getSimpleName() + " e WHERE e.id=" + id, Long.class)
				.getSingleResult() > 0;
	}

	@Override
	public <T> Boolean isUnique(T value, Function<Root<E>, Path<T>> attribute) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<E> root = query.from(entityClass);
		Path<T> expression = attribute.apply(root);
		query.select(builder.count(expression)).where(builder.equal(expression, value));
		return manager.createQuery(query).getSingleResult() == 0;
	}

	@Override
	public <T> Boolean isUnique(T value, SingularAttribute<? super E, T> attribute) {
		return isUnique(value, root -> {
			return root.get(attribute);
		});
	}

	@Override
	public E refresh(E entity) {
		manager.refresh(entity);
		return entity;
	}

	public <T> List<T> readOnlyJPQLQuery(String jpql, Class<T> typeClass, Object... params) {
		TypedQuery<T> query = manager.createQuery(jpql, typeClass);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i + 1, params[i]);
		}
		return query.getResultList();
	}

	public List<E> readOnlyJPQLQuery(String jpql, Object... params) {
		return readOnlyJPQLQuery(jpql, entityClass, params);
	}

	protected <T> T readOnlyCriteriaQuerySingleResult(TriFunction<CriteriaQuery<T>, Root<T>, CriteriaBuilder, Collection<Predicate>> restrictions,
			Class<T> clazz) {
		T ret = null;
		List<T> results = readOnlyCriteriaQuery(restrictions, clazz);
		if (results != null && !results.isEmpty()) {
			ret = results.get(0);
		}
		return ret;
	}

	protected <T> List<T> readOnlyCriteriaQuery(TriFunction<CriteriaQuery<T>, Root<T>, CriteriaBuilder, Collection<Predicate>> restrictions,
			Class<T> clazz) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(clazz).distinct(true);
		Root<T> root = query.from(clazz);
		Collection<Predicate> predicates = restrictions.apply(query, root, builder);
		query.select(root).where(predicates.toArray(new Predicate[predicates.size()]));
		return manager.createQuery(query).getResultList();
	}

	public E readOnlyCriteriaQuerySingleResult(TriFunction<CriteriaQuery<E>, Root<E>, CriteriaBuilder, Collection<Predicate>> restrictions) {
		return readOnlyCriteriaQuerySingleResult(restrictions, entityClass);
	}

	public List<E> readOnlyCriteriaQuery(TriFunction<CriteriaQuery<E>, Root<E>, CriteriaBuilder, Collection<Predicate>> restrictions) {
		return readOnlyCriteriaQuery(restrictions, entityClass);
	}

	@SuppressWarnings({
			"unchecked", "hiding" })
	protected final <T extends Comparable<? super T>, E extends EntityBase> Predicate getSingularRestriction(Expression<T> attribute, T value,
			EnumQueryComparator comparator, CriteriaBuilder builder, Path<E> path) {

		if (comparator == null) comparator = EnumQueryComparator.EQUAL;

		switch (comparator) {
			case EQUAL:
				if (value instanceof BigDecimal) {
					return builder.and(builder.not(builder.greaterThan(attribute, value)), builder.not(builder.lessThan(attribute, value)));
				} else {
					return builder.equal(attribute, value);
				}
			case GREATER_EQUAL:
				return builder.greaterThanOrEqualTo(attribute, value);
			case GREATER_THAN:
				return builder.greaterThan(attribute, value);
			case LESS_EQUAL:
				return builder.lessThanOrEqualTo(attribute, value);
			case LESS_THAN:
				return builder.lessThan(attribute, value);
			case NOT_EQUAL:
				return builder.notEqual(attribute, value);
			case LIKE:
				if (value instanceof String) { return builder.like((Expression<String>) (Expression<?>) attribute, (String) value); }
			case NOT_LIKE:
				if (value instanceof String) { return builder.notLike((Expression<String>) (Expression<?>) attribute, (String) value); }
			default:
				return null;
		}
	}

	@SuppressWarnings("hiding")
	protected final <T extends Comparable<? super T>, E extends EntityBase> Predicate getSingularRestriction(
			SingularAttribute<? super E, T> attribute, T value, EnumQueryComparator comparator, CriteriaBuilder builder, Path<E> path) {
		return getSingularRestriction(path.get(attribute), value, comparator, builder, path);
	}

	@SuppressWarnings("hiding")
	protected final <T extends Comparable<? super T>, E extends EntityBase> void getSingularRestriction(Expression<T> attribute, T value,
			Boolean useValue, EnumQueryComparator comparator, List<Predicate> list, CriteriaBuilder builder, Path<E> path) {
		if (useValue) {
			Predicate val = getSingularRestriction(attribute, value, comparator, builder, path);
			if (val != null) {
				list.add(val);
			}
		}
	}

	@SuppressWarnings("hiding")
	protected final <T extends Comparable<? super T>, E extends EntityBase> void getSingularRestriction(SingularAttribute<? super E, T> attribute,
			T value, Boolean useValue, EnumQueryComparator comparator, List<Predicate> list, CriteriaBuilder builder, Path<E> path) {
		getSingularRestriction(path.get(attribute), value, useValue, comparator, list, builder, path);
	}

	@SuppressWarnings("hiding")
	protected final <T extends Comparable<? super T>, E extends EntityBase> Predicate getSingularRestrictionCollection(Expression<T> attribute,
			List<T> values, EnumQueryComparator comparator, CriteriaBuilder builder, Path<E> path) {
		Predicate[] predicates = new Predicate[values.size()];
		for (int i = 0; i < values.size(); i++) {
			predicates[i] = this.getSingularRestriction(attribute, values.get(i), comparator, builder, path);
		}
		return builder.or(predicates);
	}

	@SuppressWarnings("hiding")
	protected final <T extends Comparable<? super T>, E extends EntityBase> Predicate getSingularRestrictionCollection(
			SingularAttribute<? super E, T> attribute, List<T> values, EnumQueryComparator comparator, CriteriaBuilder builder, Path<E> path) {
		return getSingularRestrictionCollection(path.get(attribute), values, comparator, builder, path);
	}

	@SuppressWarnings("hiding")
	protected final <T extends Comparable<? super T>, E extends EntityBase> void getSingularRestrictionCollection(Expression<T> attribute,
			List<T> values, Boolean useValue, EnumQueryComparator comparator, List<Predicate> list, CriteriaBuilder builder, Path<E> path) {
		if (useValue) list.add(getSingularRestrictionCollection(attribute, values, comparator, builder, path));
	}

	@SuppressWarnings("hiding")
	protected final <T extends Comparable<? super T>, E extends EntityBase> void getSingularRestrictionCollection(
			SingularAttribute<? super E, T> attribute, List<T> values, Boolean useValue, EnumQueryComparator comparator, List<Predicate> list,
			CriteriaBuilder builder, Path<E> path) {
		getSingularRestrictionCollection(path.get(attribute), values, useValue, comparator, list, builder, path);
	}

	@SuppressWarnings("hiding")
	protected final <V, C extends Collection<V>, E extends EntityBase> List<Predicate> getPluralRestrictionCollection(
			PluralAttribute<E, C, V> attribute, C value, EnumQueryComparator comparator, CriteriaBuilder builder, Root<E> root) {
		List<Predicate> predicates = new ArrayList<>();
		Expression<C> expression = root.get(attribute);

		if (comparator == null) comparator = EnumQueryComparator.MEMBER_OF;

		switch (comparator) {
			case MEMBER_OF:
				if (value == null) {
					predicates.add(builder.isEmpty(expression));
				} else {
					value.forEach(val -> {
						predicates.add(builder.isMember(val, expression));
					});
				}
				return predicates;
			case NOT_MEMBER_OF:
				if (value == null) {
					predicates.add(builder.isNotEmpty(expression));
				} else {
					value.forEach(val -> {
						predicates.add(builder.isNotMember(val, expression));
					});
				}
				return predicates;
			default:
				return predicates;
		}

	}

	@SuppressWarnings("hiding")
	protected final <V, C extends Collection<V>, E extends EntityBase> void getPluralRestrictionCollection(PluralAttribute<E, C, V> attribute,
			C value, Boolean useValue, EnumQueryComparator comparator, List<Predicate> list, CriteriaBuilder builder, Root<E> root) {
		if (useValue) list.addAll(getPluralRestrictionCollection(attribute, value, comparator, builder, root));
	}

	@Override
	public void flush() {
		manager.flush();
	}

}
