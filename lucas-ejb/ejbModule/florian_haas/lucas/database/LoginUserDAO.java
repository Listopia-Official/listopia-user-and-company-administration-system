package florian_haas.lucas.database;

import java.util.List;

import florian_haas.lucas.model.LoginUser;

public interface LoginUserDAO extends DAO<LoginUser> {

	public List<LoginUser> findLoginUsers(Long id, String username, Long userId, List<Long> roleIds, Boolean useId, Boolean useUsername,
			Boolean useUserId, Boolean useRoleIds, EnumQueryComparator idComparator, EnumQueryComparator usernameComparator,
			EnumQueryComparator userIdComparator, EnumQueryComparator roleIdsComparator);

}
