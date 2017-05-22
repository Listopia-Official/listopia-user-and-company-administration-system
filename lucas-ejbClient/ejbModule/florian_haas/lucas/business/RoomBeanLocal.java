package florian_haas.lucas.business;

import java.util.List;

import javax.ejb.Local;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.NotBlank;

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;
import florian_haas.lucas.validation.*;

@Local
public interface RoomBeanLocal {

	public Long createRoom(@NotBlank String name, @NotNull @Min(0) @Max(25) Integer sectionCount);

	public List<? extends ReadOnlyRoom> findAll();

	public ReadOnlyRoom findById(@ValidEntityId(entityClass = ReadOnlyRoom.class) Long roomId);

	public List<? extends ReadOnlyRoom> findRooms(@NotNull Long roomId, @NotNull String name, Long roomSectionId, @NotNull Boolean useRoomId,
			@NotNull Boolean useName, @NotNull Boolean useRoomSectionId,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator roomIdComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.TEXT) EnumQueryComparator roomNameComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.LOGIC) EnumQueryComparator roomSectionIdComparator);

	public Boolean setName(@ValidEntityId(entityClass = ReadOnlyRoom.class) Long roomId, String name);

	public Long addSection(@ValidEntityId(entityClass = ReadOnlyRoom.class) Long roomId);

	public Boolean removeSection(@ValidEntityId(entityClass = ReadOnlyRoom.class) Long roomId,
			@ValidEntityId(entityClass = ReadOnlyRoomSection.class) Long sectionId);

	public ReadOnlyRoomSection findRoomSectionById(@ValidEntityId(entityClass = ReadOnlyRoomSection.class) Long roomSectionId);

	public List<? extends ReadOnlyRoomSection> getRoomSectionsByData(@NotNull String data, @NotNull @Min(1) Integer resultsCount);

}
