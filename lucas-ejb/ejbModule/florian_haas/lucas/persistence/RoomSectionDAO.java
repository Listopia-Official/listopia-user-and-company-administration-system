package florian_haas.lucas.persistence;

import florian_haas.lucas.model.RoomSection;

public interface RoomSectionDAO extends DAO<RoomSection> {

	public Boolean isCompanyUnique(Long companyId);

	public Boolean isReferenced(Long roomSectionId);

}
