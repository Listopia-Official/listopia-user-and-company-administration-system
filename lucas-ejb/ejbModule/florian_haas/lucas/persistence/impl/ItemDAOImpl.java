package florian_haas.lucas.persistence.impl;

import java.math.BigDecimal;
import java.util.*;

import javax.persistence.criteria.Predicate;

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;

@JPADAO
public class ItemDAOImpl extends DAOImpl<Item> implements ItemDAO {

	@Override
	public List<Item> findItems(Long itemId, String name, String description, Integer itemsAvaible, BigDecimal fictionalPricePerItem,
			BigDecimal realPricePerItem, Boolean useId, Boolean useName, Boolean useDescription, Boolean useItemsAvaible,
			Boolean useFictionalPricePerItem, Boolean useRealPricePerItem, EnumQueryComparator idComparator, EnumQueryComparator nameComparator,
			EnumQueryComparator descriptionComparator, EnumQueryComparator itemsAvaibleComparator,
			EnumQueryComparator fictionalPricePerItemComparator, EnumQueryComparator realPricePerItemComparator) {
		return readOnlyCriteriaQuery((query, root, builder) -> {
			List<Predicate> predicates = new ArrayList<>();

			getSingularRestriction(Item_.id, itemId, useId, idComparator, predicates, builder, root);
			getSingularRestriction(Item_.name, name, useName, nameComparator, predicates, builder, root);
			getSingularRestriction(Item_.description, description, useDescription, descriptionComparator, predicates, builder, root);
			getSingularRestriction(Item_.itemsAvaible, itemsAvaible, useItemsAvaible, itemsAvaibleComparator, predicates, builder, root);
			getSingularRestriction(Item_.fictionalPricePerItem, fictionalPricePerItem, useFictionalPricePerItem, fictionalPricePerItemComparator,
					predicates, builder, root);
			getSingularRestriction(Item_.realPricePerItem, realPricePerItem, useRealPricePerItem, realPricePerItemComparator, predicates, builder,
					root);

			return predicates;
		});
	}

	@Override
	public Boolean isNameUnique(String name) {
		return isUnique(name, Item_.name);
	}

}
