package florian_haas.lucas.business;

import java.util.List;

import javax.ejb.Local;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.NotBlank;

import florian_haas.lucas.database.*;
import florian_haas.lucas.database.validation.QueryComparator;
import florian_haas.lucas.model.*;
import florian_haas.lucas.model.validation.ValidEntityId;

@Local
public interface RoomBeanLocal {

	public Long createRoom(@NotBlank String name, @NotNull @Min(0) @Max(25) Integer sectionCount);

	public List<Room> findAll();

	public Room findById(@ValidEntityId(entityClass = Room.class) Long roomId);

	public List<Room> findRooms(@NotNull Long roomId, @NotNull String name, @NotNull Long roomSectionId, @NotNull Boolean useRoomId,
			@NotNull Boolean useName, @NotNull Boolean useRoomSectionId,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator roomIdComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.TEXT) EnumQueryComparator roomNameComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator roomSectionIdComparator);

	public Boolean setName(@ValidEntityId(entityClass = Room.class) Long roomId, String name);

	public Long addSection(@ValidEntityId(entityClass = Room.class) Long roomId);

	public Boolean removeSection(@ValidEntityId(entityClass = Room.class) Long roomId,
			@ValidEntityId(entityClass = RoomSection.class) Long sectionId);

	public RoomSection findRoomSectionById(@ValidEntityId(entityClass = RoomSection.class) Long roomSectionId);

}
