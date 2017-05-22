package florian_haas.lucas.persistence;

import java.util.List;

import florian_haas.lucas.model.LoginUser;

public interface LoginUserDAO extends DAO<LoginUser> {

	public List<LoginUser> findLoginUsers(Long id, String username, Long userId, List<Long> roleIds, Boolean useId, Boolean useUsername,
			Boolean useUserId, Boolean useRoleIds, EnumQueryComparator idComparator, EnumQueryComparator usernameComparator,
			EnumQueryComparator userIdComparator, EnumQueryComparator roleIdsComparator);

	public LoginUser findByUsername(String username);

	public Boolean isUsernameUnique(String username);

	public Boolean isReferencedUserUnique(Long referencedUserId);

	public List<LoginUser> getLoginUsersFromData(String data, Integer resultsCount);

	public Boolean isBoundLoginUser(Long loginUserId);
}
