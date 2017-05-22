package florian_haas.lucas.persistence;

import java.util.List;

import florian_haas.lucas.model.*;

public interface RoomDAO extends DAO<Room> {

	public List<Room> findRooms(Long roomId, String name, Long sectionId, Boolean useRoomId, Boolean useName, Boolean useSectionId,
			EnumQueryComparator roomIdComparator, EnumQueryComparator nameComparator, EnumQueryComparator sectionIdComparator);

	public Boolean isNameUnique(String name);

	public List<RoomSection> getRoomsFromData(String data, Integer resultsCount);

}
