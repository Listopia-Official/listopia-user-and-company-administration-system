package florian_haas.lucas.database.impl;

import java.math.BigDecimal;
import java.util.*;

import javax.persistence.criteria.Predicate;

import florian_haas.lucas.database.*;
import florian_haas.lucas.model.*;

@JPADAO
public class ItemDAOImpl extends DAOImpl<Item> implements ItemDAO {

	@Override
	public List<Item> findItems(Long itemId, String name, String description, Integer itemsAvaible, BigDecimal pricePerItem, Boolean useId,
			Boolean useName, Boolean useDescription, Boolean useItemsAvaible, Boolean usePricePerItem, EnumQueryComparator idComparator,
			EnumQueryComparator nameComparator, EnumQueryComparator descriptionComparator, EnumQueryComparator itemsAvaibleComparator,
			EnumQueryComparator pricePerItemComparator) {
		return readOnlyCriteriaQuery((query, root, builder) -> {
			List<Predicate> predicates = new ArrayList<>();

			getSingularRestriction(Item_.id, itemId, useId, idComparator, predicates, builder, root);
			getSingularRestriction(Item_.name, name, useName, nameComparator, predicates, builder, root);
			getSingularRestriction(Item_.description, description, useDescription, descriptionComparator, predicates, builder, root);
			getSingularRestriction(Item_.itemsAvaible, itemsAvaible, useItemsAvaible, itemsAvaibleComparator, predicates, builder, root);
			getSingularRestriction(Item_.pricePerItem, pricePerItem, usePricePerItem, pricePerItemComparator, predicates, builder, root);

			return predicates.toArray(new Predicate[predicates.size()]);
		});
	}

}
