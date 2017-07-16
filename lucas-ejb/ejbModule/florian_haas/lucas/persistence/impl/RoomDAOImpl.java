package florian_haas.lucas.persistence.impl;

import java.util.*;

import javax.persistence.criteria.*;

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;

@JPADAO
public class RoomDAOImpl extends DAOImpl<Room> implements RoomDAO {

	@Override
	public List<Room> findRooms(Long roomId, String name, Long roomSectionId, Integer sectionsCount, Integer occupiedSectionsCount,
			Integer freeSectionsCount, Long companyId, Boolean useRoomId, Boolean useName, Boolean useRoomSectionId, Boolean useSectionsCount,
			Boolean useOccupiedSectionsCount, Boolean useFreeSectionsCount, Boolean useCompany, EnumQueryComparator roomIdComparator,
			EnumQueryComparator roomNameComparator, EnumQueryComparator roomSectionIdComparator, EnumQueryComparator sectionsCountComparator,
			EnumQueryComparator occupiedSectionsCountComparator, EnumQueryComparator freeSectionsCountComparator,
			EnumQueryComparator companyComparator) {
		return this.readOnlyCriteriaQuery((query, root, builder) -> {
			List<Predicate> predicates = new ArrayList<>();

			getSingularRestriction(Room_.id, roomId, useRoomId, roomIdComparator, predicates, builder, root);
			getSingularRestriction(Room_.name, name, useName, roomNameComparator, predicates, builder, root);
			Join<Room, RoomSection> sections = root.join(Room_.sections, JoinType.LEFT);
			getSingularRestriction(RoomSection_.id, roomSectionId, useRoomSectionId, roomSectionIdComparator, predicates, builder, sections);
			getSingularRestriction(builder.size(root.get(Room_.sections)), sectionsCount, useSectionsCount, sectionsCountComparator, predicates,
					builder, root);
			sectionsCountHelper(query, root, builder, useOccupiedSectionsCount, occupiedSectionsCount, occupiedSectionsCountComparator, predicates,
					Boolean.FALSE);
			sectionsCountHelper(query, root, builder, useFreeSectionsCount, freeSectionsCount, freeSectionsCountComparator, predicates, Boolean.TRUE);
			getSingularRestriction(Company_.id, companyId, useCompany, companyComparator, predicates, builder,
					sections.join(RoomSection_.company, JoinType.LEFT));

			return predicates;
		});
	}

	private void sectionsCountHelper(CriteriaQuery<Room> query, Root<Room> root, CriteriaBuilder builder, Boolean use, Integer value,
			EnumQueryComparator comparator, List<Predicate> predicates, Boolean isNull) {
		if (use) {
			Subquery<Long> subquery = query.subquery(Long.class);
			Root<Room> room = subquery.correlate(root);
			Join<Room, RoomSection> sections2 = room.join(Room_.sections, JoinType.LEFT);
			subquery.distinct(true).select(builder.count(room)).where(isNull ? builder.isNull(sections2.join(RoomSection_.company, JoinType.LEFT))
					: builder.isNotNull(sections2.join(RoomSection_.company, JoinType.LEFT)));
			switch (comparator) {
				case EMPTY:
				case CONTAINS:
				case EQUAL:
				case MEMBER_OF:
				case NOT_EMPTY:
				case CONTAINS_NOT:
				case NOT_MEMBER_OF:
				case NOT_NULL:
				case NULL:
					predicates.add(builder.equal(subquery, value));
					break;
				case GREATER_EQUAL:
					predicates.add(builder.ge(subquery, value));
					break;
				case GREATER_THAN:
					predicates.add(builder.gt(subquery, value));
					break;
				case LESS_EQUAL:
					predicates.add(builder.le(subquery, value));
					break;
				case LESS_THAN:
					predicates.add(builder.lt(subquery, value));
					break;
				case NOT_EQUAL:
					predicates.add(builder.notEqual(subquery, value));
					break;
			}
		}
	}

	@Override
	public Boolean isNameUnique(String name) {
		return isUnique(name, Room_.name);
	}

	@Override
	public List<RoomSection> getRoomsFromData(String data, Integer resultsCount) {
		data = data.trim();
		if (!data.isEmpty()) {
			CriteriaBuilder builder = manager.getCriteriaBuilder();
			CriteriaQuery<RoomSection> query = builder.createQuery(RoomSection.class);
			query.distinct(true);
			Root<RoomSection> section = query.from(RoomSection.class);
			Join<RoomSection, Room> room = section.join(RoomSection_.room);
			List<Predicate> predicates = new ArrayList<>();
			try {
				Long id = Long.parseLong(data);
				predicates.add(builder.equal(section.get(RoomSection_.id), id));
				predicates.add(builder.equal(room.get(Room_.id), id));
			}
			catch (NumberFormatException e) {}
			data = "%" + data.replaceAll(" ", "%") + "%";
			predicates.add(builder.like(room.get(Room_.name), data));
			query.select(section).where(builder.or(predicates.toArray(new Predicate[predicates.size()])));
			return manager.createQuery(query).setMaxResults(resultsCount).getResultList();
		}
		return new ArrayList<>();
	}

}
