package florian_haas.lucas.database.impl;

import java.util.*;

import javax.persistence.criteria.*;

import florian_haas.lucas.database.*;
import florian_haas.lucas.model.*;

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

}
