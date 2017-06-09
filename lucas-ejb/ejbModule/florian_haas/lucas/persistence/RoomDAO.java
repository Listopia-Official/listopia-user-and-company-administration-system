package florian_haas.lucas.persistence;

import java.util.List;

import florian_haas.lucas.model.*;

public interface RoomDAO extends DAO<Room> {

	public List<Room> findRooms(Long roomId, String name, Long roomSectionId, Integer sectionsCount, Integer occupiedSectionsCount,
			Integer freeSectionsCount, Long companyId, Boolean useRoomId, Boolean useName, Boolean useRoomSectionId, Boolean useSectionsCount,
			Boolean useOccupiedSectionsCount, Boolean useFreeSectionsCount, Boolean useCompany, EnumQueryComparator roomIdComparator,
			EnumQueryComparator roomNameComparator, EnumQueryComparator roomSectionIdComparator, EnumQueryComparator sectionsCountComparator,
			EnumQueryComparator occupiedSectionsCountComparator, EnumQueryComparator freeSectionsCountComparator,
			EnumQueryComparator companyComparator);

	public Boolean isNameUnique(String name);

	public List<RoomSection> getRoomsFromData(String data, Integer resultsCount);

}
