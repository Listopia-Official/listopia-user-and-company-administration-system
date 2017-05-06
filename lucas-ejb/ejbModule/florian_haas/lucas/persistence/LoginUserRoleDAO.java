package florian_haas.lucas.persistence;

import java.util.*;

import florian_haas.lucas.model.LoginUserRole;
import florian_haas.lucas.persistence.EnumQueryComparator;

public interface LoginUserRoleDAO extends DAO<LoginUserRole> {

	public Boolean isNameUnique(String name);

	public LoginUserRole findByName(String name);

	public Boolean isUsed(Long id);

	public List<LoginUserRole> findLoginUserRoles(Long id, String name, Set<String> permissions, Boolean useId, Boolean useName,
			Boolean usePermissions, EnumQueryComparator idComparator, EnumQueryComparator nameComparator, EnumQueryComparator permissionsComparator);
}
