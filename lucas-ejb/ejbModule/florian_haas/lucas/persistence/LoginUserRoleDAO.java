package florian_haas.lucas.persistence;

import java.util.*;

import florian_haas.lucas.model.LoginUserRole;

public interface LoginUserRoleDAO extends DAO<LoginUserRole> {

	public Boolean isNameUnique(String name);

	public LoginUserRole findByName(String name);

	public Boolean isUsed(Long id);

	public List<LoginUserRole> findLoginUserRoles(Long id, String name, Set<String> permissions, Long loginUserId, Boolean useId, Boolean useName,
			Boolean usePermissions, Boolean useLoginUserId, EnumQueryComparator idComparator, EnumQueryComparator nameComparator,
			EnumQueryComparator permissionsComparator, EnumQueryComparator loginUserIdComparator);
}
