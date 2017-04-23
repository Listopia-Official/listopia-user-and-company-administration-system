package florian_haas.lucas.database.impl;

import javax.persistence.criteria.*;

import florian_haas.lucas.database.*;
import florian_haas.lucas.model.*;

@JPADAO
public class RoomSectionDAOImpl extends DAOImpl<RoomSection> implements RoomSectionDAO {

	@Override
	public Boolean isCompanyUnique(Long companyId) {
		return isUnique(companyId, root -> {
			return root.join(RoomSection_.company).get(Company_.id);
		});
	}

	@Override
	public Boolean isReferenced(Long roomSectionId) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Company> company = query.from(Company.class);
		Path<Long> roomSectionIdPath = company.join(Company_.section).get(RoomSection_.id);
		query.select(builder.count(roomSectionIdPath)).where(builder.equal(roomSectionIdPath, roomSectionId));
		return manager.createQuery(query).getSingleResult() > 0;
	}

}
