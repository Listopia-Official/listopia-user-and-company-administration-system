package florian_haas.lucas.persistence.impl;

import java.util.*;

import javax.persistence.criteria.*;

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;

@JPADAO
public class RoomDAOImpl extends DAOImpl<Room> implements RoomDAO {

	@Override
	public List<Room> findRooms(Long roomId, String name, Long sectionId, Boolean useRoomId, Boolean useName, Boolean useSectionId,
			EnumQueryComparator roomIdComparator, EnumQueryComparator nameComparator, EnumQueryComparator sectionIdComparator) {
		return this.readOnlyCriteriaQuery((query, root, builder) -> {
			List<Predicate> predicates = new ArrayList<>();

			getSingularRestriction(Room_.id, roomId, useRoomId, roomIdComparator, predicates, builder, root);
			getSingularRestriction(Room_.name, name, useName, nameComparator, predicates, builder, root);
			getSingularRestriction(RoomSection_.id, sectionId, useSectionId, sectionIdComparator, predicates, builder,
					root.join(Room_.sections, JoinType.LEFT));

			return predicates;
		});
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
