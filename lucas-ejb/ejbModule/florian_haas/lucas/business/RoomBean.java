package florian_haas.lucas.business;

import static florian_haas.lucas.security.EnumPermission.*;

import java.util.*;

import javax.ejb.*;
import javax.inject.Inject;
import javax.validation.executable.*;

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;
import florian_haas.lucas.security.*;

@Stateless
@ValidateOnExecution(type = ExecutableType.IMPLICIT)
@Secured
public class RoomBean implements RoomBeanLocal {

	@Inject
	@JPADAO
	private RoomDAO roomDao;

	@Inject
	@JPADAO
	private RoomSectionDAO roomSectionDao;

	@EJB
	private LoginBeanLocal loginBean;

	@Override
	@RequiresPermissions(ROOM_CREATE)
	public Long createRoom(String name, Integer sectionCount) {
		checkIsNameUnique(name);
		Room room = new Room(name);
		roomDao.persist(room);
		Long id = room.getId();
		for (int i = 0; i < sectionCount; i++) {
			this.addSection(id);
		}
		return id;
	}

	@Override
	@RequiresPermissions(ROOM_FIND_ALL)
	public List<? extends ReadOnlyRoom> findAll() {
		return roomDao.findAll();
	}

	@Override
	@RequiresPermissions(ROOM_FIND_BY_ID)
	public Room findById(Long roomId) {
		return roomDao.findById(roomId);
	}

	@Override
	@RequiresPermissions(ROOM_FIND_DYNAMIC)
	public List<? extends ReadOnlyRoom> findRooms(Long roomId, String name, Long roomSectionId, Integer sectionsCount, Integer occupiedSectionsCount,
			Integer freeSectionsCount, Long companyId, Boolean useRoomId, Boolean useName, Boolean useRoomSectionId, Boolean useSectionsCount,
			Boolean useOccupiedSectionsCount, Boolean useFreeSectionsCount, Boolean useCompany, EnumQueryComparator roomIdComparator,
			EnumQueryComparator roomNameComparator, EnumQueryComparator roomSectionIdComparator, EnumQueryComparator sectionsCountComparator,
			EnumQueryComparator occupiedSectionsCountComparator, EnumQueryComparator freeSectionsCountComparator,
			EnumQueryComparator companyComparator) {
		return (!useRoomId && !useName && !useRoomSectionId && !useSectionsCount && !useOccupiedSectionsCount && !useFreeSectionsCount && !useCompany
				&& !loginBean.getSubject().isPermitted(EnumPermission.ROOM_FIND_ALL.getPermissionString()))
						? new ArrayList<>()
						: roomDao.findRooms(roomId, name, roomSectionId, sectionsCount, occupiedSectionsCount, freeSectionsCount, companyId,
								useRoomId, useName, useRoomSectionId, useSectionsCount, useOccupiedSectionsCount, useFreeSectionsCount, useCompany,
								roomIdComparator, roomNameComparator, roomSectionIdComparator, sectionsCountComparator,
								occupiedSectionsCountComparator, freeSectionsCountComparator, companyComparator);
	}

	@Override
	@RequiresPermissions(ROOM_SET_NAME)
	public Boolean setName(Long roomId, String name) {
		Room room = roomDao.findById(roomId);
		if (room.getName().equals(name)) return Boolean.FALSE;
		checkIsNameUnique(name);
		room.setName(name);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(ROOM_ADD_SECTION)
	public Long addSection(Long roomId) {
		Room room = roomDao.findById(roomId);
		RoomSection section = new RoomSection(room);
		room.addSection(section);
		roomDao.flush();
		return section.getId();
	}

	@Override
	@RequiresPermissions(ROOM_REMOVE_SECTION)
	public Boolean removeSection(Long roomId, Long sectionId) {
		Room room = roomDao.findById(roomId);
		RoomSection section = roomSectionDao.findById(sectionId);
		if (!roomSectionDao.isReferenced(sectionId)) return room.removeSection(section);
		return Boolean.FALSE;
	}

	@Override
	@RequiresPermissions(ROOM_FIND_ROOM_SECTION_BY_ID)
	public ReadOnlyRoomSection findRoomSectionById(Long roomSectionId) {
		return roomSectionDao.findById(roomSectionId);
	}

	private void checkIsNameUnique(String name) {
		if (!roomDao.isNameUnique(name)) throw new LucasException("The name is used by another room");
	}

	@Override
	@RequiresPermissions(ROOM_FIND_ROOM_SECTION_BY_DATA)
	public List<? extends ReadOnlyRoomSection> getRoomSectionsByData(String data, Integer resultsCount) {
		return roomDao.getRoomsFromData(data, resultsCount);
	}

	@Override
	@RequiresPermissions(ROOM_REMOVE)
	public Boolean removeRoom(Long roomId) {
		Room room = roomDao.findById(roomId);
		for (RoomSection section : room.getSections()) {
			if (roomSectionDao.isReferenced(section.getId())) return Boolean.FALSE;
		}
		roomDao.delete(room);
		return Boolean.TRUE;
	}

}
