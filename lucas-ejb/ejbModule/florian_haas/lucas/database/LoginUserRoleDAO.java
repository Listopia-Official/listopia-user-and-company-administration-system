package florian_haas.lucas.database;

import java.util.*;

import florian_haas.lucas.model.LoginUserRole;

public interface LoginUserRoleDAO extends DAO<LoginUserRole> {

	public LoginUserRole findByName(String name);

	public List<LoginUserRole> findLoginUserRoles(Long id, String name, Set<String> permissions, Boolean useId, Boolean useName,
			Boolean usePermissions, EnumQueryComparator idComparator, EnumQueryComparator nameComparator, EnumQueryComparator permissionsComparator);
}
