package florian_haas.lucas.model;

public interface ReadOnlyRoomSection extends ReadOnlyEntity {

	public ReadOnlyRoom getRoom();

	public ReadOnlyCompany getCompany();

	public Integer getSectionIndex();

}
