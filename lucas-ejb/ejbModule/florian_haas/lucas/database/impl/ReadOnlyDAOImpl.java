package florian_haas.lucas.database.impl;

import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.*;

import javax.inject.Named;
import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.*;

import florian_haas.lucas.database.*;
import florian_haas.lucas.model.EntityBase;
import florian_haas.lucas.util.TriFunction;

@Named
public abstract class ReadOnlyDAOImpl<E extends EntityBase> implements ReadOnlyDAO<E> {

	@PersistenceContext
	protected EntityManager manager;

	protected final Class<E> entityClass;

	@SuppressWarnings("unchecked")
	protected ReadOnlyDAOImpl() {
		entityClass = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
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
		return manager.createQuery("SELECT COUNT(e.id) FROM " + entityClass.getSimpleName() + " e WHERE e.id=:id", Long.class).getSingleResult() > 0;
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

	public List<E> readOnlyCriteriaQuery(TriFunction<CriteriaQuery<E>, Root<E>, CriteriaBuilder, Predicate[]> restrictions) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<E> query = builder.createQuery(entityClass);
		Root<E> root = query.from(entityClass);
		query.select(root).where(restrictions.apply(query, root, builder));
		return manager.createQuery(query).getResultList();
	}

	@SuppressWarnings("unchecked")
	protected final <T extends Comparable<? super T>> Predicate getSingularRestriction(SingularAttribute<? super E, T> attribute, T value,
			EnumQueryComparator comparator, CriteriaBuilder builder, Path<E> path) {
		Expression<T> comparableExpression = path.get(attribute);

		if (comparator == null) comparator = EnumQueryComparator.EQUAL;

		switch (comparator) {
			case EQUAL:
				if (value instanceof BigDecimal) {
					return builder.and(builder.not(builder.greaterThan(comparableExpression, value)),
							builder.not(builder.lessThan(comparableExpression, value)));
				} else {
					return builder.equal(comparableExpression, value);
				}
			case GREATER_EQUAL:
				return builder.greaterThanOrEqualTo(comparableExpression, value);
			case GREATER_THAN:
				return builder.greaterThan(comparableExpression, value);
			case LESS_EQUAL:
				return builder.lessThanOrEqualTo(comparableExpression, value);
			case LESS_THAN:
				return builder.lessThan(comparableExpression, value);
			case NOT_EQUAL:
				return builder.notEqual(comparableExpression, value);
			case LIKE:
				if (value instanceof String) { return builder.like((Expression<String>) (Expression<?>) comparableExpression, (String) value); }
			case NOT_LIKE:
				if (value instanceof String) { return builder.notLike((Expression<String>) (Expression<?>) comparableExpression, (String) value); }
			default:
				return null;
		}
	}

	protected final <T extends Comparable<? super T>> void getSingularRestriction(SingularAttribute<? super E, T> attribute, T value,
			Boolean useValue, EnumQueryComparator comparator, List<Predicate> list, CriteriaBuilder builder, Path<E> path) {
		if (useValue) {
			Predicate val = getSingularRestriction(attribute, value, comparator, builder, path);
			if (val != null) {
				list.add(val);
			}
		}
	}

	protected final <T extends Comparable<? super T>> List<Predicate> getSingularRestrictionCollection(SingularAttribute<? super E, T> attribute,
			Collection<T> values, EnumQueryComparator comparator, CriteriaBuilder builder, Path<E> path) {
		List<Predicate> predicates = new ArrayList<>();
		values.forEach(value -> {
			predicates.add(this.getSingularRestriction(attribute, value, comparator, builder, path));
		});
		return predicates;
	}

	protected final <T extends Comparable<? super T>> void getSingularRestrictionCollection(SingularAttribute<? super E, T> attribute,
			Collection<T> values, Boolean useValue, EnumQueryComparator comparator, List<Predicate> list, CriteriaBuilder builder, Path<E> path) {
		if (useValue) list.addAll(getSingularRestrictionCollection(attribute, values, comparator, builder, path));

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

}
