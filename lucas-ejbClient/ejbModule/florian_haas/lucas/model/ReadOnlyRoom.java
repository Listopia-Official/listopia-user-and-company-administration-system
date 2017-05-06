package florian_haas.lucas.model;

import java.util.List;

public interface ReadOnlyRoom extends ReadOnlyEntity {

	public String getName();

	public List<? extends ReadOnlyRoomSection> getSections();

}
