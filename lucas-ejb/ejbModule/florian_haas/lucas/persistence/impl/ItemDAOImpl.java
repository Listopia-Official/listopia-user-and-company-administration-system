package florian_haas.lucas.persistence.impl;

import java.math.BigDecimal;
import java.util.*;

import javax.persistence.criteria.*;

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;

@JPADAO
public class ItemDAOImpl extends DAOImpl<Item> implements ItemDAO {

	@Override
	public List<Item> findItems(Long itemId, String name, String description, Integer itemsAvaible, BigDecimal fictionalPricePerItem,
			BigDecimal realPricePerItem, Boolean hasToBeOrdered, Boolean useId, Boolean useName, Boolean useDescription, Boolean useItemsAvaible,
			Boolean useFictionalPricePerItem, Boolean useRealPricePerItem, Boolean useHasToBeOrdered, EnumQueryComparator idComparator,
			EnumQueryComparator nameComparator, EnumQueryComparator descriptionComparator, EnumQueryComparator itemsAvaibleComparator,
			EnumQueryComparator fictionalPricePerItemComparator, EnumQueryComparator realPricePerItemComparator) {
		return readOnlyCriteriaQuery((query, root, builder) -> {
			List<Predicate> predicates = new ArrayList<>();

			getSingularRestriction(Item_.id, itemId, useId, idComparator, predicates, builder, root);
			getSingularRestriction(Item_.name, name, useName, nameComparator, predicates, builder, root);
			getSingularRestriction(Item_.description, description, useDescription, descriptionComparator, predicates, builder, root);
			getSingularRestriction(Item_.itemsAvailable, itemsAvaible, useItemsAvaible, itemsAvaibleComparator, predicates, builder, root);
			getSingularRestriction(Item_.fictionalPricePerItem, fictionalPricePerItem, useFictionalPricePerItem, fictionalPricePerItemComparator,
					predicates, builder, root);
			getSingularRestriction(Item_.realPricePerItem, realPricePerItem, useRealPricePerItem, realPricePerItemComparator, predicates, builder,
					root);
			getSingularRestriction(Item_.itemsAvailable, 0, useHasToBeOrdered,
					hasToBeOrdered ? EnumQueryComparator.EQUAL : EnumQueryComparator.NOT_EQUAL, predicates, builder, root);

			return predicates;
		});
	}

	@Override
	public Boolean isNameUnique(String name) {
		return isUnique(name, Item_.name);
	}

	@Override
	public List<Item> getItemsFromData(String data, Integer resultsCount) {
		data = data.trim();
		if (!data.isEmpty()) {
			CriteriaBuilder builder = manager.getCriteriaBuilder();
			CriteriaQuery<Item> query = builder.createQuery(Item.class);
			query.distinct(true);
			Root<Item> item = query.from(Item.class);
			List<Predicate> predicates = new ArrayList<>();
			Expression<String> name = item.get(Item_.name);
			try {
				Long id = Long.parseLong(data);
				predicates.add(builder.equal(item.get(Item_.id), id));
			}
			catch (NumberFormatException e) {}
			data = "%" + data.replaceAll(" ", "%") + "%";
			predicates.add(builder.like(name, data));
			query.select(item).where(builder.or(predicates.toArray(new Predicate[predicates.size()])));
			return manager.createQuery(query).setMaxResults(resultsCount).getResultList();
		}
		return new ArrayList<>();
	}

}
